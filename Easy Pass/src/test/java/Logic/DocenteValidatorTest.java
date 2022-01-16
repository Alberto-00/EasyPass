package Logic;

import ApplicationLogic.Utils.Validator.DocenteValidator;
import ApplicationLogic.Utils.Validator.Validator;
import Storage.PersonaleUnisa.Docente.DocenteDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;


public class DocenteValidatorTest {

    HttpServletRequest request;
    DocenteDAO docenteDAO;
    Validator validator;

    @Before
    public void setUp(){
        request = Mockito.mock(HttpServletRequest.class);
        docenteDAO = Mockito.mock(DocenteDAO.class);
    }

    /*************************************
     * Validazione Registrazione Docente *
     *************************************/
    @Test
    public void registrazioneNomeVuotoTest(){
        Mockito.when(request.getParameter("nome")).thenReturn(null);
        Mockito.when(request.getParameter("cognome")).thenReturn(null);
        Mockito.when(request.getParameter("dipartimento")).thenReturn(null);
        Mockito.when(request.getParameter("email2")).thenReturn(null);
        Mockito.when(request.getParameter("password2")).thenReturn(null);

        validator = DocenteValidator.validateSignUp(request);
        assertTrue(validator.hasErrors());
        assertEquals(0, validator.getErrors().get(0).compareTo("Il nome inserito non è corretto."));
    }

    @Test
    public void registrazioneLunghezzaNomeMinoreDi3Test(){
        String nome = "a"; //non corretta

        Mockito.when(request.getParameter("nome")).thenReturn(nome);
        Mockito.when(request.getParameter("cognome")).thenReturn(null);
        Mockito.when(request.getParameter("dipartimento")).thenReturn(null);
        Mockito.when(request.getParameter("email2")).thenReturn(null);
        Mockito.when(request.getParameter("password2")).thenReturn(null);

        validator = DocenteValidator.validateSignUp(request);
        assertTrue(validator.hasErrors());
        assertEquals(0, validator.getErrors().get(0).compareTo("Il nome non rispetta il formato."));
    }

    @Test
    public void registrazioneLunghezzaNomeMaggioreDi30Test(){
        String nome = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"; //non corretta

        Mockito.when(request.getParameter("nome")).thenReturn(nome);
        Mockito.when(request.getParameter("cognome")).thenReturn(null);
        Mockito.when(request.getParameter("dipartimento")).thenReturn(null);
        Mockito.when(request.getParameter("email2")).thenReturn(null);
        Mockito.when(request.getParameter("password2")).thenReturn(null);

        validator = DocenteValidator.validateSignUp(request);
        assertTrue(validator.hasErrors());
        assertEquals(0, validator.getErrors().get(0).compareTo("Il nome non rispetta il formato."));
    }

    @Test
    public void registrazioneNomeNonRispettaFormatoTest(){
        String nome = "Luca3"; //non corretta

        Mockito.when(request.getParameter("nome")).thenReturn(nome);
        Mockito.when(request.getParameter("cognome")).thenReturn(null);
        Mockito.when(request.getParameter("dipartimento")).thenReturn(null);
        Mockito.when(request.getParameter("email2")).thenReturn(null);
        Mockito.when(request.getParameter("password2")).thenReturn(null);

        validator = DocenteValidator.validateSignUp(request);
        assertTrue(validator.hasErrors());
        assertEquals(0, validator.getErrors().get(0).compareTo("Il nome inserito non è corretto."));
    }

    @Test
    public void registrazioneCognomeVuotoTest(){
        String nome = "Luca"; //corretta

        Mockito.when(request.getParameter("nome")).thenReturn(nome);
        Mockito.when(request.getParameter("cognome")).thenReturn(null);
        Mockito.when(request.getParameter("dipartimento")).thenReturn(null);
        Mockito.when(request.getParameter("email2")).thenReturn(null);
        Mockito.when(request.getParameter("password2")).thenReturn(null);

        validator = DocenteValidator.validateSignUp(request);
        assertTrue(validator.hasErrors());
        assertEquals(0, validator.getErrors().get(0).compareTo("Il cognome inserito non è corretto."));
    }

    @Test
    public void registrazioneLunghezzaCognomeMinoreDi3Test(){
        String nome = "Luca"; //corretta
        String cognome = "a"; //non corretta

        Mockito.when(request.getParameter("nome")).thenReturn(nome);
        Mockito.when(request.getParameter("cognome")).thenReturn(cognome);
        Mockito.when(request.getParameter("dipartimento")).thenReturn(null);
        Mockito.when(request.getParameter("email2")).thenReturn(null);
        Mockito.when(request.getParameter("password2")).thenReturn(null);

        validator = DocenteValidator.validateSignUp(request);
        assertTrue(validator.hasErrors());
        assertEquals(0, validator.getErrors().get(0).compareTo("Il cognome non rispetta il formato."));
    }

    @Test
    public void registrazioneLunghezzaCognomeMaggioreDi30Test(){
        String nome = "Luca"; //corretta
        String cognome = "aaaaaaaaaaaaaaaaaaaaaaaaaaaayyyhggaaaaa"; //non corretta

        Mockito.when(request.getParameter("nome")).thenReturn(nome);
        Mockito.when(request.getParameter("cognome")).thenReturn(cognome);
        Mockito.when(request.getParameter("dipartimento")).thenReturn(null);
        Mockito.when(request.getParameter("email2")).thenReturn(null);
        Mockito.when(request.getParameter("password2")).thenReturn(null);

        validator = DocenteValidator.validateSignUp(request);
        assertTrue(validator.hasErrors());
        assertEquals(0, validator.getErrors().get(0).compareTo("Il cognome non rispetta il formato."));
    }

    @Test
    public void registrazioneCognomeNonRispettaFormatoTest(){
        String nome = "Luca"; //corretta
        String cognome = "Rossi0"; //non corretta

        Mockito.when(request.getParameter("nome")).thenReturn(nome);
        Mockito.when(request.getParameter("cognome")).thenReturn(cognome);
        Mockito.when(request.getParameter("dipartimento")).thenReturn(null);
        Mockito.when(request.getParameter("email2")).thenReturn(null);
        Mockito.when(request.getParameter("password2")).thenReturn(null);

        validator = DocenteValidator.validateSignUp(request);
        assertTrue(validator.hasErrors());
        assertEquals(0, validator.getErrors().get(0).compareTo("Il cognome inserito non è corretto."));
    }

    @Test
    public void registrazioneDipartimentoVuotoTest(){
        String nome = "Luca"; //corretta
        String cognome = "Rossi"; //corretta

        Mockito.when(request.getParameter("nome")).thenReturn(nome);
        Mockito.when(request.getParameter("cognome")).thenReturn(cognome);
        Mockito.when(request.getParameter("dipartimento")).thenReturn(null);
        Mockito.when(request.getParameter("email2")).thenReturn(null);
        Mockito.when(request.getParameter("password2")).thenReturn(null);

        validator = DocenteValidator.validateSignUp(request);
        assertTrue(validator.hasErrors());
        assertEquals(0, validator.getErrors().get(0).compareTo("Inserire il Dipartimento."));
    }

    @Test
    public void registrazioneEmailVuotaTest(){
        String nome = "Luca"; //corretta
        String cognome = "Rossi"; //corretta
        String dipartimento = "DI"; //corretta

        Mockito.when(request.getParameter("nome")).thenReturn(nome);
        Mockito.when(request.getParameter("cognome")).thenReturn(cognome);
        Mockito.when(request.getParameter("dipartimento")).thenReturn(dipartimento);
        Mockito.when(request.getParameter("email2")).thenReturn(null);
        Mockito.when(request.getParameter("password2")).thenReturn(null);

        validator = DocenteValidator.validateSignUp(request);
        assertTrue(validator.hasErrors());
        assertEquals(0, validator.getErrors().get(0).compareTo("L’e-mail inserita non è corretta."));
    }

    @Test
    public void registrazioneEmailFormatoNonValidoTest(){
        String nome = "Luca"; //corretta
        String cognome = "Rossi"; //corretta
        String dipartimento = "DI"; //corretta
        String email = "lucaunisa.it"; //non corretta

        Mockito.when(request.getParameter("nome")).thenReturn(nome);
        Mockito.when(request.getParameter("cognome")).thenReturn(cognome);
        Mockito.when(request.getParameter("dipartimento")).thenReturn(dipartimento);
        Mockito.when(request.getParameter("email2")).thenReturn(email);
        Mockito.when(request.getParameter("password2")).thenReturn(null);

        validator = DocenteValidator.validateSignUp(request);
        assertTrue(validator.hasErrors());
        assertEquals(0, validator.getErrors().get(0).compareTo("L’e-mail inserita non è corretta."));
    }

    @Test
    public void registrazioneEmailPresenteTest(){
        String nome = "Luca"; //corretta
        String cognome = "Rossi"; //corretta
        String dipartimento = "DI"; //corretta
        String email = "gravino@unisa.it"; //corretta

        Mockito.when(request.getParameter("nome")).thenReturn(nome);
        Mockito.when(request.getParameter("cognome")).thenReturn(cognome);
        Mockito.when(request.getParameter("dipartimento")).thenReturn(dipartimento);
        Mockito.when(request.getParameter("email2")).thenReturn(email);
        Mockito.when(request.getParameter("msg")).thenReturn("Email già registrata.");

        assertEquals("Email già registrata.", request.getParameter("msg"));
    }

    @Test
    public void registrazionePasswordVuotaTest(){
        String nome = "Luca"; //corretta
        String cognome = "Rossi"; //corretta
        String dipartimento = "DI"; //corretta
        String email = "luca@unisa.it"; //corretta

        Mockito.when(request.getParameter("nome")).thenReturn(nome);
        Mockito.when(request.getParameter("cognome")).thenReturn(cognome);
        Mockito.when(request.getParameter("dipartimento")).thenReturn(dipartimento);
        Mockito.when(request.getParameter("email2")).thenReturn(email);
        Mockito.when(request.getParameter("password2")).thenReturn(null);

        validator = DocenteValidator.validateSignUp(request);
        assertTrue(validator.hasErrors());
        assertEquals(0, validator.getErrors().get(0).compareTo("La password inserita non è corretta."));
    }

    @Test
    public void registrazionePasswordCortaTest(){
        String nome = "Luca"; //corretta
        String cognome = "Rossi"; //corretta
        String dipartimento = "DI"; //corretta
        String email = "luca@unisa.it"; //corretta
        String password = "Aaab"; //non corretta

        Mockito.when(request.getParameter("nome")).thenReturn(nome);
        Mockito.when(request.getParameter("cognome")).thenReturn(cognome);
        Mockito.when(request.getParameter("dipartimento")).thenReturn(dipartimento);
        Mockito.when(request.getParameter("email2")).thenReturn(email);
        Mockito.when(request.getParameter("password2")).thenReturn(password);

        validator = DocenteValidator.validateSignUp(request);
        assertTrue(validator.hasErrors());
        assertEquals(0, validator.getErrors().get(0).compareTo("La password inserita non è corretta."));
    }

    @Test
    public void registrazionePasswordNonRispettaFormatoTest(){
        String nome = "Luca"; //corretta
        String cognome = "Rossi"; //corretta
        String dipartimento = "DI"; //corretta
        String email = "luca@unisa.it"; //corretta
        String password = "Aaaa222b"; //non corretta

        Mockito.when(request.getParameter("nome")).thenReturn(nome);
        Mockito.when(request.getParameter("cognome")).thenReturn(cognome);
        Mockito.when(request.getParameter("dipartimento")).thenReturn(dipartimento);
        Mockito.when(request.getParameter("email2")).thenReturn(email);
        Mockito.when(request.getParameter("password2")).thenReturn(password);

        validator = DocenteValidator.validateSignUp(request);
        assertTrue(validator.hasErrors());
        assertEquals(0, validator.getErrors().get(0).compareTo("La password inserita non è corretta."));
    }

    @Test
    public void registrazioneTest(){
        String nome = "Luca"; //corretta
        String cognome = "Rossi"; //corretta
        String dipartimento = "DI"; //corretta
        String email = "luca@unisa.it"; //corretta
        String password = "Aaaa22b!"; //corretta

        Mockito.when(request.getParameter("nome")).thenReturn(nome);
        Mockito.when(request.getParameter("cognome")).thenReturn(cognome);
        Mockito.when(request.getParameter("dipartimento")).thenReturn(dipartimento);
        Mockito.when(request.getParameter("email2")).thenReturn(email);
        Mockito.when(request.getParameter("password2")).thenReturn(password);

        validator = DocenteValidator.validateSignUp(request);
        assertFalse(validator.hasErrors());
        assertTrue(validator.getErrors().isEmpty());
    }


    /*************************************
     * Validazione input numero Studenti *
     *************************************/
    @Test
    public void numeroStudentiVuotoTest(){
        Mockito.when(request.getParameter("nStudents")).thenReturn(null);

        validator = DocenteValidator.validateNumberOfStudents(request);
        assertTrue(validator.hasErrors());
        assertEquals(0, validator.getErrors().get(0).compareTo("Il numero di Studenti inserito non è corretto."));
    }

    @Test
    public void numeroStudentiNonRispettaFormatoTest(){
        String numberStudents = "a"; //non corretta

        Mockito.when(request.getParameter("nStudents")).thenReturn(numberStudents);

        validator = DocenteValidator.validateNumberOfStudents(request);
        assertTrue(validator.hasErrors());
        assertEquals(0, validator.getErrors().get(0).compareTo("Il numero di Studenti inserito non è corretto."));
    }

    @Test
    public void numeroStudentiMinoreUgualeZeroTest(){
        String numberStudents = "0"; //non corretta

        Mockito.when(request.getParameter("nStudents")).thenReturn(numberStudents);

        validator = DocenteValidator.validateNumberOfStudents(request);
        assertTrue(validator.hasErrors());
        assertEquals(0, validator.getErrors().get(0).compareTo("Il numero di Studenti inserito non è corretto."));
    }

    @Test
    public void numeroStudentiTest(){
        String numberStudents = "3"; //corretta
        Mockito.when(request.getParameter("nStudents")).thenReturn(numberStudents);

        validator = DocenteValidator.validateNumberOfStudents(request);
        assertFalse(validator.hasErrors());
        assertTrue(validator.getErrors().isEmpty());
    }
}
