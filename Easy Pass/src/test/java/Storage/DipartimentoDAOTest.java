package Storage;

import Storage.Dipartimento.Dipartimento;
import Storage.Dipartimento.DipartimentoDAO;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DipartimentoDAOTest {

    DipartimentoDAO dipartimentoDAO;

    @Before
    public void setUp() {
        dipartimentoDAO = new DipartimentoDAO();
    }

    /***********************
     * Test doRetrieveByID *
     ***********************/
    @Test
    public void doRetrieveByIdNullTest(){
        assertThrows(IllegalArgumentException.class, ()->dipartimentoDAO.doRetrieveById(null));
    }

    @Test
    public void doRetrieveByIdReturnNullObjectTest(){
        String id = "pp"; //non corretto
        assertNull(dipartimentoDAO.doRetrieveById(id));
    }

    @Test
    public void doRetrieveByIdOKTest(){
        String id = "DI"; //corretto
        String nome = "Informatica"; //corretto

        Dipartimento dipartimento = dipartimentoDAO.doRetrieveById(id);
        assertNotNull(dipartimento);
        assertEquals(id, dipartimento.getCodice());
        assertEquals(nome, dipartimento.getNome());
    }

    /*************************************
     * Test doRetrieveByKeyWithRelations *
     *************************************/
    @Test
    public void doRetrieveByKeyWithRelationsNullTest(){
        assertThrows(IllegalArgumentException.class, ()->dipartimentoDAO.doRetrieveByKeyWithRelations(null));
    }

    @Test
    public void doRetrieveByKeyWithRelationsReturnNullObjectTest(){
        String codice = "pp"; //non corretto
        assertNull(dipartimentoDAO.doRetrieveByKeyWithRelations(codice));
    }

    @Test
    public void doRetrieveByKeyWithRelationsOKTest(){
        String codice = "DI"; //corretto
        String nome = "Informatica"; //corretto

        Dipartimento dipartimento = dipartimentoDAO.doRetrieveByKeyWithRelations(codice);

        assertNotNull(dipartimento);
        assertEquals(codice, dipartimento.getCodice());
        assertEquals(nome, dipartimento.getNome());
        assertNotNull(dipartimento.getFormato());

        assertTrue(dipartimento.getFormato().isData());
        assertTrue(dipartimento.getFormato().isNomeCognome());
        assertTrue(dipartimento.getFormato().isNumGPNonValidi());
        assertTrue(dipartimento.getFormato().isNumStudenti());
        assertTrue(dipartimento.getFormato().isNumGPValidi());
    }

    /**********************
     * Test doRetrieveAll *
     **********************/
    @Test
    public void doRetrieveAllNullReturnOKTest(){
        List<Dipartimento> dipartimenti = dipartimentoDAO.doRetrieveAll();
        assertNotNull(dipartimenti);
        doretrieveAllParamTest("DCB", "Chimica e Biologia \"Adolfo Zambelli\"", dipartimenti.get(0));
        doretrieveAllParamTest("DF", "Fisica \"E.R.Caianiello\"", dipartimenti.get(1));
        doretrieveAllParamTest("DI", "Informatica", dipartimenti.get(2));
        doretrieveAllParamTest("DICIV", "Ingegneria Civile", dipartimenti.get(3));
        doretrieveAllParamTest("DIEM", "Ingegneria dell'Informazione ed Elettrica e Matematica applicata", dipartimenti.get(4));
        doretrieveAllParamTest("DIFARMA", "Farmacia", dipartimenti.get(5));
        doretrieveAllParamTest("DIIN", "Ingegneria Industriale", dipartimenti.get(6));
        doretrieveAllParamTest("DIPMAT", "Matematica", dipartimenti.get(7));
        doretrieveAllParamTest("DIPMED", "Medicina, Chirurgia e Odontoiatria \"Scuola Medica Salernitana\"", dipartimenti.get(8));
        doretrieveAllParamTest("DIPSUM", "Studi Umanistici", dipartimenti.get(9));
        doretrieveAllParamTest("DISA-MIS", "Scienze Aziendali - Management & Innovation Systems", dipartimenti.get(10));
        doretrieveAllParamTest("DISES", "Scienze Economiche e Statistiche", dipartimenti.get(11));
        doretrieveAllParamTest("DISPAC", "Scienze del Patrimonio Culturale", dipartimenti.get(12));
        doretrieveAllParamTest("DISPC", "Scienze Politiche e della Comunicazione", dipartimenti.get(13));
        doretrieveAllParamTest("DISPS", "Studi Politici e Sociali", dipartimenti.get(14));
        doretrieveAllParamTest("DISUFF", "Scienze Umane, Filosofiche e della Formazione", dipartimenti.get(15));
        doretrieveAllParamTest("DSG", "Scienze Giuridiche (Scuola di Giurisprudenza)", dipartimenti.get(16));

    }

    private void doretrieveAllParamTest(String codice, String nome, Dipartimento dipartimento){
        assertEquals(nome, dipartimento.getNome());
        assertEquals(codice, dipartimento.getCodice());
    }
}
