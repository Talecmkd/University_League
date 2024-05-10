package mk.ukim.finki.wp.liga.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Match {
    private Team homeTeam;
    private Team awayTeam;
    private int homeTeamPoints;
    private int awayTeamPoints;
    private List<PlayerScored> playersWhoScored;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Match(Team homeTeam, Team awayTeam, int homeTeamPoints,
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
