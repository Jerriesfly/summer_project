package servlets;

import dao.TravelImageFavorDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class DeleteFavorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=GBK");
        PrintWriter out = resp.getWriter();
        if (req.getSession().getAttribute("UserName") == null) {
            out.println("请先登陆后进行操作！");
            out.println("<script>" +
                    "setTimeout(function(){window.location.href='" + req.getContextPath() + "/jsp/Login.jsp';},1000);" +
                    "</script>");
        }

        if(req.getParameter("id") == null){
            out.println("非法的图片ID！");
            out.println("<script>" +
                    "setTimeout(function(){window.location.href='" + req.getContextPath() + "/jsp/Login.jsp';},1000);" +
                    "</script>");
        }

        try {
            new TravelImageFavorDao().removeFavor(Integer.parseInt(req.getParameter("id")), (String)req.getSession().getAttribute("UserName"));
            out.println("成功从收藏中移除！");
            out.println("<script>" +
                    "setTimeout(function(){window.location.href='" + req.getContextPath() + "/jsp/MyFavorites.jsp';},1000);" +
                    "</script>");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
