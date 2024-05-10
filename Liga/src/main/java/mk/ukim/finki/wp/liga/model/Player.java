package mk.ukim.finki.wp.liga.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.awt.*;
import java.util.Date;

@Setter
@Getter

public class Player {

    private Long id;
    private Image image;
    private String name;
    private String surname;
    private Date birthdate;
    private int index;
    private String city;
    private Sport sport;
    private String position;
    private Team team;
    private int appearances;
    private int pointsScored;
    private int assists;

    public Player(Image image, String name, String surname, Date birthdate, int index,
                  String city, Sport sport, String position, Team team) {
        this.image = image;
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.index = index;
        this.city = city;
        this.sport = sport;
        this.position = position;
        this.team = team;
        this.appearances = 0;
        this.pointsScored = 0;
        this.assists = 0;
    }

    public Player() {

    }

}