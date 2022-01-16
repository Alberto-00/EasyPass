package Logic;

import ApplicationLogic.Ajax.AjaxServlet;
import Storage.Dipartimento.Dipartimento;
import Storage.PersonaleUnisa.Docente.Docente;
import Storage.PersonaleUnisa.Docente.DocenteDAO;
import Storage.Report.Report;
import Storage.Report.ReportDAO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

public class RicercaReportTest {
    DocenteDAO docenteDAO;

    @Before
    public void setUp() {
        docenteDAO = new DocenteDAO();
    }

    /*************************************
     * Test checkDocente *
     *************************************/
    @Test
    public void checkDocenteCampoVuotoTest() {
        String nomeDocente=""; //non corretto
        assertEquals(AjaxServlet.checkDocente(nomeDocente,docenteDAO), "Inserire un Docente.");
    }

    @Test
    public void checkDocenteNonEsisteTest() {
        String nomeDocente="Martina Mulino"; //non corretto
        assertEquals(AjaxServlet.checkDocente(nomeDocente,docenteDAO), "Il Docente cercato non esiste.");
    }

    @Test
    public void checkDocenteFormatoErratoTest() {
        String nomeDocente="Martina Mulino"; //non corretto
        assertEquals(AjaxServlet.checkDocente(nomeDocente,docenteDAO), "Il Docente cercato non esiste.");
    }

    @Test
    public void checkDocenteOKTest() {
        String nomeDocente="Carmine GRAVINO"; //corretto
        assertNull(AjaxServlet.checkDocente(nomeDocente, docenteDAO));
    }


    /*************************************
     * Test checkData *
     *************************************/
    @Test
    public void checkPrimaDataCampoVuotoTest(){
        String primaData="";// non corretto
        assertEquals(AjaxServlet.checkData(primaData, null),"Inserire la prima data.");
    }

    @Test
    public void checkPrimaDataVuotaNonVuotaESecondaDataVuotaTest(){
        String primaData="2022-02-20";//corretto
        String secondaData="";// non corretto
        assertEquals(AjaxServlet.checkData(primaData,secondaData),"Inserire la seconda data.");
    }

    @Test
    public void checkPrimaDataFormatoErratoTest(){
        String primaData="20/02/2022";// non corretto
        String secondaData="";
        assertEquals(AjaxServlet.checkData(primaData,secondaData),"La prima data non rispetta il formato.");
    }

    @Test
    public void checkPrimaDataVuotaESecondaDataNonVuotaTest(){
        String primaData="";//non corretto
        String secondaData="2022-02-20";
        assertEquals(AjaxServlet.checkData(primaData,secondaData),"Inserire la prima data.");
    }

    @Test
    public void checkSecondaDataFormatoErratoTest(){
        String primaData="2022-02-20";//corretto
        String secondaData="2022/02/20";//non corretto
        assertEquals(AjaxServlet.checkData(primaData,secondaData),"La seconda data non rispetta il formato.");
    }

    @Test
    public void checkConfrontoFraDateTest(){
        String primaData="2022-02-20";//non corretto
        String secondaData="2022-02-10"; //non corretto
        assertEquals(AjaxServlet.checkData(primaData,secondaData),"La prima data deve essere minore della seconda data.");
    }

    @Test
    public void checkDateOKTest(){
        String primaData="2022-02-10";//corretto
        String secondaData="2022-02-20"; //corretto
        assertNull(AjaxServlet.checkData(primaData,secondaData));
    }

    /*************************************
     * Test searchReport *
     *************************************/

    @Test
    public void searchReportEmptyTreeMapArgumentTest(){
        JSONObject root=new JSONObject();
        TreeMap<Report, Docente> treeMap=new TreeMap<>();//non corretto
        AjaxServlet.searchReport(root,new JSONArray(),new JSONArray(), treeMap);

        assertEquals(root.get("emptyy"),"empty");
    }

    @Test
    public void searchReportOKTest() throws ParseException {
        Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse("2021-01-15");
        Report reportProva=creaReport(new Date(), d1, "prova", "DI","gravino@unisa.it");
        int id=new ReportDAO().doCreate(reportProva);
        reportProva.setId(id);

        JSONObject root=new JSONObject();
        TreeMap<Report, Docente> treeMap=new ReportDAO().doRetrieveDocByReport("DI"); //corretto
        AjaxServlet.searchReport(root,new JSONArray(),new JSONArray(), treeMap);

        String s= (String) root.get("emptyy");
        assertNull(s);

        new ReportDAO().doDelete(reportProva);
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
