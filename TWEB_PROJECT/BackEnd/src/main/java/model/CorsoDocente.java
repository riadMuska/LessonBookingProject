package model;

public class CorsoDocente {
    String nome;
    String cognome;
    String titolo;
    int idDocente;
    int idCorso;

    public CorsoDocente(String nome, String cognome, String titolo, int idDocente, int idCorso) {
        this.nome = nome;
        this.cognome = cognome;
        this.titolo = titolo;
        this.idDocente = idDocente;
        this.idCorso = idCorso;
    }
}
