package ApplicationLogic.Utils;

import ApplicationLogic.Utils.Validator.Validator;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * La classe gestisce la logica di tutte le {@code Servlet} fornendo metodi protetti
 * per validare parametri, prendere i path dalla request e inviare {@code JSON}
 */
public class ServletLogic extends HttpServlet {

    /**
     * &Egrave; usato dalla {@code Servlet} per prendere il path
     * della pagina web dalla request
     *
     * @param req request HTTP
     * @return path
     */
    protected String getPath(HttpServletRequest req) {
        return req.getPathInfo() != null ? req.getPathInfo() : "/";
    }

    /**
     * Restituisce il path completo di un file {@code jsp}
     *
     * @param viewPath path parziale ({@code package/file.jsp})
     * @return path alla {@code jsp}
     */
    protected String view(String viewPath){
        String basePath = getServletContext().getInitParameter("basePath");
        String engine = getServletContext().getInitParameter("engine");
        return basePath + viewPath + engine;
    }

    /**
     * &Egrave; utilizzato per la validazione back-end dei parametri.
     * @param validator Validator
     */
    protected void validate(Validator validator) throws InvalidRequestException{
        if(validator.hasErrors()){
            throw new InvalidRequestException("Validation Error", validator.getErrors(),
                    HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    protected HttpServletRequest validateSignup(Validator validator, HttpServletRequest request) throws InvalidRequestException, ServletException, IOException {
        if(validator.hasErrors())
            request.setAttribute("errorMsg", validator.getErrors().get(0));
        return request;
    }

    /**
     * Invia un JSON alla response.
     *
     * @param response response HTTP
     * @param object oggetto JSON
     */
    protected void sendJson(HttpServletResponse response, JSONObject object) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.print(object.toString());
        writer.flush();
    }

    /**
     * Ritorna il path in cui sono localizzate le risorse
     * che salva o che usa EasyPass su {@code Tomcat}
     * @return path
     */
    public static String getUploadPath(){
        return System.getenv("CATALINA_HOME") + File.separator + "webapps" + File.separator+ "ROOT" +
                File.separator + "Progetto_EasyPass" + File.separator;
    }
}
