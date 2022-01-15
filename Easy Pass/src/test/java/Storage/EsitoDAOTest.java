package Storage;

import Storage.Dipartimento.Dipartimento;
import Storage.Esito.Esito;
import Storage.Esito.EsitoDAO;
import Storage.PersonaleUnisa.Docente.Docente;
import Storage.Report.Report;
import Storage.Report.ReportDAO;
import Storage.SessioneDiValidazione.SessioneDiValidazione;
import Storage.SessioneDiValidazione.SessioneDiValidazioneDAO;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;


public class EsitoDAOTest {

    SessioneDiValidazione sessioneDiValidazione;
    Report reportProva;

    ReportDAO reportDAO;
    SessioneDiValidazioneDAO sessioneDiValidazioneDAO;
    EsitoDAO esitoDAO;

    @Before
    public void setUp() throws ParseException {
        esitoDAO=new EsitoDAO();

        sessioneDiValidazioneDAO=new SessioneDiValidazioneDAO();
        sessioneDiValidazione=creaSessione("idSessione","gravino@unisa.it",true,new ArrayList<>());

        reportDAO=new ReportDAO();
        Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-15");
        reportProva=creaReport(new Date(), d1, "prova", "DI","gravino@unisa.it");
    }

    /*************************************
     * Test doRetrieveByKey *
     *************************************/

    @Test
    public void doRetrieveByKeyInvalidArgumentTest(){
        assertThrows(IllegalArgumentException.class, () -> esitoDAO.doRetrieveByKey(-1));
    }

    @Test
    public void doRetrieveByKeyReturnNullTest() throws ParseException {
        Esito e=creaEsito("test","test",true,sessioneDiValidazione,reportProva);
        e.setId(10000);
        assertNull(esitoDAO.doRetrieveByKey(e.getId()));
    }

    @Test
    public void doRetrieveByKeyOKTest() throws ParseException {

        sessioneDiValidazioneDAO.doCreate(sessioneDiValidazione);

        int idReport=reportDAO.doCreate(reportProva);
        reportProva.setId(idReport);

        Esito e=creaEsito("test","test",true,sessioneDiValidazione,reportProva);
        int id=esitoDAO.doCreate(e);//Creo nel db un esito
        e.setId(id);

        Esito eInDb=esitoDAO.doRetrieveByKey(e.getId());

        //Controllo che l'oggetto restituito dal doRetrieveByKey sia uguale a quello creato precedentemente
        assertEquals(eInDb.getId(),e.getId());
        assertEquals(eInDb.getNomeStudente(),e.getNomeStudente());
        assertEquals(eInDb.getCognomeStudente(),e.getCognomeStudente());
        assertEquals(eInDb.getDataDiNascitaStudente(),e.getDataDiNascitaStudente());
        assertEquals(eInDb.isValidita(),e.isValidita());

        sessioneDiValidazioneDAO.doDelete(sessioneDiValidazione);
        reportDAO.doDelete(reportProva);
        esitoDAO.doDelete(e);
    }

    /*************************************
     * Test doCreate *
     *************************************/

    @Test
    public void doCreateNullArgumentTest(){
        assertThrows(IllegalArgumentException.class, () -> esitoDAO.doCreate(null));
    }

    @Test
    public void doCreateOKTest() throws ParseException {
        sessioneDiValidazioneDAO.doCreate(sessioneDiValidazione);

        int idReport=reportDAO.doCreate(reportProva);
        reportProva.setId(idReport);

        Esito e=creaEsito("test","test",true,sessioneDiValidazione,reportProva);
        int id=esitoDAO.doCreate(e);
        e.setId(id);
        assertTrue(id!=-1);

        //Per vedere se la doCreate ha funzionato prendo dal db l'esito con lo stesso id di quello creato e controllo che i due oggetti siano uguali
        Esito eInDb=esitoDAO.doRetrieveByKey(e.getId());
        assertEquals(eInDb.getId(),e.getId());
        assertEquals(eInDb.getNomeStudente(),e.getNomeStudente());
        assertEquals(eInDb.getCognomeStudente(),e.getCognomeStudente());
        assertEquals(eInDb.getDataDiNascitaStudente(),e.getDataDiNascitaStudente());
        assertEquals(eInDb.isValidita(),e.isValidita());


        sessioneDiValidazioneDAO.doDelete(sessioneDiValidazione);
        reportDAO.doDelete(reportProva);
        esitoDAO.doDelete(e);
    }


    /*************************************
     * Test doRetrieveAllBySession *
     *************************************/

    @Test
    public void doRetrieveAllBySessionNullArgumentTest(){
        assertThrows(IllegalArgumentException.class, () -> esitoDAO.doRetrieveAllBySession(null));
    }


    @Test
    public void doRetrieveAllBySessionOKTest() throws ParseException {
        sessioneDiValidazioneDAO.doCreate(sessioneDiValidazione);
        int idReport=reportDAO.doCreate(reportProva);
        reportProva.setId(idReport);
        Esito e=creaEsito("test","test",true,sessioneDiValidazione,reportProva);
        int id=esitoDAO.doCreate(e);
        e.setId(id);

        ArrayList<Esito> esiti=esitoDAO.doRetrieveAllBySession(sessioneDiValidazione);

        Esito eInDb=null;
        for(Esito esito: esiti) {
            if(esito.getId()==e.getId()){
               eInDb=esito;
            }
            break;
        }

        assertNotNull(eInDb);//Controllo che l'esito creato sopra esiste nell'insieme degli esiti presi dal db

        //Controllo che che l'esito creato sopra è uguale
        assertEquals(eInDb.getId(),e.getId());
        assertEquals(eInDb.getNomeStudente(),e.getNomeStudente());
        assertEquals(eInDb.getCognomeStudente(),e.getCognomeStudente());
        assertEquals(eInDb.getDataDiNascitaStudente(),e.getDataDiNascitaStudente());
        assertEquals(eInDb.isValidita(),e.isValidita());

        sessioneDiValidazioneDAO.doDelete(sessioneDiValidazione);
        reportDAO.doDelete(reportProva);
        esitoDAO.doDelete(e);
    }

    /*************************************
     * Test doRetrieveAllByPersonalData *
     *************************************/

    @Test
    public void doRetrieveAllByPersonalDataNullArgumentTest(){
        assertThrows(IllegalArgumentException.class, () -> esitoDAO.doRetrieveAllByPersonalData(null));
    }

    @Test
    public void doRetrieveAllByPersonalDataOKTest() throws ParseException {
        sessioneDiValidazioneDAO.doCreate(sessioneDiValidazione);
        int idReport=reportDAO.doCreate(reportProva);
        reportProva.setId(idReport);
        Esito e=creaEsito("test","test",true,sessioneDiValidazione,reportProva);
        int id=esitoDAO.doCreate(e);
        e.setId(id);

        ArrayList<Esito> esiti=esitoDAO.doRetrieveAllByPersonalData(e);

        Esito eInDb=null;
        for(Esito esito: esiti) {
            if(esito.getId()==e.getId()){
                eInDb=esito;
            }
            break;
        }

        assertNotNull(eInDb);//Controllo che l'esito creato sopra esiste nell'insieme degli esiti presi dal db

        //Controllo che che l'esito creato sopra è uguale
        assertEquals(eInDb.getId(),e.getId());
        assertEquals(eInDb.getNomeStudente(),e.getNomeStudente());
        assertEquals(eInDb.getCognomeStudente(),e.getCognomeStudente());
        assertEquals(eInDb.getDataDiNascitaStudente(),e.getDataDiNascitaStudente());
        assertEquals(eInDb.isValidita(),e.isValidita());

        sessioneDiValidazioneDAO.doDelete(sessioneDiValidazione);
        reportDAO.doDelete(reportProva);
        esitoDAO.doDelete(e);
    }

    /*************************************
     * Test contaEsitiValidi *
     *************************************/

    @Test
    public void contaEsitiValidiInvalidArgumentTest(){
        assertThrows(IllegalArgumentException.class, () -> esitoDAO.contaEsitiValidi(-1,false));
    }

    @Test
    public void contaEsitiValidiOKTest() throws ParseException {
        sessioneDiValidazioneDAO.doCreate(sessioneDiValidazione);
        int idReport=reportDAO.doCreate(reportProva);
        reportProva.setId(idReport);
        Esito e=creaEsito("test","test",true,sessioneDiValidazione,reportProva);
        int id=esitoDAO.doCreate(e);
        e.setId(id);

        int numEsiti=esitoDAO.contaEsitiValidi(reportProva.getId(),true);

        assertTrue(numEsiti>0);

        sessioneDiValidazioneDAO.doDelete(sessioneDiValidazione);
        reportDAO.doDelete(reportProva);
        esitoDAO.doDelete(e);
    }
    /*************************************
     * Test numEsiti *
     *************************************/

    @Test
    public void numEsitiInvalidArgumentTest(){
        assertThrows(IllegalArgumentException.class, () -> esitoDAO.numEsiti(-1));
    }

    @Test
    public void numEsitiOKTest() throws ParseException {
        sessioneDiValidazioneDAO.doCreate(sessioneDiValidazione);
        int idReport=reportDAO.doCreate(reportProva);
        reportProva.setId(idReport);
        Esito e=creaEsito("test","test",true,sessioneDiValidazione,reportProva);
        int id=esitoDAO.doCreate(e);
        e.setId(id);

        int numEsiti=esitoDAO.numEsiti(reportProva.getId());

        assertTrue(numEsiti>0);

        sessioneDiValidazioneDAO.doDelete(sessioneDiValidazione);
        reportDAO.doDelete(reportProva);
        esitoDAO.doDelete(e);
    }

    /*************************************
     * Test doCreateWithoutReport *
     *************************************/

    @Test
    public void doCreateWithoutReportNullArgumentTest(){
        assertThrows(IllegalArgumentException.class, () -> esitoDAO.doCreateWithoutReport(null));
    }

    @Test
    public void doCreateWithoutReportOKTest() throws ParseException {
        sessioneDiValidazioneDAO.doCreate(sessioneDiValidazione);

        Esito e=creaEsito("test","test",true,sessioneDiValidazione,null); //passo un report null
        int id=esitoDAO.doCreateWithoutReport(e);
        e.setId(id);
        assertTrue(id!=-1);

        //Per vedere se la doCreateWithoutReport ha funzionato prendo dal db l'esito con lo stesso id di quello creato e controllo che i due oggetti siano uguali
        Esito eInDb=esitoDAO.doRetrieveByKey(e.getId());
        assertEquals(eInDb.getId(),e.getId());
        assertEquals(eInDb.getNomeStudente(),e.getNomeStudente());
        assertEquals(eInDb.getCognomeStudente(),e.getCognomeStudente());
        assertEquals(eInDb.getDataDiNascitaStudente(),e.getDataDiNascitaStudente());
        assertEquals(eInDb.isValidita(),e.isValidita());

        sessioneDiValidazioneDAO.doDelete(sessioneDiValidazione);
        esitoDAO.doDelete(e);
    }

    /*************************************
     * Test doUpdateOnlyReport *
     *************************************/

    @Test
    public void doUpdateOnlyReportNullArgumentTest(){
        assertThrows(IllegalArgumentException.class, () -> esitoDAO.doUpdateOnlyReport(null));
    }

    @Test
    public void doUpdateOnlyReportReturnFalseTest() throws ParseException {
        sessioneDiValidazioneDAO.doCreate(sessioneDiValidazione);
        int idReport=reportDAO.doCreate(reportProva);
        reportProva.setId(idReport);

        Esito e=creaEsito("test","test",true,sessioneDiValidazione,reportProva);
        int id=esitoDAO.doCreateWithoutReport(e);


        //Per vedere se la doUpdateOnlyReport ritorna falso passo come argomento un esito che non esiste nel db
        e.setId(1000);
        assertFalse(esitoDAO.doUpdateOnlyReport(e));

        sessioneDiValidazioneDAO.doDelete(sessioneDiValidazione);
        reportDAO.doDelete(reportProva);
        e.setId(id);
        esitoDAO.doDelete(e);
    }

    @Test
    public void doUpdateOnlyReportOKTest() throws ParseException {
        sessioneDiValidazioneDAO.doCreate(sessioneDiValidazione);
        int idReport=reportDAO.doCreate(reportProva);
        reportProva.setId(idReport);

        Esito e=creaEsito("test","test",true,sessioneDiValidazione,null); //passo un report null
        int id=esitoDAO.doCreateWithoutReport(e);
        e.setId(id);

        //Per vedere se la doUpdateOnlyReport ha funzionato la chiamo aggiornando il campo Report dell'oggetto esito
        e.setReport(reportProva);

        assertTrue(esitoDAO.doUpdateOnlyReport(e));

        sessioneDiValidazioneDAO.doDelete(sessioneDiValidazione);
        reportDAO.doDelete(reportProva);
        esitoDAO.doDelete(e);
    }




    private SessioneDiValidazione creaSessione(String QRcode, String usernameDoc, boolean inCorso, ArrayList<Esito> esiti){
        SessioneDiValidazione s=new SessioneDiValidazione();
        s.setQRCode(QRcode);
        s.setDocente(new Docente());
        s.getDocente().setUsername(usernameDoc);
        s.setInCorso(inCorso);
        s.setListaEsiti(esiti);
        return s;
    }

    private Esito creaEsito(String nomeStudente, String cognomeStudente, boolean validita, SessioneDiValidazione s, Report r) throws ParseException {
        Esito e=new Esito();
        e.setNomeStudente(nomeStudente);
        e.setCognomeStudente(cognomeStudente);
        e.setValidita(validita);
        Date d = new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-15");
        e.setDataDiNascitaStudente(d);
        e.setStringaGP("");
        e.setSessione(s);
        e.setReport(r);
        return e;
    }

    private Report creaReport(Date orario, Date data, String path, String codiceDip,String usernameDoc){
        Report report = new Report();
        report.setPathFile(path);
        report.setData(data);
        report.setOrario(orario);
        report.setDip(new Dipartimento());
        report.getDip().setCodice(codiceDip);
        report.setDocente(new Docente());
        report.getDocente().setUsername(usernameDoc);
        return report;
    }

}
