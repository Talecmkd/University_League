//package mk.ukim.finki.wp.liga.model;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.awt.*;
//import java.util.ArrayList;
//import java.util.List;
//
//@Setter
//@Getter
//@Entity
//public class VolleyballTeam {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private String teamName;
//    @OneToMany
//    private List<VolleyballPlayer> players;
//    @ManyToMany
//    @JoinTable(
//            name = "volleyball_team_fixtures",
//            joinColumns = @JoinColumn(name = "volleyball_team_id"),
//            inverseJoinColumns = @JoinColumn(name = "volleyball_match_id")
//    )
//    private List<FootballMatch> fixtures;
//    @ManyToMany
//    @JoinTable(
//            name = "volleyball_team_results",
//            joinColumns = @JoinColumn(name = "volleyball_team_id"),
//            inverseJoinColumns = @JoinColumn(name = "volleyball_match_id")
//    )
//    private List<VolleyballMatch> volleyballResults;
//    private int teamMatchesPlayed;
//    private int teamLeaguePoints;
//    private int teamWins;
//    private int teamLoses;
//    private byte[] logo;
//
//    public VolleyballTeam(String teamName, List<VolleyballPlayer> players, byte[] logo) {
//        this.teamName = teamName;
//        this.players = players;
//        this.fixtures = new ArrayList<>();
//        this.volleyballResults = new ArrayList<>();
//        this.teamMatchesPlayed = 0;
//        this.teamLeaguePoints = 0;
//        this.teamWins = 0;
//        this.teamLoses = 0;
//        this.logo = logo;
//    }
//
//
//    public VolleyballTeam() {
//
//    }
//}
