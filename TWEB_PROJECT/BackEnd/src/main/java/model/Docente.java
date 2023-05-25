package model;

public class Docente {
    public int getAttivo() {
        return attivo;
    }

    public void setAttivo(int attivo) {
        this.attivo = attivo;
    }

    public int getIdDocente() {
        return idDocente;
    }

    private int idDocente;
    private int attivo;
    private String nome;
    private String cognome;


    public Docente(int idDocente,String nome, String cognome,int attivo) {
        this.idDocente=idDocente;
        this.nome = nome;
        this.cognome = cognome;
        this.attivo=attivo;
    }


    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }


    @Override
    public String toString() {
        return (nome+ " " +cognome);
    }
}

