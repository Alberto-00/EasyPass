package Storage.Dipartimento;

import Storage.Formato.Formato;
import Storage.Formato.FormatoDAO;
import Storage.Report.Report;
import Storage.Report.ReportDAO;

import java.sql.SQLException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Date;

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
        super();
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
        if(formato.getId()==this.formato.getId()){
            this.formato = formato;
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

    public Report eliminaReport(Report report) throws SQLException {
        if(report==null){
            throw new IllegalArgumentException("Cannot delete a null object");
        }
        else{
            this.reports.remove(report);
            ReportDAO reportDAO=new ReportDAO();
            reportDAO.doDelete(report);
            return report;
        }
    }

    public ArrayList<Report> ricercaReport(String docente, Date primaData, Date secondaData) throws SQLException {
        if(docente!=null || primaData!=null){
            ReportDAO reportDAO=new ReportDAO();
            ArrayList<Report> reports=reportDAO.doSearch(docente,(java.sql.Date) primaData, (java.sql.Date) secondaData);
            return reports;
        }
        else{
            throw new IllegalArgumentException("The arguments 'docente' and 'primaData' cannot be both null ");
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
