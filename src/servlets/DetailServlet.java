package servlets;

import beans.TravelImage;
import dao.TravelCommentDao;
import dao.TravelImageDao;
import dao.TravelImageFavorDao;
import dao.TravelUserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

public class DetailServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("method") == null) {
            try {
                setDetail(req, resp);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (req.getParameter("method").equals("removeFavorite")) {
            try {
                removeFavorite(req, resp);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else{
            try {
                addFavorite(req, resp);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    private void setDetail(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, SQLException {
        if (req.getParameter("id") != null && req.getParameter("id").matches("[0123456789]+")) {
            TravelImage travelImage = null;
            int imageId = Integer.parseInt(req.getParameter("id"));
            TravelImageDao travelImageDao = new TravelImageDao();

            try {
                travelImage = travelImageDao.getImageDetail(imageId);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            if (travelImage.getTitle() != null) {
                req.setAttribute("travelImage", travelImage);
                req.setAttribute("processed", true);

                ArrayList<Integer> commentList = new ArrayList<>();
                commentList = new TravelCommentDao().getImageComments(travelImage.getImageID());
                req.setAttribute("commentList", commentList);

                try {
                    if (req.getSession().getAttribute("UserName") != null) {
                        String userName = req.getSession().getAttribute("UserName").toString();
                        req.setAttribute("isFavored", new TravelImageFavorDao().isFavored(imageId, userName));
                        new TravelUserDao().setFootprints(userName, imageId);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                req.getRequestDispatcher("/jsp/Detail.jsp?id=" + imageId).forward(req, resp);
            } else {
                PrintWriter out = resp.getWriter();
                out.println("未找到图片！即将返回首页！");
                out.println("<script>" +
                        "setTimeout(function(){window.location.href='" + req.getContextPath() + "/jsp/Home.jsp';},1000);" +
                        "</script>");
            }
        } else {
            PrintWriter out = resp.getWriter();
            out.println("不合法的图片ID！即将返回首页！");
            out.println("<script>" +
                    "setTimeout(function(){window.location.href='" + req.getContextPath() + "/jsp/Home.jsp';},1000);" +
                    "</script>");
        }
    }

    private void addFavorite(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        String userName = (String)req.getSession().getAttribute("UserName");
        TravelImageFavorDao travelImageFavorDao = new TravelImageFavorDao();
        travelImageFavorDao.addFavor(Integer.parseInt(req.getParameter("id")), userName);
        resp.sendRedirect(req.getContextPath() + "/jsp/Detail.jsp?id=" + req.getParameter("id"));
    }

    private void removeFavorite(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        String userName = (String)req.getSession().getAttribute("UserName");
        TravelImageFavorDao travelImageFavorDao = new TravelImageFavorDao();
        travelImageFavorDao.removeFavor(Integer.parseInt(req.getParameter("id")), userName);
        resp.sendRedirect(req.getContextPath() + "/jsp/Detail.jsp?id=" + req.getParameter("id"));
    }
}

