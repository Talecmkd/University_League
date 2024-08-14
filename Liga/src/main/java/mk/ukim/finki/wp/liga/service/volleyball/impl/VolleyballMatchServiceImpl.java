package mk.ukim.finki.wp.liga.service.volleyball.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.liga.model.Exceptions.InvalidVolleyballMatchException;
import mk.ukim.finki.wp.liga.model.Exceptions.InvalidVolleyballTeamException;
import mk.ukim.finki.wp.liga.model.VolleyballMatch;
import mk.ukim.finki.wp.liga.model.VolleyballPlayer;
import mk.ukim.finki.wp.liga.model.VolleyballTeam;
import mk.ukim.finki.wp.liga.repository.volleyball.VolleyballMatchRepository;
import mk.ukim.finki.wp.liga.repository.volleyball.VolleyballPlayerRepository;
import mk.ukim.finki.wp.liga.repository.volleyball.VolleyballTeamRepository;
import mk.ukim.finki.wp.liga.service.volleyball.VolleyballMatchService;
import mk.ukim.finki.wp.liga.service.volleyball.VolleyballTeamService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class VolleyballMatchServiceImpl implements VolleyballMatchService {
    private final VolleyballMatchRepository matchRepository;
    private final VolleyballTeamRepository teamRepository;
    private final VolleyballPlayerRepository playerRepository;
    private final VolleyballTeamService teamService;

    @Override
    @Transactional(readOnly = true)
    public List<VolleyballMatch> listAllVolleyballMatches() {
        return matchRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public VolleyballMatch findById(Long id) {
        return matchRepository.findById(id).orElseThrow(InvalidVolleyballMatchException::new);
    }

    @Override
    @Transactional
    public VolleyballMatch create(VolleyballTeam homeTeam, VolleyballTeam awayTeam, int homeTeamPoints, int awayTeamPoints, LocalDateTime startTime) {
        VolleyballTeam home = teamRepository.findById(homeTeam.getVolleyball_team_id()).orElseThrow(InvalidVolleyballTeamException::new);
        VolleyballTeam away = teamRepository.findById(awayTeam.getVolleyball_team_id()).orElseThrow(InvalidVolleyballMatchException::new);
        VolleyballMatch vm = new VolleyballMatch(home, away, homeTeamPoints, awayTeamPoints, startTime, false);
        return matchRepository.save(vm);
    }

    @Override
    @Transactional
    public VolleyballMatch update(Long id, VolleyballTeam homeTeam, VolleyballTeam awayTeam, int homeTeamPoints, int awayTeamPoints, LocalDateTime startTime) {
        VolleyballTeam home = teamRepository.findById(homeTeam.getVolleyball_team_id()).orElseThrow(InvalidVolleyballTeamException::new);
        VolleyballTeam away = teamRepository.findById(awayTeam.getVolleyball_team_id()).orElseThrow(InvalidVolleyballMatchException::new);
        VolleyballMatch vm = matchRepository.findById(id).orElseThrow(InvalidVolleyballMatchException::new);
        vm.setHomeTeam(home);
        vm.setAwayTeam(away);
        vm.setHomeTeamPoints(homeTeamPoints);
        vm.setAwayTeamPoints(awayTeamPoints);
        vm.setStartTime(startTime);
        return matchRepository.save(vm);
    }

    @Override
    @Transactional
    public VolleyballMatch delete(Long id) {
        VolleyballMatch vm = matchRepository.findById(id).orElseThrow(InvalidVolleyballMatchException::new);
        if(vm.getEndTime().isBefore(LocalDateTime.now())){
            vm.getHomeTeam().getVolleyballResults().remove(vm);
            vm.getAwayTeam().getVolleyballResults().remove(vm);
        }
        else {
            vm.getHomeTeam().getVolleyballFixtures().remove(vm);
            vm.getAwayTeam().getVolleyballFixtures().remove(vm);
        }
        matchRepository.delete(vm);
        return vm;
    }

    @Override
    @Transactional
    public void updateTeamStatistics(VolleyballMatch match) {
        teamService.updateStats(match.getHomeTeam().getVolleyball_team_id());
        teamService.updateStats(match.getAwayTeam().getVolleyball_team_id());
    }

    @Override
    @Transactional
    public VolleyballMatch createAndAddToFixtures(VolleyballTeam homeTeam, VolleyballTeam awayTeam, int homeTeamPoints, int awayTeamPoints, LocalDateTime startTime) {
        VolleyballMatch match = this.create(homeTeam, awayTeam, homeTeamPoints, awayTeamPoints, startTime);

        if(startTime.plusHours(2).isAfter(LocalDateTime.now())) {
            homeTeam.getVolleyballFixtures().add(match);
            awayTeam.getVolleyballFixtures().add(match);
        } else if(startTime.plusHours(2).isBefore(LocalDateTime.now())){
            homeTeam.getVolleyballResults().add(match);
            awayTeam.getVolleyballResults().add(match);
        }

        teamRepository.save(homeTeam);
        teamRepository.save(awayTeam);

        return match;
    }

    @Override
    @Transactional
    public List<VolleyballMatch> createPlayoffMatches() {
        List<VolleyballMatch> existingPlayoffMatches = matchRepository.findAllByIsVolleyballPlayoffMatchTrue();

        // If matches exist, don't reinitialize, just return the existing ones
        if (!existingPlayoffMatches.isEmpty()) {
            return existingPlayoffMatches;
        }
        List<VolleyballTeam> teams = teamRepository.findAllByOrderByTeamLeaguePointsDesc();

        if (teams.size() < 8) {
            throw new RuntimeException("Not enough teams for playoffs");
        }

        // Clear existing playoff matches if needed
        matchRepository.deleteAllByIsVolleyballPlayoffMatchTrue();

        // Create the quarter-final matches
        List<VolleyballMatch> matches = new ArrayList<>();
        matches.add(createMatch(teams.get(0), teams.get(7), true)); // 1 vs 8
        matches.add(createMatch(teams.get(1), teams.get(6), true)); // 2 vs 7
        matches.add(createMatch(teams.get(2), teams.get(5), true)); // 3 vs 6
        matches.add(createMatch(teams.get(3), teams.get(4), true)); // 4 vs 5

        return matches;
    }

    private VolleyballMatch createMatch(VolleyballTeam homeTeam, VolleyballTeam awayTeam, boolean isPlayoffMatch) {
        LocalDateTime startTime = LocalDateTime.now().plusDays(1); // Set the start time for the match
        VolleyballMatch match = new VolleyballMatch(homeTeam, awayTeam, 0, 0, startTime, isPlayoffMatch);
        return matchRepository.save(match);
    }

    @Override
    //@Transactional(readOnly = true)
    public List<VolleyballMatch> listPlayoffMatches() {
        List<VolleyballTeam> teams = teamRepository.findAllByOrderByTeamLeaguePointsDesc();

        if (teams.size() < 8) {
            throw new RuntimeException("Not enough teams for playoffs");
        }

        // Fetch all matches involving the top 8 teams
        return matchRepository.findAllByIsVolleyballPlayoffMatchTrue();
    }

    @Override
    @Transactional
    public VolleyballMatch updatePlayoffMatchPoints(Long id, VolleyballTeam homeTeam, VolleyballTeam awayTeam, int homeTeamPoints, int awayTeamPoints) {
        VolleyballMatch match = matchRepository.findById(id).orElseThrow(InvalidVolleyballMatchException::new);
        match.setHomeTeam(homeTeam);
        match.setAwayTeam(awayTeam);
        match.setHomeTeamPoints(homeTeamPoints);
        match.setAwayTeamPoints(awayTeamPoints);
        return matchRepository.save(match);
    }

    private VolleyballTeam getWinner(VolleyballMatch match) {
        if (match.getHomeTeamPoints() > match.getAwayTeamPoints()) {
            return match.getHomeTeam();
        } else if (match.getAwayTeamPoints() > match.getHomeTeamPoints()) {
            return match.getAwayTeam();
        } else {
            throw new RuntimeException("Match cannot end in a draw in playoffs");
        }
    }

    private boolean isMatchCompleted(VolleyballMatch match) {
        return match.getHomeTeamPoints() != match.getAwayTeamPoints();
    }

    @Override
    @Transactional
    public List<VolleyballMatch> createSemiFinalMatches() {
        List<VolleyballMatch> playoffMatches = matchRepository.findAllByIsVolleyballPlayoffMatchTrue();

        // Check if the final match already exists (total playoff matches should be 7 if final is created)
        if (playoffMatches.size() >= 6) {
            //System.out.println("Semi-final matches have already been created.");
            return playoffMatches.subList(4, 6); // Return existing final match
        }
        List<VolleyballMatch> quarterFinals = matchRepository.findAllByIsVolleyballPlayoffMatchTrue();

        // Ensure all quarter-final matches are completed
        if (quarterFinals.size() != 4 || quarterFinals.stream().anyMatch(match -> !isMatchCompleted(match))) {
            throw new RuntimeException("Quarter-finals are not fully completed yet.");
        }

        // Determine the winners of the quarter-finals
        VolleyballTeam semiFinalTeam1 = getWinner(quarterFinals.get(0));
        VolleyballTeam semiFinalTeam2 = getWinner(quarterFinals.get(1));
        VolleyballTeam semiFinalTeam3 = getWinner(quarterFinals.get(2));
        VolleyballTeam semiFinalTeam4 = getWinner(quarterFinals.get(3));

        // Create semi-final matches
        List<VolleyballMatch> semiFinals = new ArrayList<>();
        semiFinals.add(createMatch(semiFinalTeam1, semiFinalTeam4, true));
        semiFinals.add(createMatch(semiFinalTeam2, semiFinalTeam3, true));

        return semiFinals;
    }

    @Override
    @Transactional
    public List<VolleyballMatch> createFinalMatch() {
        List<VolleyballMatch> playoffMatches = matchRepository.findAllByIsVolleyballPlayoffMatchTrue();

        // Check if the final match already exists (total playoff matches should be 7 if final is created)
        if (playoffMatches.size() >= 7) {
            //System.out.println("The final match has already been created.");
            return playoffMatches.subList(6, 7); // Return existing final match
        }
        List<VolleyballMatch> semiFinals = matchRepository.findAllByIsVolleyballPlayoffMatchTrue();

        // Ensure all quarter-final matches are completed
        if (semiFinals.size() < 6) {
            throw new RuntimeException("There are not enough matches to have semi-finals and finals.");
        }
        VolleyballMatch semiFinal1 = semiFinals.get(semiFinals.size() - 2);
        VolleyballMatch semiFinal2 = semiFinals.get(semiFinals.size() - 1);

        // Ensure both semi-final matches are completed
        if (!isMatchCompleted(semiFinal1) || !isMatchCompleted(semiFinal2)) {
            throw new RuntimeException("Semi-finals are not fully completed yet.");
        }

        // Determine the winners of the quarter-finals
        VolleyballTeam finalist1 = getWinner(semiFinal1);
        VolleyballTeam finalist2 = getWinner(semiFinal2);

        // Create the final match
        List<VolleyballMatch> finals = new ArrayList<>();
        finals.add(createMatch(finalist1, finalist2, true));

        return finals;
    }

    @Override
    public void updateLiveStats(Long volleyballMatchId, int pointsScored, Long playerId) {
        VolleyballPlayer player = playerRepository.getReferenceById(playerId);
        VolleyballMatch match = matchRepository.findById(volleyballMatchId).get();
        VolleyballTeam team = teamRepository.findAll().stream().filter(t -> t.getPlayers().contains(player)).findFirst().get();

        VolleyballTeam homeOrAway = match.getHomeTeam();

        if (team != homeOrAway) {
            match.setAwayTeamPoints(pointsScored);
        } else {
            match.setHomeTeamPoints(pointsScored);
        }

        matchRepository.save(match);
    }
}
