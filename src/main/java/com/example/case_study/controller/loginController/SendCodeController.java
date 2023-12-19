package com.example.case_study.controller.loginController;

import com.example.case_study.model.entity.User;
import com.example.case_study.model.utils.login.LoginManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login/sendCode")
public class SendCodeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();



        try {
                User user = (User) session.getAttribute("forgetUser");
                LoginManager.getInstance().sendCodeEmail(user.getEmail());
                session.setAttribute("forgetPassStep", 3);
                resp.sendRedirect("/login/forgetPassword?action=validate");

        } catch (NullPointerException e) {
            resp.sendRedirect("/login");
        }

    }

}
