package mk.ukim.finki.wp.liga.model.dtos;

import lombok.Getter;
import lombok.Setter;
import mk.ukim.finki.wp.liga.model.FootballPlayer;

@Getter
@Setter
public class FootballPlayerDTO {
    private Long football_player_id;
    private String name;
    private String surname;
    private Long teamId; // This will store the team ID

}
