package mk.ukim.finki.wp.liga.service.football.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.liga.model.FootballTeam;
import mk.ukim.finki.wp.liga.model.Playoff;
import mk.ukim.finki.wp.liga.model.PlayoffMatch;
import mk.ukim.finki.wp.liga.model.PlayoffStage;
import mk.ukim.finki.wp.liga.repository.football.FootballTeamRepository;
import mk.ukim.finki.wp.liga.repository.football.PlayoffMatchRepository;
import mk.ukim.finki.wp.liga.repository.football.PlayoffRepository;
import mk.ukim.finki.wp.liga.repository.football.PlayoffStageRepository;
import mk.ukim.finki.wp.liga.service.football.FootballPlayoffsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FootballPlayoffs implements FootballPlayoffsService {
    private final PlayoffMatchRepository playoffMatchRepository;
    private final PlayoffStageRepository playoffStageRepository;
    private final PlayoffRepository playoffRepository;
    private final FootballTeamRepository footballTeamRepository;

    @Override
    @Transactional
    public Playoff createPlayoff() {
        Playoff playoff = new Playoff();
        playoffRepository.save(playoff);

        List<FootballTeam> teams = footballTeamRepository.findAllByOrderByTeamLeaguePointsDesc();
        if (teams.size() < 8) {
            throw new IllegalArgumentException("At least 8 teams are required for the playoff.");
        }

        List<FootballTeam> top8Teams = teams.subList(0, 8);

        // Create quarter-finals
        PlayoffStage quarterFinals = new PlayoffStage();
        quarterFinals.setPlayoff(playoff);
        quarterFinals.setStageNumber(1);
        playoffStageRepository.save(quarterFinals);

        createPlayoffMatch(quarterFinals, top8Teams.get(0), top8Teams.get(7));
        createPlayoffMatch(quarterFinals, top8Teams.get(1), top8Teams.get(6));
        createPlayoffMatch(quarterFinals, top8Teams.get(2), top8Teams.get(5));
        createPlayoffMatch(quarterFinals, top8Teams.get(3), top8Teams.get(4));

        return playoff;
    }

    @Override
    @Transactional
    public void completeMatch(Long matchId, int homeTeamPoints, int awayTeamPoints) {
        PlayoffMatch match = playoffMatchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid match ID"));

        match.setHomeTeamPoints(homeTeamPoints);
        match.setAwayTeamPoints(awayTeamPoints);
        match.setCompleted(true);
        playoffMatchRepository.save(match);

        // Check if all matches in the current stage are completed
        PlayoffStage currentStage = match.getStage();
        if (currentStage.getMatches().stream().allMatch(PlayoffMatch::isCompleted)) {
            advanceToNextStage(currentStage);
        }
    }

    private void advanceToNextStage(PlayoffStage currentStage) {
        List<PlayoffMatch> completedMatches = currentStage.getMatches();
        List<FootballTeam> advancingTeams = new ArrayList<>();
        for (PlayoffMatch match : completedMatches) {
            if (match.getHomeTeamPoints() > match.getAwayTeamPoints()) {
                advancingTeams.add(match.getHomeTeam());
            } else {
                advancingTeams.add(match.getAwayTeam());
            }
        }

        if (advancingTeams.size() == 1) {
            return; // Final stage completed, playoff is finished
        }

        PlayoffStage nextStage = new PlayoffStage();
        nextStage.setPlayoff(currentStage.getPlayoff());
        nextStage.setStageNumber(currentStage.getStageNumber() + 1);
        playoffStageRepository.save(nextStage);

        for (int i = 0; i < advancingTeams.size(); i += 2) {
            createPlayoffMatch(nextStage, advancingTeams.get(i), advancingTeams.get(i + 1));
        }
    }

    @Override
    @Transactional
    public Playoff getPlayoff() {
        return playoffRepository.findAll().stream().findFirst().orElse(null); // Simplified for single playoff
    }

    @Override
    @Transactional
    public PlayoffMatch getMatchById(Long matchId) {
        return playoffMatchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid match ID"));
    }

    private void createPlayoffMatch(PlayoffStage stage, FootballTeam homeTeam, FootballTeam awayTeam) {
        PlayoffMatch match = new PlayoffMatch();
        match.setStage(stage);
        match.setHomeTeam(homeTeam);
        match.setAwayTeam(awayTeam);
        match.setCompleted(false);
        playoffMatchRepository.save(match);
    }
}
