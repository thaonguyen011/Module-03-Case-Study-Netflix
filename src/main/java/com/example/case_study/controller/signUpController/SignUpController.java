package com.example.case_study.controller.signUpController;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/signup")
public class SignUpController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        RequestDispatcher dispatcher;
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            System.out.println(cookie.getName());
            System.out.println(cookie.getValue());
            System.out.println("------------");
            if (cookie.getName().equals("JSESSIONID")) {
                System.out.println(cookie.getValue());
            }

        }
        try {
            if (Integer.parseInt((String) session.getAttribute("signUpStep")) < 3) {
                resp.sendRedirect("/signup/registration");
            } else {
                dispatcher = req.getRequestDispatcher("signup/signup.jsp");
                dispatcher.forward(req,resp);
            }
        }   catch (NumberFormatException e) {
            resp.sendRedirect("/main");
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie lastStep = new Cookie("lastStep", "2");
        HttpSession session = req.getSession();
        resp.addCookie(lastStep);

        session.setAttribute("signUpStep", "4");
        resp.sendRedirect("/signup/planform");
    }

}
