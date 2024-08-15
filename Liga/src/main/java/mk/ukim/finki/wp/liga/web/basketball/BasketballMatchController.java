package mk.ukim.finki.wp.liga.web.basketball;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.liga.model.*;
import mk.ukim.finki.wp.liga.model.dtos.BasketballPlayerDTO;
import mk.ukim.finki.wp.liga.service.basketball.BasketballMatchService;
import mk.ukim.finki.wp.liga.service.basketball.BasketballPlayerMatchStatsService;
import mk.ukim.finki.wp.liga.service.basketball.BasketballPlayerService;
import mk.ukim.finki.wp.liga.service.basketball.BasketballTeamService;
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
@RequestMapping({"/basketball/matches"})
public class BasketballMatchController {

    private final BasketballMatchService basketballMatchService;
    private final BasketballTeamService basketballTeamService;
    private final BasketballPlayerService basketballPlayerService;
    private final BasketballPlayerMatchStatsService basketballPlayerMatchStatsService;

    @GetMapping
    public String showAllMatches(Model model) {
        List<BasketballMatch> basketballMatches = basketballMatchService.listAllBasketballMatches();
        model.addAttribute("basketballMatches", basketballMatches);
        return "basketball/basketball_matches";
    }

    @GetMapping("/details/{id}")
    public String showMatchDetails(@PathVariable Long id, Model model) {
        BasketballMatch match = basketballMatchService.findById(id);
        match.setHomeTeam(basketballTeamService.findById(match.getHomeTeam().getId()));
        match.setAwayTeam(basketballTeamService.findById(match.getAwayTeam().getId()));
        model.addAttribute("match", match);
        return "basketball/basketball_match_details";
    }

    @GetMapping("/results/details/{id}")
    public String showMatchResultsDetails(@PathVariable Long id, Model model) {
        BasketballMatch match = basketballMatchService.findById(id);
        match.setHomeTeam(basketballTeamService.findById(match.getHomeTeam().getId()));
        match.setAwayTeam(basketballTeamService.findById(match.getAwayTeam().getId()));
        model.addAttribute("match", match);
        return "basketball/basketball_match_results_details";
    }

    @GetMapping("/results/details/stats/{id}")
    public String showMatchDetailsAndStats(@PathVariable Long id, Model model) {
        BasketballMatch match = basketballMatchService.findById(id);
        match.setHomeTeam(basketballTeamService.findById(match.getHomeTeam().getId()));
        match.setAwayTeam(basketballTeamService.findById(match.getAwayTeam().getId()));
        BasketballTeam homeTeam = match.getHomeTeam();
        BasketballTeam awayTeam = match.getAwayTeam();

        List<BasketballPlayer> homePlayers = homeTeam.getPlayers();
        List<BasketballPlayer> awayPlayers = awayTeam.getPlayers();

        int homePoints = 0;
        int homeRebounds = 0;
        int homeAssists = 0;

        for(BasketballPlayer player : homePlayers){
            homePoints += player.getPoints();
            homeRebounds += player.getRebounds();
            homeAssists += player.getAssists();
        }

        int awayPoints = 0;
        int awayRebounds = 0;
        int awayAssists = 0;

        for(BasketballPlayer player : awayPlayers){
            awayPoints += player.getPoints();
            awayRebounds += player.getRebounds();
            awayAssists += player.getAssists();
        }

        model.addAttribute("match", match);
        model.addAttribute("homeTeam",homeTeam);
        model.addAttribute("awayTeam",awayTeam);
        model.addAttribute("homeGoals", homePoints);
        model.addAttribute("homeSaves", homeRebounds);
        model.addAttribute("homeAssists",homeAssists);
        model.addAttribute("awayGoals", awayPoints);
        model.addAttribute("awaySaves", awayRebounds);
        model.addAttribute("awayAssists",awayAssists);
        return "basketball/basketball_match_details_stats";
    }

    @GetMapping("/fixtures")
    public String showFixtures(Model model) {

        List<BasketballMatch> fixtures = basketballMatchService.listAllBasketballMatches().stream()
                .filter(match -> match.getEndTime().isAfter(LocalDateTime.now()))
                .collect(Collectors.toList());
        model.addAttribute("fixtures", fixtures);
        return "basketball/basketball_fixtures";
    }

    @GetMapping("/results")
    public String showResults(Model model) {
        List<BasketballMatch> results = basketballMatchService.listAllBasketballMatches().stream()
                .filter(match -> match.getEndTime().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());
        results.forEach(basketballMatchService::updateTeamStatistics);
        model.addAttribute("results", results);
        return "basketball/basketball_results";
    }

    @GetMapping("/live")
    public String showLive(Model model) {
        List<BasketballMatch> live = basketballMatchService.listAllBasketballMatches().stream()
                .filter(match -> (match.getStartTime().isBefore(LocalDateTime.now()) && match.getEndTime().isAfter(LocalDateTime
                        .now())))
                .collect(Collectors.toList());
        model.addAttribute("live", live);
        return "basketball/basketball_live";
    }

    @GetMapping("/add-form")
    public String addMatch(Model model) {
        List<BasketballTeam> teams = basketballTeamService.listAllTeams();
        model.addAttribute("teams", teams);
        return "basketball/add_basketball_match";
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

        BasketballTeam homeTeam = basketballTeamService.findById(homeTeamId);
        BasketballTeam awayTeam = basketballTeamService.findById(awayTeamId);

        basketballMatchService.createAndAddToFixtures(homeTeam, awayTeam, homeTeamPoints, awayTeamPoints, start);
        return "redirect:/basketball/matches";
    }

    @GetMapping("/edit-form/{id}")
    public String editMatch(@PathVariable Long id, Model model) {
        BasketballMatch match = basketballMatchService.findById(id);
        List<BasketballTeam> teams = basketballTeamService.listAllTeams();
        model.addAttribute("match", match);
        model.addAttribute("teams", teams);
        return "basketball/edit_basketball_match";
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

        BasketballTeam homeTeam = basketballTeamService.findById(homeTeamId);
        BasketballTeam awayTeam = basketballTeamService.findById(awayTeamId);

        basketballMatchService.update(id, homeTeam, awayTeam, homeTeamPoints, awayTeamPoints, start);
        return "redirect:/basketball/matches";
    }


    @GetMapping("/edit_live/{id}")
    public String editLiveMatch(@PathVariable Long id, Model model) throws JsonProcessingException {
        BasketballMatch match = basketballMatchService.findById(id);

        List<BasketballPlayer> players = match.getHomeTeam().getPlayers();
        players.addAll(match.getAwayTeam().getPlayers());
        System.out.println(players);
        List<BasketballPlayerDTO> dtoPlayers= new ArrayList<>();
        for (BasketballPlayer player : players) {
            BasketballPlayerDTO dtoPlayer=new BasketballPlayerDTO();
            dtoPlayer.setBasketball_player_id(player.getBasketball_player_id());
            dtoPlayer.setName(player.getName());
            dtoPlayer.setSurname(player.getSurname());
            dtoPlayer.setTeamId(player.getTeam().getId());
            dtoPlayers.add(dtoPlayer);
        }
        model.addAttribute("match", match);
        List<BasketballTeam> teams = new ArrayList<>();
        teams.add(match.getHomeTeam());
        teams.add(match.getAwayTeam());
        model.addAttribute("teams", teams);
        model.addAttribute("players", players);
        model.addAttribute("dtoPlayers",dtoPlayers);
        model.addAttribute("playersHome", match.getHomeTeam().getPlayers());
        model.addAttribute("playersAway", match.getAwayTeam().getPlayers());

        return "basketball/edit_live_basketball_match";
    }

    @PostMapping("/edit_live")
    public String editLiveMatchPost(@RequestParam Long playerId,
                                    @RequestParam LocalDateTime timeScored,
                                    @RequestParam Long footballMatchId,
                                    @RequestParam int pointsScored,
                                    @RequestParam int assistsScored,
                                    @RequestParam int reboundsScored) {


        BasketballPlayer player = basketballPlayerService.findById(playerId);
        BasketballMatch basketballMatch = basketballMatchService.findById(footballMatchId);

        BasketballPlayerMatchStats existingPlayerScored = basketballPlayerMatchStatsService.findByPlayerAndBasketballMatch(player, basketballMatch);
        if (existingPlayerScored != null) {
            existingPlayerScored.setRebounds(existingPlayerScored.getRebounds() + reboundsScored);
            existingPlayerScored.setPointsScored(existingPlayerScored.getPointsScored() + pointsScored);
            existingPlayerScored.setAssists(existingPlayerScored.getAssists() + assistsScored);
            basketballPlayerMatchStatsService.save(existingPlayerScored);
        } else {
            BasketballPlayerMatchStats newPlayerScored = new BasketballPlayerMatchStats(player, basketballMatch, 0, 0, 0);
            newPlayerScored.setRebounds(reboundsScored);
            newPlayerScored.setPointsScored(pointsScored);
            newPlayerScored.setAssists(assistsScored);
            basketballPlayerMatchStatsService.save(newPlayerScored);
        }

        basketballPlayerService.addAppearances(playerId);
        basketballPlayerService.addAssists(playerId, assistsScored);
        basketballPlayerService.addPoints(playerId, pointsScored);
        basketballPlayerService.addRebounds(playerId, reboundsScored);

        BasketballTeam team = basketballTeamService.listAllTeams().stream().filter(t -> t.getPlayers().contains(player)).findFirst().get();
        basketballTeamService.updateStats(team.getId());
        basketballMatchService.updateLiveStats(footballMatchId, pointsScored, playerId);

        return "redirect:/basketball/matches";
    }


    @PostMapping("/delete/{id}")
    public String deleteMatch(@PathVariable Long id) {
        basketballMatchService.delete(id);
        return "redirect:/basketball/matches";
    }

    @GetMapping("/{teamId}/players")
    public List<BasketballPlayer> getPlayersByTeam(@PathVariable Long teamId) {
        BasketballTeam team = basketballTeamService.findById(teamId);
        return team.getPlayers();
    }

    @GetMapping("/playoffs/init")
    public String initializePlayoffMatches() {
        basketballMatchService.createPlayoffMatches();
        return "redirect:/basketball/matches/playoffs"; // Redirect to the list of playoff matches
    }

    @GetMapping("/playoffs")
    public String getPlayoffMatches(Model model) {
        List<BasketballMatch> matches = basketballMatchService.listPlayoffMatches();
        model.addAttribute("matches", matches);
        return "basketball/basketball_playoff_bracket"; // The name of your Thymeleaf template for the playoff view
    }

    @GetMapping("/playoffs/edit/{id}")
    public String editPlayoffMatch(@PathVariable Long id, Model model) {
        BasketballMatch match = basketballMatchService.findById(id);
        List<BasketballTeam> teams = basketballTeamService.listAllTeams();
        model.addAttribute("match", match);
        model.addAttribute("teams", teams);
        return "basketball/edit_basketball_playoff_match";
    }

    @PostMapping("/playoffs/edit")
    public String updatePlayoffMatchPoints(
            @RequestParam Long id,
            @RequestParam Long homeTeamId,
            @RequestParam Long awayTeamId,
            @RequestParam int homeTeamPoints,
            @RequestParam int awayTeamPoints) {
        BasketballTeam homeTeam = basketballTeamService.findById(homeTeamId);
        BasketballTeam awayTeam = basketballTeamService.findById(awayTeamId);
        basketballMatchService.updatePlayoffMatchPoints(id, homeTeam, awayTeam, homeTeamPoints, awayTeamPoints);

        return "redirect:/basketball/matches/playoffs";
    }

    @GetMapping("/playoffs/semi-finals_Init")
    public String initializeSemiFinalMatches() {
        basketballMatchService.createSemiFinalMatches();
        return "redirect:/basketball/matches/playoffs"; // Redirect to view the updated playoff bracket
    }

    @GetMapping("/playoffs/finals_Init")
    public String initializeFinalMatches() {
        basketballMatchService.createFinalMatch();
        return "redirect:/basketball/matches/playoffs"; // Redirect to view the updated playoff bracket
    }
}
