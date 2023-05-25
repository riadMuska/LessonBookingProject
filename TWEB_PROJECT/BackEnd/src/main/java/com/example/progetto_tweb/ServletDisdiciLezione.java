package com.example.progetto_tweb;

import DAO.newDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ServletDisdiciLezione", value = "/ServletDisdiciLezione")
public class ServletDisdiciLezione extends HttpServlet {
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
        HttpSession session = request.getSession(false);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        String res="";
        int id= Integer.valueOf(request.getParameter("idLezione"));
        if(session!=null){
            res=model.DisdiciLezione(id);
            if(res=="OK") {
                writer.write("OK");
                writer.close();
            }
            else
            {
                writer.write("ERROR");
                writer.close();
            }
        }
        else {
            writer.write("sessione_inesistente");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        String res="";
        int id= Integer.valueOf(request.getParameter("idLezione"));
        if(session!=null){
            res=model.DisdiciLezione(id);
            if(res=="OK") {
                writer.write("OK");
                writer.close();
            }
            else
            {
                writer.write("ERROR");
                writer.close();
            }
        }
        else {
            writer.write("sessione_inesistente");
        }
    }
}
