<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${initParam.appTitle} - Leaderboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="card wide">
    <h1>${initParam.appTitle}</h1>
    <h2>Top 10 Leaderboard</h2>

    <c:if test="${not empty dbError}"><div class="alert error">${dbError}</div></c:if>

    <table>
        <thead>
        <tr><th>Rank</th><th>Player</th><th>Score</th><th>Difficulty</th><th>Date / Time</th></tr>
        </thead>
        <tbody>
        <c:forEach var="s" items="${scores}" varStatus="st">
            <tr>
                <td>${st.count}</td>
                <td><c:out value="${s.playerName}"/></td>
                <td>${s.score}</td>
                <td>${s.difficulty}</td>
                <td><fmt:formatDate value="${s.playedAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            </tr>
        </c:forEach>
        <c:if test="${empty scores}">
            <tr><td colspan="5" class="muted">No scores yet. Be the first to play!</td></tr>
        </c:if>
        </tbody>
    </table>

    <div class="actions">
        <a class="btn" href="${pageContext.request.contextPath}/index.jsp">Play Game</a>
    </div>
</div>
</body>
</html>
