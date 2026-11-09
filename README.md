# KOBIR_IT24030_MATH_GAME
Live Lab Test #2 • Java Web Application Development

Developed using Java Servlet, JSP, JDBC, and MySQL Database.

01. Project Overview & Interactive Sequential Workflow
Mohit's Math Game is an engaging, gamified server-side mathematical web application built using Java Servlets, JSP, JDBC, and MySQL.

🎮 Step-by-Step Question & MCQ Flow:
The game runs sequentially—one challenge at a time:

Challenge 1: Prime Number Verification (2 Marks)

Player inputs a number.
The server alters it with a random offset (±1..10) to create a mutated challenge number.
Player selects between 1 (indicating Prime) and 0 (indicating Not Prime).
Java method MathService.isPrime() verifies the answer.
Challenge 2: Modulus Operation (2 Marks)

Player inputs Dividend & Divisor.
The server alters them by (±1..10) and calculates the remainder via MathService.calculateMod().
Player selects the correct remainder from 4 Multiple Choice Options (MCQ).
Challenge 3: Trigonometry 1 (3 Marks)

Player provides a Normal Digit, chooses a Trigonometric Operator (sin, cos, tan), and enters an Angle in degrees.
Server alters the digit by (±1..10) and computes:
Result
=
Mutated Digit
×
Operator
(
Angle
∘
)
Player selects the correct answer from 4 Multiple Choice Options (MCQ).
Challenge 4: Trigonometry 2 (3 Marks)

Player provides another digit, operator, and angle.
Server mutates the digit and generates 4 Multiple Choice Options (MCQ).
Evaluated via MathService methods.
Result & Top 10 Leaderboard (Total: 10 Marks)

Displays player's score, performance verdict, and full question breakdown.
Automatically saves player name, score, difficulty ("NORMAL"), and timestamp into MySQL using JDBC PreparedStatement.
Displays the Top 10 Leaderboard sorted by score DESC, played_at ASC.
02. Technologies & Software Requirements
Java Development Kit (JDK): Version 17+ / 21+
Server: Apache Tomcat 10.1+ (Jakarta EE 9/10)
Web Technologies: Jakarta Servlet 5.0+, Jakarta JSP 3.0+, HTML5, CSS3
Database: MySQL Server 8.0+ (XAMPP MySQL compatible)
Database Connectivity: Pure JDBC via ScoreDAO.java
Build Tool: Apache Maven 3.8+ (Compatible with Eclipse, IntelliJ IDEA, NetBeans)
03. Game Rules & Mark Distribution
Question	Mathematical Area	Evaluation Criteria	Marks
Q1	Prime Number	Input 1 for Prime, 0 for Not Prime. (
<
2
 is not prime)	2
Q2	Modulus Operation	4 MCQ Options (
A
(
mod
B
)
). Divisor 
≠
0
.	2
Q3	Trigonometry 1	4 MCQ Options ($\text{Digit} \times \text{Operator}(\theta^\circ)$). Tolerance: 
±
0.05
.	3
Q4	Trigonometry 2	4 MCQ Options ($\text{Digit} \times \text{Operator}(\theta^\circ)$). Tolerance: 
±
0.05
.	3
Total			10
04. Database Configuration & Schema
Database Setup Script (database/math_whiz_db.sql)
CREATE DATABASE IF NOT EXISTS math_whiz_db;
USE math_whiz_db;

DROP TABLE IF EXISTS game_scores;

CREATE TABLE game_scores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    player_name VARCHAR(100) NOT NULL,
    score INT NOT NULL,
    difficulty VARCHAR(20) DEFAULT 'NORMAL',
    played_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Leaderboard Query:
SELECT * FROM game_scores 
ORDER BY score DESC, played_at ASC 
LIMIT 10;
Database Credentials (dao/ScoreDAO.java)
Configured for standard XAMPP / local MySQL:

URL: jdbc:mysql://localhost:3306/math_whiz_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
Username: root
Password: "" (empty string for default XAMPP MySQL)
05. How to Run the Application
Open XAMPP Control Panel and ensure MySQL is started.
In phpMyAdmin (http://localhost/phpmyadmin), run database/math_whiz_db.sql to create math_whiz_db and game_scores.
In IntelliJ / Eclipse / NetBeans, reload Maven and start Apache Tomcat 10.1+.
Open your browser and navigate to:
👉 http://localhost:8080/MohitsMathGame_war_exploded/
06. Verification & Automated Test Cases (Task 09)
The project includes unit testing for all mathematical functions verifying edge cases:

Test Case	Method Call	Expected Output	Status
Prime 7	isPrime(7)	1	PASS
Prime 8	isPrime(8)	0	PASS
Prime 1	isPrime(1)	0	PASS
Modulus	calculateMod(17, 5)	2	PASS
Sine 30°	calculateSin(30)	0.5	PASS
Cosine 60°	calculateCos(60)	0.5	PASS
Tangent 45°	calculateTan(45)	1.0	PASS
Divisor 0	calculateMod(10, 0)	ArithmeticException	PASS
07. Application Screenshots
