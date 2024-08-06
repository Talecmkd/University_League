package mk.ukim.finki.wp.liga.web.football;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.liga.model.Playoff;
import mk.ukim.finki.wp.liga.model.PlayoffMatch;
import mk.ukim.finki.wp.liga.service.football.FootballPlayoffsService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/playoffs")
@AllArgsConstructor
public class PlayoffController {
    private final FootballPlayoffsService footballPlayoffsService;

    @GetMapping
    public String viewPlayoffBracket(Model model) {
        Playoff playoff = footballPlayoffsService.getCurrentPlayoff();
        model.addAttribute("playoff", playoff);
        return "playoff_bracket";
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createPlayoff() {
        footballPlayoffsService.createPlayoff();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/match/{matchId}/complete")
    @ResponseBody
    public ResponseEntity<PlayoffMatch> completeMatch(@PathVariable Long matchId, @RequestBody MatchScoreRequest request) {
        PlayoffMatch match = footballPlayoffsService.createMatch(matchId, request.getHomeTeamPoints(), request.getAwayTeamPoints());
        return ResponseEntity.ok(match);
    }

    public static class MatchScoreRequest {
        private int homeTeamPoints;
        private int awayTeamPoints;

        public int getHomeTeamPoints() {
            return homeTeamPoints;
        }

        public void setHomeTeamPoints(int homeTeamPoints) {
            this.homeTeamPoints = homeTeamPoints;
        }

        public int getAwayTeamPoints() {
            return awayTeamPoints;
        }

        public void setAwayTeamPoints(int awayTeamPoints) {
            this.awayTeamPoints = awayTeamPoints;
        }
    }
}
