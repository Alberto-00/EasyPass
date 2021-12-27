package ApplicationLogic.Servlet;

import ApplicationLogic.Utils.RequestValidator;
import Storage.SessioneDiValidazione.SessioneDiValidazione;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ReportController", value = "/direttoreServlet/*")
public class ReportController extends RequestValidator {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = getPath(request);
        SessioneDiValidazione s = new SessioneDiValidazione(true, null);

        switch (path){
            case "/HomePage":{
                request.getRequestDispatcher(view("DirettoreDiDipartimentoGUI/HomePage")).forward(request, response);
                break;
            }
            case "/GestioneFormato":{
                request.getRequestDispatcher(view("DirettoreDiDipartimentoGUI/GestioneFormato")).forward(request, response);
                break;
            }
            case "/GestioneReport":{
                request.getRequestDispatcher(view("DirettoreDiDipartimentoGUI/GestioneReport")).forward(request, response);
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

    }
}
