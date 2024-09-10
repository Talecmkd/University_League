package mk.ukim.finki.wp.liga.service.news.Impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.liga.model.Exceptions.InvalidNewsException;
import mk.ukim.finki.wp.liga.model.News;
import mk.ukim.finki.wp.liga.model.Sport;
import mk.ukim.finki.wp.liga.repository.NewsRepository;
import mk.ukim.finki.wp.liga.service.news.NewsService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    @Override
    public List<News> listAllNews() {
        return newsRepository.findAll();
    }

    @Override
    public News findById(Long id) {
        return newsRepository.findById(id).orElseThrow(InvalidNewsException::new);
    }

    @Override
    public List<News> findBySport(String sport) {
        return newsRepository.findBySport(Sport.valueOf(sport));
    }

    @Override
    public News create(String title, String sport, String content) {
        News news = new News(title, Sport.valueOf(sport), content);
        return newsRepository.save(news);
    }

    @Override
    public News update(Long id, String title, String sport, String content) {
        News news = this.findById(id);
        news.setTitle(title);
        news.setSport(Sport.valueOf(sport));
        news.setContent(content);
        return newsRepository.save(news);
    }

    @Override
    public News delete(Long id) {
        News news = this.findById(id);
        newsRepository.delete(news);
        return news;
    }
}
