package mk.ukim.finki.wp.liga.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
@Entity
@Setter
@Getter
public class BasketballPlayerMatchStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "basketball_player_id", nullable = false)
    private BasketballPlayer player;
    @ManyToOne
    @JoinColumn(name = "basketball_match_id", nullable = false)
    private BasketballMatch basketballMatch;

    private int pointsScored;
    private int assists;
    private int rebounds;

    public BasketballPlayerMatchStats(BasketballPlayer player, BasketballMatch match, int pointsScored, int assists, int rebounds) {
        this.player = player;
        this.basketballMatch = match;
        this.pointsScored = pointsScored;
        this.assists = assists;
        this.rebounds = rebounds;
    }

    public BasketballPlayerMatchStats() {

    }
}