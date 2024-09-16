package mk.ukim.finki.wp.liga.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
@Setter
@Getter
@Entity
public class VolleyballMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long volleyball_match_id;
    @ManyToOne
    @JoinColumn(name = "volleyball_home_id")
    private VolleyballTeam homeTeam;
    @ManyToOne
    @JoinColumn(name = "volleyball_away_id")
    private VolleyballTeam awayTeam;
    private int homeTeamPoints;
    private int awayTeamPoints;
    @OneToMany(mappedBy = "volleyballMatch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VolleyballPlayerMatchStats> playerMatchStats;
    @ManyToMany(mappedBy = "volleyballFixtures")
    private List<VolleyballTeam> upcomingMatches;
    @ManyToMany(mappedBy = "volleyballResults")
    private List<VolleyballTeam> playedMatches;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @Column(name = "is_volleyball_playoff_match")
    private boolean isVolleyballPlayoffMatch;

    public VolleyballMatch(VolleyballTeam homeTeam, VolleyballTeam awayTeam, int homeTeamPoints,
                           int awayTeamPoints, LocalDateTime startTime, boolean isVolleyballPlayoffMatch) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamPoints = homeTeamPoints;
        this.awayTeamPoints = awayTeamPoints;
        this.playerMatchStats = new ArrayList<>();
        this.startTime = startTime;
        this.endTime = startTime.plusHours(2);
        this.isVolleyballPlayoffMatch = isVolleyballPlayoffMatch;
    }

    public VolleyballMatch() {

    }
}
