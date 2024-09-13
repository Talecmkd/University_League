package mk.ukim.finki.wp.liga.model.shop;

import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import mk.ukim.finki.wp.liga.model.BasketballTeam;

import java.awt.*;

@Getter
@Setter
@Entity
public class BasketballProduct extends Product{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private BasketballTeam basketballMerch;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "quantity", nullable = false, columnDefinition = "integer default 1")
    private int quantity;

    public BasketballProduct() {
        super();
    }

    public BasketballProduct(String name, String description, double price, String image, BasketballTeam basketballMerch) {
        super(name, description, price, image);
        this.basketballMerch = basketballMerch;
    }
}
