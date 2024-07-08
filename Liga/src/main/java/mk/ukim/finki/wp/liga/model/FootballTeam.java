package mk.ukim.finki.wp.liga.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class FootballTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String teamName;
    @OneToMany
    private List<FootballPlayer> players;
    @ManyToMany
    @JoinTable(
            name = "team_fixtures",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "match_id")
    )
    private List<FootballMatch> fixtures;
    @ManyToMany
    @JoinTable(
            name = "team_results",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "match_id")
    )
    private List<FootballMatch> results;

    private int teamMatchesPlayed;
    private int teamLeaguePoints;
    private int teamWins;
    private int teamLoses;
    private int teamDraws;
    private Image logo;

    public FootballTeam(String teamName, List<FootballPlayer> players, Image logo) {
        this.teamName = teamName;
        this.players = players;
        this.fixtures = new ArrayList<>();
        this.results = new ArrayList<>();
        this.teamMatchesPlayed = 0;
        this.teamLeaguePoints = 0;
        this.teamWins = 0;
        this.teamLoses = 0;
        this.teamDraws = 0;
        this.logo = logo;
    }


}
