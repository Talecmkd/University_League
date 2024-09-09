package mk.ukim.finki.wp.liga.model.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VolleyBallStandings {
    private String teamName;
    private int matchesPlayed;
    private int wins;
    private int loses;
    private int points;

    public VolleyBallStandings(String teamName, int matchesPlayed, int wins, int loses, int points) {
        this.teamName = teamName;
        this.matchesPlayed = matchesPlayed;
        this.wins = wins;
        this.loses = loses;
        this.points=points;
    }
}
