package Storage.Report;

import Storage.Dipartimento.Dipartimento;
import Storage.SessioneDiValidazione.SessioneDiValidazione;

import java.util.Calendar;
import java.util.Date;

public class Report {

    private int id;
    private Calendar data;
    private Calendar orario;
    private String pathFile;
    private Dipartimento dip;
    private SessioneDiValidazione sessione;

    public Report(Calendar data, Calendar orario, String pathFile, Dipartimento dip, SessioneDiValidazione sessione) {
        this.data = data;
        this.orario = orario;
        this.pathFile = pathFile;
        //this.dip = new Dipartimento();
        this.sessione = sessione;
    }


}