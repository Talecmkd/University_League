package mk.ukim.finki.wp.liga.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class FootballPlayerScored {

    @Id
    private Long Id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "Id")
    private FootballPlayer player;
    private LocalDateTime timeScored;
    private int goalsScored;
    private int assistsScored;
    private int saves;
    @ManyToOne
    @JoinColumn(name = "match_id")
    private FootballMatch match;

    public FootballPlayerScored(FootballPlayer player, LocalDateTime timeScored, FootballMatch match) {
        this.player = player;
        this.timeScored = timeScored;
        this.match = match;
        this.goalsScored = 0;
        this.assistsScored = 0;
        this.saves = 0;
    }


}
