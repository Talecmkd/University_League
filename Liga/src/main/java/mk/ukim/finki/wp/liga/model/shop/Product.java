package mk.ukim.finki.wp.liga.model.shop;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import mk.ukim.finki.wp.liga.model.BasketballTeam;

import java.awt.*;

@Getter
@Setter
@MappedSuperclass
public abstract class Product {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private String image;

    public Product(String name, String description, double price, String image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    public Product() {

    }
}
