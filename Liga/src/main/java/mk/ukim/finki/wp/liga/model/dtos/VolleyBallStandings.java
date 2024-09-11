package mk.ukim.finki.wp.liga.model.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VolleyBallStandings {
    private String teamName;
    private int matchesPlayed;
    private int wins;
    private int loses;
    private int points;
    private List<String> lastFiveMatches;

    public VolleyBallStandings(String teamName, int matchesPlayed, int wins, int loses, int points, List<String> lastFiveMatches) {
        this.teamName = teamName;
        this.matchesPlayed = matchesPlayed;
        this.wins = wins;
        this.loses = loses;
        this.points=points;
        this.lastFiveMatches=lastFiveMatches;

    }
}
