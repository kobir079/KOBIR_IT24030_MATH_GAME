<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${initParam.appTitle} - Result</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="card wide">
    <h1>${initParam.appTitle}</h1>
    <h2>Result</h2>
    <p class="subtitle">Player: <strong><c:out value="${playerName}"/></strong></p>

    <div class="score-box">
        <span class="score">${totalScore}</span><span class="out-of"> / 10</span>
    </div>

    <c:if test="${not empty dbError}"><div class="alert error">${dbError}</div></c:if>

    <table>
        <thead><tr><th>Question</th><th>Result</th><th>Correct Answer</th><th>Marks</th></tr></thead>
        <tbody>
        <tr>
            <td>Q1 Prime Number</td>
            <td class="${primeCorrect ? 'ok' : 'bad'}">${primeCorrect ? 'Correct' : 'Incorrect'}</td>
            <td>${expectedPrime}</td><td>${primeCorrect ? 2 : 0} / 2</td>
        </tr>
        <tr>
            <td>Q2 Modulus</td>
            <td class="${modCorrect ? 'ok' : 'bad'}">${modCorrect ? 'Correct' : 'Incorrect'}</td>
            <td>${expectedMod}</td><td>${modCorrect ? 2 : 0} / 2</td>
        </tr>
        <tr>
            <td>Q3 Trigonometry</td>
            <td class="${trig1Correct ? 'ok' : 'bad'}">${trig1Correct ? 'Correct' : 'Incorrect'}</td>
            <td>${expectedTrig1}</td><td>${trig1Correct ? 3 : 0} / 3</td>
        </tr>
        <tr>
            <td>Q4 Trigonometry</td>
            <td class="${trig2Correct ? 'ok' : 'bad'}">${trig2Correct ? 'Correct' : 'Incorrect'}</td>
            <td>${expectedTrig2}</td><td>${trig2Correct ? 3 : 0} / 3</td>
        </tr>
        </tbody>
    </table>

    <div class="actions">
        <a class="btn" href="${pageContext.request.contextPath}/index.jsp">Play Again</a>
        <a class="btn secondary" href="${pageContext.request.contextPath}/game?action=leaderboard">Leaderboard</a>
    </div>
</div>
</body>
</html>
