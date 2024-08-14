package mk.ukim.finki.wp.liga.repository.volleyball;

import mk.ukim.finki.wp.liga.model.VolleyballMatch;
import mk.ukim.finki.wp.liga.model.VolleyballPlayerMatchStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VolleyballPlayerScoredRepository extends JpaRepository<VolleyballPlayerMatchStats, Long> {
    List<VolleyballPlayerMatchStats> findByVolleyballMatch(VolleyballMatch volleyballMatch);
    VolleyballPlayerMatchStats findByIdAndVolleyballMatch(Long id, VolleyballMatch volleyballMatch);
}
