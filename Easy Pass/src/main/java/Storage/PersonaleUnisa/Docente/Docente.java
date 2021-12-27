package Storage.PersonaleUnisa.Docente;

import Storage.PersonaleUnisa.PersonaleUnisa;
import Storage.SessioneDiValidazione.SessioneDiValidazione;
import Storage.SessioneDiValidazione.SessioneDiValidazioneDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Docente extends PersonaleUnisa {
    private ArrayList<SessioneDiValidazione> sessioni;

    public Docente() {}

    public Docente(String nome, String cognome, String username, String password,ArrayList<SessioneDiValidazione> sessioni){
        this.setNome(nome);
        this.setCognome(cognome);
        this.setUsername(username);
        this.setPassword(password);
        this.sessioni=sessioni;
    }

    public ArrayList<SessioneDiValidazione> getSessioni() {
        return sessioni;
    }

    public void setSessioni(ArrayList<SessioneDiValidazione> sessioni) {
        this.sessioni = sessioni;
    }



    public SessioneDiValidazione avviaSessione() throws IOException {

        //Il QRCode della sessione dobbiamo farlo passare come parametro al costruttore oppure è meglio che
        // viene generato "internamente" al momento della chiamata al costruttore?
        //Perchè se va passato come argomento allor ava modificata la firma di questo metodo avviaSessione()


        //Al posto della stringa qrCode ci va il QR code generato come identificativo della sessione
        //Come generiamo questo qrCode?
        SessioneDiValidazione sessione=new SessioneDiValidazione(true,this);
        return sessione;
    }

    public SessioneDiValidazione terminaSessione(SessioneDiValidazione sessione) throws SQLException {
        if(sessione==null){
            throw new IllegalArgumentException("The argument cannot be a null object");
        }
        else{
            sessione.setInCorso(false);
            this.sessioni.add(sessione);
            SessioneDiValidazioneDAO sessioneDao=new SessioneDiValidazioneDAO();
            //E' giusto salvare la sessione nel database all'interno di questo metodo oppure è meglio farlo fuori?
            sessioneDao.doCreate(sessione);
            return sessione;
        }
    }

    //public boolean downloadReport(Report report){}
    //Come si fa a far partire il download di un file?

    @Override
    public String toString() {
        return super.toString()+"{" +
                "sessioni=" + sessioni +
                '}';
    }
}
