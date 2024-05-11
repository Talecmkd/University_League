package mk.ukim.finki.wp.liga.model.shop;

import lombok.Getter;
import lombok.Setter;
import mk.ukim.finki.wp.liga.model.FootballTeam;

import java.awt.*;

@Getter
@Setter
public class FootballProduct extends Product {

    private FootballTeam footballMerch;

    public FootballProduct(String name, String description, double price, Image image, FootballTeam footballMerch) {
        super(name, description, price, image);
        this.footballMerch = footballMerch;
    }
}
