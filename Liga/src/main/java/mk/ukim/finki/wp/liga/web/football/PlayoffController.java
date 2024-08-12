//package mk.ukim.finki.wp.liga.web.football;
//
//import lombok.AllArgsConstructor;
//import mk.ukim.finki.wp.liga.model.Playoff;
//import mk.ukim.finki.wp.liga.model.PlayoffMatch;
//import mk.ukim.finki.wp.liga.service.football.FootballPlayoffsService;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//@RequestMapping("/playoffs")
//@AllArgsConstructor
//public class PlayoffController {
//    private final FootballPlayoffsService footballPlayoffsService;
//
//    @GetMapping
//    public String getPlayoffBracket(Model model) {
//        Playoff playoff = footballPlayoffsService.getPlayoff();
//        model.addAttribute("playoff", playoff);
//        return "playoff_bracket"; // Thymeleaf template name
//    }
//    @GetMapping("/playoffs/create")
//    public String showCreatePlayoffForm(Model model) {
//        model.addAttribute("playoffForm", new Playoff());
//        return "redirect:/playoffs";
//    }
//
//    @PostMapping("/create")
//    public String createPlayoff() {
//        footballPlayoffsService.createPlayoff();
//        return "redirect:/playoffs";
//    }
//
//    @PostMapping("/completeMatch")
//    public String completeMatch(@RequestParam Long matchId, @RequestParam int homeTeamPoints, @RequestParam int awayTeamPoints) {
//        footballPlayoffsService.completeMatch(matchId, homeTeamPoints, awayTeamPoints);
//        return "redirect:/playoffs";
//    }
//
//    @GetMapping("/match/{id}")
//    public String getMatchDetails(@PathVariable Long id, Model model) {
//        PlayoffMatch match = footballPlayoffsService.getMatchById(id);
//        model.addAttribute("match", match);
//        return "playoff_match_details"; // Thymeleaf template name
//    }
//}
