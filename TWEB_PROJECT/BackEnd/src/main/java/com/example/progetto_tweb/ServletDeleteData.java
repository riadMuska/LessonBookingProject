package com.example.progetto_tweb;

import DAO.newDAO;

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
import java.util.Objects;

@WebServlet(name = "ServletDeleteData", value = "/ServletDeleteData")
public class ServletDeleteData extends HttpServlet {
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
        String method = request.getParameter("method");
        HttpSession session = request.getSession(false);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter wr = response.getWriter();
        if(session!=null){
            switch(method)
            {
                case "deleteDoc": {
                    String jsessionId = request.getRequestedSessionId();
                    System.out.println(jsessionId);
                    response.setContentType("text/html;charset=UTF-8");
                    PrintWriter writer = response.getWriter();
                    HttpSession s = request.getSession();
                    String res = "";
                    int id = Integer.valueOf(request.getParameter("id"));
                    res = model.DeleteDocente(id);
                    if (Objects.equals(res, "OK")) {
                        response.setHeader("success", "yes");
                        writer.write("OK");
                        writer.close();
                    } else {
                        response.setHeader("success", "no");
                        writer.write("ERROR");
                        writer.close();
                    }
                    break;
                }
                case "deleteCourse": {
                    response.setContentType("text/html;charset=UTF-8");
                    PrintWriter writer = response.getWriter();
                    HttpSession s = request.getSession();
                    String res = "";
                    int id = Integer.valueOf(request.getParameter("id"));
                    res = model.DeleteCorso(id);
                    if (res == "OK") {
                        response.setHeader("success", "yes");
                        writer.write("OK");
                        writer.close();
                    } else {
                        response.setHeader("success", "no");
                        writer.write("ERROR");
                        writer.close();
                    }
                    break;
                }
                case "deletePrenotaion":{
                    response.setContentType("text/html;charset=UTF-8");
                    PrintWriter writer = response.getWriter();
                    HttpSession s = request.getSession();
                    String res="";
                    int id= Integer.valueOf(request.getParameter("idLezione"));
                    res=model.DeletePrenotation(id);
                    if(Objects.equals(res, "OK")) {
                        response.setHeader("success", "yes");
                        writer.write("OK");
                        writer.close();
                    }
                    else
                    {
                        response.setHeader("success", "no");
                        writer.write("ERROR");
                        writer.close();
                    }
                }
                case "deleteAssociation":{
                    response.setContentType("text/html;charset=UTF-8");
                    PrintWriter writer = response.getWriter();
                    HttpSession s = request.getSession();
                    String res="";
                    int idDocente= Integer.valueOf(request.getParameter("idDocente"));
                    int idCorso= Integer.valueOf(request.getParameter("idCorso"));
                    res=model.DeleteAssociation(idDocente,idCorso);
                    if(Objects.equals(res, "OK")) {
                        response.setHeader("success", "yes");
                        writer.write("OK");
                        writer.close();
                    }
                    else
                    {
                        response.setHeader("success", "no");
                        writer.write("ERROR");
                        writer.close();
                    }
                }
            }
        }
        else{
            wr.write("sessione_inesistente");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("method");
        HttpSession session = request.getSession(false);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter wr = response.getWriter();
        if(session!=null){
            switch(method)
            {
                case "deleteDoc": {
                    String jsessionId = request.getRequestedSessionId();
                    System.out.println(jsessionId);
                    response.setContentType("text/html;charset=UTF-8");
                    PrintWriter writer = response.getWriter();
                    HttpSession s = request.getSession();
                    String res = "";
                    int id = Integer.valueOf(request.getParameter("id"));
                    res = model.DeleteDocente(id);
                    if (Objects.equals(res, "OK")) {
                        response.setHeader("success", "yes");
                        writer.write("OK");
                        writer.close();
                    } else {
                        response.setHeader("success", "no");
                        writer.write("ERROR");
                        writer.close();
                    }
                    break;
                }
                case "deleteCourse": {
                    response.setContentType("text/html;charset=UTF-8");
                    PrintWriter writer = response.getWriter();
                    HttpSession s = request.getSession();
                    String res = "";
                    int id = Integer.valueOf(request.getParameter("id"));
                    res = model.DeleteCorso(id);
                    if (res == "OK") {
                        response.setHeader("success", "yes");
                        writer.write("OK");
                        writer.close();
                    } else {
                        response.setHeader("success", "no");
                        writer.write("ERROR");
                        writer.close();
                    }
                    break;
                }
                case "deletePrenotaion":{
                    response.setContentType("text/html;charset=UTF-8");
                    PrintWriter writer = response.getWriter();
                    HttpSession s = request.getSession();
                    String res="";
                    int id= Integer.valueOf(request.getParameter("idLezione"));
                    res=model.DeletePrenotation(id);
                    if(Objects.equals(res, "OK")) {
                        response.setHeader("success", "yes");
                        writer.write("OK");
                        writer.close();
                    }
                    else
                    {
                        response.setHeader("success", "no");
                        writer.write("ERROR");
                        writer.close();
                    }
                }
                case "deleteAssociation":{
                    response.setContentType("text/html;charset=UTF-8");
                    PrintWriter writer = response.getWriter();
                    HttpSession s = request.getSession();
                    String res="";
                    int idDocente= Integer.valueOf(request.getParameter("idDocente"));
                    int idCorso= Integer.valueOf(request.getParameter("idCorso"));
                    res=model.DeleteAssociation(idDocente,idCorso);
                    if(Objects.equals(res, "OK")) {
                        response.setHeader("success", "yes");
                        writer.write("OK");
                        writer.close();
                    }
                    else
                    {
                        response.setHeader("success", "no");
                        writer.write("ERROR");
                        writer.close();
                    }
                }
            }
        }
        else{
            wr.write("sessione_inesistente");
        }
    }
}
