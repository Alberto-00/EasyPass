package ApplicationLogic.Servlet;

import ApplicationLogic.Utils.InvalidRequestException;
import ApplicationLogic.Utils.RequestValidator;
import Storage.Dipartimento.Dipartimento;
import Storage.PersonaleUnisa.Direttore.DirettoreDiDipartimento;
import Storage.PersonaleUnisa.Direttore.DirettoreDiDipartimentoDAO;
import Storage.PersonaleUnisa.Direttore.DirettoreValidator;
import Storage.PersonaleUnisa.PersonaleUnisa;
import Storage.Report.ReportDAO;
import Storage.SessioneDiValidazione.SessioneDiValidazione;
import Storage.SessioneDiValidazione.SessioneDiValidazioneDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ReportController", value = "/reportServlet/*")
public class ReportController extends RequestValidator {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = getPath(request);
        SessioneDiValidazione s = new SessioneDiValidazione(true, null);
        HttpSession session = request.getSession();
        try {
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
                    DirettoreDiDipartimento direttoreSession = (DirettoreDiDipartimento) session.getAttribute("direttoreSession");
                    ReportDAO reportDAO = new ReportDAO();
                    request.setAttribute("hashMap", reportDAO.doRetrieveDocByReport(direttoreSession.getDipartimento().getCodice()));
                    request.getRequestDispatcher(view("DirettoreDiDipartimentoGUI/GestioneReport")).forward(request, response);
                    break;
                }
                case "/AvvioSessione":{
                    request.getRequestDispatcher("DocenteGUI/AvvioSessione").forward(request, response);
                }
                case "/ElencoEsiti":{
                    request.getRequestDispatcher(view("DocenteGUI/ElencoEsiti")).forward(request, response);
                }
                case "/Autenticazione":{
                    request.getRequestDispatcher(view("AutenticazioneGUI/Autenticazione")).forward(request, response);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            String path = getPath(request);
            switch (path) {
                case "/Autenticazione": {
                    validate(DirettoreValidator.validateSigin(request));
                    DirettoreDiDipartimento tmpDirettore = new DirettoreDiDipartimento();
                    tmpDirettore.setUsername(request.getParameter("email"));
                    tmpDirettore.setPassword(request.getParameter("password"));
                    DirettoreDiDipartimentoDAO direttoreDAO = new DirettoreDiDipartimentoDAO();
                    DirettoreDiDipartimento direttore = direttoreDAO.doRetrieveByKey(tmpDirettore.getUsername());

                    if (direttore != null) {
                        session.setAttribute("direttoreSession", direttore);
                        response.sendRedirect("./HomePage");
                    } else {
                        request.setAttribute("msg", "Credenziali errate.");
                        request.getRequestDispatcher(view("AutenticazioneGUI/Autenticazione")).forward(request, response);
                    }
                    break;
                }
                case "/Registrazione": {
                    request.getRequestDispatcher(view("AutenticazioneGUI/HomePage")).forward(request, response);
                    break;
                }
                case "Logout":{
                    DirettoreDiDipartimento direttoreSession = (DirettoreDiDipartimento) session.getAttribute("direttoreSession");
                    if (direttoreSession != null) {
                        session.removeAttribute("direttoreSession");
                        session.invalidate();
                        response.sendRedirect("./Autenticazione");
                    }
                    break;
                }
            }
        } catch (InvalidRequestException e) {
            e.printStackTrace();
            e.handle(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
