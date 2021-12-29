package ApplicationLogic.Servlet;
import ApplicationLogic.Utils.RequestValidator;
import Storage.SessioneDiValidazione.SessioneDiValidazione;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "SessioneController", value = "/sessioneServlet/*")
public class SessioneController extends RequestValidator {
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
                request.setAttribute("sessionId", request.getParameter("sessionId"));
                System.out.println(request.getParameter("sessionId"));
                System.out.println(request.getAttribute("sessionId"));
                request.getRequestDispatcher(view("StudenteGUI/InvioGP")).forward(request, response);
                break;
            }
            case "/AvvioSessione":{
                request.getRequestDispatcher("DocenteGUI/AvvioSessione").forward(request, response);
            }
            case "/ElencoEsiti":{
                request.getRequestDispatcher(view("DocenteGUI/ElencoEsiti")).forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String path = getPath(request);
            switch (path) {
                case "/InvioEffettuato": {
                    request.getRequestDispatcher(view("StudenteGUI/InvioEffettuato")).forward(request, response);
                    break;
                }
            }
        }

}

