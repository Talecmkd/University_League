package mk.ukim.finki.wp.liga.repository.volleyball;

import mk.ukim.finki.wp.liga.model.VolleyballPlayer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VolleyballPlayerRepository extends JpaRepository<VolleyballPlayer, Long> {
//    List<VolleyballPlayer> findByTeam_Volleyball_team_id(Long teamId);

//    List<VolleyballPlayer> findByTeam_Volleyball_team_id(Long volleyball_team_id);

}
