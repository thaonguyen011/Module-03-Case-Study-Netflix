package com.example.case_study.controller.signUpController;

import com.example.case_study.model.entity.User;
import com.example.case_study.model.service.impl.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/signup/password")
public class PasswordController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("password.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password = req.getParameter("password");
        HttpSession session = req.getSession();
        UserService userService = new UserService();
        String email = (String) session.getAttribute("email");
        User userLogin = userService.getUserByUsername(email);
        session.setAttribute("newUser", userLogin);
        if (userLogin.getPassword().equals(password)) {
            resp.sendRedirect("/signup");
        } else {
            RequestDispatcher dispatcher = req.getRequestDispatcher("password.jsp");
            req.setAttribute("wrongPassword", true);
            dispatcher.forward(req, resp);
        }

    }
}
