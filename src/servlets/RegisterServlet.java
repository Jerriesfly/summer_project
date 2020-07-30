package servlets;

import beans.TravelUser;
import dao.TravelUserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("username");
        String email = req.getParameter("Email");
        String password = req.getParameter("password");
        String repassword = req.getParameter("repassword");
        String verifyCode = req.getParameter("verifyCode");

        String verifyCodeInSession = (String) req.getSession().getAttribute("text");

        String userNameErr = null;
        String emailErr = null;
        String passwordErr = null;
        String verifyCodeErr = null;

        TravelUserDao travelUserDao = new TravelUserDao();

        //简单判断表单合法性
        if (!userName.matches("^[a-zA-Z0-9]{4,15}$")) {
            userNameErr = "Username is illegal!";
        } else {
            try {
                if (!travelUserDao.checkUserName(userName)) {
                    userNameErr = "Username is occupied!";
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        if (!email.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$")) {
            emailErr = "Email is not right!";
        } else {
            try {
                if (!travelUserDao.checkEmail(email)) {
                    emailErr = "Email is occupied!";
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        //解密password并检查



        if (!password.matches(repassword)) {
            passwordErr = "Passwords don't match!";
        }

        if(!verifyCode.equalsIgnoreCase(verifyCodeInSession)){
            verifyCodeErr = "Wrong verify code!";
        }

        if (userNameErr == null && emailErr == null && passwordErr == null && verifyCodeErr == null) {
            //向数据库添加用户，并转发至首页
            TravelUser travelUser = new TravelUser();
            travelUser.setUserName(userName);
            travelUser.setEmail(email);
            travelUser.setPass(password);

            try {
                travelUserDao.createTravelUser(travelUser);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            req.getSession().setAttribute("UserName", userName);
            resp.sendRedirect(req.getContextPath() + "/jsp/Home.jsp");

            //设置错误信息并返回
        } else {
            req.setAttribute("userName", userName);
            req.setAttribute("Email", email);
            req.setAttribute("userNameErr", userNameErr);
            req.setAttribute("emailErr", emailErr);
            req.setAttribute("passwordErr", passwordErr);
            req.setAttribute("verifyCodeErr", verifyCodeErr);
            req.getRequestDispatcher("/jsp/Register.jsp").forward(req, resp);
        }
    }
}
