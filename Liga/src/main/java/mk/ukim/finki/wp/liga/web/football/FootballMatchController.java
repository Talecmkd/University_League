package mk.ukim.finki.wp.liga.web.football;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.liga.model.FootballMatch;
import mk.ukim.finki.wp.liga.model.FootballPlayer;
import mk.ukim.finki.wp.liga.model.FootballPlayerScored;
import mk.ukim.finki.wp.liga.model.FootballTeam;
import mk.ukim.finki.wp.liga.service.football.FootballMatchService;
import mk.ukim.finki.wp.liga.service.football.FootballPlayerScoredService;
import mk.ukim.finki.wp.liga.service.football.FootballPlayerService;
import mk.ukim.finki.wp.liga.service.football.FootballTeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping({"/matches"})
public class FootballMatchController {

    private final FootballMatchService footballMatchService;
    private final FootballTeamService footballTeamService;
    private final FootballPlayerService footballPlayerService;
    private final FootballPlayerScoredService footballPlayerScoredService;

    @GetMapping
    public String showAllMatches(Model model) {
        List<FootballMatch> footballMatches = footballMatchService.listAllFootballMatches();
        model.addAttribute("footballMatches", footballMatches);
        return "football_matches";
    }

    @GetMapping("/fixtures")
    public String showFixtures(Model model) {

        List<FootballMatch> fixtures = footballMatchService.listAllFootballMatches().stream()
                .filter(match -> match.getEndTime().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
        model.addAttribute("fixtures", fixtures);
        return "football_fixtures";
    }

    @GetMapping("/results")
    public String showResults(Model model) {
        List<FootballMatch> results = footballMatchService.listAllFootballMatches().stream()
                .filter(match -> match.getEndTime().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());
        results.forEach(footballMatchService::updateTeamStatistics);
        model.addAttribute("results", results);
        return "football_results";
    }

    @GetMapping("/live")
    public String showLive(Model model) {
        List<FootballMatch> live = footballMatchService.listAllFootballMatches().stream()
                .filter(match -> (match.getStartTime().isBefore(LocalDateTime.now()) && match.getEndTime().isAfter(LocalDateTime
                        .now())))
                .collect(Collectors.toList());
        model.addAttribute("live", live);
        return "football_live";
    }

    @GetMapping("/add-form")
    public String addMatch(Model model) {
        List<FootballTeam> teams = footballTeamService.listAllTeams();
        model.addAttribute("teams", teams);
        return "add_football_match";
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

        FootballTeam homeTeam = footballTeamService.findById(homeTeamId);
        FootballTeam awayTeam = footballTeamService.findById(awayTeamId);

        footballMatchService.createAndAddToFixtures(homeTeam, awayTeam, homeTeamPoints, awayTeamPoints, start);
        return "redirect:/matches";
    }

    @GetMapping("/edit-form/{id}")
    public String editMatch(@PathVariable Long id, Model model) {
        FootballMatch match = footballMatchService.findById(id);
        List<FootballTeam> teams = footballTeamService.listAllTeams();
        model.addAttribute("match", match);
        model.addAttribute("teams", teams);
        return "edit_football_match";
    }

    @GetMapping("/edit_live/{id}")
    public String editLiveMatch(@PathVariable Long id, Model model) {
        FootballMatch match = footballMatchService.findById(id);
        List<FootballTeam> teams = footballTeamService.listAllTeams();
        List<FootballPlayer> players = footballPlayerService.listAllPlayers();
        model.addAttribute("match", match);
        model.addAttribute("teams", teams);
        model.addAttribute("players", players);
        return "edit_live_football_match";
    }

    @PostMapping("/add_live")
    public String addLiveMatch(@RequestParam FootballPlayer player,
                               @RequestParam LocalDateTime timeScored,
                               @RequestParam Long footballMatchId,
                               @RequestParam int goalsScored,
                               @RequestParam int assistsScored,
                               @RequestParam int saves){

        FootballMatch footballMatch = footballMatchService.findById(footballMatchId);
        FootballPlayerScored newPlayer = new FootballPlayerScored();
        newPlayer.setPlayer(player);
        newPlayer.setFootballMatch(footballMatch);
        newPlayer.setSaves(saves);
        newPlayer.setTimeScored(timeScored);
        newPlayer.setGoalsScored(goalsScored);
        newPlayer.setAssistsScored(assistsScored);
        Long playerId = player.getFootball_player_id();

        footballPlayerScoredService.create(player, timeScored, footballMatch);
        footballPlayerService.addAppearances(playerId);
        footballPlayerService.addAssists(playerId,assistsScored);
        footballPlayerService.addGoals(playerId, goalsScored);
        footballPlayerService.addSaves(playerId, saves);
        
        return "redirect:/matches";
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

        FootballTeam homeTeam = footballTeamService.findById(homeTeamId);
        FootballTeam awayTeam = footballTeamService.findById(awayTeamId);

        footballMatchService.update(id, homeTeam, awayTeam, homeTeamPoints, awayTeamPoints, start);
        return "redirect:/matches";
    }

    @PostMapping("/delete/{id}")
    public String deleteMatch(@PathVariable Long id) {
        footballMatchService.delete(id);
        return "redirect:/matches";
    }


}
