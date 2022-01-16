package Storage;

import Storage.Dipartimento.Dipartimento;
import Storage.Dipartimento.DipartimentoDAO;
import Storage.PersonaleUnisa.Docente.Docente;
import Storage.PersonaleUnisa.Docente.DocenteDAO;
import Storage.Report.Report;
import Storage.Report.ReportDAO;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
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

    @Test
    public void doRetrieveDocByReportOKTest() {
        Report report = createReportTest(new Date(), new Date(), "prova", "DI");

        int id = reportDAO.doCreate(report);
        report.setId(id);

        TreeMap<Report, Docente> treeMap = reportDAO.doRetrieveDocByReport("DI");
        assertTrue(treeMap.containsKey(report));
        assertTrue(treeMap.containsValue(treeMap.get(report)));

        reportDAO.doDelete(report);
    }

    private Report createReportTest(Date orario, Date data, String path, String dip){
        Report report = new Report();
        report.setPathFile(path);
        report.setData(data);
        report.setOrario(orario);
        report.setDip(new DipartimentoDAO().doRetrieveById(dip));
        report.setDocente(new DocenteDAO().doRetrieveByKeyWithRelations("deufemia@unisa.it"));
        return report;
    }

    /*************************************
     * Test doCreate *
     *************************************/
    @Test
    public void doCreateNullTest() {
        assertThrows(IllegalArgumentException.class, () -> reportDAO.doCreate(null));
    }

    @Test
    public void doCreateReturnNullObjectTest() {
        Report report = new Report(new java.sql.Date(2021-11-11), new java.sql.Time(-11), "aa", new Dipartimento(), new Docente());
        assertEquals(0, reportDAO.doCreate(report));
    }

    @Test
    public void doCreateOKTest() {
        Report report = createReportTest(new Date(), new Date(), "prova", "DI");

        int a;
        assertNotEquals(0, a = reportDAO.doCreate(report));
        report.setId(a);
        reportDAO.doDelete(report);
    }


    /*************************************
     * Test doUpdatePath *
     *************************************/
    @Test
    public void doUpdatePathNullTest() {
        assertThrows(IllegalArgumentException.class, () -> reportDAO.doUpdatePath(null));
    }

    @Test
    public void doUpdatePathReturnFalseTest() {
        Report report = new Report();
        report.setPathFile("");
        assertFalse(reportDAO.doUpdatePath(report));
    }

    @Test
    public void doUpdatePathOKTest() {
        ReportDAO reportDAO = new ReportDAO();
        Report report = createReportTest(new Date(), new Date(), "prova", "DI");
        int a = reportDAO.doCreate(report); report.setId(a);

        Report report2 = createReportTest(new Date(), new Date(), "AAA", "DI"); report2.setId(a);
        assertTrue(reportDAO.doUpdatePath(report2));
        reportDAO.doDelete(report);
        reportDAO.doDelete(report2);
    }

    /******************************
     * Test doSearch *
     ******************************/
    @Test
    public void doSearchNullTest(){
        assertThrows(IllegalArgumentException.class, ()->reportDAO.doSearch(null, null, null));
    }

    @Test
    public void doSearchOKTest() {
        Report report = createReportTest(new Date(), new Date(), "prova", "DI");
        Docente docente = new DocenteDAO().doRetrieveByKey("deufemia@unisa.it");

        int id = reportDAO.doCreate(report); report.setId(id);

        TreeMap<Report, Docente> treeMap = reportDAO.doSearch(docente, new Date(), new Date());
        assertNotNull(treeMap);

        reportDAO.doDelete(report);
    }

    /******************************
     * Test doSearchByDocName *
     ******************************/
    @Test
    public void doSearchByDocNameNullTest(){
        assertThrows(IllegalArgumentException.class, ()->reportDAO.doSearchByDocName(null));
    }

    @Test
    public void doSearchByDocNameOKTest() {
        Report report = createReportTest(new Date(), new Date(), "prova", "DI");
        Docente docente = new DocenteDAO().doRetrieveByKey("deufemia@unisa.it");

        int id = reportDAO.doCreate(report); report.setId(id);

        TreeMap<Report, Docente> treeMap = reportDAO.doSearchByDocName(docente);
        assertNotNull(treeMap);

        reportDAO.doDelete(report);
    }

    /******************************
     * Test doSearchByDate *
     ******************************/
    @Test
    public void doSearchByDateNullTest(){
        assertThrows(IllegalArgumentException.class, ()->reportDAO.doSearchByDate(null, null, null));
    }

    @Test
    public void doSearchByDateOKTest() {
        Report report = createReportTest(new Date(), new Date(), "prova", "DI");
        Dipartimento dipartimento = new DipartimentoDAO().doRetrieveById("DI");

        int id = reportDAO.doCreate(report); report.setId(id);

        TreeMap<Report, Docente> treeMap = reportDAO.doSearchByDate(new Date(), new Date(), dipartimento);
        assertNotNull(treeMap);

        reportDAO.doDelete(report);
    }


}
