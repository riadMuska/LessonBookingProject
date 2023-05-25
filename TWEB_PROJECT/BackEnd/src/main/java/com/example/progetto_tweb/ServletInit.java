package com.example.progetto_tweb;

import DAO.newDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ServletInit", value = "/ServletInit", loadOnStartup = 1, asyncSupported = true)
public class ServletInit extends HttpServlet {
    private newDAO model;
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext ctx= config.getServletContext();
        String url=ctx.getInitParameter("urlDB");
        String userDB=ctx.getInitParameter("userDB");
        String pwdDB=ctx.getInitParameter("passwordDB");
        model=new newDAO(url,userDB,pwdDB);
        ctx.setAttribute("DAO",model);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
