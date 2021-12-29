package ApplicationLogic.Servlet;

import ApplicationLogic.Utils.InvalidRequestException;
import ApplicationLogic.Utils.RequestValidator;
import Storage.Dipartimento.DipartimentoDAO;
import Storage.Formato.Formato;
import Storage.Formato.FormatoDAO;
import Storage.PersonaleUnisa.Direttore.DirettoreDiDipartimento;
import Storage.PersonaleUnisa.Direttore.DirettoreDiDipartimentoDAO;
import Storage.PersonaleUnisa.Direttore.DirettoreValidator;
import Storage.Report.ReportDAO;
import Storage.SessioneDiValidazione.SessioneDiValidazione;
import org.json.simple.JSONObject;



import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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

                    /*DirettoreDiDipartimentoDAO dirDao=new DirettoreDiDipartimentoDAO();
                    DirettoreDiDipartimento direttore=dirDao.doRetrieveByKeyWithRelations("username");
                    session.setAttribute("direttoreSession",direttore);*/

                    DirettoreDiDipartimento direttore=null;
                    direttore= (DirettoreDiDipartimento) session.getAttribute("direttoreSession");
                    if(direttore!=null) {
                        Formato formato = direttore.getDipartimento().getFormato();
                        String value = "";
                        if (formato.isNomeCognome()) value = "checked";
                        else value = "";
                        request.setAttribute("actualNomeCognome", value);
                        if (formato.isNumStudenti()) value = "checked";
                        else value = "";
                        request.setAttribute("actualNumStudenti", value);
                        if (formato.isData()) value = "checked";
                        else value = "";
                        request.setAttribute("actualDataDiNascita", value);
                        if (formato.isNumGPValidi()) value = "checked";
                        else value = "";
                        request.setAttribute("actualGPValidi", value);
                        if (formato.isNumGPNonValidi()) value = "checked";
                        else value = "";
                        request.setAttribute("actualGPNonValidi", value);

                        request.getRequestDispatcher(view("DirettoreDiDipartimentoGUI/GestioneFormato")).forward(request, response);
                    }
                    else{
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Accesso negato.");
                    }
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
                case "/salvaFormato": {
                    String anagrafica=request.getParameter("anagrafica");
                    String ddn=request.getParameter("ddn");
                    String numValidazioni=request.getParameter("numValidazioni");
                    String gpValidi=request.getParameter("gpValidi");
                    String gpNonValidi=request.getParameter("gpNonValidi");

                    String messaggi="";

                    DirettoreDiDipartimento direttore;
                    /*DirettoreDiDipartimentoDAO dirDao=new DirettoreDiDipartimentoDAO();
                    DirettoreDiDipartimento direttore=dirDao.doRetrieveByKeyWithRelations("username");
                    session.setAttribute("direttoreSession",direttore);*/

                    if(session!=null){
                        direttore= (DirettoreDiDipartimento) session.getAttribute("direttoreSession");
                        if(direttore!=null){
                            if(anagrafica==null && ddn==null && numValidazioni==null && gpValidi==null && gpNonValidi==null){
                                messaggi=messaggi+"Selezionare almeno un campo\n";
                            }
                            else if(ddn!=null && anagrafica==null){
                                messaggi=messaggi+"Impossibile salvare il formato! Se è selezionata la data deve essere selezionata anche l'anagrafica\n";
                            }
                            else{
                                FormatoDAO formatoDAO=new FormatoDAO();
                                Formato formato=formatoDAO.doRetrieveById(direttore.getDipartimento().getFormato().getId());
                                if(formato==null){
                                    formato=new Formato();
                                    formato.setId(direttore.getDipartimento().getCodice());
                                }

                                formato.setNomeCognome(anagrafica!=null);
                                formato.setData(ddn!=null);
                                formato.setNumStudenti(numValidazioni!=null);
                                formato.setNumGPValidi(gpValidi!=null);
                                formato.setNumGPNonValidi(gpNonValidi!=null);
                                formatoDAO.doUpdate(formato);
                                messaggi="Il formato è stato salvato correttamente";

                                String value="";
                                if(formato.isNomeCognome()) value="checked"; else value="";
                                request.setAttribute("actualNomeCognome", value);
                                if(formato.isNumStudenti()) value="checked"; else value="";
                                request.setAttribute("actualNumStudenti", value);
                                if(formato.isData()) value="checked"; else value="";
                                request.setAttribute("actualDataDiNascita", value);
                                if(formato.isNumGPValidi()) value="checked"; else value="";
                                request.setAttribute("actualGPValidi", value);
                                if(formato.isNumGPNonValidi()) value="checked"; else value="";
                                request.setAttribute("actualGPNonValidi", value);

                            }

                            request.setAttribute("messaggiUtente",messaggi);
                            request.getRequestDispatcher(view("DirettoreDiDipartimentoGUI/GestioneFormato")).forward(request, response);
                        }
                        else{
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Accesso negato.");
                        }
                    }
                    else{
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Accesso negato.");
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
