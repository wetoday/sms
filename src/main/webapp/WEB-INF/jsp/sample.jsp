<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <h1>${msg}</h1>
    <ul id="sample-info">
        <li><p>현재 <strong>${profile}</strong>에서 작동 중입니다.</p></li>
        <li><p>DB 버전: ${DBVersion}</p></li>
    </ul>
</body>
</html>
