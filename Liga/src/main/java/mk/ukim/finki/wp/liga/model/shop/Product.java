package mk.ukim.finki.wp.liga.model.shop;

import lombok.Getter;
import lombok.Setter;
import mk.ukim.finki.wp.liga.model.BasketballTeam;

import java.awt.*;

@Getter
@Setter
public abstract class Product {
    private String name;
    private String description;
    private double price;
    private Image image;

    public Product(String name, String description, double price, Image image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }
}
