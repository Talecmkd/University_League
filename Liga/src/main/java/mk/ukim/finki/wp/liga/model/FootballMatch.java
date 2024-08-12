package mk.ukim.finki.wp.liga.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class FootballMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long football_match_id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "football_home_id")
    private FootballTeam homeTeam;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "football_away_id")
    private FootballTeam awayTeam;
    private int homeTeamPoints;
    private int awayTeamPoints;
    @OneToMany(mappedBy = "footballMatch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FootballPlayerScored> playersWhoScored;
    @ManyToMany(mappedBy = "footballFixtures",cascade = CascadeType.REMOVE)
    private List<FootballTeam> upcomingMatches;
    @ManyToMany(mappedBy = "footballResults",cascade = CascadeType.REMOVE)
    private List<FootballTeam> playedMatches;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @Column(name = "is_playoff_match")
    private boolean isPlayoffMatch;

    public FootballMatch(FootballTeam homeTeam, FootballTeam awayTeam, int homeTeamPoints,
                         int awayTeamPoints, LocalDateTime startTime, boolean isPlayoffMatch) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamPoints = homeTeamPoints;
        this.awayTeamPoints = awayTeamPoints;
        this.playersWhoScored = new ArrayList<>();
        this.startTime = startTime;
        this.endTime = startTime.plusHours(2);
        this.isPlayoffMatch = isPlayoffMatch;
    }

    public FootballMatch() {

    }

}
