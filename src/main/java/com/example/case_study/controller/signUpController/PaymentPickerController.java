package com.example.case_study.controller.signUpController;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/signup/paymentPicker")
public class PaymentPickerController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("paymentPicker.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String paymentForm = req.getParameter("paymentForm");
        Cookie lastStep = new Cookie("lastStep", "2");
        resp.addCookie(lastStep);
        switch (paymentForm) {
            case "credit":
                resp.sendRedirect("/signup/creditOption");
                break;
            case "wallet":
                resp.sendRedirect("/signup/mobileWalletOption");
                break;
            default:
                break;
        }

    }


}
