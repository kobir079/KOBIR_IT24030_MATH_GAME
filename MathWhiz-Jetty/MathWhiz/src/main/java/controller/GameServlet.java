package controller;

import dao.ScoreDAO;
import model.GameScore;
import service.MathService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

@WebServlet("/game")
public class GameServlet extends HttpServlet {

    private static final String DIFFICULTY = "NORMAL";
    private static final String[] TRIG_FUNCTIONS = {"sin", "cos", "tan"};
    private static final int[] ANGLES = {0, 30, 45, 60, 90, 120, 135, 150, 180, 210, 225, 240, 270, 300, 315, 330, 360};

    private final ScoreDAO scoreDAO = new ScoreDAO();
    private final Random random = new Random();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("leaderboard".equals(action)) {
            showLeaderboard(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if ("start".equals(action)) {
            startGame(req, resp);
        } else if ("submit".equals(action)) {
            submitAnswers(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }

    // ---------- Screen 1 -> Screen 2 : generate four questions ----------
    private void startGame(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("playerName");
        if (name == null || name.trim().isEmpty()) {
            req.setAttribute("error", "Please enter your name.");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
            return;
        }
        name = name.trim();
        if (name.length() > 100) name = name.substring(0, 100);

        HttpSession session = req.getSession(true);
        session.setAttribute("playerName", name);

        // Q1 prime: number 1..100
        session.setAttribute("primeNum", 1 + random.nextInt(100));
        // Q2 modulus: integers only, divisor never zero
        session.setAttribute("modA", 10 + random.nextInt(90));
        session.setAttribute("modB", 2 + random.nextInt(11));
        // Q3 and Q4 trigonometry
        setTrigQuestion(session, "trig1");
        setTrigQuestion(session, "trig2");

        req.getRequestDispatcher("/game.jsp").forward(req, resp);
    }

    private void setTrigQuestion(HttpSession session, String key) {
        String fn = TRIG_FUNCTIONS[random.nextInt(TRIG_FUNCTIONS.length)];
        int angle;
        do {
            angle = ANGLES[random.nextInt(ANGLES.length)];
        } while (fn.equals("tan") && Double.isNaN(MathService.calculateTan(angle))); // avoid undefined tan
        session.setAttribute(key + "Fn", fn);
        session.setAttribute(key + "Angle", angle);
    }

    // ---------- Screen 2 -> Screen 3 : evaluate answers ----------
    private void submitAnswers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("primeNum") == null) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        int primeNum = (Integer) session.getAttribute("primeNum");
        int modA = (Integer) session.getAttribute("modA");
        int modB = (Integer) session.getAttribute("modB");
        String t1Fn = (String) session.getAttribute("trig1Fn");
        int t1Angle = (Integer) session.getAttribute("trig1Angle");
        String t2Fn = (String) session.getAttribute("trig2Fn");
        int t2Angle = (Integer) session.getAttribute("trig2Angle");
        String playerName = (String) session.getAttribute("playerName");

        // ---- validate and parse the player's answers ----
        int primeAns, modAns;
        double trig1Ans, trig2Ans;
        try {
            primeAns = Integer.parseInt(req.getParameter("primeAnswer").trim());
            if (primeAns != 0 && primeAns != 1) {
                throw new IllegalArgumentException("Prime answer must be 1 (prime) or 0 (not prime).");
            }
            modAns = Integer.parseInt(req.getParameter("modAnswer").trim());   // integers only
            trig1Ans = Double.parseDouble(req.getParameter("trig1Answer").trim());
            trig2Ans = Double.parseDouble(req.getParameter("trig2Answer").trim());
        } catch (IllegalArgumentException | NullPointerException e) {
            String msg = (e instanceof NumberFormatException || e instanceof NullPointerException)
                    ? "Please answer all four questions with valid numbers (Q1 = 1 or 0, Q2 = whole number)."
                    : e.getMessage();
            req.setAttribute("error", msg);
            req.getRequestDispatcher("/game.jsp").forward(req, resp);
            return;
        }

        // ---- evaluate using the Java methods ----
        int expectedPrime = MathService.isPrime(primeNum);
        int expectedMod = MathService.calculateMod(modA, modB);   // modB is never 0
        double expectedTrig1 = MathService.calculateTrig(t1Fn, t1Angle);
        double expectedTrig2 = MathService.calculateTrig(t2Fn, t2Angle);

        boolean primeCorrect = primeAns == expectedPrime;
        boolean modCorrect = modAns == expectedMod;
        boolean trig1Correct = MathService.isClose(expectedTrig1, trig1Ans);
        boolean trig2Correct = MathService.isClose(expectedTrig2, trig2Ans);

        int totalScore = 0;
        if (primeCorrect) totalScore += 2;
        if (modCorrect)   totalScore += 2;
        if (trig1Correct) totalScore += 3;
        if (trig2Correct) totalScore += 3;

        // ---- save to database ----
        try {
            scoreDAO.save(new GameScore(playerName, totalScore, DIFFICULTY));
        } catch (SQLException e) {
            log("Could not save score", e);
            req.setAttribute("dbError", "Your score could not be saved: " + e.getMessage());
        }

        req.setAttribute("playerName", playerName);
        req.setAttribute("totalScore", totalScore);
        req.setAttribute("primeCorrect", primeCorrect);
        req.setAttribute("modCorrect", modCorrect);
        req.setAttribute("trig1Correct", trig1Correct);
        req.setAttribute("trig2Correct", trig2Correct);
        req.setAttribute("expectedPrime", expectedPrime);
        req.setAttribute("expectedMod", expectedMod);
        req.setAttribute("expectedTrig1", String.format("%.4f", expectedTrig1));
        req.setAttribute("expectedTrig2", String.format("%.4f", expectedTrig2));

        // game is over: clear questions so answers cannot be resubmitted
        session.removeAttribute("primeNum");
        req.getRequestDispatcher("/result.jsp").forward(req, resp);
    }

    // ---------- Screen 4 : leaderboard ----------
    private void showLeaderboard(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<GameScore> top = scoreDAO.getTop10();
            req.setAttribute("scores", top);
        } catch (SQLException e) {
            log("Could not load leaderboard", e);
            req.setAttribute("dbError", "Could not load the leaderboard: " + e.getMessage());
        }
        req.getRequestDispatcher("/leaderboard.jsp").forward(req, resp);
    }
}
