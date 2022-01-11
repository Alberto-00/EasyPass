package ApplicationLogic.Utils.Validator;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *  La classe effettua il match tra le regex e i parametri da controllare.
 *  In caso di mismatch viene riempita una lista con i messaggi di errore.
 */
public class Validator {

    private final List<String> errors;
    private final HttpServletRequest request;

    public Validator(HttpServletRequest request){
        this.errors = new ArrayList<>();
        this.request = request;
    }


    /**
     * Verifica se la lista degli errori \u00E8 vuota.
     *
     * @return true se la lista ha messaggi di errore, altrimenti false
     */
    public boolean hasErrors(){
        return !errors.isEmpty();
    }

    public List<String> getErrors(){
        return errors;
    }


    /**
     * Se la condizione \u00E8 falsa, viene aggiunta alla lista degli errori
     * il messaggio passato in input, altrimenti viene ritornato true.
     *
     * @param condition booleano da controllare
     * @param message messaggio di errore
     * @return true se la condizione è vera, false altrimenti
     */
    private boolean gatherError(boolean condition, String message){
        if (condition){
            return true;
        } else {
            errors.add(message);
            return false;
        }
    }


    /**
     * Verifica se la stringa passata in input \u00E8 vuota o meno.
     *
     * @param value parametro da controllar
     * @return true se la strunga non è vuota o diversa da null, false altrimenti
     */
    private boolean required(String value){
        return value != null && !value.isBlank();
    }


    /**
     * Effettua il match tra la regex e il valore passato in input. Se si ha un mismastch,
     * viene aggiunta alla lista degli errori il messaggio passato in input.
     *
     * @param value parametro da controllare
     * @param regexp regex usata per il match
     * @param msg messaggio di errore
     * @return true nel caso in cui si ha un match tra il valore e la regex, false in cui si ha un mismatch
     */
    public boolean assertMatch(String value, Pattern regexp, String msg){
        String param = request.getParameter(value);
        boolean condition = required(param) && regexp.matcher(param).matches();
        return gatherError(condition, msg);
    }
}
