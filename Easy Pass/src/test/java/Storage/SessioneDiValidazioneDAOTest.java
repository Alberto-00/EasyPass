package Storage;

import Storage.PersonaleUnisa.Docente.Docente;
import Storage.SessioneDiValidazione.SessioneDiValidazione;
import Storage.SessioneDiValidazione.SessioneDiValidazioneDAO;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SessioneDiValidazioneDAOTest {

    SessioneDiValidazioneDAO sessionDAO;

    @Before
    public void setUp() {
        sessionDAO = new SessioneDiValidazioneDAO();
    }

    /***********************
     * Test doRetrieveByID *
     ***********************/
    @Test
    public void doRetrieveByIDNegativeTest() {
        assertThrows(IllegalArgumentException.class, () -> sessionDAO.doRetrieveById(-1));
    }

    @Test
    public void doRetrieveByIDReturnNullObjectTest() {
        int codice = 22; //non corretto
        assertNull(sessionDAO.doRetrieveById(codice));
    }

    @Test
    public void doRetrieveByIDokTest() {
        SessioneDiValidazione session = createSessionTest("98.jpg", true, "gravino@unisa.it");
        sessionDAO.doCreate(session);
        SessioneDiValidazione session2 = sessionDAO.doRetrieveById(98);

        assertNotNull(session2);
        assertEquals(session.getQRCode(), session2.getQRCode());
        assertEquals(session.isInCorso(), session2.isInCorso());
        sessionDAO.doDelete(session);
    }

    /*****************
     * Test doCreate *
     *****************/
    @Test
    public void doCreateNullTest() {
        assertThrows(IllegalArgumentException.class, () -> sessionDAO.doCreate(null));
    }

    @Test
    public void doCreateRetunFalseTest() {
        SessioneDiValidazione session = createSessionTest("98.jpg", true, "");
        assertFalse(sessionDAO.doCreate(session));
    }

    @Test
    public void doCreateOKTest() {
        SessioneDiValidazione session = createSessionTest("1.jpg", true, "gravino@unisa.it");
        assertTrue(sessionDAO.doCreate(session));
        sessionDAO.doDelete(session);
    }

    /*****************
     * Test doUpdate *
     *****************/
    @Test
    public void doUpdateNullTest() {
        assertThrows(IllegalArgumentException.class, () -> sessionDAO.doUpdate(null));
    }

    @Test
    public void doUpdateReturnFalseTest() {
        SessioneDiValidazione session = createSessionTest("98.jpg", true, "prova@unisa.it");
        assertFalse(sessionDAO.doUpdate(session));
    }

    @Test
    public void doUpdateOKTest() {
        SessioneDiValidazione sessionCreate = createSessionTest("100000.jpg", true, "gravino@unisa.it");
        sessionDAO.doCreate(sessionCreate);

        SessioneDiValidazione sessionUpdate = createSessionTest("100000.jpg", false, "gravino@unisa.it");
        assertTrue(sessionDAO.doUpdate(sessionUpdate));

        SessioneDiValidazione sessionUpdateCheck = sessionDAO.doRetrieveById(100000);
        sessionUpdateCheck.setDocente(new Docente());
        sessionUpdateCheck.getDocente().setUsername("gravino@unisa.it");

        assertEquals(sessionUpdateCheck.getDocente().getUsername(), sessionUpdate.getDocente().getUsername());
        assertEquals(sessionUpdateCheck.isInCorso(), sessionUpdate.isInCorso());
        assertEquals(sessionUpdateCheck.getQRCode(), sessionUpdate.getQRCode());
        sessionDAO.doDelete(sessionUpdate);
    }

    private SessioneDiValidazione createSessionTest(String qr, boolean isIncorso, String user){
        SessioneDiValidazione session = new SessioneDiValidazione();
        session.setInCorso(isIncorso);
        session.setDocente(new Docente());
        session.getDocente().setUsername(user);
        session.setQRCode(qr + ".jpg");
        return session;
    }
}
