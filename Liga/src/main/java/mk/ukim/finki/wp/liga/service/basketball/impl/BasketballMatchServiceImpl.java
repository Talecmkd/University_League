package mk.ukim.finki.wp.liga.service.basketball.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.liga.model.*;
import mk.ukim.finki.wp.liga.model.Exceptions.InvalidBasketballMatchException;
import mk.ukim.finki.wp.liga.model.Exceptions.InvalidBasketballTeamException;
import mk.ukim.finki.wp.liga.model.Exceptions.InvalidFootballMatchException;
import mk.ukim.finki.wp.liga.repository.basketball.BasketballMatchRepository;
import mk.ukim.finki.wp.liga.repository.basketball.BasketballPlayerRepository;
import mk.ukim.finki.wp.liga.repository.basketball.BasketballTeamRepository;
import mk.ukim.finki.wp.liga.service.basketball.BasketballMatchService;
import mk.ukim.finki.wp.liga.service.basketball.BasketballTeamService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BasketballMatchServiceImpl implements BasketballMatchService {

    private final BasketballMatchRepository basketballMatchRepository;
    private final BasketballTeamRepository basketballTeamRepository;
    private final BasketballPlayerRepository basketballPlayerRepository;
    private final BasketballTeamService basketballTeamService;


    @Override
    @Transactional(readOnly = true)
    public List<BasketballMatch> listAllBasketballMatches() {
        return basketballMatchRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public BasketballMatch findById(Long id) {
        return basketballMatchRepository.findById(id).orElseThrow(InvalidBasketballMatchException::new);
    }

    @Override
    @Transactional
    public BasketballMatch create(BasketballTeam homeTeam, BasketballTeam awayTeam, int homeTeamPoints, int awayTeamPoints, LocalDateTime startTime) {
        BasketballTeam home = basketballTeamRepository.findById(homeTeam.getId()).orElseThrow(InvalidBasketballTeamException::new);
        BasketballTeam away = basketballTeamRepository.findById(awayTeam.getId()).orElseThrow(InvalidBasketballTeamException::new);
        BasketballMatch bm = new BasketballMatch(home, away, homeTeamPoints, awayTeamPoints, startTime, false);
        return basketballMatchRepository.save(bm);
    }

    @Override
    @Transactional
    public BasketballMatch update(Long id, BasketballTeam homeTeam, BasketballTeam awayTeam, int homeTeamPoints, int awayTeamPoints, LocalDateTime startTime) {
        BasketballMatch bm = this.findById(id);
        BasketballTeam home = basketballTeamRepository.findById(homeTeam.getId()).orElseThrow(InvalidBasketballTeamException::new);
        BasketballTeam away = basketballTeamRepository.findById(awayTeam.getId()).orElseThrow(InvalidBasketballTeamException::new);
        bm.setHomeTeam(home);
        bm.setAwayTeam(away);
        bm.setHomeTeamPoints(homeTeamPoints);
        bm.setAwayTeamPoints(awayTeamPoints);
        bm.setStartTime(startTime);
        return basketballMatchRepository.save(bm);
    }

    @Override
    @Transactional
    public BasketballMatch delete(Long id) {
        BasketballMatch bm = basketballMatchRepository.findById(id).orElseThrow(InvalidBasketballMatchException::new);
        if(bm.getEndTime().isBefore(LocalDateTime.now())){
            bm.getHomeTeam().getBasketballResults().remove(bm);
            bm.getAwayTeam().getBasketballResults().remove(bm);
        }
        else {
            bm.getHomeTeam().getBasketballFixtures().remove(bm);
            bm.getAwayTeam().getBasketballFixtures().remove(bm);
        }
        basketballMatchRepository.delete(bm);
        return bm;
    }

    @Override
    @Transactional
    public void updateTeamStatistics(BasketballMatch match) {
        basketballTeamService.updateStats(match.getHomeTeam().getId());
        basketballTeamService.updateStats(match.getAwayTeam().getId());
    }

    @Override
    @Transactional
    public BasketballMatch createAndAddToFixtures(BasketballTeam homeTeam, BasketballTeam awayTeam, int homeTeamPoints, int awayTeamPoints, LocalDateTime startTime) {
        BasketballMatch match = this.create(homeTeam, awayTeam, homeTeamPoints, awayTeamPoints, startTime);

        if(startTime.plusHours(2).isAfter(LocalDateTime.now())) {
            homeTeam.getBasketballFixtures().add(match);
            awayTeam.getBasketballFixtures().add(match);
        } else if(startTime.plusHours(2).isBefore(LocalDateTime.now())){
            homeTeam.getBasketballResults().add(match);
            awayTeam.getBasketballResults().add(match);
        }


        basketballTeamRepository.save(homeTeam);
        basketballTeamRepository.save(awayTeam);

        return match;
    }

    @Override
    @Transactional
    public List<BasketballMatch> createPlayoffMatches() {
        List<BasketballMatch> existingPlayoffMatches = basketballMatchRepository.findAllByIsPlayoffMatchTrue();

        // If matches exist, don't reinitialize, just return the existing ones
        if (!existingPlayoffMatches.isEmpty()) {
            return existingPlayoffMatches;
        }
        List<BasketballTeam> teams = basketballTeamRepository.findAllByOrderByTeamLeaguePointsDesc();

        if (teams.size() < 8) {
            throw new RuntimeException("Not enough teams for playoffs to be created");
        }

        // Clear existing playoff matches if needed
        basketballMatchRepository.deleteAllByIsPlayoffMatchTrue();

        // Create the quarter-final matches
        List<BasketballMatch> matches = new ArrayList<>();
        matches.add(createMatch(teams.get(0), teams.get(7), true)); // 1 vs 8
        matches.add(createMatch(teams.get(1), teams.get(6), true)); // 2 vs 7
        matches.add(createMatch(teams.get(2), teams.get(5), true)); // 3 vs 6
        matches.add(createMatch(teams.get(3), teams.get(4), true)); // 4 vs 5

        return matches;
    }

    private BasketballMatch createMatch(BasketballTeam homeTeam, BasketballTeam awayTeam, boolean isPlayoffMatch) {
        LocalDateTime startTime = LocalDateTime.now().plusDays(1); // Set the start time for the match
        BasketballMatch match = new BasketballMatch(homeTeam, awayTeam, 0, 0, startTime, isPlayoffMatch);
        return basketballMatchRepository.save(match);
    }

    @Override
    public List<BasketballMatch> listPlayoffMatches() {
        List<BasketballTeam> teams = basketballTeamRepository.findAllByOrderByTeamLeaguePointsDesc();

        if (teams.size() < 8) {
            throw new RuntimeException("Not enough teams for playoffs to be created");
        }

        // Fetch all matches involving the top 8 teams
        return basketballMatchRepository.findAllByIsPlayoffMatchTrue();
    }

    @Override
    @Transactional
    public BasketballMatch updatePlayoffMatchPoints(Long id, BasketballTeam homeTeam, BasketballTeam awayTeam, int homeTeamPoints, int awayTeamPoints) {
        BasketballMatch match = basketballMatchRepository.findById(id).orElseThrow(InvalidBasketballMatchException::new);
        match.setHomeTeam(homeTeam);
        match.setAwayTeam(awayTeam);
        match.setHomeTeamPoints(homeTeamPoints);
        match.setAwayTeamPoints(awayTeamPoints);
        return basketballMatchRepository.save(match);
    }

    private BasketballTeam getWinner(BasketballMatch match) {
        if (match.getHomeTeamPoints() > match.getAwayTeamPoints()) {
            return match.getHomeTeam();
        } else if (match.getAwayTeamPoints() > match.getHomeTeamPoints()) {
            return match.getAwayTeam();
        } else {
            throw new RuntimeException("Match cannot end in a draw in playoffs");
        }
    }
    private boolean isMatchCompleted(BasketballMatch match) {
        return match.getHomeTeamPoints() != match.getAwayTeamPoints();
    }

    @Override
    @Transactional
    public List<BasketballMatch> createSemiFinalMatches() {
        List<BasketballMatch> playoffMatches = basketballMatchRepository.findAllByIsPlayoffMatchTrue();

        // Check if the final match already exists (total playoff matches should be 7 if final is created)
        if (playoffMatches.size() >= 6) {
            //System.out.println("Semi-final matches have already been created.");
            return playoffMatches.subList(4, 6); // Return existing final match
        }
        List<BasketballMatch> quarterFinals = basketballMatchRepository.findAllByIsPlayoffMatchTrue();

        // Ensure all quarter-final matches are completed
        if (quarterFinals.size() != 4 || quarterFinals.stream().anyMatch(match -> !isMatchCompleted(match))) {
            throw new RuntimeException("Quarter-finals are not fully completed yet.");
        }

        // Determine the winners of the quarter-finals
        BasketballTeam semiFinalTeam1 = getWinner(quarterFinals.get(0));
        BasketballTeam semiFinalTeam2 = getWinner(quarterFinals.get(1));
        BasketballTeam semiFinalTeam3 = getWinner(quarterFinals.get(2));
        BasketballTeam semiFinalTeam4 = getWinner(quarterFinals.get(3));

        // Create semi-final matches
        List<BasketballMatch> semiFinals = new ArrayList<>();
        semiFinals.add(createMatch(semiFinalTeam1, semiFinalTeam4, true));
        semiFinals.add(createMatch(semiFinalTeam2, semiFinalTeam3, true));

        return semiFinals;
    }

    @Override
    @Transactional
    public List<BasketballMatch> createFinalMatch() {
        List<BasketballMatch> playoffMatches = basketballMatchRepository.findAllByIsPlayoffMatchTrue();

        // Check if the final match already exists (total playoff matches should be 7 if final is created)
        if (playoffMatches.size() >= 7) {
            //System.out.println("The final match has already been created.");
            return playoffMatches.subList(6, 7); // Return existing final match
        }
        List<BasketballMatch> semiFinals = basketballMatchRepository.findAllByIsPlayoffMatchTrue();

        // Ensure all quarter-final matches are completed
        if (semiFinals.size() < 6) {
            throw new RuntimeException("There are not enough matches to have semi-finals and finals.");
        }
        BasketballMatch semiFinal1 = semiFinals.get(semiFinals.size() - 2);
        BasketballMatch semiFinal2 = semiFinals.get(semiFinals.size() - 1);

        // Ensure both semi-final matches are completed
        if (!isMatchCompleted(semiFinal1) || !isMatchCompleted(semiFinal2)) {
            throw new RuntimeException("Semi-finals are not fully completed yet.");
        }

        // Determine the winners of the quarter-finals
        BasketballTeam finalist1 = getWinner(semiFinal1);
        BasketballTeam finalist2 = getWinner(semiFinal2);

        // Create the final match
        List<BasketballMatch> finals = new ArrayList<>();
        finals.add(createMatch(finalist1, finalist2, true));

        return finals;
    }

    @Override
    public void updateLiveStats(Long basketballMatchId, int pointsScored, Long playerId) {
        BasketballPlayer player = basketballPlayerRepository.getReferenceById(playerId);
        BasketballMatch match = basketballMatchRepository.findById(basketballMatchId).get();
        BasketballTeam team = basketballTeamRepository.findAll().stream().filter(t -> t.getPlayers().contains(player)).findFirst().get();

        BasketballTeam homeOrAway = match.getHomeTeam();

        if (team != homeOrAway) {
            match.setAwayTeamPoints(pointsScored);
        } else {
            match.setHomeTeamPoints(pointsScored);
        }

        basketballMatchRepository.save(match);
    }
}
