package Storage.Dipartimento;

import ApplicationLogic.Utils.InvalidRequestException;
import Storage.Esito.Esito;
import Storage.Esito.EsitoDAO;
import Storage.Formato.Formato;
import Storage.Formato.FormatoDAO;
import Storage.PersonaleUnisa.Docente.Docente;
import Storage.Report.Report;
import Storage.Report.ReportDAO;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

public class Dipartimento {

    private String nome;
    private String codice;
    private Formato formato; //L'id del formato deve essere uguale all'id del dipartimento a cui appartiene
    ArrayList<Report> reports;

    public Dipartimento(String nome, String codice, Formato formato, ArrayList<Report> reports) {
        this.nome = nome;
        this.codice = codice;
        this.formato = formato;
        if(reports==null){
            this.reports=new ArrayList<>();
        }
        else{
            this.reports=reports;
        }
    }

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
        if(formato.getId().compareTo(this.codice)==0) {
            this.formato = formato;
        }
        else{
            throw new IllegalArgumentException("The id of the object 'formato' must be the same of the id of this instance of Dipartimento");
        }
    }

    public ArrayList<Report> getReports() {
        return reports;
    }

    public void setReports(ArrayList<Report> reports) {
        this.reports = reports;
    }

    //Il formato di un dipartimento non si cambia ma si aggiorna sempre lo stesso
    public Formato impostaFormato(Formato formato) throws SQLException {
        if(formato == null){
            throw new IllegalArgumentException("The argument cannot be a null object");
        }
        else{
            if(formato.getId().compareTo(this.codice)==0){
                this.formato=formato;
                FormatoDAO formatoDAO=new FormatoDAO();
                formatoDAO.doUpdate(formato);
                return formato;
            }
            else throw new IllegalArgumentException("The id of the argument is different from the attribute 'codice' of 'Dipartimento'");
        }
    }

    public void eliminaReport(Report report) throws SQLException {
        if(report == null){
            throw new IllegalArgumentException("Cannot delete a null object");
        } else{
            this.reports.remove(report);
            ReportDAO reportDAO = new ReportDAO();
            EsitoDAO esitoDAO = new EsitoDAO();

            for (Esito esito : esitoDAO.doRetrieveWithRelations(report.getId())) {
                esitoDAO.doDelete(esito);
            }
            reportDAO.doDelete(report);
        }
    }

    public TreeMap<Report, Docente> ricercaCompletaReport(Docente docente, Date primaData, Date secondaData) throws SQLException, InvalidRequestException {
        if(docente != null && primaData != null && secondaData != null){
            if (primaData.before(secondaData) || primaData.compareTo(secondaData) == 0){
                ReportDAO reportDAO=new ReportDAO();
                return reportDAO.doSearch(docente, primaData, secondaData);
            } else
                throw new InvalidRequestException("La prima data è minore della seconda data.", List.of("La prima data è minore della seconda data."), HttpServletResponse.SC_BAD_REQUEST);
        } else
            throw new IllegalArgumentException("The arguments 'docente', 'primaData' and 'secondaData' cannot be null.");
    }

    public TreeMap<Report, Docente> ricercaReportSoloDocente(Docente docente) throws SQLException {
        if(docente != null){
            ReportDAO reportDAO = new ReportDAO();
            return reportDAO.doSearchByDocName(docente);
        }
        else{
            throw new IllegalArgumentException("The arguments 'codDip' cannot be null.");
        }
    }

    public TreeMap<Report, Docente> ricercaReportSoloData(Date date1, Date date2) throws SQLException {
        if(date1.before(date2) || date1.compareTo(date2) == 0){
            ReportDAO reportDAO = new ReportDAO();
            return reportDAO.doSearchByDate(date1, date2);
        }
        else{
            throw new IllegalArgumentException("The arguments 'codDip' cannot be null.");
        }
    }

    public TreeMap<Report, Docente> ricercaReport(String id) throws SQLException {
        if(id != null && id.compareTo("") != 0){
            ReportDAO reportDAO = new ReportDAO();
            return reportDAO.doRetrieveDocByReport(id);
        }
        else{
            throw new IllegalArgumentException("The arguments 'codDip' cannot be null and empty.");
        }
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
