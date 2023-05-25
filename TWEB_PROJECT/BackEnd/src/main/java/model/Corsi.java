package model;

public class Corsi {
    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    String titolo;
    int idCorso;
    public Corsi(String titolo,int id) {
        this.titolo = titolo;
        this.idCorso=id;
    }

    @Override
    public String toString() {
        return titolo;
    }
}
