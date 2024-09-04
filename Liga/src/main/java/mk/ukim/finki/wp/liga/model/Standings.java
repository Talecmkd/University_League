package mk.ukim.finki.wp.liga.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Data
@Setter
@Getter
public class Standings {
    private List<BasketballTeam> teams;

    public Standings() {
        this.teams = new ArrayList<>();
    }

}
