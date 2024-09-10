package mk.ukim.finki.wp.liga.service.news;

import mk.ukim.finki.wp.liga.model.News;

import java.util.List;

public interface NewsService {

    List<News> listAllNews();
    News findById(Long id);
    List<News> findBySport(String sport);
    News create(String title, String sport, String content);
    News update(Long id, String title, String sport, String content);
    News delete(Long id);

}
