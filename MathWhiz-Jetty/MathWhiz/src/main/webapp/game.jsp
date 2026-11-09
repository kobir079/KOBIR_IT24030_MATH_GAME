<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${initParam.appTitle} - Game</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="card wide">
    <h1>${initParam.appTitle}</h1>
    <p class="subtitle">Player: <strong><c:out value="${sessionScope.playerName}"/></strong> &nbsp;|&nbsp; Difficulty: NORMAL</p>

    <c:if test="${not empty error}"><div class="alert error">${error}</div></c:if>

    <form action="${pageContext.request.contextPath}/game" method="post">
        <input type="hidden" name="action" value="submit">

        <div class="question">
            <span class="marks">2 marks</span>
            <h3>Q1. Prime Number</h3>
            <p>Is <strong>${sessionScope.primeNum}</strong> a prime number? Enter <strong>1</strong> for prime, <strong>0</strong> for not prime.</p>
            <input type="number" name="primeAnswer" min="0" max="1" step="1" required>
        </div>

        <div class="question">
            <span class="marks">2 marks</span>
            <h3>Q2. Modulus</h3>
            <p>What is the remainder of <strong>${sessionScope.modA} % ${sessionScope.modB}</strong>? (integer)</p>
            <input type="number" name="modAnswer" step="1" required>
        </div>

        <div class="question">
            <span class="marks">3 marks</span>
            <h3>Q3. Trigonometry</h3>
            <p>Calculate <strong>${sessionScope.trig1Fn}(${sessionScope.trig1Angle}&deg;)</strong>. Round to 2 decimal places.</p>
            <input type="number" name="trig1Answer" step="any" required>
        </div>

        <div class="question">
            <span class="marks">3 marks</span>
            <h3>Q4. Trigonometry</h3>
            <p>Calculate <strong>${sessionScope.trig2Fn}(${sessionScope.trig2Angle}&deg;)</strong>. Round to 2 decimal places.</p>
            <input type="number" name="trig2Answer" step="any" required>
        </div>

        <button type="submit" class="btn">Submit Answers</button>
    </form>
</div>
</body>
</html>
