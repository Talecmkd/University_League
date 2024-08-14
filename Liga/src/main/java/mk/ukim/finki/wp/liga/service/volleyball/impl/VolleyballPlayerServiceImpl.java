package mk.ukim.finki.wp.liga.service.volleyball.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.liga.model.Exceptions.InvalidVolleyballPlayerException;
import mk.ukim.finki.wp.liga.model.Exceptions.InvalidVolleyballTeamException;
import mk.ukim.finki.wp.liga.model.VolleyballPlayer;
import mk.ukim.finki.wp.liga.model.VolleyballTeam;
import mk.ukim.finki.wp.liga.repository.volleyball.VolleyballPlayerRepository;
import mk.ukim.finki.wp.liga.repository.volleyball.VolleyballTeamRepository;
import mk.ukim.finki.wp.liga.service.volleyball.VolleyballPlayerService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VolleyballPlayerServiceImpl implements VolleyballPlayerService {

    private final VolleyballPlayerRepository volleyballPlayerRepository;
    private final VolleyballTeamRepository volleyballTeamRepository;

    @Override
    public List<VolleyballPlayer> listAllPlayers() {
        return volleyballPlayerRepository.findAll();
    }

    @Override
    public VolleyballPlayer findById(Long id) {
        return volleyballPlayerRepository.findById(id).orElseThrow(InvalidVolleyballPlayerException::new);
    }

    @Override
    public VolleyballPlayer create(byte[] image, String name, String surname, Date birthdate, int index, String city, String position, VolleyballTeam team) {
        VolleyballTeam playerTeam;
        if (team != null)
            playerTeam = volleyballTeamRepository.findById(team.getVolleyball_team_id()).orElseThrow(InvalidVolleyballTeamException::new);
        else
            playerTeam = null;
        VolleyballPlayer player = new VolleyballPlayer(image, name, surname, birthdate, index, city, position, playerTeam);
        return volleyballPlayerRepository.save(player);
    }

    @Override
    public VolleyballPlayer update(Long id, byte[] image, String name, String surname, Date birthdate, int index, String city, String position, VolleyballTeam team) {
        VolleyballPlayer p = this.findById(id);
        if (image != null && image.length > 0)
            p.setImage(image);
        p.setName(name);
        p.setSurname(surname);
        p.setBirthdate(birthdate);
        p.setIndex(index);
        p.setCity(city);
        p.setPosition(position);
        VolleyballTeam t = volleyballTeamRepository.findById(team.getVolleyball_team_id()).orElseThrow(InvalidVolleyballTeamException::new);
        p.setTeam(t);
        return volleyballPlayerRepository.save(p);
    }

    @Override
    public VolleyballPlayer delete(Long id) {
        VolleyballPlayer p = this.findById(id);
        volleyballPlayerRepository.delete(p);
        return p;
    }

    @Override
    public VolleyballPlayer addAppearances(Long id) {
        VolleyballPlayer p = this.findById(id);
        int appearances = p.getAppearances();
        p.setAppearances(appearances + 1);
        return volleyballPlayerRepository.save(p);
    }

    @Override
    public VolleyballPlayer addPoints(Long id, int pointsToAdd) {
        VolleyballPlayer p = this.findById(id);
        p.setScoredPoints(p.getPoints() + pointsToAdd);
        return volleyballPlayerRepository.save(p);
    }

    @Override
    public VolleyballPlayer addAssists(Long id, int assistsToAdd) {
        VolleyballPlayer p = this.findById(id);
        p.setAssists(p.getAssists() + assistsToAdd);
        return volleyballPlayerRepository.save(p);
    }

    @Override
    public VolleyballPlayer addServings(Long id, int servingsToAdd) {
        VolleyballPlayer p = this.findById(id);
        p.setAssists(p.getServings() + servingsToAdd);
        return volleyballPlayerRepository.save(p);
    }

    @Override
    public VolleyballPlayer addBlocks(Long id, int blocksToAdd) {
        VolleyballPlayer p = this.findById(id);
        p.setBlocks(p.getBlocks() + blocksToAdd);
        return volleyballPlayerRepository.save(p);
    }

    @Override
    public List<VolleyballPlayer> getPlayersByIds(List<Long> ids) {
        return volleyballPlayerRepository.findAllById(ids);
    }

    @Override
    public List<VolleyballPlayer> getTop5Players() {
        return this.volleyballPlayerRepository.findAll()
                .stream().sorted((p1, p2) -> {
                    int score1 = p1.getPoints() * 2 + p1.getAssists();
                    int score2 = p2.getPoints() * 2 + p2.getAssists();
                    return Integer.compare(score2, score1);
                })
                .limit(5)
                .collect(Collectors.toList());
    }

//    @Override
//    public List<VolleyballPlayer> getTop5PlayersByTeam(Long teamId) {
//        return this.volleyballPlayerRepository.findByTeam_Id(teamId)
//                .stream()
//                .sorted((p1, p2) -> {
//                    int score1 = p1.getScoredPoints() * 2 + p1.getAssists();
//                    int score2 = p2.getScoredPoints() * 2 + p2.getAssists();
//                    return Integer.compare(score2, score1); // Descending order
//                })
//                .limit(5)
//                .collect(Collectors.toList());
//    }
}
