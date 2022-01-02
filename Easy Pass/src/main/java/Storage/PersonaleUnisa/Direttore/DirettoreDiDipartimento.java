package Storage.PersonaleUnisa.Direttore;

import Storage.Dipartimento.Dipartimento;
import Storage.Formato.Formato;
import Storage.PersonaleUnisa.PersonaleUnisa;
import Storage.Report.Report;

import java.sql.SQLException;

public class DirettoreDiDipartimento extends PersonaleUnisa {
    public DirettoreDiDipartimento() {
        this.setNome("");
        this.setCognome("");
        this.setUsername("");
        this.setPassword("");
        this.setDipartimento(null);
    }

    public DirettoreDiDipartimento(String nome, String cognome, String username, String password,Dipartimento dipartimento){
        this.setNome(nome);
        this.setCognome(cognome);
        this.setUsername(username);
        this.setPassword(password);
        this.setDipartimento(dipartimento);
    }

    public Report eliminaReport(Report report) throws SQLException {
        if(report==null){
            throw new IllegalArgumentException("Cannot delete a null object");
        }
        else{
            this.getDipartimento().eliminaReport(report);
            return report;
        }
    }
    //public ArrayList<Report> ricercaReport(String docente, Date primaData, Date secondaData){}

    public Formato impostaFormato(Formato formato) throws SQLException {
        if(formato==null){
            throw new IllegalArgumentException("The argument cannot be a null object");
        }
        else{
            this.getDipartimento().impostaFormato(formato);
            return formato;
        }
    }

    //Implementare il meccanismo di download
    //public boolean downloadReport(Report report){}
}
