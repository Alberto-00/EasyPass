package ApplicationLogic.Utils.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Validator {

    private final List<String> errors;
    private final HttpServletRequest request;

    public Validator(HttpServletRequest request){
        this.errors = new ArrayList<>();
        this.request = request;
    }

    public boolean hasErrors(){
        return !errors.isEmpty();
    }

    public List<String> getErrors(){
        return errors;
    }

    private boolean gatherError(boolean condition, String message){
        if (condition){
            return true;
        } else {
            errors.add(message);
            return false;
        }
    }

    private boolean required(String value){
        return value != null && !value.isBlank();
    }

    public boolean assertMatch(String value, Pattern regexp, String msg){
        String param = request.getParameter(value);
        boolean condition = required(param) && regexp.matcher(param).matches();
        return gatherError(condition, msg);
    }
}
