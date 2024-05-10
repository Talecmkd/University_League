package mk.ukim.finki.wp.liga.model;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Standings {
    private List<Team> teams;

    public Standings() {
        this.teams = new ArrayList<>();
    }

}
