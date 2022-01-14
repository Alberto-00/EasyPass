package Storage;

import Storage.Dipartimento.Dipartimento;
import Storage.PersonaleUnisa.Docente.Docente;
import Storage.PersonaleUnisa.Docente.DocenteDAO;
import Storage.Report.Report;
import Storage.Report.ReportDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Date;
import java.util.Set;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

public class ReportDAOTest {

    ReportDAO reportDAO;
    DocenteDAO docenteDAO;

    @Before
    public void setUp(){
        reportDAO = new ReportDAO();
        docenteDAO = new DocenteDAO();
    }

    /***********************
     * Test doRetrieveById *
     ***********************/
    @Test
    public void doRetrieveByIdNullTest(){
        assertThrows(IllegalArgumentException.class, ()->reportDAO.doRetrieveById(-1));
    }

    @Test
    public void doRetrieveByIdReturnNullObjectTest(){
        int id = 0; //non corretto
        assertNull(reportDAO.doRetrieveById(id));
    }

    @Test
    public void doRetrieveByIdOKTest(){
        Date data_orario = new Date(); //corretto
        String path = "Report.pdf"; //corretto
        String dip = "DI"; //corretto

        Report report = createReportTest(data_orario, data_orario, path, dip);
        int id = reportDAO.doCreate(report);
        report.setId(id);

        assertNotNull(reportDAO.doRetrieveById(id));
        assertEquals(id, report.getId());
        assertEquals(data_orario, report.getData());
        assertEquals(data_orario, report.getOrario());
        assertEquals(path, report.getPathFile());

        reportDAO.doDelete(report);
    }

    /******************************
     * Test doRetrieveDocByReport *
     ******************************/
    @Test
    public void doRetrieveDocByReportNullTest(){
        assertThrows(IllegalArgumentException.class, ()->reportDAO.doRetrieveDocByReport(null));
    }

    @Test
    public void doRetrieveDocByReportEmptyObjectTest(){
        String codice = "pp"; //non corretto
        assertTrue(reportDAO.doRetrieveDocByReport(codice).isEmpty());
    }

    @Test //da finire
    public void doRetrieveDocByReportOKTest() {
        Docente docente = new Docente("luca", "ROSSI", "prova@unisa.it", "EasyPass2022!", new Dipartimento());
        docente.getDipartimento().setCodice("DI");

        Report report = createReportTest(new Date(), new Date(), "prova", "DI");
        report.getDocente().setUsername("prova@unisa.it");
        docenteDAO.doCreate(docente);
        int id = reportDAO.doCreate(report);
        report.setId(id);

        TreeMap<Report, Docente> reportDocente = reportDAO.doRetrieveDocByReport("DI");
        assertNotNull(reportDocente);
        assertEquals(docente.getUsername(), reportDocente.get(report).getUsername());
        assertEquals(docente.getCognome(), reportDocente.get(report).getCognome());
        assertEquals(docente.getNome(), reportDocente.get(report).getNome());
        assertEquals(docente.getPassword(), reportDocente.get(report).getPassword());

        for (Report r : reportDocente.keySet()) {
            assertEquals(report.getId(), r.getId());
            // assertEquals(report.getOrario(), r.getOrario());
            //assertEquals(report.getData(), r.getData());
            assertEquals(report.getPathFile(), r.getPathFile());
        }
        reportDAO.doDelete(report);
        docenteDAO.doDelete(docente);
    }

    private Report createReportTest(Date orario, Date data, String path, String dip){
        Report report = new Report();
        report.setPathFile(path);
        report.setData(data);
        report.setOrario(orario);
        report.setDip(new Dipartimento());
        report.getDip().setCodice(dip);
        report.setDocente(new Docente());
        report.getDocente().setUsername("gravino@unisa.it");
        return report;
    }
}
