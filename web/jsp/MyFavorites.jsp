<%@ page import="java.util.ArrayList" %>
<%@ page import="beans.TravelImage" %>
<%@ page import="dao.TravelImageDao" %>
<%@ page import="dao.TravelUserDao" %>
<%@ page import="java.sql.SQLException" %><%--
  Created by IntelliJ IDEA.
  User: zhongyinjun
  Date: 2020/7/29
  Time: 12:17 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Favorites</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/myPhotos&Favourites.css">
</head>
<body>
<%@include file="part/Navigator.jsp" %>

<%
    if (request.getAttribute("processed") == null) {
        request.getRequestDispatcher("/MyFavoriteServlet").forward(request, response);
    }
    ArrayList<Integer> targetImages = (ArrayList<Integer>) request.getAttribute("targetImages");
    int row = 5;
    int lastPage = targetImages.size() % row == 0 ? targetImages.size() / row : targetImages.size() / row + 1;
%>

<div class="content-wrapper">
    <div class="title">
        <span class="left">
            <%
                if (request.getParameter("friendID") != null) {
                    try {
                        String userName = new TravelUserDao().getUserName(Integer.parseInt(request.getParameter("friendID")));
                        out.println(userName + "'s favorites");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                } else {
                    out.println("My favorites");
                }
            %>
        </span>
    </div>

    <div class="content">
        <div id="image-view">
            <%
                if (lastPage == 0) {
                    out.println("<div style='height:300px; line-height:300px; text-align: center'><p style='margin: 0'>Why not add some to your favorites now!</p></div>");
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
                            if (request.getParameter("friendID") == null) {
                                out.println("<a href='" + request.getContextPath() + "/DeleteFavorServlet?id=" + travelImage.getImageID() + "'>");
                                out.println("<button class='delete' value='delete'>Delete</button>");
                                out.println("</a>");
                            }
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

    <%
        if (request.getParameter("friendID") == null) {
            ArrayList<Integer> myFootprints = (ArrayList<Integer>) request.getAttribute("myFootprints");

            out.println("<div class='title'>");
            out.println("<span class='left'>My footprints</span>");
            out.println("</div>");
            out.println("<div class='content'>");
            out.println("<div id='myFootprintsWrapper'>");
            if (myFootprints.size() == 0) {
                out.println("<div style='height:100px; line-height:100px; text-align: center'><p style='margin: 0'>You haven't view any yet!</p></div>");
            } else {
                out.println("<table>");
                for (int i = 0; i < myFootprints.size(); i++) {
                    TravelImage travelImage = new TravelImageDao().getImageDetail(myFootprints.get(i));
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
                    out.println("</div>");
                    out.println("</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
            }
            out.println("</div>");
            out.println("</div>");
        }
    %>
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
        resizeAndReposition(300, 300, "myFootprintsWrapper", true);
    }
</script>
<script>
    function toDelete(ImageID) {
        const sure = confirm("Are you sure to delete this photo?");
        if (sure) {
            let xmlhttp;
            if (window.XMLHttpRequest) {
                // IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
                xmlhttp = new XMLHttpRequest();
            } else {
                // IE6, IE5 浏览器执行代码
                xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            xmlhttp.onreadystatechange = function () {
                if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                    window.location.reload();
                }
            };
            xmlhttp.open("POST", "./deletePhoto.php", true);
            xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xmlhttp.send('ImageID=' + ImageID);
        }
    }
</script>
</body>
</html>
