package Logic;

import ApplicationLogic.Utils.Validator.DocenteValidator;
import ApplicationLogic.Utils.Validator.Validator;
import Storage.PersonaleUnisa.Docente.Docente;
import Storage.PersonaleUnisa.Docente.DocenteDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class DocenteValidatorTest {

    HttpServletRequest request;
    DocenteDAO docenteDAO;
    Validator validator;

    @Before
    public void setUp(){
        request = Mockito.mock(HttpServletRequest.class);
        docenteDAO = Mockito.mock(DocenteDAO.class);
        validator = new Validator(request);
    }

    /*************************************
     * Validazione Registrazione Docente *
     *************************************/
    @Test
    public void registrazioneNomeVuotoTest(){
        String nome = "      "; //non corretta

        Mockito.when(request.getParameter("nome")).thenReturn(nome);
        Mockito.when(request.getParameter("cognome")).thenReturn(null);
        Mockito.when(request.getParameter("dipartimento")).thenReturn(null);
        Mockito.when(request.getParameter("email2")).thenReturn(null);
        Mockito.when(request.getParameter("password2")).thenReturn(null);

        validator = DocenteValidator.validateSignUp(request);
        assertTrue(validator.hasErrors());
        System.out.println(validator.getErrors().get(0));
        assertEquals(0, validator.getErrors().get(0).compareTo("Il nome inserito non è corretto."));
    }

    /*@Test
    public void registrazioneLunghezzaNomeMinoreDi3Test(){
        String nome = "a"; //non corretta
        String cognome = "GRAVINO"; //corretta
        String dipartimento = "DI"; //corretta
        String email = "gravino@unisa.it"; //corretta
        String password = "EasyPass2022!"; //corretta

        setParametersRequestsRegistration(nome, cognome, dipartimento, email, password);
        assertTrue(DocenteValidator.validateSignUp(request).hasErrors());
    }

    @Test
    public void registrazioneLunghezzaNomeMaggioreDi30Test(){
        String nome = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"; //non corretta
        String cognome = "GRAVINO"; //corretta
        String dipartimento = "DI"; //corretta
        String email = "gravino@unisa.it"; //corretta
        String password = "EasyPass2022!"; //corretta

        setParametersRequestsRegistration(nome, cognome, dipartimento, email, password);
        assertTrue(DocenteValidator.validateSignUp(request).hasErrors());
    }

    @Test
    public void registrazioneNomeNonRispettaFormatoTest(){
        String nome = "Carmine3"; //non corretta
        String cognome = "GRAVINO"; //corretta
        String dipartimento = "DI"; //corretta
        String email = "gravino@unisa.it"; //corretta
        String password = "EasyPass2022!"; //corretta

        setParametersRequestsRegistration(nome, cognome, dipartimento, email, password);
        assertTrue(DocenteValidator.validateSignUp(request).hasErrors());
    }

    @Test
    public void registrazioneCognomeVuotoTest(){
        String nome = "Carmine"; //corretta
        String cognome = ""; //non corretta
        String dipartimento = "DI"; //corretta
        String email = "gravino@unisa.it"; //corretta
        String password = "EasyPass2022!"; //corretta

        setParametersRequestsRegistration(nome, cognome, dipartimento, email, password);
        assertTrue(DocenteValidator.validateSignUp(request).hasErrors());
    }

    @Test
    public void registrazioneLunghezzaCognomeMinoreDi3Test(){
        String nome = "Carmine"; //corretta
        String cognome = "a"; //non corretta
        String dipartimento = "DI"; //corretta
        String email = "gravino@unisa.it"; //corretta
        String password = "EasyPass2022!"; //corretta

        setParametersRequestsRegistration(nome, cognome, dipartimento, email, password);
        assertTrue(DocenteValidator.validateSignUp(request).hasErrors());
    }

    @Test
    public void registrazioneLunghezzaCognomeMaggioreDi30Test(){
        String nome = "Carmine"; //corretta
        String cognome = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"; //non corretta
        String dipartimento = "DI"; //corretta
        String email = "gravino@unisa.it"; //corretta
        String password = "EasyPass2022!"; //corretta

        setParametersRequestsRegistration(nome, cognome, dipartimento, email, password);
        assertTrue(DocenteValidator.validateSignUp(request).hasErrors());
    }

    @Test
    public void registrazioneCognomeNonRispettaFormatoTest(){
        String nome = "Carmine"; //corretta
        String cognome = "Gravino0"; //non corretta
        String dipartimento = "DI"; //corretta
        String email = "gravino@unisa.it"; //corretta
        String password = "EasyPass2022!"; //corretta

        setParametersRequestsRegistration(nome, cognome, dipartimento, email, password);
        assertTrue(DocenteValidator.validateSignUp(request).hasErrors());
    }

    @Test
    public void registrazioneDipartimentoVuotoTest(){
        String nome = "Carmine"; //corretta
        String cognome = "Gravino"; //corretta
        String dipartimento = ""; //non corretta
        String email = "gravino@unisa.it"; //corretta
        String password = "EasyPass2022!"; //corretta

        setParametersRequestsRegistration(nome, cognome, dipartimento, email, password);
        assertTrue(DocenteValidator.validateSignUp(request).hasErrors());
    }

    @Test
    public void registrazioneEmailVuotaTest(){
        String nome = "Carmine"; //corretta
        String cognome = "Gravino"; //corretta
        String dipartimento = "DI"; //corretta
        String email = ""; //non corretta
        String password = "EasyPass2022!"; //corretta

        setParametersRequestsRegistration(nome, cognome, dipartimento, email, password);
        assertTrue(DocenteValidator.validateSignUp(request).hasErrors());
    }

    @Test
    public void registrazioneEmailFormatoNonValidoTest(){
        String nome = "Carmine"; //corretta
        String cognome = "Gravino"; //non corretta
        String dipartimento = "DI"; //corretta
        String email = "gravinounisa.it"; //non corretta
        String password = "EasyPass2022!"; //corretta

        setParametersRequestsRegistration(nome, cognome, dipartimento, email, password);
        assertTrue(DocenteValidator.validateSignUp(request).hasErrors());
    }

    @Test
    public void registrazioneEmailPresenteTest(){
        String nome = "Carmine"; //corretta
        String cognome = "Gravino"; //corretta
        String dipartimento = "DI"; //corretta
        String email = "gravino@unisa.it"; //corretta
        String password = "EasyPass2022!"; //corretta

        setParametersRequestsRegistration(nome, cognome, dipartimento, email, password);
        Docente docente = new Docente();
        Mockito.when(docenteDAO.doRetrieveByKey(email)).thenReturn(docente); //email già registrata
        assertTrue(DocenteValidator.validateSignUp(request).hasErrors());
        //assert
    }

    @Test
    public void registrazionePasswordVuotaTest(){
        String nome = "Carmine"; //corretta
        String cognome = "Gravino"; //corretta
        String dipartimento = "DI"; //corretta
        String email = "gravino@unisa.it"; //corretta
        String password = ""; //non corretta

        setParametersRequestsRegistration(nome, cognome, dipartimento, email, password);
        assertTrue(DocenteValidator.validateSignUp(request).hasErrors());
    }

    @Test
    public void registrazionePasswordCortaTest(){
        String nome = "Carmine"; //corretta
        String cognome = "Gravino"; //corretta
        String dipartimento = "DI"; //corretta
        String email = "gravino@unisa.it"; //corretta
        String password = "Aaab"; //non corretta

        setParametersRequestsRegistration(nome, cognome, dipartimento, email, password);
        assertTrue(DocenteValidator.validateSignUp(request).hasErrors());
    }

    @Test
    public void registrazionePasswordLungaTest(){
        String nome = "Carmine"; //corretta
        String cognome = "Gravino"; //corretta
        String dipartimento = "DI"; //corretta
        String email = "gravino@unisa.it"; //corretta
        String password = "Aaaaaa!222b"; //non corretta

        setParametersRequestsRegistration(nome, cognome, dipartimento, email, password);
        assertTrue(DocenteValidator.validateSignUp(request).hasErrors());
    }

    @Test
    public void registrazionePasswordNonRispettaFormatoTest(){
        String nome = "Carmine"; //corretta
        String cognome = "Gravino"; //corretta
        String dipartimento = "DI"; //corretta
        String email = "gravino@unisa.it"; //corretta
        String password = "Aaaa222b"; //non corretta

        setParametersRequestsRegistration(nome, cognome, dipartimento, email, password);
        assertTrue(DocenteValidator.validateSignUp(request).hasErrors());
    }

    @Test
    public void registrazioneTest(){
        String nome = "Carmine"; //corretta
        String cognome = "Gravino"; //corretta
        String dipartimento = "DI"; //corretta
        String email = "gravino@unisa.it"; //corretta
        String password = "Aaaa22b!"; //corretta

        setParametersRequestsRegistration(nome, cognome, dipartimento, email, password);
        assertTrue(DocenteValidator.validateSignUp(request).hasErrors());
    }*/


    /*************************************
     * Validazione input numero Studenti *
     *************************************/
    /*@Test
    public void numeroStudentiVuotoTest(){
        String numberStudents = ""; //non corretta
        setParametersRequestsNumberofStudents(numberStudents);
        assertTrue(DocenteValidator.validateNumberOfStudents(request).hasErrors());
    }

    @Test
    public void numeroStudentiNonRispettaFormatoTest(){
        String numberStudents = "a"; //non corretta
        setParametersRequestsNumberofStudents(numberStudents);
        assertTrue(DocenteValidator.validateNumberOfStudents(request).hasErrors());
    }

    @Test
    public void numeroStudentiMinoreUgualeZeroTest(){
        String numberStudents = "0"; //non corretta
        setParametersRequestsNumberofStudents(numberStudents);
        assertTrue(DocenteValidator.validateNumberOfStudents(request).hasErrors());
    }

    @Test
    public void numeroStudentiTest(){
        String numberStudents = "3"; //corretta
        setParametersRequestsNumberofStudents(numberStudents);
        assertTrue(DocenteValidator.validateNumberOfStudents(request).hasErrors());
    }*/
    
    private void setParametersRequestsNumberofStudents(String number){
        request.setAttribute("nStudents", number);
    }

    private void setParametersRequestsRegistration(String nome, String cognome, String dipartimento,
                                                   String email, String password){
        request.setAttribute("nome", nome);
        request.setAttribute("cognome", cognome);
        request.setAttribute("dipartimento", dipartimento);
        request.setAttribute("email2", email);
        request.setAttribute("password2", password);
    }
}
