package mk.ukim.finki.wp.liga.model;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Team {
    private String teamName;
    private List<Player> players;
    private Sport sport;
    private List<Match> fixtures;
    private List<Match> results;
    private int teamMatchesPlayed;
    private int teamLeaguePoints;
    private int teamWins;
    private int teamLoses;
    private int teamDraws;
    private Image logo;

    public Team(String teamName, List<Player> players, Sport sport, Image logo) {
        this.teamName = teamName;
        this.players = players;
        this.sport = sport;
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
