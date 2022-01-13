package Storage;

import ApplicationLogic.Utils.ConnectionSingleton;
import Storage.Dipartimento.DipartimentoDAO;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DipartimentoDAOTest extends Mockito {

    Connection connection;
    DipartimentoDAO dipartimentoDAO;

    @Before
    public void setUp() throws SQLException {
        connection = ConnectionSingleton.getInstance().getConnection();
        dipartimentoDAO = new DipartimentoDAO();
    }

    @Ignore
    @Test
    public void doRetrieveByIdNullTest(){
        String id = null;
        assertThrows(IllegalArgumentException.class, ()->dipartimentoDAO.doRetrieveById(id));
    }

    @Ignore
    @Test
    public void doRetrieveByIdOKTest(){
        String id = "DI";
        dipartimentoDAO.doRetrieveById(id);
    }
}
