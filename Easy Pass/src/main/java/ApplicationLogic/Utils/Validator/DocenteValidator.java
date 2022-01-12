package ApplicationLogic.Utils.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
 * La classe valida tutti gli input inseriti dal Docente durante la fase
 * di Login, di Registrazione e di inserimento del numero di Studenti.
 */
public class DocenteValidator {

    /**
     * Crea un {@code Validator} ed effettua vari match per controllare
     * gli input inseriti durante il Login, in particolare, viene controllata
     * l'email e la password inseriti dal Docente.
     *
     * @param request contiene i valori da controllare
     * @return {@code Validator}
     */
    public static Validator validateSignIn(HttpServletRequest request){
        Validator validator = new Validator(request);
        validator.assertMatch("email", Pattern.compile("^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){2,18}[a-zA-Z0-9]$|^[a-zA-Z0-9_.+-]{3,}@(?:(?:[a-zA-Z0-9-]+\\.)?[a-zA-Z]+\\.)?(unisa)\\.it$"), "L’e-mail inserita non è corretta");
        validator.assertMatch("password", Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[=^ ì{}+çò°àù§èé#@$!%€*?&:,;'._<>|-])[A-Za-z\\d=^ ì{}+çò°àù§èé#@$!%€*?&:,;'._<>|-]{8,20}$"),"La password inserita non è corretta");
        return validator;
    }


    /**
     * Crea un {@code Validator} ed effettua vari match per controllare
     * gli input inseriti durante la Registrazione, in particolare, viene controllato
     * il nome, il cognome, l'email e la password inseriti dal Docente.
     *
     * @param request contiene i valori da controllare
     * @return {@code Validator}
     */
    public static Validator validateSignUp(HttpServletRequest request){
        Validator validator = new Validator(request);
        validator.assertMatch("dipartimento", Pattern.compile("^(?!\\s*$).+"), "Inserire il Dipartimento.");

        validator.assertMatch("nome", Pattern.compile("^[a-zA-Z .']+$"),"Il nome inserito non è corretto.");
        if (isBlankRequest("nome", request))
            validator.getErrors().add("Il nome non rispetta il formato.");

        validator.assertMatch("cognome", Pattern.compile("^[a-zA-Z .']+$"),"Il cognome inserito non è corretto.");
        if (isBlankRequest("cognome", request))
            validator.getErrors().add("Il cognome non rispetta il formato.");

        validator.assertMatch("email2", Pattern.compile("^[a-zA-Z0-9_.]{3,}@(?:(?:[a-zA-Z0-9-]+\\.)?[a-zA-Z]+\\.)?(unisa)\\.it$"), "L’e-mail inserita non è corretta");
        validator.assertMatch("password2", Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[=^ ì{}+çò°àù§èé#@$!%€*?&:,;'._<>|-])[A-Za-z\\d=^ ì{}+çò°àù§èé#@$!%€*?&:,;'._<>|-]{8,20}$"),"La password inserita non è corretta");
        return validator;
    }

    private static boolean isBlankRequest(String value, HttpServletRequest request){
        return request.getParameter(value).isBlank() ||
                request.getParameter(value).length() < 3 ||
                request.getParameter(value).length() > 30;
    }


    /**
     * Crea un {@code Validator} ed effettua il match tra la regex e l'input del numero
     * degli Studenti.
     *
     * @param request contiene il valore da controllare
     * @return {@code Validator}
     */
    public static Validator validateNumberOfStudents(HttpServletRequest request){
        Validator validator = new Validator(request);
        validator.assertMatch("nStudents", Pattern.compile("^[1-9]+[0-9]*$"), "Il numero di Studenti inserito non è corretto.");
        return validator;
    }
}
