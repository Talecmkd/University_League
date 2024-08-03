package mk.ukim.finki.wp.liga.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.awt.*;
import java.util.Date;

@Setter
@Getter
@Entity
public class FootballPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long football_player_id;
    private  byte [] image;
    private String name;
    private String surname;
    private Date birthdate;
    private int index;
    private String city;
    private String position;
    @ManyToOne
    private FootballTeam team;
    private int appearances;
    private int goals;
    private int assists;
    private int saves;

    public FootballPlayer(byte [] image, String name, String surname, Date birthdate, int index,
                          String city, String position, FootballTeam team) {
        this.image = image;
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.index = index;
        this.city = city;
        this.position = position;
        this.team = team;
        this.appearances = 0;
        this.goals = 0;
        this.assists = 0;
        this.saves = 0;
    }

    public FootballPlayer() {

    }

}