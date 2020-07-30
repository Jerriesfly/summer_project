<%@ page import="beans.TravelImage" %>
<%@ page import="dao.TravelImageDao" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="dao.TravelImageFavorDao" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="beans.Comment" %>
<%@ page import="dao.TravelCommentDao" %><%--
  Created by IntelliJ IDEA.
  User: zhongyinjun
  Date: 2020/7/25
  Time: 3:48 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Details</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/detail.css">
</head>
<body>
<%@ include file="part/Navigator.jsp" %>
<div class="content-wrapper">
    <div class="title">
        <span>Details</span>
    </div>

    <div class="content">
        <%
            if (request.getAttribute("processed") == null) {
                request.getRequestDispatcher("/DetailServlet").forward(request, response);
            }
            TravelImage travelImage = (TravelImage) request.getAttribute("travelImage");
            ArrayList<Integer> commentList = (ArrayList<Integer>) request.getAttribute("commentList");
        %>
        <div id="content-title">
            <p>
                <span id="title"><%= travelImage.getTitle() %></span>
                <span id="uploader">by <%= travelImage.getUploader() %></span>
            </p>
        </div>

        <div id="content-information">
            <div id="overview">
                <img src="${pageContext.request.contextPath}/img/travel-images/medium/<%= travelImage.getPath() %>"
                     alt="img">
            </div>

            <div id="information-wrapper">
                <div id="like">
                    <div class="title">
                        <span>Like number</span>
                    </div>

                    <div class="content">
                        <span><%= travelImage.getFavored() %></span>
                    </div>
                </div>

                <div id="detail">
                    <div class="title">
                        <span>Image details</span>
                    </div>

                    <div class="content">
                        <p>Content:<span><%= travelImage.getContent() %></span></p>
                        <p>Country:<span><%= travelImage.getCountryName() %></span></p>
                        <p>City:<span><%= travelImage.getCityName() %></span></p>
                        <p>Uploaded time:<span><%= travelImage.getUploadedTime() %></span></p>
                    </div>
                </div>

                <c:choose>
                    <c:when test="${sessionScope['UserName'] == null}">
                        <form id="form" method="post" action="Login.jsp">
                            <input type="submit" id="favourite" value="Please login first">
                        </form>
                    </c:when>

                    <c:when test="${requestScope['isFavored'] == true}">
                        <form id="form" method="post"
                              action="${pageContext.request.contextPath}/DetailServlet?id=${param.id}&method=removeFavorite">
                            <input type="submit" id="favourite" class="favored" value="Remove from favorite">
                        </form>
                    </c:when>

                    <c:otherwise>
                        <form id="form" method="post"
                              action="${pageContext.request.contextPath}/DetailServlet?id=${param.id}&method=addFavorite">
                            <input type="submit" id="favourite" value="Add to favorite">
                        </form>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <div id="content-description">
            <p><%= travelImage.getDescription() %>
            </p>
        </div>
    </div>

    <div class="title">
        <span class="left">Comment</span>
    </div>

    <div class="content">
        <div id="comment">
            <%
                if (request.getSession().getAttribute("UserName") != null) {
                    out.println("<form action='" + request.getContextPath() + "/PostCommentServlet' method='post'>");
                    out.println("<input type='hidden' value='" + travelImage.getImageID() + "' name='imageID'>");
                    out.println("<textarea name='comment' id='comment-area' required></textarea>");
                    out.println("<input type='submit'>");
                }
            %>
        </div>

        <div id="user-comments">
            <%
                if (commentList.size() == 0) {
                    out.println("<div style='height:100px; line-height:100px; text-align: center'><p style='margin: 0'>There is no comment yet</p></div>");
                } else {
                    TravelCommentDao travelCommentDao = new TravelCommentDao();
                    for (int i = 0; i < commentList.size(); i++) {
                        Comment comment = travelCommentDao.getComment(commentList.get(i));
                        out.println("<div class='single-comment'>");
                        out.println("<p class='time'>" + (i + 1) + "F     " + comment.getTime() + "</p>");
                        out.println("<h3>" + comment.getUserName() + "</h3>");
                        out.println("<p>" + comment.getComment() + "</p>");
                        out.println("</div>");
                    }
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
        resizeAndReposition(400, 400, "overview", false);
    }
</script>
</body>
</html>
