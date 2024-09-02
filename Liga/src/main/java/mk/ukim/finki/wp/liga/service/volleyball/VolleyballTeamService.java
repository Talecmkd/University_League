package mk.ukim.finki.wp.liga.service.volleyball;

import mk.ukim.finki.wp.liga.model.*;

import java.util.List;

public interface VolleyballTeamService {

    List<VolleyballTeam> listAllTeams();


    VolleyballTeam findById(Long id);


    VolleyballTeam create(String teamName, byte [] logo);


    VolleyballTeam update(Long id, String teamName, List<VolleyballPlayer> players, byte [] logo);


    VolleyballTeam delete(Long id);

    VolleyballTeam addFixtures(Long id, List<VolleyballMatch> fixtures);

    VolleyballTeam updateStats(Long id);

    VolleyballTeam findByName(String teamName);

    VolleyballTeam saveTable(Long id, int teamPoints);
    List<VolleyballTeam> findAllOrderByPointsDesc();
}
