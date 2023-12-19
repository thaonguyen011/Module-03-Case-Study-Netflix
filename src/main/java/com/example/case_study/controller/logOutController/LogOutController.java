package com.example.case_study.controller.logOutController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogOutController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String email = (String) session.getAttribute("email");
        Cookie cookie = new Cookie(email, "out");
        resp.addCookie(cookie);
        resp.sendRedirect("logout.jsp");
    }
}
