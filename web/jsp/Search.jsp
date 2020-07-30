<%@ page import="java.util.ArrayList" %>
<%@ page import="beans.TravelImage" %>
<%@ page import="dao.TravelImageDao" %><%--
  Created by IntelliJ IDEA.
  User: zhongyinjun
  Date: 2020/7/26
  Time: 9:51 上午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Search</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/search.css">
</head>
<body>
<%@include file="part/Navigator.jsp" %>

<%
    if (request.getAttribute("processed") == null) {
        request.getRequestDispatcher("/SearchServlet").forward(request, response);
    }
    ArrayList<Integer> targetImages = (ArrayList<Integer>) request.getAttribute("targetImages");
    int row = 5;
    int lastPage = targetImages.size() % row == 0 ? targetImages.size() / row : targetImages.size() / row + 1;
%>

<div class="content-wrapper">
    <div id="search-bar">
        <div class="title">
            <span class="left">Search</span>
            <span id="filter-description"
                  class="right selection ${param.description != null ? "selected" : ""}"
                  onclick="descriptionOnDisplay()">Description</span>
            <span id="filter-title"
                  class="right selection ${param.description == null ? "selected" : ""}"
                  onclick="titleOnDisplay()">Title</span>
            <span class="right">Filter type:</span>
        </div>

        <div class="content">
            <form method="get" id="form-title" class="${param.description == null ? "active" : ""}">
                <input name="title" id="input-title" type="text"
                       value="${param.title != null ? param.title : null}"
                       placeholder="Please enter the title. For example(beach, sea, travel)">
                Show by:
                recent <input type="radio" name="sequence" value="recent" checked>
                hot <input type="radio" name="sequence" value="hot">
                <input id="submit-title" type="submit" value="Filter">
            </form>

            <form method="get" id="form-description" class="${param.description != null ? "active" : ""}">
                        <textarea name="description" id="input-description"
                                  placeholder="Please enter the description.">${param.description != null ? param.description : null}</textarea>
                Show by:
                recent <input type="radio" name="sequence" value="recent" checked>
                hot <input type="radio" name="sequence" value="hot">
                <input id="submit-description" type="submit" value="filter">
            </form>
        </div>
    </div>

    <div id="search-results">
        <div class="title">
            <span class="left">Results</span>
        </div>

        <div class="content">
            <div id="image-view">
                <%
                    if (lastPage == 0) {
                        out.println("<div style='height:300px; line-height:300px; text-align: center'><p style='margin: 0'>Sorry, no match is found :(</p></div>");
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
</div>

<%@ include file="part/Footer.jsp" %>

<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/common.js"></script>
<script defer="defer">
    resizeAndReposition(300, 300, "image-view", true);
    document.getElementById('search').className = 'left active';
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
    let a = document.getElementsByClassName("pages");
    for (let i = 0; i < a.length; i++) {
        a[i].setAttribute("onclick", "show(" + i + ")");
    }

</script>
<script src="${pageContext.request.contextPath}/js/changeFilterType.js"></script>
</body>
</html>
