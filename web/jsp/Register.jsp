<%--
  Created by IntelliJ IDEA.
  User: zhongyinjun
  Date: 2020/7/14
  Time: 10:24 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index&register.css">
</head>
<body>
<div class="container">
    <img id="icon" class="original" src="${pageContext.request.contextPath}/img/icon.png" alt="icon">
    <h1>Join us now!</h1>
    <form action="${pageContext.request.contextPath}/RegisterServlet" method="post">
        <div id="formTable">
            <label>
                Username:<span class="error">${requestScope.userNameErr}</span><br>
                <input name="username" type="text" required value="${requestScope.userName}">
            </label><br>
            <label>
                E-mail:<span class="error">${requestScope.emailErr}</span><br>
                <input name="Email" type="text" required value="${requestScope.Email}">
            </label><br>

            <label>
                Password:<span class="error">${requestScope.passwordErr}</span><br>
                <p id="strength">密码强度：
                    <span>弱</span>
                    <span>中</span>
                    <span>强</span>
                </p>
                <input name="password" type="password" id="password" required>
            </label><br>
            <label>
                Please confirm your password:<br>
                <input name="repassword" type="password" required>
            </label><br>
            <label>
                Verify code:<span class="error">${requestScope.verifyCodeErr}</span><br>
                <input name="verifyCode" type="text" id="verifyCode" required>
                <img id="verifyImage" src="${pageContext.request.contextPath}/VerifyCodeServlet" alt="verify code">
            </label><br>
            <label>
                <input type="submit" id="submit" value="Sign in">
            </label>
        </div>
    </form>
    <p>Already have an account? <a href="${pageContext.request.contextPath}/jsp/Login.jsp">Click here to login</a></p>
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
<script>
    let password = document.getElementById("password");
    const spanParent = document.getElementById("strength");
    let spanDoms = spanParent.getElementsByTagName("span");
    password.onkeyup = function () {
        let index = checkPassWord(this.value);
        for (let i = 0; i < spanDoms.length; i++) {
            spanDoms[i].className = "";
            if (index) {
                spanDoms[index - 1].className = "s" + index;
            }
        }
    }

    function checkPassWord(value) {
        let modes = 0;
        if (value.length < 6) {
            return 0;
        }
        if (value.length > 7) {
            modes++;
        }
        if (value.length > 9) {
            modes++;
        }
        if (/\d/.test(value)) {
            modes++;
        }
        if (/[a-z]/.test(value)) {
            modes++;
        }
        if (/[A-Z]/.test(value)) {
            modes++;
        }
        if (/\W/.test(value)) {
            modes++;
        }
        return Math.floor((modes + 1) / 2);
    }
</script>
</body>
</html>
