# Database Setup Guide for University League Project

## Prerequisites
- PostgreSQL installed and running
- pgAdmin (optional but recommended)
- Maven
- Java 17+

## Step 1: Create PostgreSQL Database

### Using pgAdmin:
1. Open pgAdmin and connect to your PostgreSQL server
2. Right-click on "Databases" → Create → Database
3. **Database name:** `university_league`
4. **Owner:** Your PostgreSQL user (usually `postgres`)
5. Click "Save"

### Using Command Line:
```sql
CREATE DATABASE university_league;
```

## Step 2: Create Database User (Optional but Recommended)

### Using pgAdmin:
1. Right-click on "Login/Group Roles" → Create → Login/Group Role
2. **Name:** `liga_user`
3. **Password:** `Liga123!` (or your preferred password)
4. Check "Can login?" and grant necessary privileges
5. Grant privileges to the `university_league` database

### Using Command Line:
```sql
CREATE USER liga_user WITH PASSWORD 'Liga123!';
GRANT ALL PRIVILEGES ON DATABASE university_league TO liga_user;
```

## Step 3: Update Application Configuration

The application is already configured to use PostgreSQL. The configuration in `application.properties` includes:

```properties
# PostgreSQL Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/university_league
spring.datasource.username=liga_user
spring.datasource.password=Liga123!
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

**Important:** If you used different database name, username, or password, update these values in `src/main/resources/application.properties`.

## Step 4: Run the Application and Seed Data

### Method 1: Using Maven
```bash
cd Liga
mvn spring-boot:run
```

### Method 2: Using IDE
- Import the project into your IDE (IntelliJ IDEA, Eclipse, etc.)
- Run the main class: `mk.ukim.finki.wp.liga.LigaApplication`

## Step 5: Verify Data Seeding

When the application starts, you should see output like:
```
Starting database seeding...
Seeding football data...
Seeding basketball data...
Seeding volleyball data...
Seeding news data...
Database seeding completed successfully!
```

### What Gets Seeded:

#### Teams (8 per sport):
- **Football:** Real Madrid, Barcelona, Manchester United, Liverpool, Bayern Munich, Juventus, PSG, Chelsea
- **Basketball:** Lakers, Warriors, Celtics, Bulls, Heat, Spurs, Nets, Knicks  
- **Volleyball:** Spikers, Thunder, Eagles, Lions, Titans, Wolves, Panthers, Hawks

#### Players:
- **Football:** 15 players per team (120 total)
- **Basketball:** 12 players per team (96 total)
- **Volleyball:** 10 players per team (80 total)

#### Matches:
- Round-robin matches between all teams in each sport
- Random scores and dates

#### Shop Products:
- Home and away jerseys for each team
- Team caps
- Different prices for each sport

#### News:
- 4 news articles per sport (12 total)

## Step 6: Access the Application

Once running, access the application at:
- **URL:** http://localhost:9090
- **Port:** 9090 (configured in application.properties)

## Troubleshooting

### Database Connection Issues:
1. Ensure PostgreSQL is running
2. Check database name, username, and password in `application.properties`
3. Verify PostgreSQL is listening on port 5432
4. Check firewall settings

### Seeding Issues:
1. If data already exists, the seeder will skip seeding (check console output)
2. To re-seed, drop and recreate the database
3. Check image files exist in the `images/` directory

### Image Loading Issues:
The seeder loads images from:
- `images/team_logos/` - Team logos
- `images/players/` - Player photos
- `images/jerseys/` - Jersey images for shop
- `images/hats/` - Hat images for shop

If images are missing, the seeder will continue but log warnings.

## Database Schema

The application will automatically create tables for:
- **Football:** football_team, football_player, football_match, football_player_scored, football_product
- **Basketball:** basketball_team, basketball_player, basketball_match, basketball_player_match_stats, basketball_product
- **Volleyball:** volleyball_team, volleyball_player, volleyball_match, volleyball_player_match_stats, volleyball_product
- **General:** news, shopping_cart, order
- **Playoffs:** playoff, playoff_stage, playoff_match

## Development Notes

- The `DataSeeder` class (`src/main/java/mk/ukim/finki/wp/liga/config/DataSeeder.java`) handles all seeding
- Seeding only runs if no data exists (checked by counting football teams)
- All seeded data includes realistic names and random but appropriate values
- Images are loaded as byte arrays and stored in the database

## For Colleagues

After following these steps, you'll have a fully populated database with:
- ✅ 24 teams across 3 sports
- ✅ 296 players with images and stats
- ✅ Realistic match data with scores
- ✅ Shop products for all teams
- ✅ News articles for each sport
- ✅ All necessary relationships and foreign keys

The application should be ready for development and testing! 