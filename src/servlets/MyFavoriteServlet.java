package servlets;

import dao.TravelImageDao;
import dao.TravelUserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

public class MyFavoriteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("UserName") == null) {
            PrintWriter out = resp.getWriter();
            out.println("请先登陆后访问我的收藏！");
            out.println("<script>" +
                    "setTimeout(function(){window.location.href='" + req.getContextPath() + "/jsp/Login.jsp';},1000);" +
                    "</script>");
        } else {
            boolean success = true;
            ArrayList<Integer> targetImages = new ArrayList<Integer>();
            ArrayList<Integer> myFootprints = new ArrayList<>();
            String userName = "";
            if (req.getParameter("friendID") != null) {
                TravelUserDao travelUserDao = new TravelUserDao();
                try {
                    userName = travelUserDao.getUserName(Integer.parseInt(req.getParameter("friendID")));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    if (!travelUserDao.isFriend((String) req.getSession().getAttribute("UserName"), Integer.parseInt(req.getParameter("friendID")))) {
                        success = false;
                        resp.setContentType("text/html;charset=GBK");
                        PrintWriter out = resp.getWriter();
                        out.println("你和该用户尚不是好友！");
                        out.println("<script>" +
                                "setTimeout(function(){window.location.href='" + req.getContextPath() + "/jsp/MyFavorites.jsp';},1000);" +
                                "</script>");
                    } else if (!travelUserDao.isPublic(Integer.parseInt(req.getParameter("friendID")))) {
                        success = false;
                        resp.setContentType("text/html;charset=GBK");
                        PrintWriter out = resp.getWriter();
                        out.println("该用户收藏不对外开放 ！");
                        out.println("<script>" +
                                "setTimeout(function(){window.location.href='" + req.getContextPath() + "/jsp/MyFavorites.jsp';},1000);" +
                                "</script>");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else {
                userName = (String) req.getSession().getAttribute("UserName");
                try {
                    myFootprints = new TravelUserDao().getFootprints(userName);
                    req.setAttribute("myFootprints", myFootprints);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

            if (success) {
                try {
                    targetImages = new TravelImageDao().getUserFavors(userName);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                req.setAttribute("processed", true);
                req.setAttribute("targetImages", targetImages);
                req.getRequestDispatcher("/jsp/MyFavorites.jsp").forward(req, resp);
            }
        }
    }
}
