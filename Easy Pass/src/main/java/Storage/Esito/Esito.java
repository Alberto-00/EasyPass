package Storage.Esito;

import Storage.Report.Report;

import java.time.LocalDate;
import java.util.Date;

public class Esito {

    private int id;
    private String nomeStudente;
    private String cognomeStudente;
    private Date dataDiNascitaStudente;
    private String stringaGP;
    private boolean validita;
    private Report report;

    public Esito(){}

    public Esito(int id, String nomeStudente, String cognomeStudente, Date dataDiNascitaStudente, String stringaGP, boolean validita, Report report) {
        this.id = id;
        this.nomeStudente = nomeStudente;
        this.cognomeStudente = cognomeStudente;
        this.dataDiNascitaStudente = dataDiNascitaStudente;
        this.stringaGP = stringaGP;
        this.validita = validita;
        this.report = report;
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
                ", dataDiNascitaStudente='" + dataDiNascitaStudente + '\'' +
                ", stringaGP='" + stringaGP + '\'' +
                ", validita=" + validita +
                ", reportId=" + report +
                '}';
    }
}
