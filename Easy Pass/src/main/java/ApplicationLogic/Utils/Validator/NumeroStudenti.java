package ApplicationLogic.Utils.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

public class NumeroStudenti {

    public static Validator validateNumberOfStudents(HttpServletRequest request){
        Validator validator = new Validator(request);
        validator.assertMatch("nStudents", Pattern.compile("^[1-9]+[0-9]*$"), "L’e-mail inserita non è corretta");
        return validator;
    }
}
