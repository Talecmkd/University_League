package mk.ukim.finki.wp.liga.repository.volleyball;

import mk.ukim.finki.wp.liga.model.VolleyballTeam;
import mk.ukim.finki.wp.liga.model.shop.VolleyballProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VolleyballProductRepository extends JpaRepository<VolleyballProduct, Long> {
    List<VolleyballProduct> findByVolleyballMerch(VolleyballTeam volleyballTeam);
}
