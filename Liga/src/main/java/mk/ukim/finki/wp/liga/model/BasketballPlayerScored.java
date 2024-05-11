package mk.ukim.finki.wp.liga.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class BasketballPlayerScored {
    private BasketballPlayer player;
    private LocalDateTime timeScored;

    public BasketballPlayerScored(BasketballPlayer player, LocalDateTime timeScored) {
        this.player = player;
        this.timeScored = timeScored;
    }

}
