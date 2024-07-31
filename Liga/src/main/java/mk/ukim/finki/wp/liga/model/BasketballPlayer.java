package mk.ukim.finki.wp.liga.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.Date;

@Setter
@Getter
public class BasketballPlayer {
    @Id
    private Long id;
    private Image image;
    private String name;
    private String surname;
    private Date birthdate;
    private int index;
    private String city;
    private String position;
    private BasketballTeam team;
    private int appearances;
    private int points;
    private int assists;
    private int rebounds;

    public BasketballPlayer(Image image, String name, String surname, Date birthdate, int index,
                            String city, String position, BasketballTeam team) {
        this.image = image;
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.index = index;
        this.city = city;
        this.position = position;
        this.team = team;
        this.appearances = 0;
        this.points = 0;
        this.assists = 0;
        this.rebounds = 0;
    }

    public BasketballPlayer() {

    }

}