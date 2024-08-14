package mk.ukim.finki.wp.liga.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class VolleyballPlayerMatchStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "volleyball_player_id", nullable = false)
    private VolleyballPlayer player;
    @ManyToOne
    @JoinColumn(name = "volleyball_match_id", nullable = false)
    private VolleyballMatch volleyballMatch;

    private int servings;
    private int assists;
    private int scoredPoints;
    private int blocks;

    public VolleyballPlayerMatchStats(VolleyballPlayer player, VolleyballMatch match, int scoredPoints, int assists, int servings, int blocks) {
        this.player = player;
        this.volleyballMatch = match;
        this.scoredPoints = scoredPoints;
        this.assists = assists;
        this.blocks = blocks;
        this.servings = servings;
    }

    public VolleyballPlayerMatchStats() {

    }
}