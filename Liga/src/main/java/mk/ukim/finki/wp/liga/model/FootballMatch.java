package mk.ukim.finki.wp.liga.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class FootballMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "home_id")
    private FootballTeam homeTeam;
    @ManyToOne
    @JoinColumn(name = "away_id")
    private FootballTeam awayTeam;
    private int homeTeamPoints;
    private int awayTeamPoints;
    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FootballPlayerScored> playersWhoScored;
    @ManyToMany(mappedBy = "fixtures")
    private List<FootballTeam> upcomingMatches;
    @ManyToMany(mappedBy = "results")
    private List<FootballTeam> playedMatches;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public FootballMatch(FootballTeam homeTeam, FootballTeam awayTeam, int homeTeamPoints,
                         int awayTeamPoints, LocalDateTime startTime, LocalDateTime endTime) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamPoints = homeTeamPoints;
        this.awayTeamPoints = awayTeamPoints;
        this.playersWhoScored = new ArrayList<>();
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public FootballMatch() {

    }

}
