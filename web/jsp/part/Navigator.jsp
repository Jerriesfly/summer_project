<%--
  Created by IntelliJ IDEA.
  User: zhongyinjun
  Date: 2020/7/20
  Time: 2:15 下午
  To change this template use File | Settings | File Templates.
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav id="navigator">

    <div class="left icon"><a href="${pageContext.request.contextPath}/jsp/Home.jsp">
        <img src="${pageContext.request.contextPath}/img/icon-inverse.png" height="50" width="50" alt="icon"></a>
    </div>
    <div id="home" class="left"><a href="${pageContext.request.contextPath}/jsp/Home.jsp">Home</a></div>
    <div id="search" class="left"><a href="${pageContext.request.contextPath}/jsp/Search.jsp">Search</a></div>
    <div class='right drop-down' id='drop-down'>
        <c:choose>
            <c:when test="${sessionScope['UserName'] != null }">
                <a id='drop-down-title'>${sessionScope['UserName']}<span class='caret'></span></a>
                <ul class='drop-down-menu' id='drop-down-menu'>
                    <li class='drop-down-menuItem'><a href='${pageContext.request.contextPath}/jsp/Upload.jsp'>
                        <img src='${pageContext.request.contextPath}/img/navbar/upload.png' alt='upload' height='15' width='15'>upload
                    </a></li>

                    <li class='drop-down-menuItem'><a href='${pageContext.request.contextPath}/jsp/MyPhotos.jsp'>
                        <img src='${pageContext.request.contextPath}/img/navbar/myPhotos.png' alt='myPhotos' height='15' width='15'>my photos
                    </a></li>

                    <li class='drop-down-menuItem'><a href='${pageContext.request.contextPath}/jsp/MyFavorites.jsp'>
                        <img src='${pageContext.request.contextPath}/img/navbar/myFavourites.png' alt='myFavourites' height='15' width='15'>my favourites
                    </a></li>

                    <li class='drop-down-menuItem'><a href='${pageContext.request.contextPath}/jsp/MyFriends.jsp'>
                        <img src='${pageContext.request.contextPath}/img/navbar/myFriends.png' alt='myFriends' height='15' width='15'>my friends
                    </a></li>

                    <li class='drop-down-menuItem'><a href='${pageContext.request.contextPath}/LogoutServlet'>
                        <img src='${pageContext.request.contextPath}/img/navbar/log out.png' alt='login' height='15' width='15'>log out
                    </a></li>
                </ul>
            </c:when>
            <c:otherwise>
                <a id='drop-down-title' href='${pageContext.request.contextPath}/jsp/Login.jsp'> Login</a>
            </c:otherwise>
        </c:choose>
    </div>
</nav>
