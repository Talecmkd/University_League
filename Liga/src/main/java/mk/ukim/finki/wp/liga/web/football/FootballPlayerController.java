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



        return "add-football-player";

    }
    @GetMapping("/edit-form/{id}") //localhost:8080/products/edit-form/{id}
    public String editProductPage(@PathVariable Long id, Model model) {
        if (this.footballPlayerService.findById(id)!=null) { //we check if the product with the given ID exists
            FootballPlayer player = this.footballPlayerService.findById(id); //we put the product into the product object
            List<FootballTeam> teams = this.footballTeamService.listAllTeams();//we list all the manufacturers

            model.addAttribute("teams", teams);//we set the categories into the categories attribute
            model.addAttribute("player", player);//we set the product into the product attribute
            model.addAttribute("bodyContent", "add-football-player");//we set the edit-product.html into the bodyContent attribute
            return "add-football-player";//we show the edit-product.html
            //because we've set the attributes in the model, we can work with them in the edit-product.html
        }

        return "redirect:/products?error=ProductNotFound"; //redirect to localhost:8080/products?error=ProductNotFound if the product is not found by its ID
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


}
