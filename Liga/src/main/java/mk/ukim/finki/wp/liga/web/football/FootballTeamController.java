package mk.ukim.finki.wp.liga.web.football;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.liga.model.FootballPlayer;
import mk.ukim.finki.wp.liga.model.FootballTeam;
import mk.ukim.finki.wp.liga.service.football.FootballMatchService;
import mk.ukim.finki.wp.liga.service.football.FootballPlayerScoredService;
import mk.ukim.finki.wp.liga.service.football.FootballPlayerService;
import mk.ukim.finki.wp.liga.service.football.FootballTeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/teams")
public class FootballTeamController{
    private final FootballPlayerService footballPlayerService;
    private final FootballTeamService footballTeamService;
    private final FootballMatchService footballMatchService;
    private final FootballPlayerScoredService footballPlayerScoredService;

@GetMapping()
    public String getFootballTeamsPage(@RequestParam(required = false) String error, Model model){
    if (error != null && !error.isEmpty()) {
        model.addAttribute("hasError", true);
        model.addAttribute("error", error);
    }
    List<FootballTeam> footballTeams = this.footballTeamService.findAllOrderByPointsDesc();
    model.addAttribute("footballTeams",footballTeams);
    model.addAttribute("bodyContent","football_teams");
    return "master_template";
}

@GetMapping("team/{id}")
    public String getTeam(@PathVariable Long id, Model model){
    FootballTeam team = footballTeamService.findById(id);
    if (team != null) {
        model.addAttribute("team", team);
        model.addAttribute("players", team.getPlayers());
        List<FootballPlayer> top5Players=footballPlayerService.getTop5PlayersByTeam(id);
        model.addAttribute("topPlayers",top5Players);
    } else {
        model.addAttribute("hasError", true);
        model.addAttribute("error", "Team not found");
    }
    return "football_team_details";
}

//@GetMapping("teams/team/{id}")
//    public String getTeam(@PathVariable Long id, Model model){
//    if(this.footballTeamService.findById(id)!=null){
//        FootballTeam team=footballTeamService.findById(id);
//
//        model.addAttribute("team",team);
//    }
//
//    //model.addAttribute("bodyContent","team");
//    return "footballteams";
//
//}

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        FootballTeam team = footballTeamService.findById(id);
        if (team == null) {
            return "redirect:/teams";
        }
        model.addAttribute("team", team);
        return "edit_football_table";
    }

    @PostMapping("/edit/{id}")
    public String editTeam(@PathVariable Long id,
                           @RequestParam int teamLeaguePoints) {
        FootballTeam existingTeam = footballTeamService.findById(id);
        if (existingTeam == null) {
            return "redirect:/teams";
        }

        footballTeamService.saveTable(id, teamLeaguePoints);
        return "redirect:/teams";
    }

    @GetMapping("/add")
    public String showAddTeamForm(Model model) {
        List<FootballPlayer> players = footballPlayerService.listAllPlayers();
        model.addAttribute("players", players);
        return "add_team";
    }

    @PostMapping("/add")
    public String addTeam(@RequestParam("teamName") String teamName,
                          @RequestParam(value = "logo", required = false) MultipartFile playerImage) throws IOException {
        byte [] imageBytes=null;

        // Create the team
        footballTeamService.create(teamName, null);

        // Redirect to the teams list page
        return "redirect:/teams";
    }

    @GetMapping("/show/{id}")
    public String getTeamMatches(@PathVariable Long id, Model model) {
        FootballTeam team = footballTeamService.findById(id);
        if (team == null) {
            return "redirect:/teams";
        }
        model.addAttribute("team", team);
        model.addAttribute("fixtures", team.getFootballFixtures());
        model.addAttribute("results", team.getFootballResults());
        return "show_football_team_matches";
    }

    @PostMapping("/delete/{id}")
    public String deleteTeam(@PathVariable Long id){
        footballTeamService.delete(id);
        return "redirect:/teams";
    }

}
