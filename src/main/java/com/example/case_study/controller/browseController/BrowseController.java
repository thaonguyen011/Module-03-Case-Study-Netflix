package com.example.case_study.controller.browseController;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/browse")
public class BrowseController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        RequestDispatcher dispatcher;
        try {
            if (Integer.parseInt((String) session.getAttribute("signInStep")) < 1) {
                resp.sendRedirect("/login");
            } else {
                dispatcher = req.getRequestDispatcher("browse.jsp");
                dispatcher.forward(req, resp);
            }
        }   catch (NumberFormatException e) {
            resp.sendRedirect("/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
