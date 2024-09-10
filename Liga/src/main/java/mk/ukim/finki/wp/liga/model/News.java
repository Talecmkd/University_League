package mk.ukim.finki.wp.liga.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@Getter
@Setter
@Entity
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long news_id;
    private String title;
    private Sport sport;
    private String content;
    private LocalDate publishedDate;

    public News(String title, Sport sport, String content) {
        this.title = title;
        this.sport = sport;
        this.content = content;
        this.publishedDate = LocalDate.now();
    }

    public News() {

    }
}
