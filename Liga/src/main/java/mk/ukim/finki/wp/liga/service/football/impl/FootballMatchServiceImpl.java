package mk.ukim.finki.wp.liga.service.football.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.liga.model.Exceptions.InvalidFootballMatchException;
import mk.ukim.finki.wp.liga.model.Exceptions.InvalidFootballTeamException;
import mk.ukim.finki.wp.liga.model.FootballMatch;
import mk.ukim.finki.wp.liga.model.FootballPlayerScored;
import mk.ukim.finki.wp.liga.model.FootballTeam;
import mk.ukim.finki.wp.liga.repository.football.FootballMatchRepository;
import mk.ukim.finki.wp.liga.repository.football.FootballTeamRepository;
import mk.ukim.finki.wp.liga.service.football.FootballMatchService;
import mk.ukim.finki.wp.liga.service.football.FootballTeamService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class FootballMatchServiceImpl implements FootballMatchService {
    private final FootballMatchRepository matchRepository;
    private final FootballTeamRepository teamRepository;
    private final FootballTeamService teamService;


    @Override
    @Transactional(readOnly = true)
    public List<FootballMatch> listAllFootballMatches() {
        return matchRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public FootballMatch findById(Long id) {
        return matchRepository.findById(id).orElseThrow(InvalidFootballMatchException::new);
    }

    @Override
    @Transactional
    public FootballMatch create(FootballTeam homeTeam, FootballTeam awayTeam, int homeTeamPoints, int awayTeamPoints, LocalDateTime startTime) {
        FootballTeam home = teamRepository.findById(homeTeam.getId()).orElseThrow(InvalidFootballTeamException::new);
        FootballTeam away = teamRepository.findById(awayTeam.getId()).orElseThrow(InvalidFootballMatchException::new);
        FootballMatch fm = new FootballMatch(home, away, homeTeamPoints, awayTeamPoints, startTime);
        return matchRepository.save(fm);
    }

    @Override
    @Transactional
    public FootballMatch update(Long id, FootballTeam homeTeam, FootballTeam awayTeam, int homeTeamPoints, int awayTeamPoints, LocalDateTime startTime) {
        FootballTeam home = teamRepository.findById(homeTeam.getId()).orElseThrow(InvalidFootballTeamException::new);
        FootballTeam away = teamRepository.findById(awayTeam.getId()).orElseThrow(InvalidFootballMatchException::new);
        FootballMatch fm = matchRepository.findById(id).orElseThrow(InvalidFootballMatchException::new);
        fm.setHomeTeam(home);
        fm.setAwayTeam(away);
        fm.setHomeTeamPoints(homeTeamPoints);
        fm.setAwayTeamPoints(awayTeamPoints);
        fm.setStartTime(startTime);
        return matchRepository.save(fm);
    }

    @Override
    @Transactional
    public FootballMatch delete(Long id) {
        FootballMatch fm = matchRepository.findById(id).orElseThrow(InvalidFootballMatchException::new);
        matchRepository.delete(fm);
        return fm;
    }

    @Override
    @Transactional
    public void updateTeamStatistics(FootballMatch match) {
        teamService.updateStats(match.getHomeTeam().getId());
        teamService.updateStats(match.getAwayTeam().getId());
    }

    @Override
    @Transactional
    public FootballMatch createAndAddToFixtures(FootballTeam homeTeam, FootballTeam awayTeam, int homeTeamPoints, int awayTeamPoints, LocalDateTime startTime) {
        FootballMatch match = this.create(homeTeam, awayTeam, homeTeamPoints, awayTeamPoints, startTime);

        if(startTime.isAfter(LocalDateTime.now())) {
            homeTeam.getFootballFixtures().add(match);
            awayTeam.getFootballFixtures().add(match);
        } else if(startTime.isBefore(LocalDateTime.now())){
            homeTeam.getFootballResults().add(match);
            awayTeam.getFootballResults().add(match);
        }


        teamRepository.save(homeTeam);
        teamRepository.save(awayTeam);

        return match;
    }

}
