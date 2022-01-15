package Storage;

import ApplicationLogic.Utils.InvalidRequestException;
import Storage.Dipartimento.Dipartimento;
import Storage.Dipartimento.DipartimentoDAO;
import Storage.Formato.Formato;
import Storage.PersonaleUnisa.Direttore.DirettoreDiDipartimento;
import Storage.PersonaleUnisa.Docente.Docente;
import Storage.PersonaleUnisa.Docente.DocenteDAO;
import Storage.Report.Report;
import Storage.Report.ReportDAO;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DipartimentoBeanTest {
    ReportDAO reportDao;
    DipartimentoDAO dipartimentoDAO;
    DocenteDAO docenteDAO;
    Dipartimento dipartimento;

    @Before
    public void setUp(){
        reportDao=new ReportDAO();
        dipartimentoDAO=new DipartimentoDAO();
        docenteDAO=new DocenteDAO();
        dipartimento=dipartimentoDAO.doRetrieveByKeyWithRelations("DI");

    }

    /***********************
     * Test eliminaReport *
     ***********************/
    @Test
    public void eliminaReportNullArgumentTest() {
        assertThrows(IllegalArgumentException.class,()->dipartimento.eliminaReport(null));
    }

    @Test
    public void eliminaReportOKTest() {
        Report reportProva=creaReport(new Date(), new Date(), "prova", "DI","gravino@unisa.it");
        int id=reportDao.doCreate(reportProva);
        reportProva.setId(id);

        dipartimento.eliminaReport(reportProva);
        assertNull(reportDao.doRetrieveById(reportProva.getId()));

    }


    /***********************
     * Test impostaFormato *
     ***********************/
    @Test
    public void impostaFormatoNullArgumentTest(){
        assertThrows(IllegalArgumentException.class,()->dipartimento.impostaFormato(null));
    }

    @Test
    public void impostaFormatoOKTest(){
        Formato f=new Formato(dipartimento.getCodice(),true, true, true, true, true);

        dipartimento.impostaFormato(f);

        Dipartimento d=dipartimentoDAO.doRetrieveByKeyWithRelations(dipartimento.getCodice());

        assertEquals(d.getFormato().getId(),f.getId());
        assertEquals(d.getFormato().isNumStudenti(),f.isNumStudenti());
        assertEquals(d.getFormato().isNumGPValidi(),f.isNumGPValidi());
        assertEquals(d.getFormato().isNumGPNonValidi(),f.isNumGPNonValidi());
        assertEquals(d.getFormato().isData(),f.isData());
        assertEquals(d.getFormato().isNomeCognome(),f.isNomeCognome());
    }

    /***********************
     * Test ricercaCompletaReport *
     ***********************/

    @Test
    public void ricercaCompletaReportNullArgumentsTest(){
        assertThrows(IllegalArgumentException.class,()->dipartimento.ricercaCompletaReport(null,null,null));
        assertThrows(IllegalArgumentException.class,()->dipartimento.ricercaCompletaReport(new Docente(),null,null));
        assertThrows(IllegalArgumentException.class,()->dipartimento.ricercaCompletaReport(null,new Date(),null));
        assertThrows(IllegalArgumentException.class,()->dipartimento.ricercaCompletaReport(null,null,new Date()));
        assertThrows(IllegalArgumentException.class,()->dipartimento.ricercaCompletaReport(null,new Date(),new Date()));
        assertThrows(IllegalArgumentException.class,()->dipartimento.ricercaCompletaReport(new Docente(),null,new Date()));
        assertThrows(IllegalArgumentException.class,()->dipartimento.ricercaCompletaReport(new Docente(),new Date(),null));
    }

    @Test
    public void ricercaCompletaReportConfrontoDateErratoTest() throws ParseException {
        Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-15"); //d1>d2 non corretto
        Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse("2021-01-15");
        assertThrows(InvalidRequestException.class,()->dipartimento.ricercaCompletaReport(new Docente(),d1,d2));
    }

    @Test
    public void ricercaCompletaReportOKTest() throws ParseException, InvalidRequestException {
        Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-15");
        Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-15");
        Report reportProva=creaReport(new Date(), d1, "prova", "DI","gravino@unisa.it");
        int id=reportDao.doCreate(reportProva);
        reportProva.setId(id);

        Docente d=docenteDAO.doRetrieveByKey(reportProva.getDocente().getUsername());

        TreeMap<Report,Docente> risultato=dipartimento.ricercaCompletaReport(d,d1,d2);

        assertTrue(risultato.keySet().contains(reportProva));
        Docente dInMap=risultato.get(reportProva);
        assertTrue(risultato.containsValue(dInMap));

        reportDao.doDelete(reportProva);

    }

    /***********************
     * Test ricercaReportSoloDocente *
     ***********************/

    @Test
    public void ricercaReportSoloDocenteNullArgumentsTest(){
        assertThrows(IllegalArgumentException.class,()->dipartimento.ricercaReportSoloDocente(null));
    }

    @Test
    public void ricercaReportSoloDocenteOKTest() {
        Report reportProva=creaReport(new Date(), new Date(), "prova", "DI","gravino@unisa.it");
        int id=reportDao.doCreate(reportProva);
        reportProva.setId(id);

        Docente d=docenteDAO.doRetrieveByKey(reportProva.getDocente().getUsername());

        TreeMap<Report,Docente> risultato=dipartimento.ricercaReportSoloDocente(d);

        assertTrue(risultato.keySet().contains(reportProva));
        Docente dInMap=risultato.get(reportProva);
        assertTrue(risultato.containsValue(dInMap));

        reportDao.doDelete(reportProva);
    }

    /***********************
     * Test ricercaReportSoloData *
     ***********************/
    @Test
    public void ricercaReportSoloDataConfrontoErratoTest() throws ParseException {
        Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-15"); //d1>d2 non corretto
        Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse("2021-01-15");
        assertThrows(IllegalArgumentException.class,()->dipartimento.ricercaReportSoloData(d1,d2));
    }

    @Test
    public void ricercaReportSoloDataOKTest() throws ParseException {
        Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse("2021-01-15");
        Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse("2021-01-15");

        Report reportProva=creaReport(new Date(), d1, "prova", "DI","gravino@unisa.it");
        int id=reportDao.doCreate(reportProva);
        reportProva.setId(id);

        TreeMap<Report,Docente> risultato=dipartimento.ricercaReportSoloData(d1,d2);

        assertTrue(risultato.keySet().contains(reportProva));
        Docente dInMap=risultato.get(reportProva);
        assertTrue(risultato.containsValue(dInMap));

        reportDao.doDelete(reportProva);

    }

    /***********************
     * Test ricercaReport *
     ***********************/

    @Test
    public void ricercaReportOKTest() throws ParseException {
        Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse("2021-01-15");
        Report reportProva=creaReport(new Date(), d1, "prova", "DI","gravino@unisa.it");
        int id=reportDao.doCreate(reportProva);
        reportProva.setId(id);

        TreeMap<Report,Docente> risultato=dipartimento.ricercaReport();

        assertTrue(risultato.keySet().contains(reportProva));
        Docente dInMap=risultato.get(reportProva);
        assertTrue(risultato.containsValue(dInMap));

        reportDao.doDelete(reportProva);

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
