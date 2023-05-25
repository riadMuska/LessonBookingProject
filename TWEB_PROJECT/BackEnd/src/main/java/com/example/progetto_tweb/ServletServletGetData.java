package com.example.progetto_tweb;

import DAO.newDAO;
import com.google.gson.Gson;
import model.Corsi;
import model.CorsoDocente;
import model.Docente;
import model.Lezione;

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
import java.util.ArrayList;

@WebServlet(name = "ServletServletGetData", value = "/ServletServletGetData")
public class ServletServletGetData extends HttpServlet {
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
        switch(method) {
            case "getDocs": {
                HttpSession session= request.getSession(false);
                PrintWriter out = response.getWriter();
                response.setContentType("text/html");
                if(session!=null){
                    Gson gson = new Gson();
                    response.setContentType("application/json");
                    ArrayList<Docente> docs = model.queryDoc();
                    out.write(gson.toJson(docs));
                }
                else {
                    System.out.println("sessione inesistente in "+method);
                    out.write("sessione_inesistente");
                }
                break;
            }
            case "getCourses": {
                HttpSession session= request.getSession(false);
                PrintWriter out = response.getWriter();
                response.setContentType("text/html");
                if(session!=null){
                    Gson gson = new Gson();
                    response.setContentType("application/json");

                    ArrayList<Corsi> courses = model.getCourses();
                    out.write(gson.toJson(courses));
                }
                else {
                    System.out.println("sessione inesistente in "+method);
                    out.write("sessione_inesistente");
                }
                break;
            }
            case "getLessons":
            case "getPrenotatedLessons": {
                HttpSession session= request.getSession(false);
                PrintWriter out = response.getWriter();
                response.setContentType("text/html");
                if(session!=null){
                    Gson gson = new Gson();
                    response.setContentType("application/json");
                    ArrayList<Lezione> reps = model.getLessonsPrenotated();
                    out.write(gson.toJson(reps));
                }
                else{
                    System.out.println("sessione inesistente in "+method);
                    out.write("sessione_inesistente");
                }

                break;
            }
            case "getAllLessons":
            case "getAllLezioni": {
                HttpSession session=request.getSession(false);
                PrintWriter out = response.getWriter();
                response.setContentType("text/html");
                if(session!=null){
                    Gson gson = new Gson();
                    response.setContentType("application/json");
                    ArrayList<Lezione> reps = model.getAllLessons();
                    out.write(gson.toJson(reps));
                }
                else{
                    System.out.println("sessione inesistente in "+method);
                    out.write("sessione_inesistente");
                }

                break;
            }
            case "getDocsCourse": {
                HttpSession session=request.getSession(false);
                PrintWriter out = response.getWriter();
                response.setContentType("text/html");
                if(session!=null){
                    Gson gson = new Gson();
                    response.setContentType("application/json");
                    ArrayList<CorsoDocente> courses = model.getAssociationsDocsCourses();
                    out.write(gson.toJson(courses));
                }
                else{
                    System.out.println("sessione inesistente in "+method);
                    out.write("sessione_inesistente");
                }


                break;
            }
            case "getPrenotazioniUtente": {
                HttpSession session = null;
                try {
                    session = request.getSession(false);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                int idUtente = Integer.parseInt(request.getParameter("utente"));
                if (session != null) {
                    Gson gson = new Gson();
                    response.setContentType("application/json");
                    ArrayList<Lezione> courses = model.getLezioniUtente(idUtente);
                    out.write(gson.toJson(courses));
                } else {
                    System.out.println("sessione inesistente in "+method);
                    out.write("sessione_inesistente");
                    //System.out.println("SESSIONE INESISTENTE id:"+session.getId());
                }
                break;
            }
            case "getLezioniDisponibili": {
                HttpSession session=request.getSession(false);
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                if(session!=null){
                    Gson gson = new Gson();
                    response.setContentType("application/json");
                    ArrayList<Lezione> reps = model.getFreeLessons();
                    out.print(gson.toJson(reps));
                }
                else{
                    System.out.println("sessione inesistente in "+method);
                    response.setHeader("sessione","inesistente");
                    Gson gson = new Gson();
                    response.setContentType("application/json");
                    ArrayList<Lezione> reps = model.getFreeLessons();
                    out.print(gson.toJson(reps));
                }
                break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String method = request.getParameter("method");
        System.out.println(method);
        switch(method) {
            case "getDocs": {
                HttpSession session= request.getSession(false);
                PrintWriter out = response.getWriter();
                response.setContentType("text/html");
                if(session!=null){
                    Gson gson = new Gson();
                    response.setContentType("application/json");
                    ArrayList<Docente> docs = model.queryDoc();
                    out.write(gson.toJson(docs));
                }
                else {
                    System.out.println("sessione inesistente in "+method);
                    Gson gson = new Gson();
                    response.setContentType("application/json");

                    ArrayList<Docente> docs = model.queryDoc();


                    out.print(gson.toJson(docs));
                }
                break;
            }
            case "getCourses": {
                HttpSession session= request.getSession(false);
                PrintWriter out = response.getWriter();
                response.setContentType("text/html");
                if(session!=null){
                    Gson gson = new Gson();
                    response.setContentType("application/json");

                    ArrayList<Corsi> courses = model.getCourses();
                    out.write(gson.toJson(courses));
                }
                else {
                    //response.setHeader("sessione","inesistente...");
                    System.out.println("sessione inesistente in "+method);
                    Gson gson = new Gson();
                    response.setContentType("application/json");


                    ArrayList<Corsi> courses = model.getCourses();
                    out.write(gson.toJson(courses));
                }
                break;
            }
            case "getLessons":
            case "getPrenotatedLessons": {
                HttpSession session= request.getSession(false);
                PrintWriter out = response.getWriter();
                response.setContentType("text/html");
                if(session!=null){
                    Gson gson = new Gson();
                    response.setContentType("application/json");
                    ArrayList<Lezione> reps = model.getLessonsPrenotated();
                    out.write(gson.toJson(reps));
                }
                else{
                    System.out.println("sessione inesistente in "+method);
                    out.write("sessione_inesistente");
                }

                break;
            }
            case "getAllLessons":
            case "getAllLezioni": {
                HttpSession session=request.getSession(false);
                PrintWriter out = response.getWriter();
                response.setContentType("text/html");
                if(session!=null){
                    Gson gson = new Gson();
                    response.setContentType("application/json");
                    ArrayList<Lezione> reps = model.getAllLessons();
                    out.write(gson.toJson(reps));
                }
                else{
                    response.setHeader("sessione","inesistente");
                    System.out.println("sessione inesistente in "+method);
                    out.write("sessione_inesistente");
                }

                break;
            }
            case "getDocsCourse": {
                HttpSession session=request.getSession(false);
                PrintWriter out = response.getWriter();
                response.setContentType("text/html");
                if(session!=null){
                    Gson gson = new Gson();
                    response.setContentType("application/json");
                    ArrayList<CorsoDocente> courses = model.getAssociationsDocsCourses();
                    /*for (int i = 0; i < courses.size(); i++) {
                        System.out.println(courses.get(i).toString());
                    }*/
                    out.write(gson.toJson(courses));
                }
                else{
                    System.out.println("sessione inesistente in "+method);
                    out.write("sessione_inesistente");
                }


                break;
            }
            case "getPrenotazioniUtente": {
                HttpSession session = request.getSession(false);
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                int idUtente = Integer.parseInt(request.getParameter("utente"));
                if (session != null) {
                    Gson gson = new Gson();
                    response.setContentType("application/json");
                    ArrayList<Lezione> courses = model.getLezioniUtente(idUtente);
                    out.write(gson.toJson(courses));
                } else {
                    System.out.println("sessione inesistente in "+method);
                    out.write("sessione_inesistente");
                    //System.out.println("SESSIONE INESISTENTE id:"+session.getId());
                }
                break;
            }
            case "getLezioniDisponibili": {
                HttpSession session=request.getSession(false);
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                if(session!=null){
                    Gson gson = new Gson();
                    response.setContentType("application/json");
                    ArrayList<Lezione> reps = model.getFreeLessons();
                    out.print(gson.toJson(reps));
                }
                else{
                    System.out.println("sessione inesistente in "+method);
                    response.setHeader("sessione","inesistente");
                    Gson gson = new Gson();
                    response.setContentType("application/json");
                    ArrayList<Lezione> reps = model.getFreeLessons();
                    out.print(gson.toJson(reps));
                }
                break;
            }
        }
    }
}
