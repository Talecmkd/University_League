package mk.ukim.finki.wp.liga.model.shop;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Order {
    private Long orderId;
    private List<Product> orderedProducts;
    private double totalPrice;
    private boolean isPaid;

    public Order() {
        this.orderedProducts = new ArrayList<>();
        this.totalPrice = 0;
        this.isPaid = false;
    }
}
