package Storage;

import Storage.Dipartimento.Dipartimento;
import Storage.Dipartimento.DipartimentoDAO;
import Storage.Formato.Formato;
import Storage.PersonaleUnisa.Docente.Docente;
import Storage.PersonaleUnisa.Docente.DocenteDAO;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DocenteDAOTest {

    DocenteDAO docenteDAO;

    @Before
    public void setUp() {
        docenteDAO = new DocenteDAO();
    }

    /***********************
     * Test doRetrieveByKey *
     ***********************/
    @Test
    public void doRetrieveByKeyNullTest() {
        assertThrows(IllegalArgumentException.class, () -> docenteDAO.doRetrieveByKey(null));
    }

    @Test
    public void doRetrieveByKeyReturnNullObjectTest() {
        String id = "a"; //non corretto
        assertNull(docenteDAO.doRetrieveByKey(id));
    }

    @Test
    public void doRetrieveByKeyOKTest() {
        String id = "a.meriani@unisa.it"; //corretto
        String nome = "Angelo"; //corretto
        String cognome = "MERIANI"; //corretto
        String pw = DigestUtils.sha256Hex("EasyPass2022!"); //corretto

        Docente docente = docenteDAO.doRetrieveByKey(id);
        assertNotNull(docente);
        assertEquals(id, docente.getUsername());
        assertEquals(nome, docente.getNome());
        assertEquals(cognome, docente.getCognome());
        assertEquals(DigestUtils.sha256Hex(pw), docente.getPassword());
    }


    /*************************************
     * Test doRetrieveByKeyWithRelations *
     *************************************/
    @Test
    public void doRetrieveByKeyWithRelationsNullTest() {
        assertThrows(IllegalArgumentException.class, () -> docenteDAO.doRetrieveByKeyWithRelations(null));
    }

    @Test
    public void doRetrieveByKeyWithRelationsReturnNullObjectTest() {
        String codice = "pp"; //non corretto
        assertNull(docenteDAO.doRetrieveByKeyWithRelations(codice));
    }

    @Test
    public void doRetrieveByKeyWithRelationsOKTest() {
        String id = "gravino@unisa.it"; //corretto
        String nome = "Carmine"; //corretto
        String cognome = "GRAVINO"; //corretto
        String pw = DigestUtils.sha256Hex("EasyPass2022!"); //corretto

        Docente docente = docenteDAO.doRetrieveByKeyWithRelations(id);

        String idDip = "DI";
        String nomeDip = "Informatica";

        assertNotNull(docente);
        assertEquals(id, docente.getUsername());
        assertEquals(nome, docente.getNome());
        assertEquals(cognome, docente.getCognome());
        assertEquals(DigestUtils.sha256Hex(pw), docente.getPassword());
        assertNotNull(docente.getDipartimento());

        assertEquals(idDip, docente.getDipartimento().getCodice());
        assertEquals(nomeDip, docente.getDipartimento().getNome());

    }

    /*************************************
     * Test doRetrieveAllWithRelations *
     *************************************/
    @Test
    public void doRetrieveAllWithRelationsNullTest() {
        assertThrows(IllegalArgumentException.class, () -> docenteDAO.doRetrieveAllWithRelations(null));
    }

    @Test
    public void doRetrieveAllWithRelationsReturnNullObjectTest() {
        String codiceDip = "pp"; //non corretto
        assertNull(docenteDAO.doRetrieveAllWithRelations(codiceDip));
    }

    @Test
    public void doRetrieveAllWithRelationsOKTest() {
        String codiceDip = "DI"; //corretto
        String pw = DigestUtils.sha256Hex("EasyPass2022!");
        ArrayList<Docente> docenti = docenteDAO.doRetrieveAllWithRelations(codiceDip);

        assertNotNull(docenti);
        doRetrieveAllParamTest("Vincenzo", "DEUFEMIA", DigestUtils.sha256Hex(pw), "deufemia@unisa.it", docenti.get(0));
        doRetrieveAllParamTest("Gennaro", "COSTAGLIOLA", DigestUtils.sha256Hex(pw), "gencos@unisa.it", docenti.get(1));
        doRetrieveAllParamTest("Carmine", "GRAVINO", DigestUtils.sha256Hex(pw), "gravino@unisa.it", docenti.get(2));
    }

    private void doRetrieveAllParamTest(String nome, String cognome, String pw, String username, Docente docente){
        assertEquals(nome, docente.getNome());
        assertEquals(cognome, docente.getCognome());
        assertEquals(pw, docente.getPassword());
        assertEquals(username, docente.getUsername());
    }

    /*************************************
     * Test checkUserAndPassw *
     *************************************/
    @Test
    public void checkUserAndPasswNullTest() {
        assertThrows(IllegalArgumentException.class, () -> docenteDAO.checkUserAndPassw(null));
    }

    @Test
    public void checkUserAndPasswReturnNullObjectTest() {
        Docente docente = new Docente("Alfonso", "Maria", "maria@unisa.it", "hag23!1111a", new Dipartimento()); //non corretto
        assertNull(docenteDAO.checkUserAndPassw(docente));
    }

    @Test
    public void checkUserAndPasswOKTest() {
        Docente docente1 = new Docente("Vincenzo", "DEUFEMIA", "deufemia@unisa.it","EasyPass2022!", new Dipartimento()); //corretto
        assertTrue(docenteDAO.checkUserAndPassw(docente1));
    }


}