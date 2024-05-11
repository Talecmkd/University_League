package mk.ukim.finki.wp.liga.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class FootballPlayerScored {
    private FootballPlayer player;
    private LocalDateTime timeScored;

    public FootballPlayerScored(FootballPlayer player, LocalDateTime timeScored) {
        this.player = player;
        this.timeScored = timeScored;
    }

}
