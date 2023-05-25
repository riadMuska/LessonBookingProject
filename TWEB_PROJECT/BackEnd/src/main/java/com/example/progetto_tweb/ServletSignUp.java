package com.example.progetto_tweb;

import DAO.newDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ServletSignUp", value = "/ServletSignUp")
public class ServletSignUp extends HttpServlet {
    private newDAO model;
    public void init(ServletConfig config) throws ServletException {

        super.init(config);
        ServletContext ctx= config.getServletContext();
        String url=ctx.getInitParameter("urlDB");
        String userDB=ctx.getInitParameter("userDB");
        String pwdDB=ctx.getInitParameter("passwordDB");
        model=(newDAO) ctx.getAttribute("DAO");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("username");
        String pwd = request.getParameter("password");
        String type = request.getParameter("type");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        String res = "";
        res = model.newUser(user,pwd,type);
        if (res == "OK") {
            response.setHeader("success", "yes");
            writer.write("OK");
            writer.close();
        } else {
            response.setHeader("success", "no");
            writer.write(res);
            writer.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
