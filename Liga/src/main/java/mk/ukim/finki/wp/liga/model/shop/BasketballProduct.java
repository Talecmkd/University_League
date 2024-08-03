package mk.ukim.finki.wp.liga.model.shop;

import lombok.Getter;
import lombok.Setter;
import mk.ukim.finki.wp.liga.model.BasketballTeam;

import java.awt.*;

@Getter
@Setter

public class BasketballProduct extends Product{
    private BasketballTeam basketballMerch;

    public BasketballProduct(String name, String description, double price, Image image, BasketballTeam basketballMerch) {
        super(name, description, price, image);
        this.basketballMerch = basketballMerch;
    }
}
