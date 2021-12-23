package ApplicationLogic.Utils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

public class RequestValidator extends HttpServlet {

    protected String getPath(HttpServletRequest req) {
        return req.getPathInfo() != null ? req.getPathInfo() : "/";
    }

    protected String view(String viewPath){
        String basePath = getServletContext().getInitParameter("basePath");
        String engine = getServletContext().getInitParameter("engine");
        return basePath + viewPath + engine;
    }
}
