package com.example.progetto_tweb;

import DAO.newDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ServletLogOut", value = "/ServletLogOut")
public class ServletLogOut extends HttpServlet {
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
        HttpSession s= request.getSession(false);
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();
        String res="";
        try{
            System.out.println("sessione che vado a invalidare: "+s.getId());
            s.invalidate();
            res="OK";
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            res="ERROR";
        }
        out.print(res);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession s= request.getSession(false);
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();
        String res="";
        try{
            System.out.println("sessione che vado a invalidare: "+s.getId());
            s.invalidate();
            res="OK";
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            res="ERROR";
        }
        out.print(res);
    }
}
