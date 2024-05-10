package mk.ukim.finki.wp.liga.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Playoff {
    private List<Match> playoffMatches;
    private List<Team> qualifiedTeams;

    public Playoff(List<Match> playoffMatches) {
        this.playoffMatches = playoffMatches;
        this.qualifiedTeams = new ArrayList<>();
    }
}
