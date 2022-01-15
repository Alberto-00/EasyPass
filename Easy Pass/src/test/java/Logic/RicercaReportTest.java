package Logic;

import ApplicationLogic.Ajax.AjaxServlet;
import ApplicationLogic.Servlet.SessionController;
import Storage.PersonaleUnisa.Docente.DocenteDAO;
import Storage.Report.ReportDAO;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
        assertEquals(AjaxServlet.checkDocente(nomeDocente,docenteDAO), "Il Docente ricercato non esiste.");
    }

    @Test
    public void checkDocenteFormatoErratoTest() {
        String nomeDocente="Martina Mulino"; //non corretto
        assertEquals(AjaxServlet.checkDocente(nomeDocente,docenteDAO), "Il Docente ricercato non esiste.");
    }

    @Test
    public void checkDocenteOKTest() {
        String nomeDocente="Carmine GRAVINO"; //corretto
        assertEquals(AjaxServlet.checkDocente(nomeDocente,docenteDAO),null);
    }

    /*************************************
     * Test checkData *
     *************************************/

    @Test
    public void checkPrimaDataCampoVuotoTest(){
        String primaData="";// non corretto
        String secondaData=null;
        assertEquals(AjaxServlet.checkData(primaData,secondaData),"Inserire la prima data.");
    }

    @Test
    public void checkPrimaDataFormatoErratoTest(){
        String primaData="20/02/2022";// non corretto
        String secondaData=null;
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
}
