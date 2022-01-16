package Logic;

import ApplicationLogic.Utils.Validator.StudenteValidator;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class CheckGPTest {
    public String GP, expected;

    public CheckGPTest(String GP, String expected) {
        this.GP = GP;
        this.expected = expected;
    }

    @Parameterized.Parameters public static Collection<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
                {"", "Nessun Green Pass inserito."},
                {"Ciao", "Errore! Formato non valido."},
                {"HC1:6BFOXN%TS3DHPVO13J /G-/2YRVA.Q/R8JF62FC1J9M$DI9CJG9T2TZ4PS$S:LC/GPWBILC9GGBYPLDXI03L-+R2YBV44PZB6H0CJ0%H0%P8. KOKGTM8$M8SNCXL9LM0C KPLIUM45FM$GGK3MTDC-JE6GQ2%KYZPQV6YP8/22-K6Y/60N8UL87J1SNCSL6XI000B-L97J11:6/.6L1D9K693D:WOK*R3T3+7A.N88J4R$F/MAITHP+PIJ6W*PP+PDPIGOK-*GN*Q:XJR-GM%O-RQV*Q6QS03L0QIRR97I2HOAZEKX:CIGF5JNCPIGSUIJAII0-:V.UIJRUN HC.U7NI-3F :7 7PEEWJ*UU23JINQ+MN/Q19QE8Q4A7E:7LYPFTQBK84EOHCRST5EFGFSC.SK1ZQ8AO*$VA%F.FN%E9DO44HDXW0FM8D:57AF/.TH9SN54EPTA8KI%2G U/Z5APCHF9FJN 0NH/FL/K8*UR+T71AX9FT%UI40J/2R4","Green Pass inviato correttamente."}});
    }

    @Test
    public void GPvalidationTest(){
        assertEquals(0, StudenteValidator.checkGP(GP).compareTo(expected));
    }
}
