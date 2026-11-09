# YOUR NAME – MATH WHIZ

A web-based mathematical game built with **Java Servlets, JSP and JDBC (MySQL)**. Players enter their name, answer four questions (prime number, modulus, two trigonometry), see their score out of 10, and the result is saved to a database and shown on a Top 10 leaderboard.

## Project Objectives
- Build reusable Java methods for mathematical calculations (answers are always computed, never hard-coded).
- Apply conditional logic and algorithm integration.
- Understand Servlet request/response processing.
- Use JDBC with `PreparedStatement` for INSERT and SELECT.
- Retrieve, sort and display database records (leaderboard).
- Document and submit a project via GitHub.

## Technologies Used
Java 17, Servlet 4.0 / JSP / JSTL, JDBC, MySQL 8, Apache Tomcat 9, Maven, JUnit 5, HTML/CSS.

## Software Requirements
- JDK 17+
- IntelliJ IDEA (Ultimate has built-in Tomcat support; Community can use the Smart Tomcat plugin)
- Apache Tomcat **9.x** (uses the `javax.*` namespace)
- MySQL Server 8+

## Project Structure
```
MathWhiz/
├── pom.xml
├── database/math_whiz_db.sql
├── src/main/java/
│   ├── controller/GameServlet.java
│   ├── model/GameScore.java
│   ├── service/MathService.java
│   └── dao/{ScoreDAO.java, DBConnection.java}
├── src/main/webapp/
│   ├── index.jsp, game.jsp, result.jsp, leaderboard.jsp
│   ├── css/style.css
│   └── WEB-INF/web.xml
├── src/test/java/service/MathServiceTest.java
└── README.md
```

## Database Configuration
1. Run `database/math_whiz_db.sql` in MySQL Workbench or the `mysql` client.
2. Open `src/main/java/dao/DBConnection.java` and set your MySQL `USER` and `PASSWORD`
   (or set the environment variables `DB_URL`, `DB_USER`, `DB_PASSWORD`).

### Database Creation SQL
```sql
CREATE DATABASE IF NOT EXISTS math_whiz_db;
USE math_whiz_db;

CREATE TABLE IF NOT EXISTS game_scores (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    player_name VARCHAR(100) NOT NULL,
    score       INT          NOT NULL,
    difficulty  VARCHAR(20)  NOT NULL DEFAULT 'NORMAL',
    played_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);
```

## Steps to Run (IntelliJ IDEA)
1. **File → Open** and select the `MathWhiz` folder (the `pom.xml`). Let Maven import dependencies.
2. Set **File → Project Structure → SDK** to JDK 17+.
3. Open `src/main/webapp/WEB-INF/web.xml` and replace `YOUR NAME` with your real name.
4. Create the database (see above) and edit `DBConnection.java` credentials.
5. **Run → Edit Configurations → + → Tomcat Server → Local** (Ultimate)
   - Choose your Tomcat 9 installation.
   - **Deployment** tab → **+ → Artifact → MathWhiz:war exploded**.
   - Set **Application context** to `/MathWhiz`.
6. Click **Run**. Open <http://localhost:8080/MathWhiz/>.

*IntelliJ Community:* install the **Smart Tomcat** plugin, add a configuration pointing to Tomcat 9, the module's `src/main/webapp` as the deployment directory, and context path `/MathWhiz`.

### Alternative: Run with Jetty (no Tomcat install needed)
The `pom.xml` includes the Jetty Maven plugin, so the app can also be started without Tomcat:
1. Open the **Maven** panel in IntelliJ → **MathWhiz → Plugins → jetty → jetty:run** (or from a terminal: `mvn jetty:run`).
2. Wait for `Started Jetty Server`, then open <http://localhost:8080/MathWhiz/>.
3. Press Stop (■) to shut it down.

Run unit tests with `mvn test` or right-click `MathServiceTest` → Run.

## Game Rules and Scoring
| Question | Area | Marks |
|----------|------|-------|
| Q1 | Prime number (answer 1 = prime, 0 = not prime) | 2 |
| Q2 | Modulus (integers only, divisor never 0) | 2 |
| Q3 | Trigonometry (sin/cos/tan, degrees) | 3 |
| Q4 | Trigonometry (sin/cos/tan, degrees) | 3 |
| **Total** | | **10** |

- Numbers less than 2 are not prime.
- Angles are given in degrees and converted to radians with `Math.toRadians` before `Math.sin/cos/tan`.
- Decimal answers are accepted within a tolerance of ±0.01.
- Undefined tangent angles (90°, 270°) are never asked; `calculateTan` returns `NaN` for them.
- Difficulty is `NORMAL` for all questions and is stored in the database.
- Leaderboard: `ORDER BY score DESC, played_at ASC LIMIT 10`.

## Test Cases
| Test | Expected |
|------|----------|
| Prime 7 / 8 / 1 | 1 / 0 / 0 |
| 17 % 5 | 2 |
| sin 30° | 0.5 |
| cos 60° | 0.5 |
| tan 45° | 1.0 |
| Divisor 0 | Validation error (`IllegalArgumentException`) |
| All correct | 10 marks |
| Wrong answers | Marks not awarded |

## Application Screenshots

### Welcome Page
![Welcome Page](screenshots/home.png)

### Game Page
![Game Page](screenshots/game.png)

### Result Page
![Result Page](screenshots/result.png)

### Leaderboard
![Leaderboard](screenshots/leaderboard.png)

## GitHub Project Directory Link
https://github.com/username/repository/tree/main/MathWhiz
