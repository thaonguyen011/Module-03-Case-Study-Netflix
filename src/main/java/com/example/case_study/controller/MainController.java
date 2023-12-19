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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/main")
public class MainController extends HttpServlet {



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Cookie[] cookies = req.getCookies();

        String email = req.getParameter("email");
        System.out.println("email : " + email.replace("@", "-"));
        Validator emailCheck = new EmailRegexValidator(email);
        RequestDispatcher dispatcher;
        if (emailCheck.isCheck()) {
            HttpSession session = req.getSession();
            session.setAttribute("signUpStep", "1");
            session.setAttribute("signInStep", "1");
            session.setAttribute("email", email);

            boolean isCookie = false;
            for (Cookie cookie : cookies) {
                System.out.println("Cookie name: " + cookie.getName());
                System.out.println("Cookie value: " + cookie.getValue());
                System.out.println("--------");
                if (cookie.getName().equals(email.replace("@", "-"))) {
                    switch (cookie.getValue()) {
                        case "in":
                            resp.sendRedirect("/signup/password");
                            break;
                        case "already":
                            resp.sendRedirect("/login");
                            break;
                    }
                    isCookie = true;
                    break;
                }
            }

            if (!isCookie) {
                User user = (new UserService().getUserByUsername(email));

                if (user == null) {
                    resp.sendRedirect("/signup/registration");
                } else {
                    resp.sendRedirect("/login");
                }
            }




        } else {
            req.setAttribute("emailRegex", false);
            dispatcher = req.getRequestDispatcher("index.jsp");
            dispatcher.forward(req, resp);

        }


    }
}
