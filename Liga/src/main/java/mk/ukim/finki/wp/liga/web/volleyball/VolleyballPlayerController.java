package mk.ukim.finki.wp.liga.web.volleyball;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.liga.model.VolleyballPlayer;
import mk.ukim.finki.wp.liga.model.VolleyballPlayerMatchStats;
import mk.ukim.finki.wp.liga.model.VolleyballTeam;
import mk.ukim.finki.wp.liga.service.volleyball.*;
import mk.ukim.finki.wp.liga.service.volleyball.VolleyballPlayerService;
import mk.ukim.finki.wp.liga.service.volleyball.VolleyballTeamService;
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
import java.util.Date;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/volleyball/players")
public class VolleyballPlayerController {

    private final VolleyballPlayerService volleyballPlayerService;
    private final VolleyballTeamService volleyballTeamService;
    private final VolleyballPlayerMatchStatsService volleyballPlayerMatchStatsService;


    @GetMapping
    public String getVolleyballPlayers(Model model) {
        List<VolleyballPlayer> players = volleyballPlayerService.listAllPlayers();
        List<VolleyballPlayer> top5Players = volleyballPlayerService.getTop5Players();
        model.addAttribute("players", players);
        model.addAttribute("topPlayers",top5Players);
        model.addAttribute("bodyContent","volleyball/volleyball_players");
        return "volleyball/master_template";
    }

    @GetMapping("/{id}")
    public String getVolleyballPlayer(@PathVariable Long id, Model model) {
        VolleyballPlayer player = volleyballPlayerService.findById(id);
        model.addAttribute("player", player);
        model.addAttribute("bodyContent","volleyball/volleyball_player_details");
        return "volleyball/master_template";
    }

    @GetMapping("/add-form")
    public String showAddVolleyballPlayerForm(Model model) {
        List<VolleyballTeam> teams = volleyballTeamService.listAllTeams();
        model.addAttribute("teams", teams);
        model.addAttribute("player", new VolleyballPlayer());
        model.addAttribute("bodyContent","volleyball/add_volleyball_player");
        return "volleyball/master_template";
    }

    @PostMapping("/add")
    public String addVolleyballPlayer(@RequestParam("image") MultipartFile image,
                                      @RequestParam("name") String name,
                                      @RequestParam("surname") String surname,
                                      @RequestParam("birthdate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthdate,
                                      @RequestParam("index") int index,
                                      @RequestParam("city") String city,
                                      @RequestParam("position") String position,
                                      @RequestParam(value = "teamId", required = false) Long teamId,
                                      Model model) {
        byte[] imageBytes = null;
        try {
            if (!image.isEmpty()) {
                imageBytes = image.getBytes();
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }

        VolleyballTeam team = null;
        if (teamId != null) {
            team = volleyballTeamService.findById(teamId);

            model.addAttribute("teams", volleyballTeamService.listAllTeams());
            return "volleyball/add_volleyball_player"; // or the correct view
        }

        VolleyballPlayer player = volleyballPlayerService.create(imageBytes, name, surname, birthdate, index, city, position, team);

        return "redirect:/volleyball/players";
    }




    @GetMapping("/edit/{id}")
    public String editVolleyballPlayer(@PathVariable Long id, Model model) {
        VolleyballPlayer player = volleyballPlayerService.findById(id);
        List<VolleyballTeam> teams = volleyballTeamService.listAllTeams();
        model.addAttribute("player", player);
        model.addAttribute("teams", teams);
        model.addAttribute("bodyContent","volleyball/edit_volleyball_player");
        return "volleyball/master_template";
    }

    @PostMapping("/edit/{id}")
    public String updateVolleyballPlayer(@PathVariable Long id,
                                         @RequestParam("image") MultipartFile image,
                                         @RequestParam("name") String name,
                                         @RequestParam("surname") String surname,
                                         @RequestParam("birthdate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthdate,
                                         @RequestParam("index") int index,
                                         @RequestParam("city") String city,
                                         @RequestParam("position") String position,
                                         @RequestParam(value = "teamId", required = false) Long teamId) {
        byte[] imageBytes = null;
        try {
            if (!image.isEmpty()) {
                imageBytes = image.getBytes();
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }

        VolleyballTeam team = null;
        if (teamId != null) {
            team = volleyballTeamService.findById(teamId);
        }

        volleyballPlayerService.update(id, imageBytes, name, surname, birthdate, index, city, position, team);

        return "redirect:/volleyball/players";
    }


    @PostMapping("/delete/{id}")
    public String deleteVolleyballPlayer(@PathVariable Long id) {
        volleyballPlayerService.delete(id);
        return "redirect:/volleyball/players";
    }


    @GetMapping("/details/{id}")
    public String getPlayerDetails(@PathVariable Long id, Model model){
        VolleyballPlayer volleyballPlayer = volleyballPlayerService.findById(id);
        if (volleyballPlayer == null) {
            return "redirect:/basketball/players"; // Redirect to the list of players or another appropriate page
        }
        model.addAttribute("volleyballPlayer", volleyballPlayer);
        String imageUrl = "basketball/players/image/" + id;
        model.addAttribute("playerImageUrl", imageUrl);
        model.addAttribute("bodyContent","volleyball/volleyball_player_details");
        return "/volleyball/master_template";
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getPlayerImage(@PathVariable Long id) {
        VolleyballPlayer player = volleyballPlayerService.findById(id);

        if (player != null && player.getImage() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Set appropriate media type
            return new ResponseEntity<>(player.getImage(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/stats")
    public String getVolleyballPlayerStats(@PathVariable Long id, Model model) {
        VolleyballPlayer player = volleyballPlayerService.findById(id);
        VolleyballPlayerMatchStats stats = volleyballPlayerMatchStatsService.findPlayerStatsByPlayerId(id);
        model.addAttribute("player", player);
        model.addAttribute("stats", stats);
        model.addAttribute("bodyContent","volleyball/volleyball_player_stats");
        return "volleyball/master_template";
    }
}