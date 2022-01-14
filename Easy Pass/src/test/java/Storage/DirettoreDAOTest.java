package Storage;

import Storage.PersonaleUnisa.Direttore.DirettoreDiDipartimento;
import Storage.PersonaleUnisa.Direttore.DirettoreDiDipartimentoDAO;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DirettoreDAOTest {

    DirettoreDiDipartimentoDAO dir = new DirettoreDiDipartimentoDAO();

    @Before
    public void setUp() {
        dir = new DirettoreDiDipartimentoDAO();
    }

    /***********************
     * Test doRetrieveByKey *
     ***********************/

    @Test
    public void doRetrieveByKeyNullTest() {
        assertThrows(IllegalArgumentException.class, () -> dir.doRetrieveByKey(null));
    }

    @Test
    public void doRetrieveByKeyReturnNullObjectTest() {
        String id = "a"; //non corretto
        assertNull(dir.doRetrieveByKey(id));
    }

    @Test
    public void doRetrieveByKeyOKTest() {
        String id = "lgargano@unisa.it"; //corretto
        String nome = "Luisa"; //corretto
        String cognome = "GARGANO"; //corretto
        String pw = DigestUtils.sha256Hex("EasyPass2022!"); //corretto

        DirettoreDiDipartimento direttore = dir.doRetrieveByKey(id);
        assertNotNull(direttore);
        assertEquals(id, direttore.getUsername());
        assertEquals(nome, direttore.getNome());
        assertEquals(cognome, direttore.getCognome());
        assertEquals(DigestUtils.sha256Hex(pw), direttore.getPassword());
    }

    /*************************************
     * Test checkUserAndPassw *
     *************************************/
    @Test
    public void checkUserAndPasswNullTest() {
        assertThrows(IllegalArgumentException.class, () -> dir.checkUserAndPassw(null));
    }

    @Test
    public void checkUserAndPasswReturnNullObjectTest() {
        DirettoreDiDipartimento direttore = new DirettoreDiDipartimento();
        direttore.setUsername("aa");//non corretto
        direttore.setPassword("AAA122!!_");//non corretto
        assertFalse(dir.checkUserAndPassw(direttore));
    }

    @Test
    public void checkUserAndPasswOKTest() {
        DirettoreDiDipartimento direttore = new DirettoreDiDipartimento();
        direttore.setUsername("lgargano@unisa.it");//non corretto
        direttore.setPassword("EasyPass2022!");//non corretto
        assertTrue(dir.checkUserAndPassw(direttore));
    }

    /*************************************
     * Test doRetrieveByKeyWithRelations *
     *************************************/
    @Test
    public void doRetrieveByKeyWithRelationsNullTest() {
        assertThrows(IllegalArgumentException.class, () -> dir.doRetrieveByKeyWithRelations(null));
    }

    @Test
    public void doRetrieveByKeyWithRelationsReturnNullObjectTest() {
        String codice = "pp"; //non corretto
        assertNull(dir.doRetrieveByKeyWithRelations(codice));
    }

    @Test
    public void doRetrieveByKeyWithRelationsOKTest() {
        String id = "lgargano@unisa.it"; //corretto
        String nome = "Luisa"; //corretto
        String cognome = "GARGANO"; //corretto
        String pw = DigestUtils.sha256Hex("EasyPass2022!"); //corretto

        DirettoreDiDipartimento direttore = dir.doRetrieveByKeyWithRelations(id);

        String idDip = "DI";
        String nomeDip = "Informatica";

        assertNotNull(direttore);
        assertEquals(id, direttore.getUsername());
        assertEquals(nome, direttore.getNome());
        assertEquals(cognome, direttore.getCognome());
        assertEquals(DigestUtils.sha256Hex(pw), direttore.getPassword());
        assertNotNull(direttore.getDipartimento());

        assertEquals(idDip, direttore.getDipartimento().getCodice());
        assertEquals(nomeDip, direttore.getDipartimento().getNome());

    }



}
