package mk.ukim.finki.wp.liga.repository;

import mk.ukim.finki.wp.liga.model.News;
import mk.ukim.finki.wp.liga.model.Sport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findBySport(Sport sport);
}
