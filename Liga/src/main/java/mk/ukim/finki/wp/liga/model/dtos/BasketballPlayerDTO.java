package mk.ukim.finki.wp.liga.model.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasketballPlayerDTO {
    private Long basketball_player_id;
    private String name;
    private String surname;
    private Long teamId; // This will store the team ID

}
