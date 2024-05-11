package mk.ukim.finki.wp.liga.model;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.Date;

@Setter
@Getter

public class VolleyballPlayer {

    private Long id;
    private Image image;
    private String name;
    private String surname;
    private Date birthdate;
    private int index;
    private String city;
    private String position;
    private VolleyballTeam team;
    private int appearances;
    private int servings;
    private int assists;
    private int scoredPoints;
    private int blocks;

    public VolleyballPlayer(Image image, String name, String surname, Date birthdate, int index,
                            String city, String position, VolleyballTeam team) {
        this.image = image;
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.index = index;
        this.city = city;
        this.position = position;
        this.team = team;
        this.appearances = 0;
        this.servings = 0;
        this.assists = 0;
        this.scoredPoints = 0;
        this.blocks = 0;
    }

    public VolleyballPlayer() {

    }

}