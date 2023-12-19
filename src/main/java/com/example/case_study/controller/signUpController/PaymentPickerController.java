package com.example.case_study.controller.signUpController;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/signup/paymentPicker")
public class PaymentPickerController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        RequestDispatcher dispatcher;
        try {
            if (Integer.parseInt((String) session.getAttribute("signUpStep")) < 5) {
                resp.sendRedirect("/signup/planform");
            } else {
                dispatcher = req.getRequestDispatcher("paymentPicker.jsp");
                dispatcher.forward(req, resp);
            }
        } catch (NumberFormatException e) {
            resp.sendRedirect("/main");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String paymentForm = req.getParameter("paymentForm");
        Cookie lastStep = new Cookie("lastStep", "2");
        resp.addCookie(lastStep);
        HttpSession session = req.getSession();
        session.setAttribute("signUpStep", "6");
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
