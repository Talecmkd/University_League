package mk.ukim.finki.wp.liga.service.football.impl;

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
    private  final PlayoffStageRepository playoffStageRepository;
    private final PlayoffRepository playoffRepository;
    private final FootballTeamRepository footballTeamRepository;
    @Override
    public Playoff createPlayoff(){
        Playoff playoff = new Playoff();
        List<FootballTeam> teams = footballTeamRepository.findAllByOrderByTeamLeaguePointsDesc();

        // Assuming the top 8 teams qualify for playoffs
        if (teams.size() < 8) {
            throw new IllegalArgumentException("Not enough teams to create playoffs");
        }

        List<PlayoffStage> stages = new ArrayList<>();

        // Quarter-finals
        PlayoffStage quarterFinals = new PlayoffStage();
        quarterFinals.setPlayoff(playoff);
        quarterFinals.setStageNumber(1);

        List<PlayoffMatch> quarterFinalMatches = new ArrayList<>();
        quarterFinalMatches.add(new PlayoffMatch(teams.get(0), teams.get(7), 0, 0, false)); // 1 vs 8
        quarterFinalMatches.add(new PlayoffMatch(teams.get(1), teams.get(6), 0, 0, false)); // 2 vs 7
        quarterFinalMatches.add(new PlayoffMatch(teams.get(2), teams.get(5), 0, 0, false)); // 3 vs 6
        quarterFinalMatches.add(new PlayoffMatch(teams.get(3), teams.get(4), 0, 0, false)); // 4 vs 5

        quarterFinals.setMatches(quarterFinalMatches);
        stages.add(quarterFinals);

        // Adding playoff to the repository
        playoff.setStages(stages);
        playoffRepository.save(playoff);
        playoffStageRepository.save(quarterFinals);
        playoffMatchRepository.saveAll(quarterFinalMatches);

        return playoff;

    }
    //@Override
//   public void advanceStage(Playoff playoff){
//        List<PlayoffStage> stages = playoff.getStages();
//        PlayoffStage lastStage = stages.get(stages.size() - 1);
//
//        if (lastStage.getStageNumber() == 3) {
//            throw new IllegalStateException("Playoffs already completed");
//        }
//
//        List<PlayoffMatch> matches = lastStage.getMatches();
//        List<FootballTeam> winners = new ArrayList<>();
//        for (PlayoffMatch match : matches) {
//            if (!match.isCompleted()) {
//                throw new IllegalStateException("All matches in the stage must be completed");
//            }
//            winners.add(match.getHomeTeamPoints() > match.getAwayTeamPoints() ? match.getHomeTeam() : match.getAwayTeam());
//        }
//
//        PlayoffStage newStage = new PlayoffStage();
//        newStage.setPlayoff(playoff);
//        newStage.setStageNumber(lastStage.getStageNumber() + 1);
//
//        List<PlayoffMatch> newMatches = new ArrayList<>();
//        if (newStage.getStageNumber() == 2) {
//            newMatches.add(new PlayoffMatch(winners.get(0), winners.get(3), 0, 0, false)); // Semi-finals
//            newMatches.add(new PlayoffMatch(winners.get(1), winners.get(2), 0, 0, false));
//        } else if (newStage.getStageNumber() == 3) {
//            newMatches.add(new PlayoffMatch(winners.get(0), winners.get(1), 0, 0, false)); // Final
//        }
//
//        newStage.setMatches(newMatches);
//        stages.add(newStage);
//
//        playoff.setStages(stages);
//        playoffRepository.save(playoff);
//        playoffStageRepository.save(newStage);
//        playoffMatchRepository.saveAll(newMatches);
//
//    }
    @Override
    public PlayoffMatch  createMatch(Long matchId, int homeTeamPoints, int awayTeamPoints){
        PlayoffMatch match = playoffMatchRepository.findById(matchId)
                .orElseThrow(() -> new IllegalArgumentException("Match not found."));
        match.setHomeTeamPoints(homeTeamPoints);
        match.setAwayTeamPoints(awayTeamPoints);
        match.setCompleted(true);

        PlayoffStage currentStage = match.getStage();
        Playoff playoff = currentStage.getPlayoff();
        int currentStageNumber = currentStage.getStageNumber();

        if (currentStageNumber < 3) {
            PlayoffStage nextStage = playoff.getStages().stream()
                    .filter(stage -> stage.getStageNumber() == currentStageNumber + 1)
                    .findFirst()
                    .orElseGet(() -> {
                        PlayoffStage newStage = new PlayoffStage();
                        newStage.setStageNumber(currentStageNumber + 1);
                        newStage.setPlayoff(playoff);
                        playoff.getStages().add(newStage);
                        return newStage;
                    });

            PlayoffMatch nextMatch = nextStage.getMatches().stream()
                    .filter(m -> m.getHomeTeam() == null || m.getAwayTeam() == null)
                    .findFirst()
                    .orElseGet(() -> {
                        PlayoffMatch newMatch = new PlayoffMatch();
                        newMatch.setStage(nextStage);
                        nextStage.getMatches().add(newMatch);
                        return newMatch;
                    });

            if (nextMatch.getHomeTeam() == null) {
                nextMatch.setHomeTeam(homeTeamPoints > awayTeamPoints ? match.getHomeTeam() : match.getAwayTeam());
            } else {
                nextMatch.setAwayTeam(homeTeamPoints > awayTeamPoints ? match.getHomeTeam() : match.getAwayTeam());
            }
        }

        return match;
    }

    @Override
    public List<PlayoffMatch> getPlayoffMatches(Long playoffId){
        return playoffMatchRepository.findAll();
    }
    public Playoff getCurrentPlayoff() {
      return playoffRepository.findTopByOrderByIdDesc().orElse(null);
    }
}
