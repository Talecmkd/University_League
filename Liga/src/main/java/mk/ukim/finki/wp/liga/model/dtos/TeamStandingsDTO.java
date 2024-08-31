package mk.ukim.finki.wp.liga.model.dtos;

import lombok.Getter;
import lombok.Setter;
import mk.ukim.finki.wp.liga.model.FootballTeam;

import java.util.List;
@Getter
@Setter
public class TeamStandingsDTO {
        private String teamName;
        private int matchesPlayed;
        private int wins;
        private int draws;
        private int loses;
        private int points;
        private int goalsFor;
        private int goalsAgainst;
        private int goalDifference;
        private List<String> lastFiveMatches;

        public TeamStandingsDTO(String teamName, int matchesPlayed, int wins, int draws, int loses, int points, int goalsFor, int goalsAgainst, int goalDifference, List<String> lastFiveMatches) {
            this.teamName = teamName;
            this.matchesPlayed = matchesPlayed;
            this.wins = wins;
            this.draws = draws;
            this.loses = loses;
            this.points = points;
            this.goalsFor = goalsFor;
            this.goalsAgainst = goalsAgainst;
            this.goalDifference = goalDifference;
            this.lastFiveMatches = lastFiveMatches;
    }
}
