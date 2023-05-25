package model;

public class Lezione {
    int idLezione;
    String data_ora_inizio;
    String data_ora_fine;
    String docente;
    String corso;

    String utente;
    int idUtente;
    int idDocente;
    int idCorso;
    boolean disponibile;
    boolean disdetta;
    boolean effetuata;
    public Lezione(int idLezione,String data_ora_inizio, String data_ora_fine, String docente,String corso, int idDocente,int idCorso,boolean disponibile,boolean disdetta,String utente,int idutente,boolean effetuata) {
        this.idLezione=idLezione;
        this.data_ora_inizio = data_ora_inizio;
        this.data_ora_fine = data_ora_fine;
        this.docente = docente;
        this.corso=corso;
        this.idDocente=idDocente;
        this.idCorso=idCorso;
        this.disponibile=disponibile;
        this.disdetta=disdetta;
        this.idUtente=idutente;
        this.utente=utente;
        this.effetuata=effetuata;
    }
}
