package com.example.case_study.controller;

import com.example.case_study.model.entity.User;
import com.example.case_study.model.service.impl.UserService;
import com.example.case_study.model.utils.login.LoginManager;
import com.example.case_study.model.utils.login.Validator;
import com.example.case_study.model.utils.regexValidator.EmailRegexValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/main")
public class MainController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        Validator emailCheck = new EmailRegexValidator(email);
        RequestDispatcher dispatcher;
        LoginManager loginManager = LoginManager.getInstance();
        if (emailCheck.isCheck()) {
            UserService userService = new UserService();

            User user = userService.getUserByUsername(email);
            HttpSession session = req.getSession();
//            if (user != null) {
//                session.setAttribute("userLogin", user);
//            }
            boolean isExistedEmail = user != null;

//            // Generate HTML/JavaScript code to save data in localStorage
//            String htmlResponse = "<html><head><script>";
//            htmlResponse += "localStorage.setItem('myData', '" + email + "');";
//            htmlResponse += "</script></head><body>";
//            htmlResponse += "<h2>Data has been saved in localStorage.</h2>";
//            htmlResponse += "</body></html>";
//
//            // Set content type and write the response
//            resp.setContentType("text/html");
//            PrintWriter out = resp.getWriter();
//            out.println(htmlResponse);
//            System.out.println("respone");


            if (isExistedEmail) {
                try {
                    if (loginManager.isOnlineUser(user.getId())) {
                        req.setAttribute("isOnlineUser", true);
                        dispatcher = req.getRequestDispatcher("index.jsp");
                        dispatcher.forward(req, resp);
                    } else {
                        Cookie[] cookies = req.getCookies();
                        for (Cookie cookie : cookies) {
                            if (cookie.getName().equals("lastStep")) {

                            }
                        }
                        session.setAttribute("emailSignIn", email);
                        // xet user da dang ki thanh cong chua
                        // thanh cong
                        resp.sendRedirect("/login");
                        // khong thanh cong
                        //resp.sendRedirect("/signup/password");
//                        dispatcher = req.getRequestDispatcher("signup/password.jsp");
//                        dispatcher.forward(req, resp);
//                        resp.sendRedirect("signup/password.jsp");
                    }
                } catch (NullPointerException ignored) {

                }

            } else {
                session.setAttribute("emailSignUp", email);
                resp.sendRedirect("/signup/registration");
            }

        } else {
            req.setAttribute("emailRegex", false);
            dispatcher = req.getRequestDispatcher("index.jsp");
            dispatcher.forward(req, resp);
        }


    }
}
