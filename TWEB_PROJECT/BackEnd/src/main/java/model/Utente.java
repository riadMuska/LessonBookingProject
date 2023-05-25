package model;

public class Utente {
    int idUtente;
    String username;
    String password;
    String ruolo;

    public Utente(int idUtente,String username, String password, String ruolo) {
        this.idUtente=idUtente;
        this.username = username;
        this.password = password;
        this.ruolo = ruolo;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }
}
