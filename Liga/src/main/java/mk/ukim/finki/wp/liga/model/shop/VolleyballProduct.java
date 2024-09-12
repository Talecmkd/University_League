package mk.ukim.finki.wp.liga.model.shop;

import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import mk.ukim.finki.wp.liga.model.BasketballTeam;
import mk.ukim.finki.wp.liga.model.VolleyballTeam;

import java.awt.*;

@Getter
@Setter
@Entity
public class VolleyballProduct extends Product{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private VolleyballTeam volleyballMerch;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "quantity", nullable = false, columnDefinition = "integer default 1")
    private int quantity;

    public VolleyballProduct() {
        super();
    }

    public VolleyballProduct(String name, String description, double price, String image, VolleyballTeam volleyballMerch) {
        super(name, description, price, image);
        this.volleyballMerch = volleyballMerch;
    }
}
