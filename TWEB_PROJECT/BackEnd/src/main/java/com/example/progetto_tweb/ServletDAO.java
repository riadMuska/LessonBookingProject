package com.example.progetto_tweb;

import DAO.newDAO;
import model.Corsi;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

@WebServlet(name = "ServletDAO", value = "/ServletDAO")
public class ServletDAO extends HttpServlet {

    private newDAO model;
    String url;
    String userDB;
    String passDB;

    public void init(ServletConfig config) throws ServletException {
        System.out.println("sei nella servlet DAO");
        super.init(config);
        ServletContext ctx= config.getServletContext();
        this.url=ctx.getInitParameter("urlDB");
        this.userDB=ctx.getInitParameter("userDB");
        this.passDB=ctx.getInitParameter("passwordDB");
        model=(newDAO) ctx.getAttribute("DAO");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn1 = null;
        ArrayList<Corsi> res=new ArrayList<Corsi>();
        try {
            conn1 = DriverManager.getConnection(url, userDB, passDB);
            Statement st = conn1.createStatement();
            ResultSet rs=st.executeQuery("SELECT * FROM corso");

            while (rs.next())
            {
                res.add(new Corsi(rs.getString("titolo"),rs.getInt("idCorso")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
        }
        request.setAttribute("corsi", res);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
