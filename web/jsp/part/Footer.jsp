<%--
  Created by IntelliJ IDEA.
  User: zhongyinjun
  Date: 2020/7/20
  Time: 3:26 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<footer>
    <div class="container">
        <div id="left-footer">
            <table>
                <tr>
                    <td><a href="#">使用条款</a></td>
                    <td><a href="#">关于</a></td>
                </tr>
                <tr>
                    <td><a href="#">隐私保护</a></td>
                    <td><a href="#">联系我们</a></td>
                </tr>
                <tr>
                    <td><a href="#">Cookie</a></td>
                </tr>
            </table>
        </div>

        <div id="right-footer">
            <a href="#"><img src="${pageContext.request.contextPath}/img/footer/QRcode.jpeg" alt="QRcode" height="120" width="120"></a>
        </div>

        <div id="middle-footer">
            <table>
                <tr>
                    <td><a href="#"><img src="${pageContext.request.contextPath}/img/footer/github.png" alt="github" height="30" width="30"></a>
                    </td>
                    <td><a href="#"><img src="${pageContext.request.contextPath}/img/footer/shot.png" alt="shot" height="30" width="30"></a></td>
                </tr>
                <tr>
                    <td><a href="#"><img src="${pageContext.request.contextPath}/img/footer/tencent.png" alt="tencent" height="30" width="30"></a>
                    </td>
                    <td><a href="#"><img src="${pageContext.request.contextPath}/img/footer/weixin.png" alt="wechat" height="30" width="30"></a>
                    </td>
                </tr>
            </table>
        </div>
    </div>

    <div>
        <p>Copyright © 2019-2021 Web fundamental. All Rights Reserved. 备案号：19302010035</p>
    </div>
</footer>
