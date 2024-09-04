package mk.ukim.finki.wp.liga.config;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.liga.model.BasketballTeam;
import mk.ukim.finki.wp.liga.model.FootballTeam;
import mk.ukim.finki.wp.liga.model.VolleyballTeam;
import mk.ukim.finki.wp.liga.service.basketball.BasketballMatchService;
import mk.ukim.finki.wp.liga.service.basketball.BasketballPlayerService;
import mk.ukim.finki.wp.liga.service.basketball.BasketballTeamService;
import mk.ukim.finki.wp.liga.service.football.FootballMatchService;
import mk.ukim.finki.wp.liga.service.football.FootballPlayerService;
import mk.ukim.finki.wp.liga.service.football.FootballTeamService;
import mk.ukim.finki.wp.liga.service.volleyball.VolleyballMatchService;
import mk.ukim.finki.wp.liga.service.volleyball.VolleyballPlayerService;
import mk.ukim.finki.wp.liga.service.volleyball.VolleyballTeamService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class DataInitializer {

    private final FootballTeamService footballTeamService;
    private final FootballPlayerService footballPlayerService;
    private final FootballMatchService footballMatchService;

    private final VolleyballTeamService volleyballTeamService;
    private final VolleyballPlayerService volleyballPlayerService;
    private final VolleyballMatchService volleyballMatchService;

    private final BasketballPlayerService basketballPlayerService;
    private final BasketballTeamService basketballTeamService;
    private final BasketballMatchService basketballMatchService;

    public DataInitializer(FootballTeamService footballTeamService, FootballPlayerService footballPlayerService, FootballMatchService footballMatchService, VolleyballTeamService volleyballTeamService, VolleyballPlayerService volleyballPlayerService, VolleyballMatchService volleyballMatchService, BasketballPlayerService basketballPlayerService, BasketballTeamService basketballTeamService, BasketballMatchService basketballMatchService) {
        this.footballTeamService = footballTeamService;
        this.footballPlayerService = footballPlayerService;
        this.footballMatchService = footballMatchService;
        this.volleyballTeamService = volleyballTeamService;
        this.volleyballPlayerService = volleyballPlayerService;
        this.volleyballMatchService = volleyballMatchService;
        this.basketballPlayerService = basketballPlayerService;
        this.basketballTeamService = basketballTeamService;
        this.basketballMatchService = basketballMatchService;
    }


    @PostConstruct
    @Transactional
    public void initData() {

        if (footballTeamService.listAllTeams().isEmpty()) {  // Check if teams already exist
            for (int i = 1; i <= 8; i++) {
                this.footballTeamService.create("Team " + i, null);
            }
        }

        if (volleyballTeamService.listAllTeams().isEmpty()) {  // Check if teams already exist
            for (int i = 1; i <= 8; i++) {
                this.volleyballTeamService.create("Team " + i, null);
            }
        }

        if (basketballTeamService.listAllTeams().isEmpty()) {  // Check if teams already exist
            for (int i = 1; i <= 8; i++) {
                this.basketballTeamService.create("Team " + i, null);
            }
        }

        if (footballPlayerService.listAllPlayers().isEmpty()) {  // Check if players already exist
            for (int i = 1; i <= 8; i++) {
                this.footballPlayerService.create(null, "Player " + i, "Surname" + i, null, i, null, null, this.footballTeamService.findById((long) i));
            }
        }

        if (volleyballPlayerService.listAllPlayers().isEmpty()) {  // Check if players already exist
            for (int i = 1; i <= 8; i++) {
                this.volleyballPlayerService.create(null, "Player " + i, "Surname" + i, null, i, null, null, this.volleyballTeamService.findById((long) i));
            }
        }

        if (basketballPlayerService.listAllPlayers().isEmpty()) {  // Check if players already exist
            for (int i = 1; i <= 8; i++) {
                this.basketballPlayerService.create(null, "Player " + i, "Surname" + i, null, i, null, null, this.basketballTeamService.findById((long) i));
            }
        }


        if (footballMatchService.listAllFootballMatches().isEmpty()) {  // Check if matches already exist
            for (int i = 1; i <= 8; i++) {
                FootballTeam home = this.footballTeamService.findByName("Team " + i);
                FootballTeam away = this.footballTeamService.findByName("Team " + (i % 5 + 1)); // Rotate to the first team after the last one
                if (home != null && away != null) {
                    this.footballMatchService.createAndAddToFixtures(home, away, 0, 0, LocalDateTime.now());
                }
            }
        }

        if (volleyballMatchService.listAllVolleyballMatches().isEmpty()) {  // Check if matches already exist
            for (int i = 1; i <= 8; i++) {
                VolleyballTeam home = this.volleyballTeamService.findByName("Team " + i);
                VolleyballTeam away = this.volleyballTeamService.findByName("Team " + (i % 5 + 1)); // Rotate to the first team after the last one
                if (home != null && away != null) {
                    this.volleyballMatchService.createAndAddToFixtures(home, away, 0, 0, LocalDateTime.now());
                }
            }
        }

        if (basketballMatchService.listAllBasketballMatches().isEmpty()) {  // Check if matches already exist
            for (int i = 1; i <= 8; i++) {
                BasketballTeam home = this.basketballTeamService.findByName("Team " + i);
                BasketballTeam away = this.basketballTeamService.findByName("Team " + (i % 5 + 1)); // Rotate to the first team after the last one
                if (home != null && away != null) {
                    this.basketballMatchService.createAndAddToFixtures(home, away, 0, 0, LocalDateTime.now());
                }
            }
        }

    }
}