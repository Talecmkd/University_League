package mk.ukim.finki.wp.liga.model;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class FootballTeam {
    private String teamName;
    private List<FootballPlayer> players;
    private List<FootballMatch> fixtures;
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
