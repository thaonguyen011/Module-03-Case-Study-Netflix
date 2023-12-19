package com.example.case_study.controller.signUpController;

import com.example.case_study.model.entity.User;
import com.example.case_study.model.service.impl.UserService;
import com.example.case_study.model.utils.login.LoginManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/momo")
public class MomoController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        RequestDispatcher dispatcher;
        try {
        if (Integer.parseInt((String) session.getAttribute("signUpStep")) < 7) {
            resp.sendRedirect("/signup/mobileWalletOption");
        } else {
            dispatcher = req.getRequestDispatcher("signup/momo.jsp");
            dispatcher.forward(req,resp);
        }
        }  catch (NumberFormatException e) {
            resp.sendRedirect("/main");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //logic xac thuc thanh toan
        // thanh cong
        HttpSession session = req.getSession();
        String email = (String) session.getAttribute("email");
        Cookie cookie = new Cookie(email.replace("@","-"), "already");
        UserService userService = new UserService();
        User newUser = (User) session.getAttribute("newUser");
        userService.insert(newUser);
        User newUser1 =  userService.getUserByUsername(newUser.getEmail());
        LoginManager.getInstance().addUser(newUser1);
        resp.addCookie(cookie);
        resp.sendRedirect("/browse");

    }
}
