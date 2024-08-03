package mk.ukim.finki.wp.liga.web.football;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.liga.model.FootballPlayer;
import mk.ukim.finki.wp.liga.model.FootballTeam;
import mk.ukim.finki.wp.liga.service.football.FootballPlayerService;
import mk.ukim.finki.wp.liga.service.football.FootballTeamService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping({"/players"})
public class FootballPlayerController {

    private final FootballPlayerService footballPlayerService;
    private final FootballTeamService footballTeamService;


    @GetMapping
    public String showAllPlayers(Model model) {
        List<FootballPlayer> footballPlayers = footballPlayerService.listAllPlayers();
       model.addAttribute("footballPlayers", footballPlayers);
       //model.addAttribute("bodyContent", "football_players");
       return "football_players";
    }

    @GetMapping("/add-form")
    public String addPlayerForm(Model model){
        List<FootballTeam> teams=this.footballTeamService.listAllTeams();
        model.addAttribute("teams",teams);
        model.addAttribute("bodyContent","add-football-player");



        return "add_football_player";

    }



    @PostMapping("/add")
    public String addPlayer(@RequestParam(value = "playerImage", required = false) MultipartFile playerImage,
                            @RequestParam String playerName,
                            @RequestParam String playerSurname,
                            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate playerBirthDate,
                            @RequestParam Integer playerIndex,
                            @RequestParam String playerCity,
                            @RequestParam String playerPosition,
                            @RequestParam Long team
    ) throws IOException {
        byte [] imageBytes=null;
        FootballTeam team1=this.footballTeamService.findById(team);
        Date birthDate = Date.from(playerBirthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        this.footballPlayerService.create(null,playerName,playerSurname,
                birthDate,playerIndex,playerCity,playerPosition,team1);

        return "redirect:/players";
    }
    @GetMapping("/details/{id}")
    public String getPlayerDetails(@PathVariable Long id, Model model){
        FootballPlayer footballPlayer = footballPlayerService.findById(id);
        if (footballPlayer == null) {
            // Handle the case where the player does not exist (e.g., return a 404 page or redirect to an error page)
            return "redirect:/players"; // Redirect to the list of players or another appropriate page
        }
        model.addAttribute("footballPlayer", footballPlayer);
        return "football_player_details";
    }
    @PostMapping("/delete/{id}")
    public String deletePlayer(@PathVariable Long id){
        footballPlayerService.delete(id);
        return "redirect:/players";
    }



}
