package Storage.Dipartimento;

import ApplicationLogic.Utils.InvalidRequestException;
import ApplicationLogic.Utils.ServletLogic;
import Storage.Formato.Formato;
import Storage.Formato.FormatoDAO;
import Storage.PersonaleUnisa.Docente.Docente;
import Storage.Report.Report;
import Storage.Report.ReportDAO;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

/**
 * La classe crea degli oggetti {@code Dipartimento} che contengono come informazione, oltre
 * al nome e al codice del Dipartimento, anche il {@code Formato} e la lista dei {@code Report} associata.
 * Le operazioni svolte da questi oggetti riguardano la gestione dei Report, in particolare:
 * <ul>
 *     <li>eliminazione dei {@code Report};</li>
 *     <li>impostare il {@code Formato} degli Esiti;</li>
 *     <li>ricerca dei {@code Report} secondo dei filtri.</li>
 * </ul>
 */
public class Dipartimento {

    private String nome;
    private String codice;
    private Formato formato;
    ArrayList<Report> reports;

    /**
     * Crea un ogetto {@code Dipartimento} con tutte le informazioni
     * passate in input.
     *
     * @param nome nome del Dipartimento
     * @param codice codice di Dipartimento
     * @param formato Formato degli Esiti del Dipartimento
     * @param reports lista di Report generata dai Docenti di quel Dipartimento
     */
    public Dipartimento(String nome, String codice, Formato formato, ArrayList<Report> reports) {
        this.nome = nome;
        this.codice = codice;
        this.formato = formato;
        this.reports = Objects.requireNonNullElseGet(reports, ArrayList::new);
    }

    /**
     * Costruttore vuoto.
     */
    public Dipartimento() {
        this.nome = "";
        this.codice = "";
        this.formato = null;
        this.reports=new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public Formato getFormato() {
        return formato;
    }

    public void setFormato(Formato formato) {
        if(formato.getId().compareTo(this.codice)==0)
            this.formato = formato;
        else
            throw new IllegalArgumentException("The id of the object 'formato' must be the same of the id of this instance of Dipartimento.");
    }

    public ArrayList<Report> getReports() {
        return reports;
    }

    public void setReports(ArrayList<Report> reports) {
        this.reports = reports;
    }


    /**
     * Aggiorna il {@code Formato} corrispondente all'oggetto
     * {@code Dipartimento} in questione.
     *
     * @param formato Formato da aggiornare
     */
    public void impostaFormato(Formato formato) {
        if(formato == null)
            throw new IllegalArgumentException("The argument cannot be a null object");
        else{
            if(formato.getId().compareTo(this.codice)==0){
                this.formato=formato;
                FormatoDAO formatoDAO=new FormatoDAO();
                formatoDAO.doUpdate(formato);
            }
            else
                throw new IllegalArgumentException("The id of the argument is different from the attribute 'codice' of 'Dipartimento'");
        }
    }


    /**
     * Elimina il Report, passato in input, sia dal Database
     * che dalla cartella di Tomcat.
     *
     * @param report Report da eliminare
     */
    public void eliminaReport(Report report) {
        if(report == null){
            throw new IllegalArgumentException("Cannot delete a null object");
        } else{
            this.reports.remove(report);
            ReportDAO reportDAO = new ReportDAO();
            reportDAO.doDelete(report);
            try {
                File file = new File(ServletLogic.getUploadPath() + "Report" + File.separator +  report.getPathFile());
                if (file.exists()) {
                    file.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Crea un TreeMap avente come key i Report generati da un
     * Docente e come value le informazioni sul Docente: la ricerca avviene
     * mediante il nome e il cognome del Docente che ha generato i Report
     * in un certo periodo di tempo.
     *
     * @param docente docente che ha generato un insieme di Report
     * @param primaData prima data dell'intervallo di tempo
     * @param secondaData seconda data dell'intervallo di tempo
     * @return insieme ordinato composto da una lista di Report e di Docenti
     */
    public TreeMap<Report, Docente> ricercaCompletaReport(Docente docente, Date primaData, Date secondaData)
            throws InvalidRequestException {

        if(docente != null && primaData != null && secondaData != null){
            if (primaData.before(secondaData) || primaData.compareTo(secondaData) == 0){
                ReportDAO reportDAO=new ReportDAO();
                return reportDAO.doSearch(docente, primaData, secondaData);
            } else
                throw new InvalidRequestException("La prima data è minore della seconda data.", List.of("La prima data è minore della seconda data."), HttpServletResponse.SC_BAD_REQUEST);
        } else
            throw new IllegalArgumentException("The arguments 'docente', 'primaData' and 'secondaData' cannot be null.");
    }


    /**
     * Crea un TreeMap avente come key i Report generati da un Docente
     * e come value le informazioni sul Docente: la ricerca avviene mediante il
     * nome e il cognome del Docente.
     *
     * @param docente docente che ha generato un insieme di Report
     * @return insieme ordinato composto da una lista di Report e di Docenti
     */
    public TreeMap<Report, Docente> ricercaReportSoloDocente(Docente docente) {
        if(docente != null){
            ReportDAO reportDAO = new ReportDAO();
            return reportDAO.doSearchByDocName(docente);
        }
        else
            throw new IllegalArgumentException("The arguments 'codDip' cannot be null.");
    }


    /**
     * Crea un TreeMap avente come key i Report generati
     * da un Docente e come value le informazioni sul Docente:
     * la ricerca avviene specificando un certo intervallo di tempo.
     *
     * @param primaData prima data dell'intervallo di tempo
     * @param secondaData seconda data dell'intervallo di tempo
     * @return insieme ordinato composto da una lista di Report e di Docenti
     */
    public TreeMap<Report, Docente> ricercaReportSoloData(Date primaData, Date secondaData) {
        if(primaData.before(secondaData) || primaData.compareTo(secondaData) == 0){
            ReportDAO reportDAO = new ReportDAO();
            return reportDAO.doSearchByDate(primaData, secondaData, this);
        }
        else
            throw new IllegalArgumentException("The arguments 'codDip' cannot be null.");
    }

    public TreeMap<Report, Docente> ricercaReport() {
        ReportDAO reportDAO = new ReportDAO();
        return reportDAO.doRetrieveDocByReport(this.getCodice());
    }

    @Override
    public String toString() {
        return "Dipartimento{" +
                "nome='" + nome + '\'' +
                ", codice='" + codice + '\'' +
                ", formato=" + formato +
                ", reports=" + reports +
                '}';
    }
}
