package ApplicationLogic.Servlet;
import ApplicationLogic.Utils.RequestValidator;
import Storage.SessioneDiValidazione.SessioneDiValidazione;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "SessionController", value = "/sessioneServlet/*")
public class SessionController extends RequestValidator {
    SessioneDiValidazione s;

    {
        try {
            s = new SessioneDiValidazione(true, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = getPath(request);
        switch (path){
            case "/showQRCode":{
                System.out.println("sono nella sevlet giusta");
                System.out.println(request.getParameter("sessionId"));
                break;
            }
            case "/InvioGP":{
                request.getRequestDispatcher(view("StudenteGUI/InvioGP")).forward(request, response);
                break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}

