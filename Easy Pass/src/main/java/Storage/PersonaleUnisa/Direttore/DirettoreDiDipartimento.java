package Storage.PersonaleUnisa.Direttore;

import Storage.Dipartimento.Dipartimento;
import Storage.Formato.Formato;
import Storage.PersonaleUnisa.PersonaleUnisa;
import Storage.Report.Report;

public class DirettoreDiDipartimento extends PersonaleUnisa {
    public DirettoreDiDipartimento() {}

    public DirettoreDiDipartimento(String nome, String cognome, String username, String password){
        this.setNome(nome);
        this.setCognome(cognome);
        this.setUsername(username);
        this.setPassword(password);
    }

    //public Report eliminaReport(Report report){}
    //public boolean downloadReport(Report report){}
    //public ArrayList<Report> ricercaReport(String docente, Date primaData, Date secondaData){}
    public Formato impostaFormato(Formato formato){
        if(formato==null){
            throw new IllegalArgumentException("The argument cannot be a null object");
        }
        else{
            this.getDipartimento().setFormato(formato);
            return formato;
        }
    }
}
