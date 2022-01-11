package ApplicationLogic.Utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * La classe crea degli errori associando, a ognuno di essi, dei messaggi personalizzati
 * che saranno visualizzati quando la request non è valida.
 */
public class InvalidRequestException extends Exception{

    private final List<String> errors;
    private final int errorCode;

    /**
     * @param message messaggio di errore
     * @param errors lista di errore
     * @param errorCode codice di errore
     */
    public InvalidRequestException(String message, List<String> errors, int errorCode){
        super(message);
        this.errors = errors;
        this.errorCode = errorCode;
    }

    /**
     * Verifica se l'errore &egrave; di tipo {@code SC_BAD_REQUEST}:
     * <ul>
     *     <li>in caso di successo, viene creato un oggetto {@code Alert} di default,</li>
     *     <li>altrimenti crea il nuovo tipo di errore.</li>
     * </ul>
     * @param request request HTTP
     * @param response response HTTP
     */
    public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        if (errorCode == HttpServletResponse.SC_BAD_REQUEST) {
            request.setAttribute("alert", new Alert(errors, "danger"));
            String backPath = (String) request.getAttribute("back");
            response.setStatus(errorCode);
            request.getRequestDispatcher(backPath).forward(request, response);
        } else
            response.sendError(errorCode, errors.get(0));
    }

    public List<String> getErrors(){
        return errors;
    }
}
