package mk.ukim.finki.wp.liga.web.volleyball;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.liga.model.*;
import mk.ukim.finki.wp.liga.model.dtos.FootballPlayerDTO;
import mk.ukim.finki.wp.liga.service.volleyball.VolleyballMatchService;
import mk.ukim.finki.wp.liga.service.volleyball.VolleyballPlayerScoredService;
import mk.ukim.finki.wp.liga.service.volleyball.VolleyballPlayerService;
import mk.ukim.finki.wp.liga.service.volleyball.VolleyballTeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping({"/volleyball_matches"})
public class VolleyballMatchController {
    private final VolleyballMatchService volleyballMatchService;
    private final VolleyballPlayerService volleyballPlayerService;
    private final VolleyballTeamService volleyballTeamService;
    private final VolleyballPlayerScoredService volleyballPlayerScoredService;

    @GetMapping
    public String showAllMatches(Model model) {
        List<VolleyballMatch> volleyballMatches = volleyballMatchService.listAllVolleyballMatches();
        model.addAttribute("volleyballMatches", volleyballMatches);
        return "volleyball_matches";
    }

    @GetMapping("/details/{id}")
    public String showMatchDetails(@PathVariable Long id, Model model) {
        VolleyballMatch match = volleyballMatchService.findById(id);
        match.setHomeTeam(volleyballTeamService.findById(match.getHomeTeam().getVolleyball_team_id()));
        match.setAwayTeam(volleyballTeamService.findById(match.getAwayTeam().getVolleyball_team_id()));
        model.addAttribute("match", match);
        return "volleyball_match_details";
    }

    @GetMapping("/results/details/{id}")
    public String showMatchResultsDetails(@PathVariable Long id, Model model) {
        VolleyballMatch match = volleyballMatchService.findById(id);
        match.setHomeTeam(volleyballTeamService.findById(match.getHomeTeam().getVolleyball_team_id()));
        match.setAwayTeam(volleyballTeamService.findById(match.getAwayTeam().getVolleyball_team_id()));
        model.addAttribute("match", match);
        return "volleyball_match_results_details";
    }

    @GetMapping("/results/details/stats/{id}")
    public String showMatchDetailsAndStats(@PathVariable Long id, Model model) {
        VolleyballMatch match = volleyballMatchService.findById(id);
        match.setHomeTeam(volleyballTeamService.findById(match.getHomeTeam().getVolleyball_team_id()));
        match.setAwayTeam(volleyballTeamService.findById(match.getAwayTeam().getVolleyball_team_id()));
        VolleyballTeam homeTeam = match.getHomeTeam();
        VolleyballTeam awayTeam = match.getAwayTeam();

        List<VolleyballPlayer> homePlayers = homeTeam.getPlayers();
        List<VolleyballPlayer> awayPlayers = awayTeam.getPlayers();

        int homePoints = 0;
        int homeBlocks = 0;
        int homeAssists = 0;
        int homeServings = 0;

        for(VolleyballPlayer player : homePlayers){
            homePoints += player.getPoints();
            homeAssists += player.getAssists();
            homeBlocks += player.getBlocks();
            homeServings += player.getServings();
        }

        int awayPoints = 0;
        int awayBlocks = 0;
        int awayAssists = 0;
        int awayServings = 0;

        for(VolleyballPlayer player : awayPlayers){
            awayPoints += player.getPoints();
            awayAssists += player.getAssists();
            awayBlocks += player.getBlocks();
            awayServings += player.getServings();
        }

        model.addAttribute("match", match);
        model.addAttribute("homeTeam",homeTeam);
        model.addAttribute("awayTeam",awayTeam);
        model.addAttribute("homePoints",homePoints);
        model.addAttribute("homeBlocks",homeBlocks);
        model.addAttribute("homeAssists",homeAssists);
        model.addAttribute("homeServings",homeServings);
        model.addAttribute("awayServings",awayServings);
        model.addAttribute("awayBlocks",awayBlocks);
        model.addAttribute("awayPoints",awayPoints);
        model.addAttribute("awayAssists",awayAssists);
        return "volleyball_match_details_stats";
    }

    @GetMapping("/fixtures")
    public String showFixtures(Model model) {

        List<VolleyballMatch> fixtures = volleyballMatchService.listAllVolleyballMatches().stream()
                .filter(match -> match.getEndTime().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
        model.addAttribute("fixtures", fixtures);
        return "volleyball_fixtures";
    }

    @GetMapping("/results")
    public String showResults(Model model) {
        List<VolleyballMatch> results = volleyballMatchService.listAllVolleyballMatches().stream()
                .filter(match -> match.getEndTime().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());
        results.forEach(volleyballMatchService::updateTeamStatistics);
        model.addAttribute("results", results);
        return "volleyball_results";
    }

    @GetMapping("/live")
    public String showLive(Model model) {
        List<VolleyballMatch> live = volleyballMatchService.listAllVolleyballMatches().stream()
                .filter(match -> (match.getStartTime().isBefore(LocalDateTime.now()) && match.getEndTime().isAfter(LocalDateTime
                        .now())))
                .collect(Collectors.toList());
        model.addAttribute("live", live);
        return "volleyball_live";
    }

    @GetMapping("/add-form")
    public String addMatch(Model model) {
        List<VolleyballTeam> teams = volleyballTeamService.listAllTeams();
        model.addAttribute("teams", teams);
        return "add_volleyball_match";
    }

    @PostMapping("/add")
    public String saveMatch(
            @RequestParam Long homeTeamId,
            @RequestParam Long awayTeamId,
            @RequestParam int homeTeamPoints,
            @RequestParam int awayTeamPoints,
            @RequestParam String startTime,
            @RequestParam String endTime) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime start = LocalDateTime.parse(startTime, formatter);
        LocalDateTime end = LocalDateTime.parse(endTime, formatter);

        VolleyballTeam homeTeam = volleyballTeamService.findById(homeTeamId);
        VolleyballTeam awayTeam = volleyballTeamService.findById(awayTeamId);

        volleyballMatchService.createAndAddToFixtures(homeTeam, awayTeam, homeTeamPoints, awayTeamPoints, start);
        return "redirect:/volleyball_matches";
    }

    @GetMapping("/edit-form/{id}")
    public String editMatch(@PathVariable Long id, Model model) {
        VolleyballMatch match = volleyballMatchService.findById(id);
        List<VolleyballTeam> teams = volleyballTeamService.listAllTeams();
        model.addAttribute("match", match);
        model.addAttribute("teams", teams);
        return "edit_volleyball_match";
    }

    @GetMapping("/edit_live/{id}")
    public String editLiveMatch(@PathVariable Long id, Model model) throws JsonProcessingException {
        VolleyballMatch match = volleyballMatchService.findById(id);

        List<VolleyballPlayer> players = match.getHomeTeam().getPlayers();
        players.addAll(match.getAwayTeam().getPlayers());
        System.out.println(players);
        List<FootballPlayerDTO> dtoPlayers= new ArrayList<>();
        for (VolleyballPlayer player : players) {
            FootballPlayerDTO dtoPlayer=new FootballPlayerDTO();
            dtoPlayer.setFootball_player_id(player.getVolleyball_player_id());
            dtoPlayer.setName(player.getName());
            dtoPlayer.setSurname(player.getSurname());
            dtoPlayer.setTeamId(player.getTeam().getVolleyball_team_id());
            dtoPlayers.add(dtoPlayer);
        }
        model.addAttribute("match", match);
        List<VolleyballTeam> teams = new ArrayList<>();
        teams.add(match.getHomeTeam());
        teams.add(match.getAwayTeam());
        model.addAttribute("teams", teams);
        model.addAttribute("players", players);
        model.addAttribute("dtoPlayers",dtoPlayers);
        model.addAttribute("playersHome", match.getHomeTeam().getPlayers());
        model.addAttribute("playersAway", match.getAwayTeam().getPlayers());

        return "edit_live_volleyball_match";
    }

    @PostMapping("/edit_live")
    public String editLiveMatchPost(@RequestParam Long playerId,
                                    @RequestParam LocalDateTime timeScored,
                                    @RequestParam Long volleyballMatchId,
                                    @RequestParam int points,
                                    @RequestParam int assists,
                                    @RequestParam int blocks,
                                    @RequestParam int servings) {


        VolleyballPlayer player = volleyballPlayerService.findById(playerId);
        VolleyballMatch volleyballMatch = volleyballMatchService.findById(volleyballMatchId);
//
//        FootballPlayerScored existingPlayerScored = footballPlayerScoredService.findByPlayerAndMatch(player, footballMatch);
//        if (existingPlayerScored != null) {
//            existingPlayerScored.setSaves(existingPlayerScored.getSaves() + saves);
//            existingPlayerScored.setGoalsScored(existingPlayerScored.getGoalsScored() + goalsScored);
//            existingPlayerScored.setAssistsScored(existingPlayerScored.getAssistsScored() + assistsScored);
//            footballPlayerScoredService.save(existingPlayerScored);
//        } else {
//            FootballPlayerScored newPlayerScored = new FootballPlayerScored(player, timeScored, footballMatch);
//            newPlayerScored.setSaves(saves);
//            newPlayerScored.setGoalsScored(goalsScored);
//            newPlayerScored.setAssistsScored(assistsScored);
//            footballPlayerScoredService.save(newPlayerScored);
//        }

        volleyballPlayerService.addAppearances(playerId);
        volleyballPlayerService.addAssists(playerId, assists);
        volleyballPlayerService.addPoints(playerId, points);
        volleyballPlayerService.addBlocks(playerId, blocks);
        volleyballPlayerService.addServings(playerId, servings);

        VolleyballTeam team = volleyballTeamService.listAllTeams().stream().filter(t -> t.getPlayers().contains(player)).findFirst().get();
        volleyballTeamService.updateStats(team.getVolleyball_team_id());
        volleyballMatchService.updateLiveStats(volleyballMatchId, points, playerId);

        return "redirect:/volleyball_matches";
    }


    @PostMapping("/edit")
    public String updateMatch(
            @RequestParam Long id,
            @RequestParam Long homeTeamId,
            @RequestParam Long awayTeamId,
            @RequestParam int homeTeamPoints,
            @RequestParam int awayTeamPoints,
            @RequestParam String startTime,
            @RequestParam String endTime) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime start = LocalDateTime.parse(startTime, formatter);
        LocalDateTime end = LocalDateTime.parse(endTime, formatter);

        VolleyballTeam homeTeam = volleyballTeamService.findById(homeTeamId);
        VolleyballTeam awayTeam = volleyballTeamService.findById(awayTeamId);

        volleyballMatchService.update(id, homeTeam, awayTeam, homeTeamPoints, awayTeamPoints, start);
        return "redirect:/volleyball_matches";
    }

    @PostMapping("/delete/{id}")
    public String deleteMatch(@PathVariable Long id) {
        volleyballMatchService.delete(id);
        return "redirect:/volleyball_matches";
    }

    @GetMapping("/{teamId}/players")
    public List<VolleyballPlayer> getPlayersByTeam(@PathVariable Long teamId) {
        VolleyballTeam team = volleyballTeamService.findById(teamId);
        return team.getPlayers();
    }

    @GetMapping("/playoffs/init")
    public String initializePlayoffMatches() {
        volleyballMatchService.createPlayoffMatches();
        return "redirect:/matches/playoffs"; // Redirect to the list of playoff matches
    }

    @GetMapping("/playoffs")
    public String getPlayoffMatches(Model model) {
        List<VolleyballMatch> matches = volleyballMatchService.listPlayoffMatches();
        model.addAttribute("matches", matches);
        return "playoff_bracket"; // The name of your Thymeleaf template for the playoff view
    }

    @GetMapping("/playoffs/edit/{id}")
    public String editPlayoffMatch(@PathVariable Long id, Model model) {
        VolleyballMatch match = volleyballMatchService.findById(id);
        List<VolleyballTeam> teams = volleyballTeamService.listAllTeams();
        model.addAttribute("match", match);
        model.addAttribute("teams", teams);
        return "edit_playoff_match";
    }
    @PostMapping("/playoffs/edit")
    public String updatePlayoffMatchPoints(
            @RequestParam Long id,
            @RequestParam Long homeTeamId,
            @RequestParam Long awayTeamId,
            @RequestParam int homeTeamPoints,
            @RequestParam int awayTeamPoints) {
        VolleyballTeam homeTeam = volleyballTeamService.findById(homeTeamId);
        VolleyballTeam awayTeam = volleyballTeamService.findById(awayTeamId);
        volleyballMatchService.updatePlayoffMatchPoints(id, homeTeam, awayTeam, homeTeamPoints, awayTeamPoints);

        return "redirect:/matches/playoffs";
    }
    @GetMapping("/playoffs/semi-finals_Init")
    public String initializeSemiFinalMatches() {
        volleyballMatchService.createSemiFinalMatches();
        return "redirect:/matches/playoffs"; // Redirect to view the updated playoff bracket
    }
    @GetMapping("/playoffs/finals_Init")
    public String initializeFinalMatches() {
        volleyballMatchService.createFinalMatch();
        return "redirect:/matches/playoffs"; // Redirect to view the updated playoff bracket
    }
}
