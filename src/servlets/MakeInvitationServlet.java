package servlets;

import dao.TravelInvitationDao;
import dao.TravelUserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class MakeInvitationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("content-type", "text/html;charset=utf-8");
        if (req.getSession().getAttribute("UserName") == null) {
            PrintWriter out = resp.getWriter();
            out.println("请先登陆后进行操作！");
            out.println("<script>" +
                    "setTimeout(function(){window.location.href='" + req.getContextPath() + "/jsp/Login.jsp';},1000);" +
                    "</script>");
        } else {
            String userName = (String) req.getSession().getAttribute("UserName");
            String addName = req.getParameter("addName");
            TravelUserDao travelUserDao = new TravelUserDao();
            PrintWriter out = resp.getWriter();
            String annoucement = "";

            try {
                if (travelUserDao.checkUserName(addName)) {
                    annoucement = "该用户不存在！";
                } else{
                    int friendID = travelUserDao.getUID(addName);
                    int UID = travelUserDao.getUID(userName);
                    if(UID == friendID){
                        annoucement = "不能给自己发送好友请求！";
                    } else if(travelUserDao.isFriend(userName, friendID)){
                        annoucement = "你们已经是好友了！";
                    } else{
                        annoucement = "好友请求发送成功！";
                        new TravelInvitationDao().makeInvitation(UID, friendID);
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            out.println(annoucement);
            out.println("<script>" +
                    "setTimeout(function(){window.location.href='" + req.getContextPath() + "/jsp/MyFriends.jsp';},1000);" +
                    "</script>");
        }
    }
}
