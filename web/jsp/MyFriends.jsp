<%@ page import="java.util.ArrayList" %>
<%@ page import="dao.TravelUserDao" %>
<%@ page import="beans.TravelUser" %><%--
  Created by IntelliJ IDEA.
  User: zhongyinjun
  Date: 2020/7/29
  Time: 12:17 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My Friends</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/myFriends.css">
</head>
<body>
<%@include file="part/Navigator.jsp" %>

<%
    if (request.getAttribute("processed") == null) {
        request.getRequestDispatcher("/MyFriendServlet").forward(request, response);
    }
    boolean isOpen = (boolean)request.getAttribute("isOpen");
    ArrayList<Integer> friendList = (ArrayList<Integer>) request.getAttribute("friendList");
    ArrayList<Integer> friendInvitationList = (ArrayList<Integer>) request.getAttribute("friendInvitationList");
%>

<div class="content-wrapper">
    <div class="title">
        <span class="left">User info</span>
    </div>

    <div class="content">
        <span id="userName">Hello, ${sessionScope.UserName}! Your favorite status is：<%= isOpen ? "public" : "hidden"%></span>
        <a class="modify" href="<%= request.getContextPath()%>/SetStatusServlet">Modify</a><br>
        <button class="button" onclick="show(0)" value="My friends">My friends</button>
        <button class="button" onclick="show(1)" value="My friend invitations">My friend invitations</button>
        <button class="button" onclick="show(2)" value="Add friends">Add friends</button>
    </div>

    <div class="wrapper">
        <div class="title">
            <span class="left">My friends</span>
        </div>

        <div class="content">
            <%
                if (friendList.size() == 0) {
                    out.println("<div style='height:100px; line-height:100px; text-align: center'><p style='margin: 0'>Why not add some friends now!</p></div>");
                } else {
                    TravelUserDao travelUserDao = new TravelUserDao();
                    for (int i = 0; i < friendList.size(); i++) {
                        out.println("<div class='single'>");
                        out.println("<span>" + travelUserDao.getUserName(friendList.get(i)) + "</span>");
                        out.println("<a class='modify' href='" + request.getContextPath() + "/jsp/MyFavorites.jsp?friendID=" + friendList.get(i) + "'>");
                        out.println("view favorite</a>");
                        out.println("</div>");
                    }
                }
            %>
        </div>
    </div>

    <div class="wrapper">
        <div class="title">
            <span class="left">My friend invitations</span>
        </div>

        <div class="content">
            <%
                if (friendInvitationList.size() == 0) {
                    out.println("<div style='height:100px; line-height:100px; text-align: center'><p style='margin: 0'>You have no friend invitations now</p></div>");
                } else {
                    TravelUserDao travelUserDao = new TravelUserDao();
                    for (int i = 0; i < friendInvitationList.size(); i++) {
                        out.println("<div class='single'>");
                        out.println("<span>" + travelUserDao.getUserName(friendInvitationList.get(i)) + "</span>");
                        out.println("<a class='modify' href='" + request.getContextPath() + "/AddFriendServlet?action=refuse&UID=" + friendInvitationList.get(i) + "'>");
                        out.println("refuse</a>");
                        out.println("<a class='modify' href='" + request.getContextPath() + "/AddFriendServlet?action=accept&UID=" + friendInvitationList.get(i) + "'>");
                        out.println("accept</a>");
                        out.println("</div>");
                    }
                }
            %>
        </div>
    </div>

    <div class="wrapper">
        <div class="title">
            <span class="left">Add friend</span>
        </div>

        <div class="content">
           <form method="post" action="<%= request.getContextPath()%>/MakeInvitationServlet">
               <input type="text" name="addName" required placeholder="input person's name">
               <input type="submit" value="submit">
           </form>
        </div>
    </div>
</div>

<jsp:include page="/jsp/part/Footer.jsp" />

<script>
    let functionDivs = document.getElementsByClassName("wrapper");

    function show(id){
        for(let i = 0; i < functionDivs.length; i++){
            functionDivs[i].setAttribute("class", "wrapper");
        }
        functionDivs[id].setAttribute("class", "wrapper show");
    }
</script>
</body>
</html>
