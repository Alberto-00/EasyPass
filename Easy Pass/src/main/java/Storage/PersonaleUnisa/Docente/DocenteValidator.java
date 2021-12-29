package Storage.PersonaleUnisa.Docente;

import ApplicationLogic.Utils.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

public class DocenteValidator {

    public static Validator validateSigin(HttpServletRequest request){
        Validator validator = new Validator(request);
        validator.assertMatch("email", Pattern.compile("^[a-zA-Z0-9_.]+@(?:(?:[a-zA-Z0-9-]+\\.)?[a-zA-Z]+\\.)?(unisa)\\.it$"), "L’e-mail inserita non è corretta");
        validator.assertMatch("password", Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[=^ ì{}+çò°àù§èé#@$!%€*?&:,;'._<>|-])[A-Za-z\\d=^ ì{}+çò°àù§èé#@$!%€*?&:,;'._<>|-]{8,20}$"),"La password inserita non è corretta");
        return validator;
    }

    public static Validator validateSigup(HttpServletRequest request){
        Validator validator = new Validator(request);
        validator.assertMatch("nome", Pattern.compile("^[a-zA-Z .']+$"),"Il nome inserito non è corretto.");
        validator.assertMatch("cognome", Pattern.compile("^[a-zA-Z .']+$"),"Il cognome inserito non è corretto.");
        validator.assertMatch("email2", Pattern.compile("^[a-zA-Z0-9_.]+@(?:(?:[a-zA-Z0-9-]+\\.)?[a-zA-Z]+\\.)?(unisa)\\.it$"), "L’e-mail inserita non è corretta");
        validator.assertMatch("password2", Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[=^ ì{}+çò°àù§èé#@$!%€*?&:,;'._<>|-])[A-Za-z\\d=^ ì{}+çò°àù§èé#@$!%€*?&:,;'._<>|-]{8,20}$"),"La password inserita non è corretta");
        return validator;
    }
}
