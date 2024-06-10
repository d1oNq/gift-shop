package com.giftshop.user.servlet;

import com.giftshop.log.GiftLogger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/update_address")
public class UpdateAddressServlet extends BaseUpdateServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GiftLogger.logInfo("Received request to update address");
        processRequest(req, resp, false);
        GiftLogger.logInfo("Address update request processed");
    }
}
