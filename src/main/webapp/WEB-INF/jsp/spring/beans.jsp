<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Bean Info</title>
    <style>
        ul.bean-info {
            font-size: 1.2em;
        }
    </style>
</head>
<body>
<h2><spring:message code="spring.beaninfo.root" />:</h2>
<ul class="bean-info">
    <c:forEach var="bean" items="${rootBeans}">
        <li>${bean}</li>
    </c:forEach>
</ul>

<h2><spring:message code="spring.beaninfo.web" />:</h2>
<ul class="bean-info">
    <c:forEach var="bean" items="${webBeans}">
        <li>${bean}</li>
    </c:forEach>
</ul>
</body>
</html>