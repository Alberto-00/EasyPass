package Logic;

import ApplicationLogic.Servlet.SessionController;
import ApplicationLogic.Utils.Validator.StudenteValidator;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;
import static org.mockito.Mockito.*;

@RunWith(Parameterized.class)
public class CheckGPTest {
    public String GP, expected;

    public CheckGPTest(String GP, String expected) {
        this.GP = GP;
        this.expected = expected;
    }

    @Parameterized.Parameters public static Collection<Object[]> parameters() {
        return Arrays.asList(new Object[][]{{"", "Nessun Green Pass inserito."},{"Ciao", "Errore! Formato non valido."}});
    }

    @Test
    public void GPvalidationTest(){
        assertTrue(StudenteValidator.checkGP(GP).compareTo(expected) == 0);
    }
}
