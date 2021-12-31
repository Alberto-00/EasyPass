package ApplicationLogic.Utils.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

public class DirettoreValidator {

    public static Validator validateSigin(HttpServletRequest request){
        Validator validator = new Validator(request);
        validator.assertMatch("email", Pattern.compile("^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){2,18}[a-zA-Z0-9]$|^[a-zA-Z0-9_.+-]{3,}@(?:(?:[a-zA-Z0-9-]+\\.)?[a-zA-Z]+\\.)?(unisa)\\.it$"), "L’e-mail inserita non è corretta");
        validator.assertMatch("password", Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[=^ ì{}+çò°àù§èé#@$!%€*?&:,;'._<>|-])[A-Za-z\\d=^ ì{}+çò°àù§èé#@$!%€*?&:,;'._<>|-]{8,20}$"),"La password inserita non è corretta");
        return validator;
    }
}
