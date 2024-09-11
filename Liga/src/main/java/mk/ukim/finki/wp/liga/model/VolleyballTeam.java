package mk.ukim.finki.wp.liga.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
@Data
@Entity
@Setter
@Getter
public class VolleyballTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long volleyball_team_id;
    private String teamName;
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VolleyballPlayer> players;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "volleyball_team_fixtures",
            joinColumns = @JoinColumn(name = "volleyball_team_id"),
            inverseJoinColumns = @JoinColumn(name = "volleyball_match_id")
    )
    private List<VolleyballMatch> volleyballFixtures;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "volleyball_team_results",
            joinColumns = @JoinColumn(name = "volleyball_team_id"),
            inverseJoinColumns = @JoinColumn(name = "volleyball_match_id")
    )
    private List<VolleyballMatch> volleyballResults;
    private int teamMatchesPlayed;
    private int teamLeaguePoints;
    private int teamWins;
    private int teamLoses;
    @Lob
    @Column(name="volleyball_team_logo")
    private byte[] logo;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "volleyball_team_last_five_matches", joinColumns = @JoinColumn(name = "volleyball_team_id"))
    @Column(name = "volleyball_match_result")
    private List<String> lastFiveMatches = new ArrayList<>();
    public VolleyballTeam(String teamName, byte[] logo) {
        this.teamName = teamName;
        this.players = new ArrayList<>();
        this.volleyballFixtures = new ArrayList<>();
        this.volleyballResults = new ArrayList<>();
        this.teamMatchesPlayed = 0;
        this.teamLeaguePoints = 0;
        this.teamWins = 0;
        this.teamLoses = 0;
        this.logo = logo;
    }
    public void addMatchResult(String result) {
        if (lastFiveMatches.size() == 5) {
            lastFiveMatches.remove(0);
        }
        lastFiveMatches.add(result);
    }


    public VolleyballTeam() {

    }
}
