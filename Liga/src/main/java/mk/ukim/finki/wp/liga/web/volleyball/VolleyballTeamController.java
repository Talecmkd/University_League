package mk.ukim.finki.wp.liga.web.volleyball;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.liga.model.VolleyballPlayer;
import mk.ukim.finki.wp.liga.model.VolleyballTeam;
import mk.ukim.finki.wp.liga.service.volleyball.VolleyballMatchService;
import mk.ukim.finki.wp.liga.service.volleyball.VolleyballPlayerService;
import mk.ukim.finki.wp.liga.service.volleyball.VolleyballTeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/volleyball/teams")
public class VolleyballTeamController {

    private final VolleyballPlayerService volleyballPlayerService;
    private final VolleyballTeamService volleyballTeamService;
    private final VolleyballMatchService volleyballMatchService;

    @GetMapping()
    public String getVolleyballTeamsPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<VolleyballTeam> volleyballTeams = this.volleyballTeamService.findAllOrderByPointsDesc();
        model.addAttribute("volleyballTeams", volleyballTeams);
        return "volleyball_teams";
    }

    @GetMapping("team/{id}")
    public String getTeam(@PathVariable Long id, Model model) {
        VolleyballTeam team = volleyballTeamService.findById(id);
        if (team != null) {
            model.addAttribute("team", team);
            model.addAttribute("players", team.getPlayers());
            List<VolleyballPlayer> top5Players = new ArrayList<>();
//                    volleyballPlayerService.getTop5PlayersByTeam(id);
            model.addAttribute("topPlayers", top5Players);
        } else {
            model.addAttribute("hasError", true);
            model.addAttribute("error", "Team not found");
        }
        return "volleyball_team_details";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        VolleyballTeam team = volleyballTeamService.findById(id);
        if (team == null) {
            return "redirect:/volleyball/teams";
        }
        model.addAttribute("team", team);
        return "edit_volleyball_team";
    }

    @PostMapping("/edit/{id}")
    public String editTeam(@PathVariable Long id,
                           @RequestParam int teamLeaguePoints) {
        VolleyballTeam existingTeam = volleyballTeamService.findById(id);
        if (existingTeam == null) {
            return "redirect:/volleyball/teams";
        }

        volleyballTeamService.saveTable(id, teamLeaguePoints);
        return "redirect:/volleyball/teams";
    }

    @GetMapping("/add")
    public String showAddTeamForm(Model model) {
        List<VolleyballPlayer> players = volleyballPlayerService.listAllPlayers();
        model.addAttribute("players", players);
        return "add_volleyball_team";
    }

    @PostMapping("/add")
    public String addTeam(@RequestParam("teamName") String teamName,
                          @RequestParam(value = "logo", required = false) MultipartFile teamLogo) throws IOException {
        byte[] logoBytes = null;
        if (teamLogo != null && !teamLogo.isEmpty()) {
            logoBytes = teamLogo.getBytes();
        }

        // Create the team
        volleyballTeamService.create(teamName, logoBytes);

        // Redirect to the teams list page
        return "redirect:/volleyball/teams";
    }

    @GetMapping("/show/{id}")
    public String getTeamMatches(@PathVariable Long id, Model model) {
        VolleyballTeam team = volleyballTeamService.findById(id);
        if (team == null) {
            return "redirect:/volleyball/teams";
        }
        model.addAttribute("team", team);
        model.addAttribute("fixtures", team.getVolleyballFixtures());
        model.addAttribute("results", team.getVolleyballResults());
        return "show_volleyball_team_matches";
    }

    @PostMapping("/delete/{id}")
    public String deleteTeam(@PathVariable Long id) {
        volleyballTeamService.delete(id);
        return "redirect:/volleyball/teams";
    }
}