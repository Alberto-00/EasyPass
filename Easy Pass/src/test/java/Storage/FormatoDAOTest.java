package Storage;

import Storage.Dipartimento.Dipartimento;
import Storage.Dipartimento.DipartimentoDAO;
import Storage.Formato.Formato;
import Storage.Formato.FormatoDAO;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormatoDAOTest {

    FormatoDAO formatoDAO;

    @Before
    public void setUp() {
        formatoDAO = new FormatoDAO();
    }

    /***********************
     * Test doRetrieveById *
     ***********************/
    @Test
    public void doRetrieveByIdNullTest(){
        assertThrows(IllegalArgumentException.class, ()->formatoDAO.doRetrieveById(null));
    }

    public void doRetrieveByIdReturnNullObjectTest(){
        String id = "pp"; //non corretto
        assertNull(formatoDAO.doRetrieveById(id));
    }

    @Test
    public void doRetrieveByIdOKTest(){
        String id = "DI"; //corretto

        Formato formato=formatoDAO.doRetrieveById(id);
        assertNotNull(formato);
        assertEquals(id, formato.getId());
    }

    /***********************
     * Test doUpdate*
     ***********************/
    @Test
    public void doUpdateNullArgumentTest(){
        assertThrows(IllegalArgumentException.class, ()->formatoDAO.doUpdate(null));
    }

    @Test
    public void doUpDateReturnFalseTest(){ //Testo se il metodo ritorna false quando qualcosa va storto.
        Formato formato=new Formato();
        formato.setId("a"); //ID non presente nel database
        assertFalse(formatoDAO.doUpdate(formato));
    }

    @Test
    public void doUpDateOKTest(){
        Formato formato=new Formato("DI",true,true,true,true,true);
        formatoDAO.doUpdate(formato);

        Formato formatoAggiornato=formatoDAO.doRetrieveById(formato.getId());

        assertNotNull(formatoAggiornato);
        assertEquals(formato.isData(),formatoAggiornato.isData());
        assertEquals(formato.isNomeCognome(),formatoAggiornato.isNomeCognome());
        assertEquals(formato.isNumGPNonValidi(),formatoAggiornato.isNumGPNonValidi());
        assertEquals(formato.isNumGPValidi(),formatoAggiornato.isNumGPValidi());
        assertEquals(formato.isNumStudenti(),formatoAggiornato.isNumStudenti());
    }

}
