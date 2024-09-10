package mk.ukim.finki.wp.liga.web.news;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.liga.model.BasketballPlayer;
import mk.ukim.finki.wp.liga.model.News;
import mk.ukim.finki.wp.liga.service.basketball.BasketballPlayerService;
import mk.ukim.finki.wp.liga.service.news.NewsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/basketball/news")
public class BasketballNewsController {

    private final NewsService newsService;
    private final BasketballPlayerService basketballPlayerService;

    @GetMapping
    public String showBasketballNews(Model model) {
        List<News> basketballNews = newsService.findBySport("BASKETBALL");
        List<BasketballPlayer> topPlayers = basketballPlayerService.getTop5Players();
        model.addAttribute("basketballNews", basketballNews);
        model.addAttribute("topPlayers", topPlayers);
        model.addAttribute("bodyContent", "basketball/basketball_news");
        return "basketball/master_template";
    }

    @GetMapping("details/{id}")
    public String showNewsDetails(@PathVariable Long id, Model model) {
        News news = newsService.findById(id);
        model.addAttribute("news", news);
        model.addAttribute("bodyContent", "basketball/basketball_news_details");
        return "basketball/master_template";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        News news = newsService.findById(id);
        if (news == null) {
            return "redirect:/basketball/news";
        }
        model.addAttribute("news", news);
        model.addAttribute("bodyContent","basketball/edit_basketball_news");
        return "basketball/master_template";
    }

    @PostMapping("/edit/{id}")
    public String editNews(@PathVariable Long id,
                           @RequestParam String title,
                           @RequestParam String content) {
        News existingNews = newsService.findById(id);
        if (existingNews == null) {
            return "redirect:/basketball/news";
        }

        newsService.update(id, title, "BASKETBALL", content);
        return "redirect:/basketball/news";
    }

    @GetMapping("/add")
    public String showAddNewsForm(Model model) {
        model.addAttribute("news", new News());
        model.addAttribute("bodyContent", "basketball/add_basketball_news");
        return "basketball/master_template";
    }

    @PostMapping("/add")
    public String addNews(@RequestParam String title,
                          @RequestParam String content) {
        newsService.create(title, "BASKETBALL", content);
        return "redirect:/basketball/news";
    }

    @GetMapping("/delete/{id}")
    public String deleteNews(@PathVariable Long id) {
        newsService.delete(id);
        return "redirect:/basketball/news";
    }
}
