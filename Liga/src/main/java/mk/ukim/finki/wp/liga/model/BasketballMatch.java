package mk.ukim.finki.wp.liga.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
@Entity
@Setter
@Getter
public class BasketballMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long basketball_match_id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "basketball_home_id")
    private BasketballTeam homeTeam;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "basketball_away_id")
    private BasketballTeam awayTeam;
    private int homeTeamPoints;
    private int awayTeamPoints;
    @OneToMany(mappedBy = "basketballMatch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BasketballPlayerMatchStats> playerMatchStats;
    @ManyToMany(mappedBy = "basketballFixtures",cascade = CascadeType.REMOVE)
    private List<BasketballTeam> upcomingMatches;
    @ManyToMany(mappedBy = "basketballResults",cascade = CascadeType.REMOVE)
    private List<BasketballTeam> playedMatches;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @Column(name = "is_basketball_playoff_match")
    private boolean isPlayoffMatch;

    public BasketballMatch(BasketballTeam homeTeam, BasketballTeam awayTeam, int homeTeamPoints,
                           int awayTeamPoints, LocalDateTime startTime, boolean isPlayoffMatch) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamPoints = homeTeamPoints;
        this.awayTeamPoints = awayTeamPoints;
        this.playerMatchStats = new ArrayList<>();
        this.startTime = startTime;
        this.endTime = startTime.plusHours(2);
        this.isPlayoffMatch = isPlayoffMatch;
    }

    public BasketballMatch() {

    }
}
