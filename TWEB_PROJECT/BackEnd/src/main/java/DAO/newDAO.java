package DAO;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import model.*;
import java.sql.*;
import java.util.ArrayList;

public class newDAO {
    String url;
    String userDB;
    String passDB;

    public String getUrl() {
        return url;
    }

    public String getUserDB() {
        return userDB;
    }

    public String getPassDB() {
        return passDB;
    }

    public newDAO(String url, String userDB, String passDB)
    {
        this.url=url;
        this.userDB=userDB;
        this.passDB=passDB;
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            System.out.println("Driver correttamente registrato");
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    public boolean queryInsertDoc(String name,String surname,int act) {

        boolean out=false;
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(url, userDB, passDB);
            if (conn1 != null) {
            }
            Statement st = conn1.createStatement();
            int rs=st.executeUpdate("INSERT INTO docente(nome,cognome,attivo)VALUES('"+name+"','"+surname+"',"+act+")");
            if(rs!=-1)
            {
                out=true;
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
        return out;
    }

    public  Utente login(String username,String pass) {
        Connection conn1 = null;
        Utente res=null;
        try {
            conn1 = DriverManager.getConnection(url, userDB, passDB);
            Statement st = conn1.createStatement();
            ResultSet sq=st.executeQuery("SELECT * FROM utente WHERE username='"+username+"'AND password='"+pass+"'");
            sq.next();
            res=new Utente(sq.getInt("idUtente"),sq.getString("username"),sq.getString("password"),sq.getString("ruolo"));
        } catch (SQLException e) {
            System.out.println("errore nel DAO");
            System.out.println(e.getMessage());
        }
        finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e2) {
                    System.out.println("errore nel DAO");
                    System.out.println(e2.getMessage());
                }
            }
            return res;
        }
    }
    public  ArrayList<Docente> queryDoc() {
        Connection conn1 = null;
        ArrayList<Docente>res=new ArrayList<Docente>();
        try {
            conn1 = DriverManager.getConnection(url, userDB, passDB);
            Statement st = conn1.createStatement();
            ResultSet rs=st.executeQuery("SELECT * FROM docente WHERE eliminato=0");

            while (rs.next())
            {
                res.add(new Docente(rs.getInt("idDocente"),rs.getString("nome"),rs.getString("cognome"),rs.getInt("attivo")));
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
        return res;
    }
    public  ArrayList<Corsi> getCourses() {
        Connection conn1 = null;
        ArrayList<Corsi>res=new ArrayList<Corsi>();
        try {
            conn1 = DriverManager.getConnection(url, userDB, passDB);

            Statement st = conn1.createStatement();
            ResultSet rs=st.executeQuery("SELECT * FROM corso WHERE eliminato=0");

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
        return res;
    }

    public  ArrayList<Lezione> getAllLessons() {
        Connection conn1 = null;
        ArrayList<Lezione>res=new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url, userDB, passDB);

            Statement st = conn1.createStatement();
            ResultSet rs=st.executeQuery("SELECT lezione.effetuata as effetuata, lezione.disponibile as disponibile, lezione_prenotata.disdetta as disdetta,lezione_prenotata.idLezionePrenotata, data_ora_inizio as inizio,data_ora_fine as fine, corso.titolo as corso, docente.nome as docente, docente.idDocente as idDoc, corso.idCorso as idCourse,utente.username as user, utente.idUtente as idUtente FROM lezione_prenotata right JOIN lezione ON(lezione.idLezione=lezione_prenotata.idLezionePrenotata) JOIN corso ON (lezione.materia=corso.idCorso) JOIN docente ON (lezione.doc=docente.idDocente) left JOIN utente ON(lezione_prenotata.ut=utente.idUtente)");
            while (rs.next())
            {
                boolean disdetta=rs.getInt("disdetta")==0?false:true;
                boolean disponibile=rs.getInt("disponibile")==0?false:true;
                boolean effetuata=rs.getInt("effetuata")==0?false:true;
                int idUt=rs.getInt("idUtente");
                res.add(new Lezione(rs.getInt("idLezionePrenotata"),rs.getDate("inizio").toString()+" "+rs.getTime("inizio").toString(),rs.getDate("fine").toString()+" "+rs.getTime("fine").toString(),rs.getString("docente"),rs.getString("corso"),rs.getInt("idDoc"),rs.getInt("idCourse"),disponibile,disdetta,rs.getString("user"),idUt,effetuata));
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
        return res;
    }


    public  ArrayList<Lezione> getLessonsPrenotated() {
        Connection conn1 = null;
        ArrayList<Lezione>res=new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url, userDB, passDB);

            Statement st = conn1.createStatement();
            ResultSet rs=st.executeQuery("SELECT corso.titolo as titolo, lezione.effetuata as effetuata, lezione.idLezione, data_ora_inizio as inizio,data_ora_fine as fine, lezione.corso as corso, lezione.docente as nome_docente,docente.cognome as cognome_docente, lezione.doc as idDoc, lezione.materia as idCourse,lezione_prenotata.disdetta as disdetta,utente.idUtente,utente.username as utente FROM lezione  JOIN lezione_prenotata ON(lezione_prenotata.idLezionePrenotata=lezione.idLezione) JOIN utente  ON(lezione_prenotata.ut=utente.idUtente) JOIN corso ON(lezione.materia=corso.idCorso) JOIN docente ON(lezione.doc=docente.idDocente)");
            while (rs.next())
            {
                boolean disdetta=rs.getInt("disdetta")==0?false:true;
                boolean effetuata=rs.getInt("effetuata")==0?false:true;
                res.add(new Lezione(rs.getInt("idLezione"),rs.getDate("inizio").toString()+" "+rs.getTime("inizio").toString(),rs.getDate("fine").toString()+" "+rs.getTime("fine").toString(),rs.getString("nome_docente")+" "+rs.getString("cognome_docente"),rs.getString("titolo"),rs.getInt("idDoc"),rs.getInt("idCourse"),false,disdetta,rs.getString("utente"),rs.getInt("idUtente"),effetuata));
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
        return res;
    }

    public  ArrayList<Lezione> getFreeLessons() {
        Connection conn1 = null;
        ArrayList<Lezione>res=new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url, userDB, passDB);

            Statement st = conn1.createStatement();

            ResultSet rs=st.executeQuery("SELECT corso.titolo as titolo,lezione.effetuata as effetuata, lezione.idLezione, data_ora_inizio as inizio,data_ora_fine as fine, docente.nome as docente, docente.idDocente as idDoc, lezione.materia as idCourse FROM lezione JOIN docente on (lezione.doc=docente.idDocente) JOIN corso ON(lezione.materia=corso.idCorso)  WHERE disponibile=1 AND corso.eliminato=0 AND docente.eliminato=0");
            while (rs.next())
            {
                boolean effetuata=rs.getInt("effetuata")==0?false:true;
                res.add(new Lezione(rs.getInt("idLezione"),rs.getDate("inizio").toString()+" "+rs.getTime("inizio").toString(),rs.getDate("fine").toString()+" "+rs.getTime("fine").toString(),rs.getString("docente"),rs.getString("titolo"),rs.getInt("idDoc"),rs.getInt("idCourse"),true,false,null,-1,effetuata));
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
        return res;
    }

    public  ArrayList<Lezione> getLezioniUtente(int idUtente) {
        Connection conn1 = null;
        ArrayList<Lezione>res=new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url, userDB, passDB);
            System.out.println("id utente dal DAO: "+idUtente);
            Statement st = conn1.createStatement();
            ResultSet rs=st.executeQuery("SELECT lezione.effetuata as effetuata, lezione_prenotata.disdetta as disdetta,lezione_prenotata.idLezionePrenotata as idLezionePrenotata, data_ora_inizio as inizio,data_ora_fine as fine, corso.titolo as corso, docente.nome as docente, docente.idDocente as idDoc, corso.idCorso as idCourse, utente.username as username FROM lezione_prenotata JOIN lezione ON(lezione.idLezione=lezione_prenotata.idLezionePrenotata) JOIN corso ON (lezione.materia=corso.idCorso) JOIN docente ON (lezione.doc=docente.idDocente) JOIN utente ON(lezione_prenotata.ut=utente.idUtente) WHERE utente.idUtente="+idUtente);
            while (rs.next())
            {

                boolean disdetta=rs.getInt("disdetta")==0?false:true;
                boolean effetuata=rs.getInt("effetuata")==0?false:true;
                System.out.println(rs.getString("idLezionePrenotata")+rs.getDate("inizio").toString()+" "+rs.getTime("inizio").toString()+rs.getDate("fine").toString()+" "+rs.getTime("fine").toString()+rs.getString("docente")+rs.getString("corso")+rs.getString("idDoc")+rs.getString("idCourse")+"false"+disdetta+rs.getString("username")+idUtente+effetuata);
                res.add(new Lezione(rs.getInt("idLezionePrenotata"),rs.getDate("inizio").toString()+" "+rs.getTime("inizio").toString(),rs.getDate("fine").toString()+" "+rs.getTime("fine").toString(),rs.getString("docente"),rs.getString("corso"),rs.getInt("idDoc"),rs.getInt("idCourse"),false,disdetta,rs.getString("username"),idUtente ,effetuata));
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
        return res;
    }

    /*public  ArrayList<Lezione> getAllLezioni() {
        Connection conn1 = null;
        ArrayList<Lezione>res=new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url, userDB, passDB);

            Statement st = conn1.createStatement();
            ResultSet rs=st.executeQuery("SELECT data_ora_inizio as inizio,data_ora fine as fine,idDocente as docente,idCorso as corso FROM lezione LEFT JOIN lezione_prenotata ON (lezione.idLezione=lezione_prenotata.idLezionePrenotata)");
            while (rs.next())
            {

                boolean disdetta=rs.getInt("disdetta")==0?false:true;
                res.add(new Lezione(rs.getInt("idLezionePrenotata"),rs.getDate("inizio").toString()+" "+rs.getTime("inizio").toString(),rs.getDate("fine").toString()+" "+rs.getTime("fine").toString(),rs.getString("docente"),rs.getString("corso"),rs.getInt("idDoc"),rs.getInt("idCourse"),false,disdetta));
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
        return res;
    }*/



    public String CreatePrenotation(int idLezione,int idUtente)
    {
        Connection conn1 = null;
        String res="";
        try {
            conn1 = DriverManager.getConnection(url, userDB, passDB);
            Statement st = conn1.createStatement();
            st.executeUpdate("UPDATE lezione set disponibile=0 WHERE idLezione="+idLezione);
            st.executeUpdate("INSERT INTO lezione_prenotata (idLezionePrenotata,ut) VALUES('"+idLezione+"','"+idUtente+"')");
            res="OK";
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            res="ERROR";
        }
        finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
            return res;
        }
    }
    public String CreateDoc(String nome,String cognome, int attivita)
    {
        Connection conn1 = null;
        String res="";
        try {
            conn1 = DriverManager.getConnection(url, userDB, passDB);
            Statement st = conn1.createStatement();
            st.executeUpdate("INSERT INTO docente (nome,cognome,attivo,eliminato) VALUES('"+nome+"','"+cognome+"','"+attivita+"','0')");
            res="OK";
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            res="ERROR";
        }
        finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
            return res;
        }
    }

    public String CreateCourse(String titolo)
    {
        Connection conn1 = null;
        String res="";
        try {
            conn1 = DriverManager.getConnection(url, userDB, passDB);
            Statement st = conn1.createStatement();
            st.executeUpdate("INSERT INTO corso (titolo,eliminato) VALUES('"+titolo+"','0')");
            res="OK";
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            res="ERROR";
        }
        finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
            return res;
        }
    }
    public String DeletePrenotation(int idLezione)
    {
        Connection conn1 = null;
        String res="";
        try {
            conn1 = DriverManager.getConnection(url, userDB, passDB);

            Statement st = conn1.createStatement();
            st.executeUpdate("DELETE FROM lezione WHERE idLezione="+idLezione);
            res="OK";
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            res="ERROR";
        }
        finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
            return res;
        }
    }

    public String UpdatePrenotation(int idLezione)
    {
        Connection conn1 = null;
        String res="";
        try {
            conn1 = DriverManager.getConnection(url, userDB, passDB);

            Statement st = conn1.createStatement();
            st.executeUpdate("UPDATE lezione SET effetuata=1 WHERE idLezione="+idLezione);
            res="OK";
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            res="ERROR";
        }
        finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
            return res;
        }
    }
    public String DeleteDocente(int idDocente)
    {
        Connection conn1 = null;
        String res="";
        try {
            conn1 = DriverManager.getConnection(url, userDB, passDB);

            Statement st = conn1.createStatement();
            st.executeUpdate("UPDATE docente SET eliminato=1 WHERE idDocente="+idDocente);
            res="OK";
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            res="ERROR";
        }
        finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
            return res;
        }
    }
    public String DeleteCorso(int idCorso)
    {
        Connection conn1 = null;
        String res="";
        try {
            conn1 = DriverManager.getConnection(url, userDB, passDB);

            Statement st = conn1.createStatement();
            st.executeUpdate("UPDATE corso SET eliminato=1 WHERE idCorso="+idCorso);
            res="OK";
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            res="ERROR";
        }
        finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
            return res;
        }
    }
    public String DeleteAssociation(int idDocente,int idCorso)
    {
        Connection conn1 = null;
        String res="";
        try {
            conn1 = DriverManager.getConnection(url, userDB, passDB);

            Statement st = conn1.createStatement();
            st.executeUpdate("DELETE FROM corso_docente WHERE corso_id='"+idCorso+"'AND docente_id='"+idDocente+"'");
            res="OK";
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            res="ERROR";
        }
        finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
            return res;
        }
    }
    public String DisdiciLezione(int idLezione)
    {
        Connection conn1 = null;
        String res="";
        try {
            conn1 = DriverManager.getConnection(url, userDB, passDB);
            Statement st = conn1.createStatement();
            st.executeUpdate("UPDATE lezione_prenotata set disdetta=1 WHERE idLezionePrenotata="+idLezione);
            res="OK";
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            res="ERROR";
        }
        finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
            return res;
        }
    }
    public  ArrayList<CorsoDocente> getAssociationsDocsCourses() {
        Connection conn1 = null;
        ArrayList<CorsoDocente>res=new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url, userDB, passDB);

            Statement st = conn1.createStatement();
            ResultSet rs=st.executeQuery("SELECT corso.idCorso,docente.idDocente,corso.titolo as titolo, docente.nome as nome,docente.cognome as cognome FROM corso_docente JOIN corso on (corso_docente.corso_id=corso.idCorso) JOIN docente on(corso_docente.docente_id=docente.idDocente)");
            while (rs.next())
            {
                res.add(new CorsoDocente(rs.getString("nome"),rs.getString("cognome"),rs.getString("titolo"), rs.getInt("idDocente"),rs.getInt("idCorso")));
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
        return res;
    }
    public  String newUser(String usr,String pwd,String type) {
        Connection conn1 = null;
        String res="";
        try {
            conn1 = DriverManager.getConnection(url, userDB, passDB);

            Statement st = conn1.createStatement();
            st.executeUpdate("INSERT INTO utente(username,password,ruolo)VALUES('"+usr+"','"+pwd+"','"+type+"')");
            res="OK";
        } catch (SQLException e) {

            if(e instanceof MySQLIntegrityConstraintViolationException){
                res="duplicated";
            }
            else
            {
                System.out.println(e.getMessage());
                res=e.getMessage();
            }

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
        return res;
    }

    public  String newAssociation(int idDocente,int idCorso) {
        Connection conn1 = null;
        String res="";
        try {
            conn1 = DriverManager.getConnection(url, userDB, passDB);

            Statement st = conn1.createStatement();
            st.executeUpdate("INSERT INTO corso_docente(corso_id,docente_id)VALUES('"+idCorso+"','"+idDocente+"')");
            res="OK";
        } catch (SQLException e) {

            if(e instanceof MySQLIntegrityConstraintViolationException){
                res="duplicated";
            }
            else
            {
                System.out.println(e.getMessage());
                res=e.getMessage();
            }

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
        return res;
    }
}

