package mk.ukim.finki.wp.liga.config;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.liga.model.*;
import mk.ukim.finki.wp.liga.model.shop.*;
import mk.ukim.finki.wp.liga.service.basketball.*;
import mk.ukim.finki.wp.liga.service.football.*;
import mk.ukim.finki.wp.liga.service.news.NewsService;
import mk.ukim.finki.wp.liga.service.volleyball.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class DataSeeder {

    private final FootballTeamService footballTeamService;
    private final FootballPlayerService footballPlayerService;
    private final FootballMatchService footballMatchService;
    private final FootballProductService footballProductService;

    private final BasketballTeamService basketballTeamService;
    private final BasketballPlayerService basketballPlayerService;
    private final BasketballMatchService basketballMatchService;
    private final BasketballProductService basketballProductService;

    private final VolleyballTeamService volleyballTeamService;
    private final VolleyballPlayerService volleyballPlayerService;
    private final VolleyballMatchService volleyballMatchService;
    private final VolleyballProductService volleyballProductService;

    private final NewsService newsService;

    // Team names for each sport
    private final String[] footballTeamNames = {
        "Real Madrid", "Barcelona", "Manchester United", "Liverpool", 
        "Bayern Munich", "Juventus", "Paris Saint-Germain", "Chelsea"
    };

    private final String[] basketballTeamNames = {
        "Lakers", "Warriors", "Celtics", "Bulls", 
        "Heat", "Spurs", "Nets", "Knicks"
    };

    private final String[] volleyballTeamNames = {
        "Spikers", "Thunder", "Eagles", "Lions", 
        "Titans", "Wolves", "Panthers", "Hawks"
    };

    // Player names
    private final String[] firstNames = {
        "Aleksandar", "Nikola", "Stefan", "Marko", "Dejan", "Milan", "Vladimir", "Bojan",
        "Nemanja", "Milos", "Luka", "Filip", "Jovica", "Petar", "Dragan", "Zoran",
        "Milorad", "Branko", "Goran", "Srdjan", "Dusan", "Velimir", "Rade", "Miodrag"
    };

    private final String[] lastNames = {
        "Petrovic", "Jovanovic", "Nikolic", "Stojanovic", "Milanovic", "Djordjevic",
        "Markovic", "Stankovic", "Pavlovic", "Radic", "Milic", "Lazic", "Antic",
        "Simic", "Bogdanovic", "Zivkovic", "Tomic", "Savic", "Kostic", "Vasic"
    };

    private final String[] footballPositions = {"GK", "CB", "LB", "RB", "CM", "CAM", "CDM", "LW", "RW", "ST"};
    private final String[] basketballPositions = {"PG", "SG", "SF", "PF", "C"};
    private final String[] volleyballPositions = {"Setter", "Outside Hitter", "Middle Blocker", "Opposite", "Libero"};

    public DataSeeder(FootballTeamService footballTeamService, FootballPlayerService footballPlayerService,
                     FootballMatchService footballMatchService, FootballProductService footballProductService,
                     BasketballTeamService basketballTeamService, BasketballPlayerService basketballPlayerService,
                     BasketballMatchService basketballMatchService, BasketballProductService basketballProductService,
                     VolleyballTeamService volleyballTeamService, VolleyballPlayerService volleyballPlayerService,
                     VolleyballMatchService volleyballMatchService, VolleyballProductService volleyballProductService,
                     NewsService newsService) {
        this.footballTeamService = footballTeamService;
        this.footballPlayerService = footballPlayerService;
        this.footballMatchService = footballMatchService;
        this.footballProductService = footballProductService;
        this.basketballTeamService = basketballTeamService;
        this.basketballPlayerService = basketballPlayerService;
        this.basketballMatchService = basketballMatchService;
        this.basketballProductService = basketballProductService;
        this.volleyballTeamService = volleyballTeamService;
        this.volleyballPlayerService = volleyballPlayerService;
        this.volleyballMatchService = volleyballMatchService;
        this.volleyballProductService = volleyballProductService;
        this.newsService = newsService;
    }

    @PostConstruct
    @Transactional
    public void seedDatabase() {
        System.out.println("Starting database seeding...");

        // Check if data already exists
        if (!footballTeamService.listAllTeams().isEmpty()) {
            System.out.println("Database already seeded. Skipping...");
            return;
        }

        try {
            seedFootballData();
            seedBasketballData();
            seedVolleyballData();
            seedNews();
            System.out.println("Database seeding completed successfully!");
        } catch (Exception e) {
            System.err.println("Error during database seeding: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void seedFootballData() {
        System.out.println("Seeding football data...");
        
        // Create teams
        List<FootballTeam> footballTeams = new ArrayList<>();
        for (int i = 0; i < footballTeamNames.length; i++) {
            byte[] logo = loadImageAsBytes("images/team_logos/team" + ((i % 4) + 1));
            FootballTeam team = footballTeamService.create(footballTeamNames[i], logo);
            footballTeams.add(team);
        }

        // Create players
        Random random = new Random();
        for (FootballTeam team : footballTeams) {
            for (int i = 0; i < 15; i++) { // 15 players per team
                String firstName = firstNames[random.nextInt(firstNames.length)];
                String lastName = lastNames[random.nextInt(lastNames.length)];
                String position = footballPositions[random.nextInt(footballPositions.length)];
                int playerNumber = i + 1;
                
                byte[] playerImage = loadImageAsBytes("images/players/player" + ((i % 5) + 1));
                
                Date birthDate = new Date(System.currentTimeMillis() - (18 + random.nextInt(15)) * 365L * 24 * 60 * 60 * 1000);
                
                footballPlayerService.create(playerImage, firstName, lastName, birthDate, 
                                           playerNumber, "Skopje", position, team);
            }
        }

        // Create matches
        createFootballMatches(footballTeams);

        // Create products
        createFootballProducts(footballTeams);
    }

    private void seedBasketballData() {
        System.out.println("Seeding basketball data...");
        
        // Create teams
        List<BasketballTeam> basketballTeams = new ArrayList<>();
        for (int i = 0; i < basketballTeamNames.length; i++) {
            byte[] logo = loadImageAsBytes("images/team_logos/team" + ((i % 4) + 1));
            BasketballTeam team = basketballTeamService.create(basketballTeamNames[i], logo);
            basketballTeams.add(team);
        }

        // Create players
        Random random = new Random();
        for (BasketballTeam team : basketballTeams) {
            for (int i = 0; i < 12; i++) { // 12 players per team
                String firstName = firstNames[random.nextInt(firstNames.length)];
                String lastName = lastNames[random.nextInt(lastNames.length)];
                String position = basketballPositions[random.nextInt(basketballPositions.length)];
                int playerNumber = i + 1;
                
                byte[] playerImage = loadImageAsBytes("images/players/player" + ((i % 5) + 1));
                
                Date birthDate = new Date(System.currentTimeMillis() - (18 + random.nextInt(15)) * 365L * 24 * 60 * 60 * 1000);
                
                basketballPlayerService.create(playerImage, firstName, lastName, birthDate, 
                                             playerNumber, "Skopje", position, team);
            }
        }

        // Create matches
        createBasketballMatches(basketballTeams);

        // Create products
        createBasketballProducts(basketballTeams);
    }

    private void seedVolleyballData() {
        System.out.println("Seeding volleyball data...");
        
        // Create teams
        List<VolleyballTeam> volleyballTeams = new ArrayList<>();
        for (int i = 0; i < volleyballTeamNames.length; i++) {
            byte[] logo = loadImageAsBytes("images/team_logos/team" + ((i % 4) + 1));
            VolleyballTeam team = volleyballTeamService.create(volleyballTeamNames[i], logo);
            volleyballTeams.add(team);
        }

        // Create players
        Random random = new Random();
        for (VolleyballTeam team : volleyballTeams) {
            for (int i = 0; i < 10; i++) { // 10 players per team
                String firstName = firstNames[random.nextInt(firstNames.length)];
                String lastName = lastNames[random.nextInt(lastNames.length)];
                String position = volleyballPositions[random.nextInt(volleyballPositions.length)];
                int playerNumber = i + 1;
                
                byte[] playerImage = loadImageAsBytes("images/players/player" + ((i % 5) + 1));
                
                Date birthDate = new Date(System.currentTimeMillis() - (18 + random.nextInt(15)) * 365L * 24 * 60 * 60 * 1000);
                
                volleyballPlayerService.create(playerImage, firstName, lastName, birthDate, 
                                             playerNumber, "Skopje", position, team);
            }
        }

        // Create matches
        createVolleyballMatches(volleyballTeams);

        // Create products
        createVolleyballProducts(volleyballTeams);
    }

    private void createFootballMatches(List<FootballTeam> teams) {
        Random random = new Random();
        LocalDateTime startDate = LocalDateTime.now().minusDays(30);
        
        // Create round-robin matches
        for (int i = 0; i < teams.size(); i++) {
            for (int j = i + 1; j < teams.size(); j++) {
                FootballTeam homeTeam = teams.get(i);
                FootballTeam awayTeam = teams.get(j);
                
                int homeScore = random.nextInt(5);
                int awayScore = random.nextInt(5);
                
                LocalDateTime matchTime = startDate.plusDays(random.nextInt(60));
                
                footballMatchService.createAndAddToFixtures(homeTeam, awayTeam, homeScore, awayScore, matchTime);
            }
        }
    }

    private void createBasketballMatches(List<BasketballTeam> teams) {
        Random random = new Random();
        LocalDateTime startDate = LocalDateTime.now().minusDays(30);
        
        // Create round-robin matches
        for (int i = 0; i < teams.size(); i++) {
            for (int j = i + 1; j < teams.size(); j++) {
                BasketballTeam homeTeam = teams.get(i);
                BasketballTeam awayTeam = teams.get(j);
                
                int homeScore = 80 + random.nextInt(40);
                int awayScore = 80 + random.nextInt(40);
                
                LocalDateTime matchTime = startDate.plusDays(random.nextInt(60));
                
                basketballMatchService.createAndAddToFixtures(homeTeam, awayTeam, homeScore, awayScore, matchTime);
            }
        }
    }

    private void createVolleyballMatches(List<VolleyballTeam> teams) {
        Random random = new Random();
        LocalDateTime startDate = LocalDateTime.now().minusDays(30);
        
        // Create round-robin matches
        for (int i = 0; i < teams.size(); i++) {
            for (int j = i + 1; j < teams.size(); j++) {
                VolleyballTeam homeTeam = teams.get(i);
                VolleyballTeam awayTeam = teams.get(j);
                
                int homeScore = random.nextInt(4);
                int awayScore = random.nextInt(4);
                
                LocalDateTime matchTime = startDate.plusDays(random.nextInt(60));
                
                volleyballMatchService.createAndAddToFixtures(homeTeam, awayTeam, homeScore, awayScore, matchTime);
            }
        }
    }

    private void createFootballProducts(List<FootballTeam> teams) {
        for (FootballTeam team : teams) {
            // Jersey
            footballProductService.createNewFootballProduct(
                team.getTeamName() + " Home Jersey", 
                "Official home jersey for " + team.getTeamName(),
                59.99,
                "jersey1.jpg",
                team.getId()
            );
            
            // Away Jersey
            footballProductService.createNewFootballProduct(
                team.getTeamName() + " Away Jersey", 
                "Official away jersey for " + team.getTeamName(),
                59.99,
                "jersey2.jpeg",
                team.getId()
            );
            
            // Hat
            footballProductService.createNewFootballProduct(
                team.getTeamName() + " Cap", 
                "Official cap for " + team.getTeamName(),
                19.99,
                "hat1.jpeg",
                team.getId()
            );
        }
    }

    private void createBasketballProducts(List<BasketballTeam> teams) {
        for (BasketballTeam team : teams) {
            // Jersey
            basketballProductService.createNewBasketballProduct(
                team.getTeamName() + " Home Jersey", 
                "Official home jersey for " + team.getTeamName(),
                69.99,
                "jersey1.jpg",
                team.getId()
            );
            
            // Away Jersey
            basketballProductService.createNewBasketballProduct(
                team.getTeamName() + " Away Jersey", 
                "Official away jersey for " + team.getTeamName(),
                69.99,
                "jersey2.jpeg",
                team.getId()
            );
            
            // Hat
            basketballProductService.createNewBasketballProduct(
                team.getTeamName() + " Cap", 
                "Official cap for " + team.getTeamName(),
                24.99,
                "hat1.jpeg",
                team.getId()
            );
        }
    }

    private void createVolleyballProducts(List<VolleyballTeam> teams) {
        for (VolleyballTeam team : teams) {
            // Jersey
            volleyballProductService.createNewVolleyballProduct(
                team.getTeamName() + " Home Jersey", 
                "Official home jersey for " + team.getTeamName(),
                49.99,
                "jersey1.jpg",
                team.getVolleyball_team_id()
            );
            
            // Away Jersey
            volleyballProductService.createNewVolleyballProduct(
                team.getTeamName() + " Away Jersey", 
                "Official away jersey for " + team.getTeamName(),
                49.99,
                "jersey2.jpeg",
                team.getVolleyball_team_id()
            );
            
            // Hat
            volleyballProductService.createNewVolleyballProduct(
                team.getTeamName() + " Cap", 
                "Official cap for " + team.getTeamName(),
                17.99,
                "hat1.jpeg",
                team.getVolleyball_team_id()
            );
        }
    }

    private void seedNews() {
        System.out.println("Seeding news data...");
        
        String[] footballNews = {
            "Real Madrid Wins Champions League Final",
            "Barcelona Signs New Star Player",
            "Manchester United's Amazing Comeback Victory",
            "Liverpool Extends Winning Streak to 10 Games"
        };
        
        String[] basketballNews = {
            "Lakers Defeat Warriors in Overtime Thriller",
            "Celtics Sign MVP Candidate",
            "Bulls Complete Perfect Home Season",
            "Heat Advance to Conference Finals"
        };
        
        String[] volleyballNews = {
            "Spikers Win National Championship",
            "Thunder Defeats Eagles in Five Sets",
            "Lions Sign International Star",
            "Titans Complete Undefeated Season"
        };

        // Create football news
        for (String title : footballNews) {
            newsService.create(title, "FOOTBALL", 
                "This is detailed content for " + title + ". Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud " +
                "exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
        }

        // Create basketball news
        for (String title : basketballNews) {
            newsService.create(title, "BASKETBALL", 
                "This is detailed content for " + title + ". Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud " +
                "exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
        }

        // Create volleyball news
        for (String title : volleyballNews) {
            newsService.create(title, "VOLLEYBALL", 
                "This is detailed content for " + title + ". Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud " +
                "exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.");
        }
    }

    private byte[] loadImageAsBytes(String basePath) {
        try {
            // Try different extensions
            String[] extensions = {".png", ".jpg", ".jpeg", ".webp"};
            
            for (String ext : extensions) {
                Path imagePath = Paths.get(basePath + ext);
                if (Files.exists(imagePath)) {
                    return Files.readAllBytes(imagePath);
                }
            }
            
            // If no image found, return null (will be handled gracefully)
            System.out.println("Warning: Image not found for path: " + basePath);
            return null;
        } catch (IOException e) {
            System.err.println("Error loading image: " + basePath + " - " + e.getMessage());
            return null;
        }
    }
} 