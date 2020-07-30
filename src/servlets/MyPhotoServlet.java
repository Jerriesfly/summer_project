package servlets;

import dao.TravelImageDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

public class MyPhotoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("UserName") == null) {
            PrintWriter out = resp.getWriter();
            out.println("请先登陆后访问我的照片！");
            out.println("<script>" +
                    "setTimeout(function(){window.location.href='" + req.getContextPath() + "/jsp/Login.jsp';},1000);" +
                    "</script>");
        } else {
            ArrayList<Integer> targetImages = new ArrayList<Integer>();
            String userName = (String) req.getSession().getAttribute("UserName");
            try {
                targetImages = new TravelImageDao().getUserImages(userName);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            req.setAttribute("processed", true);
            req.setAttribute("targetImages", targetImages);
            req.getRequestDispatcher("/jsp/MyPhotos.jsp").forward(req, resp);
        }
    }
}

