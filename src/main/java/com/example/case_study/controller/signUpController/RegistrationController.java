package com.example.case_study.controller.signUpController;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/signup/registration")
public class RegistrationController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        RequestDispatcher dispatcher;
        try {
            if (Integer.parseInt((String) session.getAttribute("signUpStep")) < 1) {
                resp.sendRedirect("/main");
            } else {
                dispatcher = req.getRequestDispatcher("registration.jsp");
                dispatcher.forward(req,resp);
            }
        }

        catch (NumberFormatException e) {
            resp.sendRedirect("/main");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.setAttribute("signUpStep", "2");
        String email = (String) session.getAttribute("email");
        Cookie cookie = new Cookie(email.replace("@","-"), "not");
        resp.addCookie(cookie);
        resp.sendRedirect("/signup/regform");
    }
}
