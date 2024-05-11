package mk.ukim.finki.wp.liga.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class VolleyballMatch {
    private BasketballTeam homeTeam;
    private BasketballTeam awayTeam;
    private int homeTeamPoints;
    private int awayTeamPoints;
    private List<VolleyballPlayerScored> playersWhoScored;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public VolleyballMatch(BasketballTeam homeTeam, BasketballTeam awayTeam, int homeTeamPoints,
                           int awayTeamPoints, LocalDateTime startTime, LocalDateTime endTime) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamPoints = homeTeamPoints;
        this.awayTeamPoints = awayTeamPoints;
        this.playersWhoScored = new ArrayList<>();
        this.startTime = startTime;
        this.endTime = endTime;
    }

}
