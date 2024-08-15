package mk.ukim.finki.wp.liga.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class BasketballTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String teamName;
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BasketballPlayer> players;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "basketball_team_fixtures",
            joinColumns = @JoinColumn(name = "basketball_team_id"),
            inverseJoinColumns = @JoinColumn(name = "basketball_match_id")
    )
    private List<BasketballMatch> basketballFixtures;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "basketball_team_results",
            joinColumns = @JoinColumn(name = "basketball_team_id"),
            inverseJoinColumns = @JoinColumn(name = "basketball_match_id")
    )
    private List<BasketballMatch> basketballResults;
    private int teamMatchesPlayed;
    private int teamLeaguePoints;
    private int teamWins;
    private int teamLoses;
    private byte[] logo;

    public BasketballTeam(String teamName, byte[] logo) {
        this.teamName = teamName;
        this.players = new ArrayList<>();
        this.basketballFixtures = new ArrayList<>();
        this.basketballResults = new ArrayList<>();
        this.teamMatchesPlayed = 0;
        this.teamLeaguePoints = 0;
        this.teamWins = 0;
        this.teamLoses = 0;
        this.logo = logo;
    }


    public BasketballTeam() {

    }
}
