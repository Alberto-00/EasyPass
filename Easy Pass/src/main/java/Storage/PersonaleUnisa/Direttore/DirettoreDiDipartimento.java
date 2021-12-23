package Storage.PersonaleUnisa.Direttore;

import Storage.PersonaleUnisa.PersonaleUnisa;

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
    //public Formato impostaFormato(Formato formato){}
}
