package Storage.PersonaleUnisa.Docente;

import ApplicationLogic.Utils.JSONSerializable;
import Storage.Dipartimento.Dipartimento;
import Storage.PersonaleUnisa.PersonaleUnisa;
import Storage.Report.Report;
import Storage.SessioneDiValidazione.SessioneDiValidazione;
import Storage.SessioneDiValidazione.SessioneDiValidazioneDAO;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * La classe crea degli oggetti {@code Docente} estendendo la classe astratta
 * {@code PersonaleUnisa}. I metodi forniti da questa classe consentono agli oggetti
 * {@code Docente} di effettuare operazioni relative alla gestione della
 * {@code Sessione di Validazione}.
 *
 * @see PersonaleUnisa
 */
public class Docente extends PersonaleUnisa implements JSONSerializable {

    private ArrayList<SessioneDiValidazione> sessioni;
    private ArrayList<Report> listaReport;

    /**
     * Costruttore vuoto.
     */
    public Docente() {
        this.setNome("");
        this.setCognome("");
        this.setUsername("");
        this.setPassword("");
        this.setDipartimento(null);
        this.sessioni=new ArrayList<>();
        this.listaReport = new ArrayList<>();
    }

    /**
     * Crea un oggetto {@code Docente} con le relative foreign key.
     *
     * @param nome nome del Docente
     * @param cognome cognome del Docente
     * @param username username del Docente
     * @param password password del Docente
     * @param dipartimento Dipartimento a cui appartiene il Docente
     * @param sessioni Sessioni di validazioni create dal Docente
     * @param reports Report generati dal Docente
     */
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

    /**
     * Crea un oggetto {@code Docente} senza le relative foreign key.
     *
     * @param nome nome del Docente
     * @param cognome cognome del Docente
     * @param username username del Docente
     * @param password password del Docente
     * @param dipartimento Dipartimento a cui appartiene il Docente
     */
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

    /**
     * Avvia una nuova Sessione di Validazione.
     */
    public SessioneDiValidazione avviaSessione() throws IOException {
        return new SessioneDiValidazione(true, this);
    }

    /**
     * Termina una Sessione di Validazione, ancora in corso, dal database e la invalida.
     *
     * @param sessione Sessione di validazione in corso
     */
    public void terminaSessione(SessioneDiValidazione sessione) {
        if(sessione == null)
            throw new IllegalArgumentException("The argument cannot be a null object");
        else{
            sessione.setInCorso(false);
            this.sessioni.remove(sessione);
            SessioneDiValidazioneDAO sessioneDAO = new SessioneDiValidazioneDAO();
            sessioneDAO.doUpdate(sessione);
        }
    }

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
