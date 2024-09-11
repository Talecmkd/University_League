package mk.ukim.finki.wp.liga.model.shop;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import mk.ukim.finki.wp.liga.model.FootballTeam;

import java.awt.*;

@Getter
@Setter
@Entity
public class FootballProduct extends Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private FootballTeam footballMerch;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public FootballProduct() {
        super();
    }

    public FootballProduct(String name, String description, double price, String image, FootballTeam footballMerch) {
        super(name, description, price, image);
        this.footballMerch = footballMerch;
    }
}
