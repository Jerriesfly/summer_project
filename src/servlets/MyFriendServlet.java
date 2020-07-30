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
import java.util.ArrayList;

public class MyFriendServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("UserName") == null) {
            PrintWriter out = resp.getWriter();
            out.println("请先登陆后访问我的好友！");
            out.println("<script>" +
                    "setTimeout(function(){window.location.href='" + req.getContextPath() + "/jsp/Login.jsp';},1000);" +
                    "</script>");
        } else {
            int UID = 0;
            ArrayList<Integer> friendList = new ArrayList<>();
            ArrayList<Integer> friendInvitationList = new ArrayList<>();
            String userName = (String) req.getSession().getAttribute("UserName");
            TravelUserDao travelUserDao = new TravelUserDao();
            try {
                UID = travelUserDao.getUID(userName);
                friendList = travelUserDao.getFriends(UID);
                friendInvitationList = new TravelInvitationDao().getFriendInvitations(UID);
                req.setAttribute("friendList", friendList);
                req.setAttribute("friendInvitationList", friendInvitationList);
                req.setAttribute("isOpen", travelUserDao.isPublic(UID));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            req.setAttribute("processed", true);

            req.getRequestDispatcher("/jsp/MyFriends.jsp").forward(req, resp);
        }
    }
}
