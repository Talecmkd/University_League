package mk.ukim.finki.wp.liga.web.football;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.liga.model.FootballTeam;
import mk.ukim.finki.wp.liga.service.football.FootballMatchService;
import mk.ukim.finki.wp.liga.service.football.FootballPlayerScoredService;
import mk.ukim.finki.wp.liga.service.football.FootballPlayerService;
import mk.ukim.finki.wp.liga.service.football.FootballTeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/")
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
    List<FootballTeam> footballTeams = this.footballTeamService.listAllTeams();
    model.addAttribute("footballTeams",footballTeams);
    //model.addAttribute("bodyContent","football_teams");
    return "football_teams";
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




}
