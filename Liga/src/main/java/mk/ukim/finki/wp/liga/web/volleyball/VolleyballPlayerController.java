package mk.ukim.finki.wp.liga.web.volleyball;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.liga.model.VolleyballPlayer;
import mk.ukim.finki.wp.liga.model.VolleyballPlayerMatchStats;
import mk.ukim.finki.wp.liga.model.VolleyballTeam;
import mk.ukim.finki.wp.liga.service.volleyball.*;
import mk.ukim.finki.wp.liga.service.volleyball.VolleyballPlayerService;
import mk.ukim.finki.wp.liga.service.volleyball.VolleyballTeamService;
import org.springframework.format.annotation.DateTimeFormat;
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
    private final VolleyballPlayerScoredService volleyballPlayerScoredService;


    @GetMapping
    public String getVolleyballPlayers(Model model) {
        List<VolleyballPlayer> players = volleyballPlayerService.listAllPlayers();
        model.addAttribute("players", players);
        return "volleyball/players";
    }

    @GetMapping("/{id}")
    public String getVolleyballPlayer(@PathVariable Long id, Model model) {
        VolleyballPlayer player = volleyballPlayerService.findById(id);
        model.addAttribute("player", player);
        return "volleyball/player";
    }

    @GetMapping("/add")
    public String showAddVolleyballPlayerForm(Model model) {
        List<VolleyballTeam> teams = volleyballTeamService.listAllTeams();
        model.addAttribute("teams", teams);
        model.addAttribute("player", new VolleyballPlayer());
        return "volleyball/player-add";
    }

    @PostMapping("/add")
    public String addVolleyballPlayer(@RequestParam("image") MultipartFile image,
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

        VolleyballPlayer player = volleyballPlayerService.create(imageBytes, name, surname, birthdate, index, city, position, team);

        return "redirect:/volleyball/players";
    }



    @GetMapping("/edit/{id}")
    public String editVolleyballPlayer(@PathVariable Long id, Model model) {
        VolleyballPlayer player = volleyballPlayerService.findById(id);
        List<VolleyballTeam> teams = volleyballTeamService.listAllTeams();
        model.addAttribute("player", player);
        model.addAttribute("teams", teams);
        return "volleyball/player-edit";
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

    @GetMapping("/{id}/stats")
    public String getVolleyballPlayerStats(@PathVariable Long id, Model model) {
        VolleyballPlayer player = volleyballPlayerService.findById(id);
        VolleyballPlayerMatchStats stats = volleyballPlayerScoredService.findPlayerStatsByPlayerId(id);
        model.addAttribute("player", player);
        model.addAttribute("stats", stats);
        return "volleyball/player-stats";
    }
}