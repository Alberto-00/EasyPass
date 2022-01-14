package Storage;

import Storage.Dipartimento.Dipartimento;
import Storage.Dipartimento.DipartimentoDAO;
import Storage.PersonaleUnisa.Docente.Docente;
import Storage.PersonaleUnisa.Docente.DocenteDAO;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Before;
import org.junit.Test;

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
     * Test doRetrieveByNameSurname *
     *************************************/

    @Test
    public void doRetrieveByNameSurnameNullTest() {
        assertThrows(IllegalArgumentException.class, () -> docenteDAO.doRetrieveByNameSurname(null, null));
    }

    @Test
    public void doRetrieveByNameSurnameReturnNullObjectTest() {
        String nome = "a"; //non corretto
        String cognome = "a"; //non corretto
        assertNull(docenteDAO.doRetrieveByNameSurname(nome, cognome));
    }

    @Test
    public void doRetrieveByNameSurnameOKTest() {
        String nome = "Angelo"; //corretto
        String cognome = "MERIANI"; //corretto

        Docente docente = docenteDAO.doRetrieveByNameSurname(nome, cognome);
        assertNotNull(docente);
        assertEquals(nome, docente.getNome());
        assertEquals(cognome, docente.getCognome());
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
        assertEquals(new ArrayList<>(), docenteDAO.doRetrieveAllWithRelations(codiceDip));
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
        assertFalse(docenteDAO.checkUserAndPassw(docente));
    }

    @Test
    public void checkUserAndPasswOKTest() {
        Docente docente = new Docente("Vincenzo", "DEUFEMIA", "deufemia@unisa.it","EasyPass2022!", new Dipartimento()); //corretto
        assertTrue(docenteDAO.checkUserAndPassw(docente));
    }

    /*************************************
     * Test doCreate *
     *************************************/

    @Test
    public void doCreateNullTest() {
        assertThrows(IllegalArgumentException.class, () -> docenteDAO.doCreate(null));
    }

    @Test
    public void doCreateReturnNullObjectTest() {
        DipartimentoDAO dipartimentoDAO = new DipartimentoDAO();
        assertFalse(docenteDAO.doCreate(new Docente("Carmine", "Gravino", "gravino@unisa.it", "A", dipartimentoDAO.doRetrieveById("DI"))));
    }

    @Test
    public void doCreateOKTest() {
        String id = "rossi@unisa.it"; //corretto
        String nome = "Mario"; //corretto
        String cognome = "ROSSI"; //corretto
        String pw = DigestUtils.sha256Hex("EasyPass2022!"); //corretto
        DipartimentoDAO dipartimentoDAO = new DipartimentoDAO();
        Docente docente = new Docente(nome, cognome, id, DigestUtils.sha256Hex(pw), dipartimentoDAO.doRetrieveById("DI"));

        assertTrue(docenteDAO.doCreate(docente));
        docenteDAO.doDelete(docente);
    }



}