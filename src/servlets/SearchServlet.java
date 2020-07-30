package servlets;

import dao.TravelImageDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArrayList<Integer> targetImages = new ArrayList<Integer>();
        String type;
        String input;
        if (req.getParameter("description") != null) {
            type = " description ";
            input = req.getParameter("description");
        } else {
            type = " title ";
            input = req.getParameter("title") != null ? req.getParameter("title") : "";
        }
        String condition = "WHERE travelimage." + type + " LIKE '%" + input + "%'";
        if (req.getParameter("sequence") != null && req.getParameter("sequence").equals("hot")) {
            try {
                targetImages = new TravelImageDao().getHotImagesID(0, condition);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            try {
                targetImages = new TravelImageDao().getRecentImagesID(0, condition);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        req.setAttribute("processed", true);
        req.setAttribute("targetImages", targetImages);
        req.getRequestDispatcher("/jsp/Search.jsp?" + type + "=" + input + "&sequence=" + req.getParameter("sequence")).forward(req, resp);
    }

}
