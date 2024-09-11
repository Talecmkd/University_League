package mk.ukim.finki.wp.liga.repository.volleyball;

import mk.ukim.finki.wp.liga.model.BasketballPlayer;
import mk.ukim.finki.wp.liga.model.VolleyballPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VolleyballPlayerRepository extends JpaRepository<VolleyballPlayer, Long> {
    @Query("SELECT p FROM VolleyballPlayer p WHERE p.team.volleyball_team_id = :teamId")
    List<VolleyballPlayer> findByTeamId(@Param("teamId") Long teamId);}
