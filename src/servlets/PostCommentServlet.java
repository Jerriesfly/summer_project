package servlets;

import dao.TravelCommentDao;
import dao.TravelUserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class PostCommentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getSession().getAttribute("UserName") != null){
            String comment = req.getParameter("comment");
            int UID = 0;
            int imageID = Integer.parseInt(req.getParameter("imageID"));
            String userName = (String) req.getSession().getAttribute("UserName");
            try {
                UID = new TravelUserDao().getUID(userName);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                new TravelCommentDao().sendComment(comment, UID, imageID);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            req.getRequestDispatcher("/jsp/Detail.jsp?id=" + imageID).forward(req, resp);;
        } else{
            PrintWriter out = resp.getWriter();
            out.println("请先登录后发表评论！");
            out.println("<script>" +
                    "setTimeout(function(){window.location.href='" + req.getContextPath() + "/jsp/Login.jsp';},1000);" +
                    "</script>");
        }
    }
}
