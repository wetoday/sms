<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Sample</title>
    <style>
        ul#sample-info {
            font-size: 1.4em;
        }
    </style>
</head>
<body>
    <h1><spring:message code="sample.welcome"/></h1>
    <ul id="sample-info">
        <li>
            <strong><spring:message code="sample.profile"/>:</strong> ${profile}
        </li>
        <li>
            <strong><spring:message code="sample.dbversion"/>:</strong> ${DBVersion}
        </li>
    </ul>
</body>
</html>