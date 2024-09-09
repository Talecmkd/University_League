package mk.ukim.finki.wp.liga.service.volleyball.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.liga.model.Exceptions.InvalidVolleyballTeamException;
import mk.ukim.finki.wp.liga.model.VolleyballMatch;
import mk.ukim.finki.wp.liga.model.VolleyballPlayer;
import mk.ukim.finki.wp.liga.model.VolleyballTeam;
import mk.ukim.finki.wp.liga.model.dtos.VolleyBallStandings;
import mk.ukim.finki.wp.liga.repository.volleyball.VolleyballMatchRepository;
import mk.ukim.finki.wp.liga.repository.volleyball.VolleyballPlayerRepository;
import mk.ukim.finki.wp.liga.repository.volleyball.VolleyballTeamRepository;
import mk.ukim.finki.wp.liga.service.volleyball.VolleyballTeamService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VolleyballTeamServiceImpl implements VolleyballTeamService {
    private final VolleyballTeamRepository volleyballTeamRepository;
    private final VolleyballPlayerRepository volleyballPlayerRepository;
    private final VolleyballMatchRepository volleyballMatchRepository;

    @Override
    @Transactional(readOnly = true)
    public List<VolleyballTeam> listAllTeams() {
        return volleyballTeamRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public VolleyballTeam findById(Long id) {
        return volleyballTeamRepository.findById(id).orElseThrow(InvalidVolleyballTeamException::new);
    }

    @Override
    @Transactional
    public VolleyballTeam create(String teamName, byte[] logo) {
        VolleyballTeam team = new VolleyballTeam(teamName, logo);
        return volleyballTeamRepository.save(team);
    }

    @Override
    @Transactional
    public VolleyballTeam update(Long id, String teamName, List<VolleyballPlayer> players, byte[] logo) {
        VolleyballTeam team = volleyballTeamRepository.findById(id).orElseThrow(InvalidVolleyballTeamException::new);
        team.setTeamName(teamName);
        team.setPlayers(players);
        team.setLogo(logo);
        return volleyballTeamRepository.save(team);
    }

    @Override
    @Transactional
    public VolleyballTeam delete(Long id) {
        VolleyballTeam team = volleyballTeamRepository.findById(id).orElseThrow(InvalidVolleyballTeamException::new);
        volleyballTeamRepository.delete(team);
        return team;
    }

    @Override
    @Transactional
    public VolleyballTeam addFixtures(Long id, List<VolleyballMatch> fixtures) {
        VolleyballTeam team = volleyballTeamRepository.findById(id).orElseThrow(InvalidVolleyballTeamException::new);

        team.getVolleyballFixtures().addAll(fixtures);
        return volleyballTeamRepository.save(team);
    }

    @Override
    @Transactional
    public VolleyballTeam updateStats(Long id) {
        VolleyballTeam team = volleyballTeamRepository.findById(id).orElseThrow(InvalidVolleyballTeamException::new);
        int wins = 0;
        int loses = 0;
        int leaguePoints = 0;

        for (VolleyballMatch match : team.getVolleyballResults()) {
            if (match.getEndTime().isBefore(java.time.LocalDateTime.now())) { // Match has been played
                boolean isHomeTeam = match.getHomeTeam().equals(team);
                int teamPoints = isHomeTeam ? match.getHomeTeamPoints() : match.getAwayTeamPoints();
                int opponentPoints = isHomeTeam ? match.getAwayTeamPoints() : match.getHomeTeamPoints();

                if (teamPoints > opponentPoints) {
                    wins++;
                    leaguePoints += 2; // Assuming a win gives 2 points
                } else if (teamPoints < opponentPoints) {
                    loses++;
                }
            }
        }

        team.setTeamMatchesPlayed(wins + loses);
        team.setTeamWins(wins);
        team.setTeamLoses(loses);
        team.setTeamLeaguePoints(leaguePoints);

        return volleyballTeamRepository.save(team);
    }

    @Override
    @Transactional(readOnly = true)
    public VolleyballTeam findByName(String teamName) {
        return volleyballTeamRepository.findVolleyballTeamByTeamName(teamName).get(0);
    }

    @Override
    @Transactional
    public VolleyballTeam saveTable(Long id, int teamPoints) {
        VolleyballTeam vt = volleyballTeamRepository.findById(id).orElseThrow(InvalidVolleyballTeamException::new);
        vt.setTeamLeaguePoints(teamPoints);
        return volleyballTeamRepository.save(vt);
    }

    @Override
    @Transactional
    public List<VolleyballTeam> findAllOrderByPointsDesc() {
        return volleyballTeamRepository.findAllByOrderByTeamLeaguePointsDesc();
    }
    @Override
    @Transactional
    public List<VolleyBallStandings> getStandings() {
        List<VolleyballTeam> teams = volleyballTeamRepository.findAll();

        return teams.stream()
                .map(team -> new VolleyBallStandings(
                        team.getTeamName(),
                        team.getTeamMatchesPlayed(),
                        team.getTeamWins(),
                        team.getTeamLoses(),
                        team.getTeamLeaguePoints()))
                .sorted(Comparator.comparingInt(VolleyBallStandings::getWins).reversed())
                .collect(Collectors.toList());
    }
}
