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

@WebServlet("/login/forgetPassword")
public class ForgetPassController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        try {
            if ((int) session.getAttribute("forgetPassStep") >= 2) {
                RequestDispatcher dispatcher = req.getRequestDispatcher("forgetPass.jsp");
                dispatcher.forward(req, resp);
            } else {
                resp.sendRedirect("/login");
            }
        } catch (NullPointerException e) {
            resp.sendRedirect("/login");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code").trim();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("forgetUser");
        boolean validateCode = LoginManager.getInstance().validateCode(user.getId(), code);
        if (validateCode) {
            req.setAttribute("codeValidate", true);
            resp.sendRedirect("/login/changePass");
        } else {
            RequestDispatcher dispatcher = req.getRequestDispatcher("forgetPass.jsp");
            req.setAttribute("codeValidate", false);
            dispatcher.forward(req, resp);
        }

    }

}
