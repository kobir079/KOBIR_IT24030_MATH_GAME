# KOBIR – MATH WHIZ

A web-based mathematical game built with **Java Servlets, JSP and JDBC (MySQL)**. Players enter their name, answer four questions (prime number, modulus, two trigonometry), see their score out of 10, and the result is saved to a database and shown on a Top 10 leaderboard.

## Project Objectives
- Build reusable Java methods for mathematical calculations (answers are always computed, never hard-coded).
- Apply conditional logic and algorithm integration.
- Understand Servlet request/response processing.
- Use JDBC with `PreparedStatement` for INSERT and SELECT.
- Retrieve, sort and display database records (leaderboard).
- Document and submit a project via GitHub.

## Technologies Used
Java 17, Servlet 4.0 / JSP / JSTL, JDBC, MySQL 8, Apache Tomcat 9 / Jetty 9.4, Maven, JUnit 5, HTML/CSS.

## Software Requirements
- JDK 17+
- IntelliJ IDEA
- Apache Tomcat 9.x (or the Jetty Maven plugin, which needs no separate install)
- MySQL Server 8+

## Database Configuration
1. Run `database/math_whiz_db.sql` in MySQL Workbench.
2. Open `src/main/java/dao/DBConnection.java` and set your MySQL `USER` and `PASSWORD`
   (or set the environment variables `DB_URL`, `DB_USER`, `DB_PASSWORD`).

### Database Creation SQL
CREATE DATABASE IF NOT EXISTS math_whiz_db;
USE math_whiz_db;

CREATE TABLE IF NOT EXISTS game_scores (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    player_name VARCHAR(100) NOT NULL,
    score       INT          NOT NULL,
    difficulty  VARCHAR(20)  NOT NULL DEFAULT 'NORMAL',
    played_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

## Steps to Run (IntelliJ IDEA)
1. File → Open, select the `MathWhiz` folder (the `pom.xml`). Let Maven import dependencies.
2. Set File → Project Structure → SDK to JDK 17+.
3. In `src/main/webapp/WEB-INF/web.xml`, set `appTitle` to `KOBIR – MATH WHIZ`.
4. Create the database (see above) and set the credentials in `DBConnection.java`.
5. Run with Jetty: Maven panel → MathWhiz → Plugins → jetty → jetty:run
   (or `mvn jetty:run` in a terminal).
6. Open http://localhost:8080/MathWhiz/

Alternative (Tomcat 9): Run → Edit Configurations → + → Tomcat Server → Local, choose Tomcat 9,
deploy `MathWhiz:war exploded`, set Application context to `/MathWhiz`, then Run.

Run unit tests with `mvn test`.

## Game Rules and Scoring
| Question | Area | Marks |
|----------|------|-------|
| Q1 | Prime number (1 = prime, 0 = not prime) | 2 |
| Q2 | Modulus (integers only, divisor never 0) | 2 |
| Q3 | Trigonometry (sin/cos/tan, degrees) | 3 |
| Q4 | Trigonometry (sin/cos/tan, degrees) | 3 |
| **Total** | | **10** |

- Numbers less than 2 are not prime.
- Angles are in degrees and converted to radians before using Math.sin/cos/tan.
- Decimal answers are accepted within a tolerance of ±0.01.
- Undefined tangent angles (90°, 270°) are never asked.
- Difficulty is NORMAL for all questions and is stored in the database.
- Leaderboard: ORDER BY score DESC, played_at ASC LIMIT 10.

## Test Cases
| Test | Expected |
|------|----------|
| Prime 7 / 8 / 1 | 1 / 0 / 0 |
| 17 % 5 | 2 |
| sin 30° | 0.5 |
| cos 60° | 0.5 |
| tan 45° | 1.0 |
| Divisor 0 | Validation error |
| All correct | 10 marks |
| Wrong answers | Marks not awarded |

## Application Screenshots

### Welcome Page


### Game Page


### Result Page


### Leaderboard


## GitHub Project Directory Link

