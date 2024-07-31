package mk.ukim.finki.wp.liga.config;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.liga.model.FootballTeam;
import mk.ukim.finki.wp.liga.service.football.FootballMatchService;
import mk.ukim.finki.wp.liga.service.football.FootballPlayerService;
import mk.ukim.finki.wp.liga.service.football.FootballTeamService;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    private final FootballTeamService footballTeamService;
    private final FootballPlayerService footballPlayerService;
    private final FootballMatchService footballMatchService;

    public DataInitializer(FootballTeamService footballTeamService, FootballPlayerService footballPlayerService, FootballMatchService footballMatchService) {
        this.footballTeamService = footballTeamService;
        this.footballPlayerService = footballPlayerService;
        this.footballMatchService = footballMatchService;
    }


    @PostConstruct
    public void initData() {
        for (int i = 1; i <= 5; i++) {
            this.footballTeamService.create("Team " + i, null, null);
        }

        for (int i = 1; i <= 5; i++) {
            this.footballPlayerService.create(null, "Player " + i, null, null, 000000, null, null, this.footballTeamService.findById((long) i));
        }
        for (int i = 1; i <= 5; i++) {
            FootballTeam home = this.footballTeamService.findByName("Team " + i);
            FootballTeam away = this.footballTeamService.findByName("Team " + (i % 5 + 1)); // Rotate to the first team after the last one

            // Assuming the teams exist and service methods are correctly implemented
            if (home != null && away != null) {
                this.footballMatchService.create(home, away, 0, 0, null, null);
            }
        }
    }
}
