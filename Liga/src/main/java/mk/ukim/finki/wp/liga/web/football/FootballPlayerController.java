package mk.ukim.finki.wp.liga.web.football;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.liga.model.FootballPlayer;
import mk.ukim.finki.wp.liga.model.FootballTeam;
import mk.ukim.finki.wp.liga.service.football.FootballPlayerService;
import mk.ukim.finki.wp.liga.service.football.FootballTeamService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
        if (playerImage != null && !playerImage.isEmpty()) {
            try {
                imageBytes = playerImage.getBytes();
            } catch (IOException e) {
                // Log the error and handle the exception appropriately
                e.printStackTrace();
                throw new RuntimeException("Failed to read the image file", e);
            }
        }        FootballTeam team1=this.footballTeamService.findById(team);
        Date birthDate = Date.from(playerBirthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        this.footballPlayerService.create(imageBytes,playerName,playerSurname,
                birthDate,playerIndex,playerCity,playerPosition,team1);

        return "redirect:/players";
    }
    @GetMapping("/edit/{id}") //localhost:8080/products/edit-form/{id}
    public String editProductPage(@PathVariable Long id, Model model) {
        if (this.footballPlayerService.findById(id)!=null) { //we check if the product with the given ID exists
            FootballPlayer player = this.footballPlayerService.findById(id); //we put the product into the product object
            List<FootballTeam> teams = this.footballTeamService.listAllTeams();//we list all the manufacturers

            model.addAttribute("teams", teams);//we set the categories into the categories attribute
            model.addAttribute("player", player);//we set the product into the product attribute
            model.addAttribute("bodyContent", "add-football-player");//we set the edit-product.html into the bodyContent attribute
            return "edit_football_player";
        }

        return "redirect:/players?error=PlayerNotFound"; //redirect to localhost:8080/products?error=ProductNotFound if the product is not found by its ID
    }
    @PostMapping("/edit/{id}")
    public String editPlayer(@PathVariable("id") Long id,
                             @RequestParam(value = "playerImage", required = false) MultipartFile playerImage,
                             @RequestParam String playerName,
                             @RequestParam String playerSurname,
                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate playerBirthDate,
                             @RequestParam int playerIndex,
                             @RequestParam String playerCity,
                             @RequestParam String playerPosition,
                             @RequestParam Long team,
                             Model model
    ) {
        FootballPlayer existingPlayer = footballPlayerService.findById(id);
        if (existingPlayer == null) {
            return "redirect:/players";
        }
        byte[] imageBytes = existingPlayer.getImage();
        if (playerImage != null && !playerImage.isEmpty()) {
            try {
                imageBytes = playerImage.getBytes();
                System.out.println(imageBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FootballTeam team1 = footballTeamService.findById(team);
        Date birthDate = Date.from(playerBirthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        // Update the player's details
        footballPlayerService.update(id, imageBytes, playerName, playerSurname, birthDate, playerIndex, playerCity, playerPosition, team1);
        String imageUrl = "/players/image/" + id;
        model.addAttribute("playerImageUrl", imageUrl);
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
        String imageUrl = "/players/image/" + id;
        model.addAttribute("playerImageUrl", imageUrl);

        return "football_player_details";
    }
    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getPlayerImage(@PathVariable Long id) {
        FootballPlayer player = footballPlayerService.findById(id);

        if (player != null && player.getImage() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Set appropriate media type
            return new ResponseEntity<>(player.getImage(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/delete/{id}")
    public String deletePlayer(@PathVariable Long id){
        footballPlayerService.delete(id);
        return "redirect:/players";
    }



}
