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
public class Playoff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "playoff", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PlayoffStage> stages;

    public Playoff(Long id, List<PlayoffStage> stages) {
        this.id = id;
        this.stages = stages;
    }
    public Playoff(){}
}
