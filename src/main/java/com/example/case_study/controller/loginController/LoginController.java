package com.example.case_study.controller.loginController;

import com.example.case_study.model.entity.User;
import com.example.case_study.model.service.impl.UserService;
import com.example.case_study.model.utils.login.ILoginRequest;
import com.example.case_study.model.utils.login.LoginManager;
import com.example.case_study.model.utils.login.LoginRequest;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getAttribute("auth") == null) {
            req.setAttribute("auth", -1);
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher("login/login.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");


        ILoginRequest loginRequest = new LoginRequest(username, password);
        LoginManager loginManager = LoginManager.getInstance();
        HttpSession session = req.getSession();

        int auth = loginManager.authentic(loginRequest);
        RequestDispatcher dispatcher;
        session.setAttribute("email", username);

        if (auth == -1) {
            User user = (new UserService()).getUserByUsername(username);
            session.setAttribute("currentUser", user);
            session.setAttribute("signInStep", "2");
            try {
                String rememberMe = req.getParameter("rememberMe");

                if (rememberMe.equals("true")) {

                    // Nếu thông tin đăng nhập hợp lệ, lưu username và password vào cookie
                    Cookie usernameCookie = new Cookie("username", username);
                    Cookie passwordCookie = new Cookie("password", password);
                    Cookie rememberMe1 = new Cookie("rememberMe", "true");


                    System.out.println("current user " +user.getEmail());

                    // Đặt thời gian sống của cookie (ví dụ: 1 ngày)
                    usernameCookie.setMaxAge(365 * 24 * 60 * 60);
                    passwordCookie.setMaxAge(365 * 24 * 60 * 60);
                    rememberMe1.setMaxAge(365 * 24 * 60 * 60);

                    // Thêm cookie vào phản hồi
                    resp.addCookie(usernameCookie);
                    resp.addCookie(passwordCookie);
                    resp.addCookie(rememberMe1);

                }
            } catch (NullPointerException ignored) {

            }
            UserService userService = new UserService();
            User loginUser = userService.getUserByUsername(username);
            if (!loginManager.isOnlineUser(loginUser.getId())) {
                loginManager.addOnlineUser(loginUser.getId());
                resp.sendRedirect("/browse");
            } else {
                dispatcher = req.getRequestDispatcher("login/login.jsp");
                req.setAttribute("isOnlineUser", true);
                dispatcher.forward(req, resp);
            }

        } else {
            req.setAttribute("auth", auth);
            dispatcher = req.getRequestDispatcher("login/login.jsp");
            dispatcher.forward(req, resp);
            session.setAttribute("email", username);
        }
    }



}
