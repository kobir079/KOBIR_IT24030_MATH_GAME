<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${initParam.appTitle}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="card">
    <h1>${initParam.appTitle}</h1>
    <p class="subtitle">Four questions. Prime numbers, modulus and trigonometry. Maximum score: 10.</p>

    <c:if test="${not empty error}"><div class="alert error">${error}</div></c:if>

    <form action="${pageContext.request.contextPath}/game" method="post">
        <input type="hidden" name="action" value="start">
        <label for="playerName">Player Name</label>
        <input type="text" id="playerName" name="playerName" maxlength="100" required autofocus>
        <button type="submit" class="btn">Start Game</button>
    </form>

    <a class="link" href="${pageContext.request.contextPath}/game?action=leaderboard">View Leaderboard</a>
</div>
</body>
</html>
