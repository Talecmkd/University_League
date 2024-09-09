package mk.ukim.finki.wp.liga.model.dtos;

import jakarta.websocket.server.ServerEndpoint;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasketballTeamStandings {
    private String teamName;
    private int matchesPlayed;
    private int wins;
    private int loses;

    public BasketballTeamStandings(String teamName, int matchesPlayed, int wins, int loses) {
        this.teamName = teamName;
        this.matchesPlayed = matchesPlayed;
        this.wins = wins;
        this.loses = loses;
    }
}
