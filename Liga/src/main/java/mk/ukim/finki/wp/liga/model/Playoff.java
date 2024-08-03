package mk.ukim.finki.wp.liga.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Playoff {
    private List<FootballMatch> playoffMatches;
    private List<BasketballTeam> qualifiedTeams;

    public Playoff(List<FootballMatch> playoffMatches) {
        this.playoffMatches = playoffMatches;
        this.qualifiedTeams = new ArrayList<>();
    }
}
