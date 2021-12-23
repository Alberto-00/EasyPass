package Storage.PersonaleUnisa.Docente;

import Storage.PersonaleUnisa.PersonaleUnisa;
import Storage.SessioneDiValidazione.SessioneDiValidazione;

import java.util.ArrayList;

public class Docente extends PersonaleUnisa {
    private ArrayList<SessioneDiValidazione> sessioni;

    public Docente() {}

    public Docente(String nome, String cognome, String username, String password,ArrayList<SessioneDiValidazione> sessioni){
        this.setNome(nome);
        this.setCognome(cognome);
        this.setUsername(username);
        this.setPassword(password);
        this.sessioni=sessioni;
    }

    public ArrayList<SessioneDiValidazione> getSessioni() {
        return sessioni;
    }

    public void setSessioni(ArrayList<SessioneDiValidazione> sessioni) {
        this.sessioni = sessioni;
    }

    @Override
    public String toString() {
        return super.toString()+"{" +
                "sessioni=" + sessioni +
                '}';
    }

    //public SessioneDiValidazione avviaSessione(){}
    //public SessioneDiValidazione terminaSessione(SessioneDiValidazione sessione){}
    //public boolean downloadReport(Report){}
}
