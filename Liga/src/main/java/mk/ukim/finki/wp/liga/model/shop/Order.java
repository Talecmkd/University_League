package mk.ukim.finki.wp.liga.model.shop;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FootballProduct> orderedFootballProducts;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BasketballProduct> orderedBasketballProducts;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VolleyballProduct> orderedVolleyballProducts;

    private double totalPrice;
    private boolean isPaid;

    public Order() {
        this.orderedFootballProducts = new ArrayList<>();
        this.orderedBasketballProducts = new ArrayList<>();
        this.orderedVolleyballProducts = new ArrayList<>();
        this.totalPrice = 0;
        this.isPaid = false;
    }
}
