//package mk.ukim.finki.wp.liga.model;
//
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//
//@Setter
//@Getter
//@Entity
//public class VolleyballPlayerScored {
//
//    @Id
//    private Long Id;
//    @OneToOne
//    @MapsId
//    @JoinColumn(name = "Id")
//    private VolleyballPlayer player;
//    private LocalDateTime timeScored;
//    private int servings;
//    private int assists;
//    private int scoredPoints;
//    private int blocks;
//
//    public VolleyballPlayerScored(VolleyballPlayer player, LocalDateTime timeScored) {
//        this.player = player;
//        this.timeScored = timeScored;
//        this.servings = 0;
//        this.assists = 0;
//        this.scoredPoints = 0;
//        this.blocks = 0;
//    }
//
//    public VolleyballPlayerScored() {
//
//    }
//}
