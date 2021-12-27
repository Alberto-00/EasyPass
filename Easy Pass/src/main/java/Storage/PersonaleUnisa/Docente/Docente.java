package Storage.PersonaleUnisa.Docente;

import Storage.Dipartimento.Dipartimento;
import Storage.PersonaleUnisa.PersonaleUnisa;
import Storage.SessioneDiValidazione.SessioneDiValidazione;
import Storage.SessioneDiValidazione.SessioneDiValidazioneDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Docente extends PersonaleUnisa {
    private ArrayList<SessioneDiValidazione> sessioni;

    public Docente() {}

    public Docente(String nome, String cognome, String username, String password, Dipartimento dipartimento, ArrayList<SessioneDiValidazione> sessioni){
        this.setNome(nome);
        this.setCognome(cognome);
        this.setUsername(username);
        this.setPassword(password);
        this.setDipartimento(dipartimento);
        this.sessioni=sessioni;
    }

    public ArrayList<SessioneDiValidazione> getSessioni() {
        return sessioni;
    }

    public void setSessioni(ArrayList<SessioneDiValidazione> sessioni) {
        this.sessioni = sessioni;
    }



    public SessioneDiValidazione avviaSessione() throws IOException {
        //Come generiamo questo qrCode?
        SessioneDiValidazione sessione=new SessioneDiValidazione(true,this);
        return sessione;
    }

    public SessioneDiValidazione terminaSessione(SessioneDiValidazione sessione) throws SQLException {
        if(sessione==null){
            throw new IllegalArgumentException("The argument cannot be a null object");
        }
        else{
            sessione.setInCorso(false);
            this.sessioni.add(sessione);
            SessioneDiValidazioneDAO sessioneDao=new SessioneDiValidazioneDAO();
            sessioneDao.doCreate(sessione);
            return sessione;
        }
    }

    //public boolean downloadReport(Report report){}

    @Override
    public String toString() {
        return super.toString()+"{" +
                "sessioni=" + sessioni +
                '}';
    }
}
