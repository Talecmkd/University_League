package mk.ukim.finki.wp.liga.model.shop;

import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @JoinColumn(name = "cart_id")
    private List<FootballProduct> itemsFootball;

    @OneToMany
    @JoinColumn(name = "cart_id")
    private List<BasketballProduct> itemsBasketball;

    @OneToMany
    @JoinColumn(name = "cart_id")
    private List<VolleyballProduct> itemsVolleyball;

    public ShoppingCart() {
        this.itemsFootball = new ArrayList<>();
        this.itemsBasketball = new ArrayList<>();
        this.itemsVolleyball = new ArrayList<>();
    }
}
