package Storage.Dipartimento;

import ApplicationLogic.Utils.RangeDateException;
import Storage.Esito.Esito;
import Storage.Esito.EsitoDAO;
import Storage.Formato.Formato;
import Storage.Formato.FormatoDAO;
import Storage.PersonaleUnisa.Docente.Docente;
import Storage.Report.Report;
import Storage.Report.ReportDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
        if(formato==null){
            throw new IllegalArgumentException("The argument cannot be a null object");
        }
        else{
            if(formato.getId()==this.formato.getId()){
                this.formato=formato;
                FormatoDAO formatoDAO=new FormatoDAO();
                formatoDAO.doUpdate(formato);
                return formato;
            }
            else return null;
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

    public ArrayList<Report> ricercaCompletaReport(Docente docente, Date primaData, Date secondaData) throws SQLException, RangeDateException {
        if(docente != null && primaData != null && secondaData != null){
            if (primaData.before(secondaData) || primaData.compareTo(secondaData) == 0){
                ReportDAO reportDAO=new ReportDAO();
                return reportDAO.doSearch(docente,(java.sql.Date) primaData, (java.sql.Date) secondaData);
            } else
                throw new RangeDateException("The first date must be lower that second date.");
        } else
            throw new IllegalArgumentException("The arguments 'docente', 'primaData' and 'secondaData' cannot be null.");
    }

    public TreeMap<Report, Docente> ricercaReportSoloDocente(String codDip) throws SQLException {
        if(codDip != null && codDip.compareTo("") != 0){
            ReportDAO reportDAO = new ReportDAO();
            return  reportDAO.doRetrieveDocByReport(codDip);
        }
        else{
            throw new IllegalArgumentException("The arguments 'codDip' cannot be null or empty.");
        }
    }

    /*public TreeMap<Report, Docente> ricercaReportSoloData(Date date1, Date date2) throws SQLException, RangeDateException {
        if(date1 != null && date2 != null){
            if (date1.before(date2) || date1.compareTo(date2) == 0){

            }
            else
                throw new RangeDateException("The first date must be lower that second date.");
        } else
            throw new IllegalArgumentException("The arguments 'date1' and 'date' cannot be null.");

    }*/

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
