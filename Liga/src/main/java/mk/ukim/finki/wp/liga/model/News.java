package mk.ukim.finki.wp.liga.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Data
@Getter
@Setter
public class News {
    private String title;
    private Sport sport;
    private String content;
    private LocalDateTime publishedDateTime;

    public News(String title, Sport sport, String content, LocalDateTime publishedDateTime) {
        this.title = title;
        this.sport = sport;
        this.content = content;
        this.publishedDateTime = publishedDateTime;
    }
}
