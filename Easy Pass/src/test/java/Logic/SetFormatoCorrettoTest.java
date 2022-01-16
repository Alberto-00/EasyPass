package Logic;

import ApplicationLogic.Servlet.ReportController;
import Storage.Dipartimento.Dipartimento;
import Storage.Formato.Formato;
import Storage.PersonaleUnisa.Direttore.DirettoreDiDipartimento;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SetFormatoCorrettoTest {
    Formato formato;
    DirettoreDiDipartimento direttore;
    String anagrafica, ddn, numValidazioni, gpValidi, gpNonValidi;

    @Test
    public void setFormatoCorrettoTest(){
        formato = null;
        direttore = mock(DirettoreDiDipartimento.class);
        Dipartimento dip = mock(Dipartimento.class);
        when(direttore.getDipartimento()).thenReturn(dip);
        when(dip.getCodice()).thenReturn("DI");
        anagrafica = "true";
        ddn = "true";
        numValidazioni = "true";
        gpValidi = "true";
        gpNonValidi = "true";
        formato = ReportController.setFormatoCorretto(formato, direttore, anagrafica, ddn, numValidazioni, gpValidi, gpNonValidi);
        assertTrue(formato.isNomeCognome());
        assertTrue(formato.isData());
        assertTrue(formato.isNumStudenti());
        assertTrue(formato.isNumGPValidi());
        assertTrue(formato.isNumGPNonValidi());
    }
}
