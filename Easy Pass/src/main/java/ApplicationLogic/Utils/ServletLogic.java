package ApplicationLogic.Utils;

import ApplicationLogic.Utils.Validator.Validator;
import org.json.simple.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class ServletLogic extends HttpServlet {

    protected String getPath(HttpServletRequest req) {
        return req.getPathInfo() != null ? req.getPathInfo() : "/";
    }

    protected String view(String viewPath){
        String basePath = getServletContext().getInitParameter("basePath");
        String engine = getServletContext().getInitParameter("engine");
        return basePath + viewPath + engine;
    }

    protected void validate(Validator validator) throws InvalidRequestException{
        if(validator.hasErrors()){
            throw new InvalidRequestException("Validation Error", validator.getErrors(),
                    HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    protected void sendJson(HttpServletResponse response, JSONObject object) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.print(object.toString());
        writer.flush();
    }

    public static String getUploadPath(){
        return System.getenv("CATALINA_HOME") + File.separator + "Progetto_EasyPass" + File.separator;
    }

    public static String getDownloadPath(){
        return System.getProperty("user.home") + File.separator + "Downloads" + File.separator;
    }
}
