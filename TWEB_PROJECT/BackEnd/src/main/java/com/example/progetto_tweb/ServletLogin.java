package com.example.progetto_tweb;

import DAO.newDAO;
import model.Utente;
import org.json.JSONObject;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "servletLogin", value = "/servletLogin")
public class ServletLogin extends HttpServlet {
    private String message;
    private newDAO model;
    //L'argomento non lo mette di default ma va messo
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext ctx= config.getServletContext();
        String url=ctx.getInitParameter("urlDB");
        String userDB=ctx.getInitParameter("userDB");
        String pwdDB=ctx.getInitParameter("passwordDB");
        model=(newDAO) ctx.getAttribute("DAO");
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Utente ut;
        response.setContentType("application/json");
        JSONObject myObj = new JSONObject();
        PrintWriter writer = response.getWriter();
        if (request.getParameter("user") != null && request.getParameter("pwd") != null) {
            HttpSession session = request.getSession();
            ut=model.login(request.getParameter("user"), request.getParameter("pwd"));
            try {
                System.out.println("sessione utente: "+session.getId());
                myObj.put("token", session.getId());
                myObj.put("username", ut.getUsername());
                myObj.put("ruolo", ut.getRuolo());
                myObj.put("idUser", ut.getIdUtente());

                session.setAttribute("user", ut.getUsername());
                session.setAttribute("ruolo", ut.getRuolo());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (myObj != null) {
                //gson.toJson(s);
                writer.print(myObj);
                writer.close();
                System.out.println(session.getId());
            }
        }
    }

    public void destroy() {

    }
}