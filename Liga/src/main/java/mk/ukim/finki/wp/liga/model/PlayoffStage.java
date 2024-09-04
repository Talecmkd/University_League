package mk.ukim.finki.wp.liga.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Data
@Getter
@Setter
@Entity
public class PlayoffStage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "playoff_id")
    private Playoff playoff;

    private int stageNumber = 1; // 1 for quarter-finals, 2 for semi-finals, etc.

    @OneToMany(mappedBy = "stage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PlayoffMatch> matches;

    public PlayoffStage(Long id, Playoff playoff, List<PlayoffMatch> matches) {
        this.id = id;
        this.playoff = playoff;
        this.stageNumber = 1;
        this.matches = matches;
    }
    public  PlayoffStage(){
        this.stageNumber = 1;
    }
}
