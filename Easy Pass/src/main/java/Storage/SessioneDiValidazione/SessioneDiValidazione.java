package Storage.SessioneDiValidazione;

import Storage.PersonaleUnisa.Docente.Docente;

import java.util.Date;

public class SessioneDiValidazione {

    private String qRCode;
    private boolean isInCorso;
    private Docente docente;

    public SessioneDiValidazione(String qRCode, boolean isInCorso, Docente docente) {
        this.qRCode = qRCode;
        this.isInCorso = isInCorso;
        this.docente = docente;
    }

    public String getqRCode() {
        return qRCode;
    }

    public void setqRCode(String qRCode) {
        this.qRCode = qRCode;
    }

    public boolean isInCorso() {
        return isInCorso;
    }

    public void setInCorso(boolean inCorso) {
        isInCorso = inCorso;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    @Override
    public String toString() {
        return "SessioneDiValidazione{" +
                "qRCode='" + qRCode + '\'' +
                ", isInCorso=" + isInCorso +
                ", docente=" + docente +
                '}';
    }
}
