package com.example.progetto_tweb;

import DAO.newDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

@WebServlet(name = "ServletCreateData", value = "/ServletCreateData")
public class ServletCreateData extends HttpServlet {
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
        switch(method)
        {
            case "createPrenotation":{
                HttpSession session = request.getSession(false);
                response.setContentType("text/html;charset=UTF-8");
                PrintWriter writer = response.getWriter();
                String res="";
                int id= Integer.valueOf(request.getParameter("idLezione"));
                int idUtente= Integer.valueOf(request.getParameter("idUtente"));
                if(session!=null){
                    res=model.CreatePrenotation(id,idUtente);
                    if(Objects.equals(res, "OK")) {
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
                break;
            }
            case "setEffetuata":{
                System.out.println("impostando in base alla data");
                HttpSession session=request.getSession(false);
                response.setContentType("text/html");
                PrintWriter writer = response.getWriter();
                if(session!=null){
                    String res="";
                    int id= Integer.valueOf(request.getParameter("idLezione"));
                    res=model.UpdatePrenotation(id);
                    if(Objects.equals(res, "OK")) {
                        writer.write("OK");
                        writer.close();
                    }
                    else
                    {
                        writer.write("ERROR");
                        writer.close();
                    }
                }
                else{
                    writer.write("sessione_inesistente");
                }

                break;
            }
            case "createAssociation":{
                int idDocente = Integer.valueOf(request.getParameter("idDoc"));
                int idCorso = Integer.valueOf(request.getParameter("idCorso"));

                response.setContentType("text/html");
                PrintWriter writer = response.getWriter();
                String res="";
                HttpSession session = request.getSession(false);
                if(session!=null){
                    res=model.newAssociation(idDocente,idCorso);
                    if(Objects.equals(res, "OK")) {
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
                break;
            }
            case "createDoc":{
                String nome=request.getParameter("nome");
                String cognome=request.getParameter("cognome");
                int attivita=Integer.valueOf(request.getParameter("attivita"));

                response.setContentType("text/html");
                PrintWriter writer = response.getWriter();
                String res="";
                HttpSession session = request.getSession(false);
                if(session!=null){
                    res=model.CreateDoc(nome,cognome,attivita);
                    if(Objects.equals(res, "OK")) {
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
                break;
            }
            case "createCourse":{
                String titolo=request.getParameter("titolo");

                response.setContentType("text/html");
                PrintWriter writer = response.getWriter();
                String res="";
                HttpSession session = request.getSession(false);
                if(session!=null){
                    res=model.CreateCourse(titolo);
                    if(Objects.equals(res, "OK")) {
                        writer.write("OK");
                        writer.close();
                    }
                    else
                    {
                        writer.write("ERROR");
                        writer.close();
                    }
                }
                else{
                    writer.write("sessione_inesistente");
                }
                break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("method");
        switch(method)
        {
            case "createPrenotation":{
                HttpSession session = request.getSession(false);
                response.setContentType("text/html;charset=UTF-8");
                PrintWriter writer = response.getWriter();
                String res="";
                int id= Integer.valueOf(request.getParameter("idLezione"));
                int idUtente= Integer.valueOf(request.getParameter("idUtente"));
                if(session!=null){
                    res=model.CreatePrenotation(id,idUtente);
                    if(Objects.equals(res, "OK")) {
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
                break;
            }
            case "setEffetuata":{
                HttpSession session=request.getSession(false);
                response.setContentType("text/html");
                PrintWriter writer = response.getWriter();
                if(session!=null){
                    String res="";
                    int id= Integer.valueOf(request.getParameter("idLezione"));
                    res=model.UpdatePrenotation(id);
                    if(Objects.equals(res, "OK")) {
                        writer.write("OK");
                        writer.close();
                    }
                    else
                    {
                        writer.write("ERROR");
                        writer.close();
                    }
                }
                else{
                    writer.write("sessione_inesistente");
                }

                break;
            }
            case "createAssociation":{
                int idDocente = Integer.valueOf(request.getParameter("idDoc"));
                int idCorso = Integer.valueOf(request.getParameter("idCorso"));

                response.setContentType("text/html");
                PrintWriter writer = response.getWriter();
                String res="";
                HttpSession session = request.getSession(false);
                if(session!=null){
                    res=model.newAssociation(idDocente,idCorso);
                    if(Objects.equals(res, "OK")) {
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
                break;
            }
            case "createDoc":{
                String nome=request.getParameter("nome");
                String cognome=request.getParameter("cognome");
                int attivita=Integer.valueOf(request.getParameter("attivita"));

                response.setContentType("text/html");
                PrintWriter writer = response.getWriter();
                String res="";
                HttpSession session = request.getSession(false);
                if(session!=null){
                    res=model.CreateDoc(nome,cognome,attivita);
                    if(Objects.equals(res, "OK")) {
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
                break;
            }
            case "createCourse":{
                String titolo=request.getParameter("titolo");
                response.setContentType("text/html");
                PrintWriter writer = response.getWriter();
                String res="";
                HttpSession session = request.getSession(false);
                if(session!=null){
                    res=model.CreateCourse(titolo);
                    if(Objects.equals(res, "OK")) {
                        writer.write("OK");
                        writer.close();
                    }
                    else
                    {
                        writer.write("ERROR");
                        writer.close();
                    }
                }
                else{
                    writer.write("sessione_inesistente");
                }
                break;
            }
        }
    }
}
