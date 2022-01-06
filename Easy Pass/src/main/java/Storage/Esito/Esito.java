package Storage.Esito;

import ApplicationLogic.Utils.JSONSerializable;
import Storage.Report.Report;
import org.json.simple.JSONObject;
import Storage.SessioneDiValidazione.SessioneDiValidazione;

import java.util.Date;

public class Esito implements JSONSerializable {

    private int id;
    private String nomeStudente;
    private String cognomeStudente;
    private Date dataDiNascitaStudente;
    private String stringaGP;
    private boolean validita;
    private Report report;
    private SessioneDiValidazione sessione;

    public Esito(){
        this.id = 0;
        this.nomeStudente = "";
        this.cognomeStudente = "";
        this.dataDiNascitaStudente = null;
        this.stringaGP = "";
        this.validita = false;
        this.report = null;
        this.sessione = null;
    }

    public Esito(int id, String nomeStudente, String cognomeStudente,
                 Date dataDiNascitaStudente, String stringaGP,
                 boolean validita, Report report, SessioneDiValidazione sessione) {
        this.id = id;
        this.nomeStudente = nomeStudente;
        this.cognomeStudente = cognomeStudente;
        this.dataDiNascitaStudente = dataDiNascitaStudente;
        this.stringaGP = stringaGP;
        this.validita = validita;
        this.report = report;
        this.sessione = sessione;
    }

    public SessioneDiValidazione getSessione() {
        return sessione;
    }

    public void setSessione(SessioneDiValidazione sessione) {
        this.sessione = sessione;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeStudente() {
        return nomeStudente;
    }

    public void setNomeStudente(String nomeStudente) {
        this.nomeStudente = nomeStudente;
    }

    public String getCognomeStudente() {
        return cognomeStudente;
    }

    public void setCognomeStudente(String cognomeStudente) {
        this.cognomeStudente = cognomeStudente;
    }

    public Date getDataDiNascitaStudente() {
        return dataDiNascitaStudente;
    }

    public void setDataDiNascitaStudente(Date dataDiNascitaStudente) {
        this.dataDiNascitaStudente = dataDiNascitaStudente;
    }

    public String getStringaGP() {
        return stringaGP;
    }

    public void setStringaGP(String stringaGP) {
        this.stringaGP = stringaGP;
    }

    public boolean isValidita() {
        return validita;
    }

    public void setValidita(boolean validita) {
        this.validita = validita;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    @Override
    public String toString() {
        return "Esito{" +
                "id=" + id +
                ", nomeStudente='" + nomeStudente + '\'' +
                ", cognomeStudente='" + cognomeStudente + '\'' +
                ", dataDiNascitaStudente=" + dataDiNascitaStudente +
                ", stringaGP='" + stringaGP + '\'' +
                ", validita=" + validita +
                ", report=" + report +
                ", sessione=" + sessione +
                '}';
    }

    @Override
    public JSONObject toJson(){
        JSONObject object = new JSONObject();
        object.put("id", ""+this.id);
        object.put("nomeStudente", this.nomeStudente);
        object.put("cognomeStudente", this.cognomeStudente);
        object.put("dataDiNascitaStudente",this.dataDiNascitaStudente.toString());

        if(this.validita)
            object.put("validita", "Valido");
        else
            object.put("validita","Non valido");
        return object;
    }
}
