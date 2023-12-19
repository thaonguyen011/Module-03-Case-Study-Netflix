package com.example.case_study.controller.signUpController;

import com.example.case_study.model.entity.User;
import com.example.case_study.model.service.impl.UserService;
import com.example.case_study.model.utils.login.Validator;
import com.example.case_study.model.utils.regexValidator.EmailRegexValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;


@WebServlet("/signup/regform")
public class RegFormController extends HttpServlet {
    private String action;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        RequestDispatcher dispatcher;
        try{
            int currentStep = Integer.parseInt((String) session.getAttribute("signUpStep"));
            if (currentStep < 2) {
                resp.sendRedirect("/signup/registration");
            } else if (currentStep == 2){
                dispatcher = req.getRequestDispatcher("regform.jsp");
                action= "regform";
                dispatcher.forward(req,resp);}
            else {
                action= "existEmail";
                dispatcher = req.getRequestDispatcher("existEmail.jsp");
                dispatcher.forward(req,resp);
            }
        } catch (NumberFormatException e) {
            resp.sendRedirect("/main");
        }

        }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (action.equals("regform")) {
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            UserService userService = new UserService();
            boolean existEmail = userService.isExistUser(email);
            req.setAttribute("existEmail", existEmail);
            RequestDispatcher dispatcher;
            Validator emailValidator = new EmailRegexValidator(email);
            boolean emailCheck = emailValidator.isCheck();
            HttpSession session = req.getSession();


            if (!existEmail) {
                if (emailCheck) {
                    User newUser = new User();
                    newUser.setUsername(email);
                    newUser.setPassword(password);
                    newUser.setEmail(email);

                    session.setAttribute("newUser", newUser);

                    session.setAttribute("signUpStep", "3");
                    Cookie cookie1 = new Cookie("lastStep", "1");
                    Cookie cookie = new Cookie(email.replace("@", "-"), "in");
                    cookie.setMaxAge(365 * 24 * 60 * 60);
                    cookie1.setMaxAge(365 * 24 * 60 * 60);

                    resp.addCookie(cookie1);
                    resp.addCookie(cookie);
                    resp.sendRedirect("/signup");
                } else {
                    req.setAttribute("emailCheck", false);
                    dispatcher = req.getRequestDispatcher("regform.jsp");
                    session.setAttribute("signUpStep", "2");
                    dispatcher.forward(req,resp);
                }

            } else {
                dispatcher = req.getRequestDispatcher("regform.jsp");
                session.setAttribute("signUpStep", "2");
                dispatcher.forward(req,resp);

            }
        } else {
            resp.sendRedirect("/signup");
        }

    }



}
