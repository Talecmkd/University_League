package mk.ukim.finki.wp.liga.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Data
@Setter
@Getter
@Entity
public class FootballPlayerScored {

    @Id
    private Long Id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "football_player_id")
    private FootballPlayer player;
    private LocalDateTime timeScored;
    private int goalsScored;
    private int assistsScored;
    private int saves;
    @ManyToOne
    @JoinColumn(name = "football_match_id")
    private FootballMatch footballMatch;

    public FootballPlayerScored(FootballPlayer player, LocalDateTime timeScored, FootballMatch match) {
        this.player = player;
        this.timeScored = timeScored;
        this.footballMatch = match;
        this.goalsScored = 0;
        this.assistsScored = 0;
        this.saves = 0;
    }


    public FootballPlayerScored() {

    }
}
