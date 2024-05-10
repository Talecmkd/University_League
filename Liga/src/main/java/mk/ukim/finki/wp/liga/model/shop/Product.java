package mk.ukim.finki.wp.liga.model.shop;

import lombok.Getter;
import lombok.Setter;
import mk.ukim.finki.wp.liga.model.Team;

import java.awt.*;

@Getter
@Setter
public class Product {
    private String name;
    private Team teamMerch;
    private String description;
    private double price;
    private Image image;

    public Product(String name, Team teamMerch, String description, double price, Image image) {
        this.name = name;
        this.teamMerch = teamMerch;
        this.description = description;
        this.price = price;
        this.image = image;
    }
}
