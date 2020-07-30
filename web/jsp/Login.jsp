<%--
  Created by IntelliJ IDEA.
  User: zhongyinjun
  Date: 2020/7/23
  Time: 10:55 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index&register.css">
    <link id="settings" class="original" rel="stylesheet"
          href="${pageContext.request.contextPath}/css/index&register.css">
</head>
<body>
<div class="container">
    <img id="icon" class="original" src="${pageContext.request.contextPath}/img/icon.png" alt="icon">
    <h1>Welcome to gayhub!</h1>
    <form action="${pageContext.request.contextPath}/LoginServlet" method="post">
        <div id="formTable">
            <label>
                Username:<span class="error">${requestScope.userNameErr}</span><br>
                <input name="username" type="text" required value="${requestScope.userName}">
            </label><br>
            <label>
                Password:
                <input name="password" type="password" required>
            </label><br>
            <label>
                Verify code:<span class="error">${requestScope.verifyCodeErr}</span><br>
                <input name="verifyCode" type="text" id="verifyCode" required>
                <img id="verifyImage" src="${pageContext.request.contextPath}/VerifyCodeServlet" alt="verify code">
            </label><br>
            <label>
                <input type="submit" id="submit" value="Sign in">
            </label><br>
        </div>
    </form>
    <p>New to gayhub? Why not create one! <a href="${pageContext.request.contextPath}/jsp/Register.jsp">Create an
        account</a></p>
</div>

<footer>
    <p>Copyright © 2019-2021 Web fundamental. All Rights Reserved. 备案号：19302010035</p>
</footer>

<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/common.js"></script>
<script type="text/javascript">
    document.getElementById("verifyImage").onclick = function () {
        // 获取img元素
        // 为了让浏览器发送请求到servlet, 所以一定要改变src
        document.getElementById("verifyImage").src =
            "${pageContext.request.contextPath}/VerifyCodeServlet?time=" + new Date().getTime();
    };
</script>
</body>
</html>
