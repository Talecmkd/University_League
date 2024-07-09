package mk.ukim.finki.wp.liga.service.football.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.liga.model.Exceptions.InvalidFootballPlayerException;
import mk.ukim.finki.wp.liga.model.Exceptions.InvalidFootballTeamException;
import mk.ukim.finki.wp.liga.model.FootballPlayer;
import mk.ukim.finki.wp.liga.model.FootballTeam;
import mk.ukim.finki.wp.liga.repository.football.FootballPlayerRepository;
import mk.ukim.finki.wp.liga.repository.football.FootballTeamRepository;
import mk.ukim.finki.wp.liga.service.football.FootballPlayerService;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class FootballPlayerServiceImpl implements FootballPlayerService {

    private final FootballPlayerRepository footballPlayerRepository;
    private final FootballTeamRepository footballTeamRepository;

    @Override
    public List<FootballPlayer> listAllPlayers() {
        return footballPlayerRepository.findAll();
    }

    @Override
    public FootballPlayer findById(Long id) {
        return footballPlayerRepository.findById(id).orElseThrow(InvalidFootballPlayerException::new);
    }

    @Override
    public FootballPlayer create(Image image, String name, String surname, Date birthdate, int index, String city, String position, FootballTeam team) {
        FootballTeam playerTeam = footballTeamRepository.findById(team.getId()).orElseThrow(InvalidFootballTeamException::new);
        FootballPlayer player = new FootballPlayer(image,name, surname, birthdate, index, city, position, playerTeam);
        return footballPlayerRepository.save(player);
    }

    @Override
    public FootballPlayer update(Long id, Image image, String name, String surname, Date birthdate, int index, String city, String position, FootballTeam team) {
        FootballPlayer p = this.findById(id);
        p.setImage(image);
        p.setName(name);
        p.setSurname(surname);
        p.setBirthdate(birthdate);
        p.setIndex(index);
        p.setCity(city);
        p.setPosition(position);
        FootballTeam t = footballTeamRepository.findById(team.getId()).orElseThrow(InvalidFootballTeamException::new);
        p.setTeam(t);
        return footballPlayerRepository.save(p);
    }

    @Override
    public FootballPlayer delete(Long id) {
        FootballPlayer p = this.findById(id);
        footballPlayerRepository.delete(p);
        return p;
    }

    @Override
    public FootballPlayer addAppearances(Long Id) {
        FootballPlayer p = this.findById(Id);
        int appearances = p.getAppearances();
        p.setAppearances(appearances + 1);
        return footballPlayerRepository.save(p);
    }

    @Override
    public FootballPlayer addGoals(Long Id, int goalsToAdd) {
        FootballPlayer p = this.findById(Id);
        p.setGoals(p.getGoals() + goalsToAdd);
        return footballPlayerRepository.save(p);
    }

    @Override
    public FootballPlayer addAssists(Long Id, int assistsToAdd) {
        FootballPlayer p = this.findById(Id);
        p.setAssists(p.getAssists() + assistsToAdd);
        return footballPlayerRepository.save(p);
    }

    @Override
    public FootballPlayer addSaves(Long Id, int savesToAdd) {
        FootballPlayer p = this.findById(Id);
        p.setSaves(p.getSaves() + savesToAdd);
        return footballPlayerRepository.save(p);
    }
}
