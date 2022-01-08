package ApplicationLogic.Servlet;

import ApplicationLogic.Utils.InvalidRequestException;
import ApplicationLogic.Utils.ServletLogic;
import ApplicationLogic.Utils.Validator.NumeroStudenti;
import Storage.Dipartimento.DipartimentoDAO;
import Storage.Esito.Esito;
import Storage.Esito.EsitoDAO;
import Storage.PersonaleUnisa.Docente.Docente;
import Storage.Report.Report;
import Storage.Report.ReportDAO;
import Storage.SessioneDiValidazione.SessioneDiValidazione;
import Storage.SessioneDiValidazione.SessioneDiValidazioneDAO;
import com.itextpdf.text.DocumentException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@WebServlet(name = "SessionController", value = "/sessioneServlet/*")
public class SessionController extends ServletLogic {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = getPath(request);
        Docente docente = (Docente) request.getSession().getAttribute("docenteSession");
        SessioneDiValidazioneDAO sessioneDAO = new SessioneDiValidazioneDAO();
        HttpSession session = request.getSession();

        try {
            switch (path) {
                case "/InvioGP" -> {
                    int sessionId;
                    if (request.getParameter("sessionId") != null && request.getParameter("sessionId").length() == 5) {
                        sessionId = Integer.parseInt(request.getParameter("sessionId"));
                    } else
                        throw new InvalidRequestException("Inserisci una sessione", List.of("Inserisci una sessione"), HttpServletResponse.SC_NOT_FOUND);

                    SessioneDiValidazioneDAO seDAO = new SessioneDiValidazioneDAO();
                    SessioneDiValidazione sessioneDiValidazione = seDAO.doRetrieveById(sessionId);
                    if (sessioneDiValidazione != null && sessioneDiValidazione.isInCorso()) {
                        session.setAttribute("sessioneSenzaRelazioni", sessioneDiValidazione);
                        request.getRequestDispatcher(view("StudenteGUI/InvioGP")).forward(request, response);
                    } else
                        throw new InvalidRequestException("Sessione non esistente", List.of("Sessione non esistente"), HttpServletResponse.SC_NOT_FOUND);
                }

                case "/AvvioSessione" -> {
                    if (docente != null)
                        request.getRequestDispatcher(view("DocenteGUI/AvvioSessione")).forward(request, response);
                    else
                        throw new InvalidRequestException("Non sei Autorizzato", List.of("Non sei Autorizzato"), HttpServletResponse.SC_FORBIDDEN);
                }

                case "/CreaNuovaSessione" -> {
                    if (docente != null) {
                        validate(NumeroStudenti.validateNumberOfStudents(request));
                        SessioneDiValidazione sessioneDiValidazione = docente.avviaSessione();
                        sessioneDAO.doCreate(sessioneDiValidazione);
                        session.setAttribute("sessioneDiValidazione", sessioneDiValidazione);
                        response.sendRedirect("ElencoEsiti?nStudents=" + request.getParameter("nStudents"));
                    } else
                        throw new InvalidRequestException("Non sei Autorizzato", List.of("Non sei Autorizzato"), HttpServletResponse.SC_FORBIDDEN);
                }

                case "/ElencoEsiti" -> {
                    if (docente != null) {
                        EsitoDAO esitoDAO = new EsitoDAO();
                        SessioneDiValidazione sessioneDiValidazione = (SessioneDiValidazione) session.getAttribute("sessioneDiValidazione");
                        if (sessioneDiValidazione != null) {
                            ArrayList<Esito> esiti = esitoDAO.doRetrieveAllBySession(sessioneDiValidazione);
                            request.setAttribute("esiti", esiti);
                            request.getRequestDispatcher(view("DocenteGUI/ElencoEsiti")).forward(request, response);
                        } else {
                            throw new InvalidRequestException("Non sei Autorizzato", List.of("Non sei Autorizzato"), HttpServletResponse.SC_FORBIDDEN);
                        }
                    } else
                        throw new InvalidRequestException("Non sei Autorizzato", List.of("Non sei Autorizzato"), HttpServletResponse.SC_FORBIDDEN);
                }

                case "/AnteprimaReport" -> {
                    if (docente != null){
                        SessioneDiValidazione sessioneDiValidazione = (SessioneDiValidazione)
                                request.getSession().getAttribute("sessioneDiValidazione");
                        if (sessioneDiValidazione != null){
                            Report report = new Report(new Date(System.currentTimeMillis()),
                                    new Date(System.currentTimeMillis()), "",
                                    docente.getDipartimento(), docente);

                            DipartimentoDAO dipartimentoDAO = new DipartimentoDAO();
                            report.setDip(dipartimentoDAO.doRetrieveByKeyWithRelations(report.getDip().getCodice()));
                            report.setPathFile("");

                            ReportDAO reportDAO = new ReportDAO();
                            report.setId(reportDAO.doCreate(report));
                            report.setPathFile( "Report_" + builderPathReport(docente) + "_" + report.getId() + ".pdf");
                            reportDAO.doUpdatePath(report);

                            EsitoDAO esitoDAO = new EsitoDAO();
                            ArrayList<Esito> esiti = esitoDAO.doRetrieveAllBySession(sessioneDiValidazione);
                            for (Esito esi : esiti){
                                esi.setReport(report);
                                esitoDAO.doUpdateOnlyReport(esi);
                            }
                            SessioneDiValidazioneDAO sessionDAO = new SessioneDiValidazioneDAO();
                            sessioneDiValidazione.setInCorso(false);
                            sessionDAO.doUpdate(sessioneDiValidazione);
                            session.removeAttribute("sessioneDiValidazione");

                            report.creaFile(esiti);
                            request.setAttribute("report", report);
                            request.getRequestDispatcher(view("DocenteGUI/AnteprimaReport")).forward(request, response);
                        } else
                            throw new InvalidRequestException("Sessione non trovata", List.of("Sessione non trovata"), HttpServletResponse.SC_NOT_FOUND);
                    } else
                        throw new InvalidRequestException("Non sei Autorizzato", List.of("Non sei Autorizzato"), HttpServletResponse.SC_FORBIDDEN);
                }
            }
        } catch (InvalidRequestException e) {
            e.printStackTrace();
            e.handle(request,response);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = getPath(request);
        HttpSession session = request.getSession();

        try {
            if ("/InvioGP".equals(path)) {
                SessioneDiValidazione sessioneDiValidazione = (SessioneDiValidazione) session.getAttribute("sessioneSenzaRelazioni");
                if (sessioneDiValidazione != null){
                    Esito esitoValidazione = sessioneDiValidazione.validaGreenPass(request.getParameter("dgc"));
                    esitoValidazione.setStringaGP("");
                    EsitoDAO edDao = new EsitoDAO();
                    if (edDao.doRetrieveAllByPersonalData(esitoValidazione).isEmpty()){
                        edDao.doCreateWithoutReport(esitoValidazione);
                        request.setAttribute("sessionId", sessioneDiValidazione.getqRCode().replaceAll("\\D+",""));
                        request.getRequestDispatcher(view("StudenteGUI/InvioEffettuato")).forward(request, response);
                    } else {
                        throw new InvalidRequestException("Esito già inviato", List.of("Esito già inviato"), HttpServletResponse.SC_FORBIDDEN);
                    }

                } else
                    throw new InvalidRequestException("Sessione non esistente", List.of("Sessione non esistente"), HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (InvalidRequestException e) {
            e.printStackTrace();
            e.handle(request, response);
        }
    }

    private String builderPathReport(Docente doc){
        String[] str = (doc.getNome() + " " + doc.getCognome()).split(" ");
        StringBuilder out = new StringBuilder();

        for (int i = 0; i < str.length; i++){
            if (i < str.length - 1)
                out.append(str[i]).append("_");
            else out.append(str[i]);
        }
        return out.toString();
    }
}

