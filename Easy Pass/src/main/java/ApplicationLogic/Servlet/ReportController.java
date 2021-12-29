package ApplicationLogic.Servlet;

import ApplicationLogic.Utils.InvalidRequestException;
import ApplicationLogic.Utils.RequestValidator;
import Storage.PersonaleUnisa.Direttore.DirettoreDiDipartimento;
import Storage.Report.ReportDAO;
import Storage.SessioneDiValidazione.SessioneDiValidazione;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ReportController", value = "/reportServlet/*")
public class ReportController extends RequestValidator {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = getPath(request);
        SessioneDiValidazione s = new SessioneDiValidazione(true, null);
        HttpSession session = request.getSession();
        DirettoreDiDipartimento direttore = (DirettoreDiDipartimento) session.getAttribute("direttoreSession");

        try {
            switch (path){
                case "/HomePage":{
                    if (direttore != null)
                        request.getRequestDispatcher(view("DirettoreDiDipartimentoGUI/HomePage")).forward(request, response);
                    else
                        throw new InvalidRequestException("Non sei Autorizzato", List.of("Non sei Autorizzato"), HttpServletResponse.SC_FORBIDDEN);
                    break;
                }
                case "/GestioneFormato":{
                    request.getRequestDispatcher(view("DirettoreDiDipartimentoGUI/GestioneFormato")).forward(request, response);
                    break;
                }
                case "/GestioneReport":{
                    if (direttore != null){
                        ReportDAO reportDAO = new ReportDAO();
                        request.setAttribute("hashMap", reportDAO.doRetrieveDocByReport(direttore.getDipartimento().getCodice()));
                        request.getRequestDispatcher(view("DirettoreDiDipartimentoGUI/GestioneReport")).forward(request, response);
                    } else
                        throw new InvalidRequestException("Non sei Autorizzato", List.of("Non sei Autorizzato"), HttpServletResponse.SC_FORBIDDEN);
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InvalidRequestException e) {
            e.printStackTrace();
            e.handle(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
