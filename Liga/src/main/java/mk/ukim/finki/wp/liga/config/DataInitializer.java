package mk.ukim.finki.wp.liga.config;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.liga.service.football.FootballPlayerService;
import mk.ukim.finki.wp.liga.service.football.FootballTeamService;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    private final FootballTeamService footballTeamService;
    private final FootballPlayerService footballPlayerService;

    public DataInitializer(FootballTeamService footballTeamService, FootballPlayerService footballPlayerService) {
        this.footballTeamService = footballTeamService;
        this.footballPlayerService = footballPlayerService;
    }


    @PostConstruct
    public void initData() {
        for (int i = 1; i <= 5; i++) {
            this.footballTeamService.create("Team " + i, null, null);
        }

        for (int i = 1; i <= 5; i++) {
            this.footballPlayerService.create(null, "Player " + i, null, null, 000000, null, null, this.footballTeamService.findById((long) i));
        }
    }
}
