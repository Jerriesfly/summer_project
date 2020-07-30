<%--
  Created by IntelliJ IDEA.
  User: zhongyinjun
  Date: 2020/7/20
  Time: 3:14 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="carousel" uri="http://mycompany2.com" %>
<%@ taglib prefix="showImages" uri="http://mycompany3.com" %>
<html>
<head>
    <title>Home</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.staticfile.org/popper.js/1.15.0/umd/popper.min.js"></script>
    <script src="https://cdn.staticfile.org/twitter-bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
</head>
<body>
<%@include file="part/Navigator.jsp"%>

<div id="display-carousel" class="carousel slide" data-ride="carousel">

    <ul class="carousel-indicators">
        <carousel:getIndicator count="5" />
    </ul>

    <div class="carousel-inner">
        <carousel:getItems count="5"/>
    </div>

    <a class="carousel-control-prev" href="#display-carousel" data-slide="prev">
        <span class="carousel-control-prev-icon"></span>
    </a>
    <a class="carousel-control-next" href="#display-carousel" data-slide="next">
        <span class="carousel-control-next-icon"></span>
    </a>

</div>

<div class="content-wrapper">
    <div id="showImages">
        <showImages:displayRecent length="3" count="6"/>
    </div>
</div>

<jsp:include page="/jsp/part/Footer.jsp" />
</body>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/common.js"></script>
<script>
    window.onload = function () {
        document.getElementById('home').className = 'left active';
        resizeAndReposition(250, 250, "showImages", true);
    }
</script>
</html>
