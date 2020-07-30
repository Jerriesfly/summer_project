<%@ page import="java.util.ArrayList" %>
<%@ page import="beans.TravelImage" %>
<%@ page import="dao.TravelImageDao" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: zhongyinjun
  Date: 2020/7/28
  Time: 11:38 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Photos</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/myPhotos&Favourites.css">
</head>
<body>
<%@include file="part/Navigator.jsp"%>

<%
    if (request.getAttribute("processed") == null) {
        request.getRequestDispatcher("/MyPhotoServlet").forward(request, response);
    }
    ArrayList<Integer> targetImages = (ArrayList<Integer>) request.getAttribute("targetImages");
    int row = 5;
    int lastPage = targetImages.size() % row == 0 ? targetImages.size() / row : targetImages.size() / row + 1;
%>

<div class="content-wrapper">
    <div class="title">
        <span class="left">My photos</span>
    </div>

    <div class="content">
        <div id="image-view">
            <%
                if (lastPage == 0) {
                    out.println("<div style='height:300px; line-height:300px; text-align: center'><p style='margin: 0'>Why not upload some now!</p></div>");
                } else {
                    for (int pageNumber = 0; pageNumber < lastPage; pageNumber++) {
                        out.println("<table class='page'>");
                        for (int imgNumber = pageNumber * row; imgNumber < (pageNumber + 1) * row && imgNumber < targetImages.size(); imgNumber++) {
                            TravelImage travelImage = new TravelImageDao().getImageDetail(targetImages.get(imgNumber));
                            out.println("<tr>");
                            out.println("<td>");
                            out.println("<div class='imgDiv'>");
                            out.println("<a href='" + request.getContextPath() + "/jsp/Detail.jsp?id=" + travelImage.getImageID() + "'>");
                            out.println("<img src='" + request.getContextPath() + "/img/travel-images/small/" + travelImage.getPath() + "' alt='img-view'>");
                            out.println("</a>");
                            out.println("</div>");
                            out.println("</td>");
                            out.println("<td>");
                            out.println("<div class='descriptionDiv'>");
                            out.println("<h1>" + travelImage.getTitle() + "</h1>");
                            out.println("<p>" + travelImage.getDescription() + "</p>");
                            out.println("<a href='" + request.getContextPath() + "jsp/Upload.jsp?id=" + travelImage.getImageID() + "'>");
                            out.println("<button class='modify' value='Modify'>Modify</button>");
                            out.println("</a>");
                            out.println("<a href='" + request.getContextPath() + "/DeletePhotoServlet?id=" + travelImage.getImageID() + "'>");
                            out.println("<button class='delete' value='Delete'>Delete</button>");
                            out.println("</a>");
                            out.println("</div>");
                            out.println("</td>");
                            out.println("</tr>");
                        }
                        out.println("</table>");
                    }

                    out.println("<div id='page-wrapper'>");
                    for (int pageNumber = 0; pageNumber < lastPage; pageNumber++) {
                        out.println("<span class='pages'>" + (pageNumber + 1) + "</span>");
                    }
                    out.println("</div>");
                }
            %>
        </div>
    </div>
</div>

<%@ include file="part/Footer.jsp" %>

<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/common.js"></script>
<script>
    window.onload = function () {
        resizeAndReposition(300, 300, "image-view", true);
        document.getElementById('search').className = 'left active';
    }
</script>
<script>
    let pages = document.getElementsByClassName("page")

    function show(page) {
        for (let i = 0; i < pages.length; i++) {
            pages[i].style.display = "none";
        }
        pages[page].style.display = "block";
    }

    show(0);
</script>
<script>
    var a = document.getElementsByClassName("pages");
    for (var i = 0; i < a.length; i++) {
        a[i].setAttribute("onclick", "show(" + i + ")");
    }

</script>

<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/common.js"></script>
<script>
    window.onload = function () {
        resizeAndReposition(300, 300, "image-view", true);
    }
</script>
</body>
</html>
