package mk.ukim.finki.wp.liga.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class PlayerScored {
    private Player player;
    private LocalDateTime timeScored;

    public PlayerScored(Player player, LocalDateTime timeScored) {
        this.player = player;
        this.timeScored = timeScored;
    }

}
