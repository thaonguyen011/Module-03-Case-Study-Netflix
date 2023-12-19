package com.example.case_study.controller.loginController;

import com.example.case_study.model.entity.User;
import com.example.case_study.model.service.impl.UserService;
import com.example.case_study.model.utils.login.LoginManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login/formValidate")
public class FormValidateController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("formValidate.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String form = req.getParameter("form");
        String email = req.getParameter("emailAddress");
        User user = (new UserService().getUserByUsername(email));
        RequestDispatcher dispatcher;
        HttpSession session = req.getSession();

        if (form.equals("email")) {
            if (user == null) {
                req.setAttribute("isUser", false);
                dispatcher = req.getRequestDispatcher("formValidate.jsp");
                dispatcher.forward(req, resp);
            } else {
                LoginManager loginManager = LoginManager.getInstance();
                if (loginManager.isBlockedUser(email)) {
                    req.setAttribute("isBlockedUser", true);
                    dispatcher = req.getRequestDispatcher("formValidate.jsp");
                    dispatcher.forward(req, resp);
                } else {
                    session.setAttribute("forgetUser", user);
                    session.setAttribute("forgetPassStep", 2);
                    loginManager.sendCodeEmail(email);
                    resp.sendRedirect("/login/forgetPassword");
                }
            }
        } else {
            dispatcher = req.getRequestDispatcher("formValidate.jsp");
            dispatcher.forward(req, resp);
        }

    }
}
