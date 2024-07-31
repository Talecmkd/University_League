package mk.ukim.finki.wp.liga.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class FootballTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String teamName;
    @OneToMany
    private List<FootballPlayer> players;
    @ManyToMany
    @JoinTable(
            name = "football_team_fixtures",
            joinColumns = @JoinColumn(name = "football_team_id"),
            inverseJoinColumns = @JoinColumn(name = "football_match_id")
    )
    private List<FootballMatch> footballFixtures;
    @ManyToMany
    @JoinTable(
            name = "football_team_results",
            joinColumns = @JoinColumn(name = "football_team_id"),
            inverseJoinColumns = @JoinColumn(name = "football_match_id")
    )
    private List<FootballMatch> footballResults;

    private int teamMatchesPlayed;
    private int teamLeaguePoints;
    private int teamWins;
    private int teamLoses;
    private int teamDraws;
    private byte [] logo;

    public FootballTeam(String teamName, List<FootballPlayer> players, byte [] logo) {
        this.teamName = teamName;
        this.players = players;
        this.footballFixtures = new ArrayList<>();
        this.footballResults = new ArrayList<>();
        this.teamMatchesPlayed = 0;
        this.teamLeaguePoints = 0;
        this.teamWins = 0;
        this.teamLoses = 0;
        this.teamDraws = 0;
        this.logo = logo;
    }


    public FootballTeam() {

    }
}
