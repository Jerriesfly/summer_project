package servlets;

import beans.TravelUser;
import dao.TravelUserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("username");
        String password = req.getParameter("password");
        String verifyCode = req.getParameter("verifyCode");

        String verifyCodeInSession = (String) req.getSession().getAttribute("text");

        String userNameErr = null;
        String verifyCodeErr = null;

        //简单判断表单合法性
        if (!userName.matches("^[a-zA-Z0-9]{4,15}$")) {
            userNameErr = "Username is illegal!";
        } else {
            try {
                if(!new TravelUserDao().checkUser(userName, password)){
                    userNameErr = "no match is found!";
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        if(!verifyCode.equalsIgnoreCase(verifyCodeInSession)){
            verifyCodeErr = "Wrong verify code!";
        }

        if (userNameErr == null && verifyCodeErr == null) {
            //向数据库添加用户，并转发至首页
            req.getSession().setAttribute("UserName", userName);
            resp.sendRedirect(req.getContextPath() + "/jsp/Home.jsp");

            //设置错误信息并返回
        } else {
            req.setAttribute("userName", userName);
            req.setAttribute("userNameErr", userNameErr);
            req.setAttribute("verifyCodeErr", verifyCodeErr);
            req.getRequestDispatcher("/jsp/Login.jsp").forward(req, resp);
        }
    }
}
