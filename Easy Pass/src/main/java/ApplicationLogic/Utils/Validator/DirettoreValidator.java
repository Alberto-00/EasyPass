package ApplicationLogic.Utils.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
 * La classe valida tutti gli input inseriti dal Direttore durante la fase di Login
 */
public class DirettoreValidator {

    /**
     * Crea un Validator ed effettua vari match per controllare
     * gli input inseriti durante il Login, in particolare, viene controllata
     * l'email e la password inseriti dal Direttore.
     *
     * @param request contiene i valori da controllare
     * @return Validator
     */
    public static Validator validateSignIn(HttpServletRequest request){
        Validator validator = new Validator(request);
        if (!validator.assertMatch("email", Pattern.compile("^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){2,18}[a-zA-Z0-9]$|^[a-zA-Z0-9_.+-]{3,}@(?:(?:[a-zA-Z0-9-]+\\.)?[a-zA-Z]+\\.)?(unisa)\\.it$"), "L’e-mail inserita non è corretta"))
            return validator;

        validator.assertMatch("password", Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[=^ ì{}+çò°àù§èé#@$!%€*?&:,;'._<>|-])[A-Za-z\\d=^ ì{}+çò°àù§èé#@$!%€*?&:,;'._<>|-]{8,}$"),"La password inserita non è corretta");
        return validator;
    }
}
