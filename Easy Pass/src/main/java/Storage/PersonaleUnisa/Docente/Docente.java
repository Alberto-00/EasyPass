package Storage.PersonaleUnisa.Docente;

import ApplicationLogic.Utils.JSONSerializable;
import Storage.Dipartimento.Dipartimento;
import Storage.PersonaleUnisa.PersonaleUnisa;
import Storage.Report.Report;
import Storage.SessioneDiValidazione.SessioneDiValidazione;
import Storage.SessioneDiValidazione.SessioneDiValidazioneDAO;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Docente extends PersonaleUnisa implements JSONSerializable {

    private ArrayList<SessioneDiValidazione> sessioni;
    private ArrayList<Report> listaReport;

    public Docente() {
        this.setNome("");
        this.setCognome("");
        this.setUsername("");
        this.setPassword("");
        this.setDipartimento(null);
        this.sessioni=new ArrayList<>();
        this.listaReport = new ArrayList<>();
    }

    public Docente(String nome, String cognome, String username, String password,
                   Dipartimento dipartimento, ArrayList<SessioneDiValidazione> sessioni,
                   ArrayList<Report> reports){
        this.setNome(nome);
        this.setCognome(cognome);
        this.setUsername(username);
        this.setPassword(password);
        this.setDipartimento(dipartimento);
        this.sessioni = sessioni;
        this.listaReport = reports;
    }

    public Docente(String nome, String cognome, String username, String password,
                   Dipartimento dipartimento){
        this.setNome(nome);
        this.setCognome(cognome);
        this.setUsername(username);
        this.setPassword(password);
        this.setDipartimento(dipartimento);
    }

    public ArrayList<Report> getListaReport() {
        return listaReport;
    }

    public void setListaReport(ArrayList<Report> listaReport) {
        this.listaReport = listaReport;
    }

    public ArrayList<SessioneDiValidazione> getSessioni() {
        return sessioni;
    }

    public void setSessioni(ArrayList<SessioneDiValidazione> sessioni) {
        this.sessioni = sessioni;
    }

    public SessioneDiValidazione avviaSessione() throws IOException, SQLException {
        return new SessioneDiValidazione(true, this);
    }

    public SessioneDiValidazione terminaSessione(SessioneDiValidazione sessione) throws SQLException {
        if(sessione == null)
            throw new IllegalArgumentException("The argument cannot be a null object");

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
        return "Docente{" +
                "sessioni=" + sessioni +
                ", listaReport=" + listaReport +
                '}';
    }

    @Override
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        object.put("nome", this.getNome());
        object.put("cognome", this.getCognome());
        return object;
    }
}
