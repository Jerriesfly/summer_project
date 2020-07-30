package servlets;

import dao.TravelUserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class SetStatusServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("UserName") == null) {
            PrintWriter out = resp.getWriter();
            out.println("请先登陆后进行操作！");
            out.println("<script>" +
                    "setTimeout(function(){window.location.href='" + req.getContextPath() + "/jsp/Login.jsp';},1000);" +
                    "</script>");
        } else {
            String userName = (String) req.getSession().getAttribute("UserName");
            try {
                new TravelUserDao().setStatus(userName);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            resp.sendRedirect(req.getContextPath() + "/jsp/MyFriends.jsp");
        }
    }
}
