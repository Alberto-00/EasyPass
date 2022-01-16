package Storage.PersonaleUnisa.Direttore;

import ApplicationLogic.Utils.InvalidRequestException;
import Storage.Formato.Formato;
import Storage.PersonaleUnisa.Docente.Docente;
import Storage.PersonaleUnisa.PersonaleUnisa;
import Storage.Report.Report;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

/**
 * La classe crea degli oggetti {@code DirettoreDiDipartimento} estendendo la classe astratta
 * {@code PersonaleUnisa}, inoltre, tale classe funge da wrapper per alcuni metodi della classe
 * {@code Dipartimento}. I metodi forniti da questa classe consentono agli oggetti {@code DirettoreDiDipartimento}
 * di effettuare operazioni relative alla gestione dei Report e del Formato.
 *
 * @see PersonaleUnisa
 * @see Storage.Dipartimento.Dipartimento
 */
public class DirettoreDiDipartimento extends PersonaleUnisa {

    /**
     * Costruttore vuoto.
     */
    public DirettoreDiDipartimento() {
        this.setNome("");
        this.setCognome("");
        this.setUsername("");
        this.setPassword("");
        this.setDipartimento(null);
    }

    /**
     * Elimina un Report dal database e dalla cartella dei Report di Tomcat.
     *
     * @param report {@code Report} da eliminare
     */
    public void eliminaReport(Report report) {
        if(report == null)
            throw new IllegalArgumentException("Cannot delete a null object");
        else
            this.getDipartimento().eliminaReport(report);
    }

    /**
     * Aggiorna il {@code Formato} del Dipartimento in cui risiede
     * il Direttore.
     *
     * @param formato {@code Formato} da aggiornare
     */
    public void impostaFormato(Formato formato) {
        if(formato == null)
            throw new IllegalArgumentException("The argument cannot be a null object");
        else
            this.getDipartimento().impostaFormato(formato);
    }

    /**
     * Cerca un insieme ordinato di Report e di Docenti secondo un filtro: viene creato
     * un TreeMap contenente la lista dei Report e dei Docenti che hanno generato quei
     * Report.
     * Il filtro usato per la ricerca opera sul nome e sul cognome del Docente e su un range di
     * date per i quali sono stati generati i Report.
     *
     * @param docente {@code Docente} che ha generato un sottoinsieme di {@code Report}
     * @param primaData prima data dell'intervallo di tempo
     * @param secondaData seconda data dell'intervallo di tempo
     * @return {@code TreeMap} avente come key i {@code Report} e value i {@code Docenti}
     */
    public TreeMap<Report, Docente> ricercaCompletaReport(Docente docente, Date primaData, Date secondaData) throws InvalidRequestException {
        if(docente != null && primaData != null && secondaData != null){
            if (primaData.before(secondaData) || primaData.compareTo(secondaData) == 0)
               return this.getDipartimento().ricercaCompletaReport(docente, primaData, secondaData);
            else
                throw new InvalidRequestException("La prima data è minore della seconda data.", List.of("La prima data è minore della seconda data."), HttpServletResponse.SC_BAD_REQUEST);
        } else
            throw new IllegalArgumentException("The arguments 'docente', 'primaData' and 'secondaData' cannot be null.");
    }

    /**
     * Cerca un insieme ordinato di Report e di Docenti secondo un filtro: viene creato
     * un TreeMap contenente la lista dei Report e dei Docenti che hanno generato quei
     * Report.
     * Il filtro usato per la ricerca opera sul nome e sul cognome del Docente.
     *
     * @param docente {@code Docente} che ha generato un sottoinsieme di {@code Report}
     * @return {@code TreeMap} avente come key i {@code Report} e value i {@code Docenti}
     */
    public TreeMap<Report, Docente> ricercaReportSoloDocente(Docente docente) {
        if(docente != null)
            return this.getDipartimento().ricercaReportSoloDocente(docente);
        else
            throw new IllegalArgumentException("The arguments 'codDip' cannot be null.");
    }

    /**
     * Cerca un insieme ordinato di Report e di Docenti secondo un filtro: viene creato
     * un TreeMap contenente la lista dei Report e dei Docenti che hanno generato quei
     * Report.
     * Il filtro usato per la ricerca opera su un intervallo di tempo per i quali sono
     * stati generati i Report.
     *
     * @param primaData prima data dell'intervallo di tempo
     * @param secondaData seconda data dell'intervallo di tempo
     * @return {@code TreeMap} avente come key i {@code Report} e value i {@code Docenti}
     */
    public TreeMap<Report, Docente> ricercaReportSoloData(Date primaData, Date secondaData) {
        if(primaData.before(secondaData) || primaData.compareTo(secondaData) == 0)
           return this.getDipartimento().ricercaReportSoloData(primaData, secondaData);
        else
            throw new IllegalArgumentException("The arguments 'codDip' cannot be null.");
    }

    /**
     * Cerca tutti Report generati dai Docenti del Dipartimento del {@code DirettoreDiDipartimento}.
     * @return {@code TreeMap} avente come key i {@code Report} e value i {@code Docenti}
     */
    public TreeMap<Report, Docente> ricercaReport() {
        return this.getDipartimento().ricercaReport();
    }

}
