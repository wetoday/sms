<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Sample</title>
</head>
<body>
    <c:if test="${message != null}">
       <h2>${message}</h2>
    </c:if>

    <h1><spring:message code="sample.fileupload.title"/></h1>

    <form method="POST" enctype="multipart/form-data" action="/sample/file">
        <%--for protecting csfr attacks with spring security--%>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <table>
            <tr><td>File to upload:</td><td><input type="file" name="file" /></td></tr>
            <tr><td></td><td><input type="submit" value="Upload" /></td></tr>
        </table>
    </form>

    <ul>
        <c:forEach var="file" items="${files}">
            <li><a href="${file}">${file}</a></li>
        </c:forEach>
    </ul>
</body>
</html>