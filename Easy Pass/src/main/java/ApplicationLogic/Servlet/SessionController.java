package ApplicationLogic.Servlet;

import ApplicationLogic.Utils.InvalidRequestException;
import ApplicationLogic.Utils.ServletLogic;
import ApplicationLogic.Utils.Validator.DocenteValidator;
import ApplicationLogic.Utils.Validator.StudenteValidator;
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

/**
 * La classe si occupa della gestione della Sessione di validazione e delle funzionalità
 * dello Studente come l'invio e la validazione dei Green Pass.
 *
 * La gestione della Sessione riguarda la creazione di una nuova Sessione di Validazione,
 * la visualizzazione a run-time e il salvataggio nel database degli esiti generati dopo
 * l'invio e la validazione del Green Pass, la terminazione della Sessione di Validazione
 * e, infine, l'anteprima del Report che il Docente può visualizzare e scaricare sulla
 * sua macchina.
 *
 * @version 0.1
 * @author Viviana Rinaldi, Alberto Montefusco, Gennaro Spina, Martina Mulino
 */
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
                /* Lo Studente viene portato alla pagina per l'invio del Green Pass specificando la
                 * Sessione di Validazione a cui è connesso.
                 * */
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

                /* Viene creata una nuova Sessione di validazione e viene controllato l'input sul numero di Studenti
                * inviato dal Docente.
                * */
                case "/CreaNuovaSessione" -> {
                    if (docente != null) {
                        validate(DocenteValidator.validateNumberOfStudents(request));
                        SessioneDiValidazione sessioneDiValidazione = docente.avviaSessione();
                        sessioneDAO.doCreate(sessioneDiValidazione);
                        session.setAttribute("sessioneDiValidazione", sessioneDiValidazione);
                        response.sendRedirect("ElencoEsiti?nStudents=" + request.getParameter("nStudents"));
                    } else
                        throw new InvalidRequestException("Non sei Autorizzato", List.of("Non sei Autorizzato"), HttpServletResponse.SC_FORBIDDEN);
                }

                /* Vengono presi gli esiti collegati alla Sessione di validazione dal database e mostrati al Docente. */
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

                /* Viene creato il Report con la lista degli Esiti associata in formato PDF,
                 * successivamente, tale Report creato è mostrato al Docente tramite un'anteprima
                 * e la Sessione di validazione viene invalidata.
                 * */
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
                            docente.terminaSessione(sessioneDiValidazione);
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

        /* Si prende dalla Sessione HTTP la Sessione di validazione e, se quest'ultima esiste, allora
         * si passa alla validazione del Green Pass dello Studente, creando e salvando l'esito
         * nel database solo nel caso in cui non sia stato già creato in precedenza nella stessa Sessione
         * di Validazione. In caso di errore vengono mostrati i rispettivi error message.
         * */
        try {
            if ("/InvioGP".equals(path)) {
                SessioneDiValidazione sessioneDiValidazione = (SessioneDiValidazione) session.getAttribute("sessioneSenzaRelazioni");
                if (sessioneDiValidazione != null){
                    Esito esitoValidazione = new Esito();
                    esitoValidazione.setStringaGP(request.getParameter("dgc"));
                    if (StudenteValidator.checkGP(esitoValidazione.getStringaGP()).compareTo("Green Pass inviato correttamente.") == 0) {
                        esitoValidazione = sessioneDiValidazione.validaGreenPass(esitoValidazione);
                        esitoValidazione.setStringaGP("");
                        EsitoDAO edDao = new EsitoDAO();
                        if (edDao.doRetrieveAllByPersonalData(esitoValidazione).isEmpty()) {
                            edDao.doCreateWithoutReport(esitoValidazione);
                            request.setAttribute("sessionId", sessioneDiValidazione.getQRCode().replaceAll("\\D+", ""));
                            request.getRequestDispatcher(view("StudenteGUI/InvioEffettuato")).forward(request, response);
                        } else {
                            throw new InvalidRequestException("Esito già inviato", List.of("Esito già inviato"), HttpServletResponse.SC_FORBIDDEN);
                        }
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


    /* Il metodo permette di costruire il nome del Report combinando
     * il nome e il cognome del Docente che lo ha generato, con l'ID
     * del Report stesso (es: "Report_NomeCognomeDocente_IDReport").
     * */
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

