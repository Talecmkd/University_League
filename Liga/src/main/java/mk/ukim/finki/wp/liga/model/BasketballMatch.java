package mk.ukim.finki.wp.liga.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class BasketballMatch {
    private BasketballTeam homeTeam;
    private BasketballTeam awayTeam;
    private int homeTeamPoints;
    private int awayTeamPoints;
    private List<BasketballPlayerScored> playersWhoScored;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public BasketballMatch(BasketballTeam homeTeam, BasketballTeam awayTeam, int homeTeamPoints,
                           int awayTeamPoints, LocalDateTime startTime, LocalDateTime endTime) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamPoints = homeTeamPoints;
        this.awayTeamPoints = awayTeamPoints;
        this.playersWhoScored = new ArrayList<>();
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public BasketballMatch() {

    }
}
