package mk.ukim.finki.wp.liga.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class VolleyballPlayerScored {
    private VolleyballPlayer player;
    private LocalDateTime timeScored;

    public VolleyballPlayerScored(VolleyballPlayer player, LocalDateTime timeScored) {
        this.player = player;
        this.timeScored = timeScored;
    }

}
