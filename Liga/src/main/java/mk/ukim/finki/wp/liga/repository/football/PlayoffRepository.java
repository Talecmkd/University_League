package mk.ukim.finki.wp.liga.repository.football;

import mk.ukim.finki.wp.liga.model.Playoff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayoffRepository extends JpaRepository<Playoff, Long> {
    Optional<Playoff> findTopByOrderByIdDesc();
}
