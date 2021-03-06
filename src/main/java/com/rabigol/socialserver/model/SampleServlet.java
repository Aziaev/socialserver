package com.rabigol.socialserver.model;

import org.eclipse.jetty.http.MimeTypes;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SampleServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JSONObject json = new JSONObject();
        json.put("error", "unknown error");
        json.put("errorCode", 100);

        resp.setContentType(MimeTypes.Type.APPLICATION_JSON.asString());
        resp.getWriter().write(json.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
