package mk.ukim.finki.wp.liga.web.news;

import lombok.AllArgsConstructor;
import mk.ukim.finki.wp.liga.model.News;
import mk.ukim.finki.wp.liga.model.VolleyballPlayer;
import mk.ukim.finki.wp.liga.service.news.NewsService;
import mk.ukim.finki.wp.liga.service.volleyball.VolleyballPlayerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/volleyball/news")
public class VolleyballNewsController {

    private final NewsService newsService;
    private final VolleyballPlayerService volleyballPlayerService;

    @GetMapping
    public String showBasketballNews(Model model) {
        List<News> volleyballNews = newsService.findBySport("VOLLEYBALL");
        List<VolleyballPlayer> topPlayers = volleyballPlayerService.getTop5Players();
        model.addAttribute("volleyballNews", volleyballNews);
        model.addAttribute("topPlayers", topPlayers);
        model.addAttribute("bodyContent", "volleyball/volleyball_news");
        return "volleyball/master_template";
    }

    @GetMapping("details/{id}")
    public String showNewsDetails(@PathVariable Long id, Model model) {
        News news = newsService.findById(id);
        model.addAttribute("news", news);
        model.addAttribute("bodyContent", "volleyball/volleyball_news_details");
        return "volleyball/master_template";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        News news = newsService.findById(id);
        if (news == null) {
            return "redirect:/volleyball/news";
        }
        model.addAttribute("news", news);
        model.addAttribute("bodyContent","volleyball/edit_volleyball_news");
        return "volleyball/master_template";
    }

    @PostMapping("/edit/{id}")
    public String editNews(@PathVariable Long id,
                           @RequestParam String title,
                           @RequestParam String content) {
        News existingNews = newsService.findById(id);
        if (existingNews == null) {
            return "redirect:/volleyball/news";
        }

        newsService.update(id, title, "VOLLEYBALL", content);
        return "redirect:/volleyball/news";
    }

    @GetMapping("/add")
    public String showAddNewsForm(Model model) {
        model.addAttribute("news", new News());
        model.addAttribute("bodyContent", "volleyball/add_volleyball_news");
        return "volleyball/master_template";
    }

    @PostMapping("/add")
    public String addNews(@RequestParam String title,
                          @RequestParam String content) {
        newsService.create(title, "VOLLEYBALL", content);
        return "redirect:/volleyball/news";
    }

    @GetMapping("/delete/{id}")
    public String deleteNews(@PathVariable Long id) {
        newsService.delete(id);
        return "redirect:/volleyball/news";
    }
}
