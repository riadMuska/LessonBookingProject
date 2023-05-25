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

@WebServlet(name = "ServletErrorNotLog", value = "/ServletErrorNotLog")
public class ServletErrorNotLog extends HttpServlet {

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

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Sto controllando la sessione");
        HttpSession session = request.getSession(false);
        PrintWriter out = response.getWriter();
        response.setContentType("text/html;charset=UTF-8");
        String user=request.getParameter("user");
        String ruolo=request.getParameter("ruolo");
        System.out.println("ruolo: "+ruolo+" user: "+user);
        if (session!=null) {
            if (session.getAttribute("user") != null) {
                String sessionUser = session.getAttribute("user").toString();
                if (session.getAttribute("ruolo") != null) {
                    String ruoloSessione = session.getAttribute("ruolo").toString();
                    if (Objects.equals(sessionUser, user)) {
                        if(Objects.equals(ruoloSessione,ruolo)){
                            out.write("logged_in");
                        }
                        else{
                            out.write("no_permission");
                        }
                    } else {
                            out.write("not_logged");
                        }
                    }
                }
            else {
                out.write("not_logged");
            }
        }
        else {
            System.out.println("sessione_inesistente in ctrl_session_erro_not_logged");
            out.write("sessione_inesistente");
        }
    }
}
