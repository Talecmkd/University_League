//package mk.ukim.finki.wp.liga.model;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Setter
//@Getter
//@Entity
//public class VolleyballMatch {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @ManyToOne
//    @JoinColumn(name = "volleyball_home_id")
//    private VolleyballTeam homeTeam;
//    @ManyToOne
//    @JoinColumn(name = "volleyball_away_id")
//    private VolleyballTeam awayTeam;
//    private int homeTeamPoints;
//    private int awayTeamPoints;
////    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
////    private List<VolleyballPlayerScored> playersWhoScored;
//    @ManyToMany(mappedBy = "fixtures")
//    private List<VolleyballTeam> upcomingMatches;
//    @ManyToMany(mappedBy = "volleyballResults")
//    private List<VolleyballTeam> playedMatches;
//    private LocalDateTime startTime;
//    private LocalDateTime endTime;
//
//    public VolleyballMatch(VolleyballTeam homeTeam, VolleyballTeam awayTeam, int homeTeamPoints,
//                           int awayTeamPoints, LocalDateTime startTime, LocalDateTime endTime) {
//        this.homeTeam = homeTeam;
//        this.awayTeam = awayTeam;
//        this.homeTeamPoints = homeTeamPoints;
//        this.awayTeamPoints = awayTeamPoints;
//        //this.playersWhoScored = new ArrayList<>();
//        this.startTime = startTime;
//        this.endTime = endTime;
//    }
//
//    public VolleyballMatch() {
//
//    }
//}
