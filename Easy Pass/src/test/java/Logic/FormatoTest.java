package Logic;

import ApplicationLogic.Servlet.ReportController;
import Storage.PersonaleUnisa.Direttore.DirettoreDiDipartimento;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;
import static org.mockito.Mockito.*;

@RunWith(Parameterized.class)
public class FormatoTest {
    public String anagrafica, ddn, numValidazioni, gpValidi, gpNonValidi, expectedMessaggio;
    public DirettoreDiDipartimento direttore;
    public HttpServletRequest request;

    public FormatoTest(String anagrafica, String ddn, String numValidazioni, String gpValidi, String gpNonValidi,String expectedMessaggio, DirettoreDiDipartimento direttore, HttpServletRequest request) {
        this.anagrafica = anagrafica;
        this.ddn = ddn;
        this.numValidazioni = numValidazioni;
        this.gpValidi = gpValidi;
        this.gpNonValidi = gpNonValidi;
        this.direttore = direttore;
        this.request = request;
        this.expectedMessaggio = expectedMessaggio;
    }

    @Parameterized.Parameters public static Collection<Object[]> parameters(){
        return Arrays.asList(new Object[][] {{null,"true",null,null,null,"Impossibile salvare il formato! Se è selezionata la data deve essere selezionata anche l'anagrafica.\n", mock(DirettoreDiDipartimento.class), mock(HttpServletRequest.class)},
                {null,null,null,null,null, "Selezionare almeno un campo.\n", mock(DirettoreDiDipartimento.class), mock(HttpServletRequest.class)}});
    }

    @Test
    public void validationTest(){
        ReportController reportControllerTest = new ReportController();
        assertEquals("Un messaggio ritornato dal check del formato non era corrispondente a quello atteso", 0, reportControllerTest.checkAndSetFormato(anagrafica, ddn, numValidazioni, gpValidi, gpNonValidi, direttore, request).compareTo(expectedMessaggio));
    }
}
