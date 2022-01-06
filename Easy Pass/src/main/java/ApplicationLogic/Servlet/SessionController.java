package ApplicationLogic.Servlet;

import ApplicationLogic.Utils.InvalidRequestException;
import ApplicationLogic.Utils.ServletLogic;
import ApplicationLogic.Utils.Validator.NumeroStudenti;
import Storage.Esito.Esito;
import Storage.Esito.EsitoDAO;
import Storage.PersonaleUnisa.Docente.Docente;
import Storage.PersonaleUnisa.Docente.DocenteDAO;
import Storage.SessioneDiValidazione.SessioneDiValidazione;
import Storage.SessioneDiValidazione.SessioneDiValidazioneDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.text.ParseException;
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
                    Integer sessionId;
                    if (request.getParameter("sessionId") != null && request.getParameter("sessionId").length() == 5) {
                        sessionId = Integer.parseInt(request.getParameter("sessionId"));
                    } else {
                        throw new InvalidRequestException("Inserisci una sessione", List.of("Inserisci una sessione"), HttpServletResponse.SC_FORBIDDEN);
                    }

                    SessioneDiValidazioneDAO seDAO = new SessioneDiValidazioneDAO();
                    SessioneDiValidazione s = seDAO.doRetrieveById(sessionId);
                    if (s != null) {
                        session.setAttribute("sessioneDiValidazione", s);
                        request.getRequestDispatcher(view("StudenteGUI/InvioGP")).forward(request, response);
                    } else {
                        throw new InvalidRequestException("Sessione non esistente", List.of("Sessione non esistente"), HttpServletResponse.SC_FORBIDDEN);
                    }

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
                /*per prendere gli esiti bisogna prendere la sessioneDiValidazione dalla sessioneHttp
                  con nome "sessioneDiValidazione". Fatto ciò bisogna chiamare il metodo doRetrieveAllBySession
                  dell'EsitoDao, che ritorna un arrayList di esiti
                 */
                    request.setAttribute("path", "report");
                    request.getRequestDispatcher(view("DocenteGUI/AnteprimaReport")).forward(request, response);
                }
            }
        } catch (InvalidRequestException e) {
            e.printStackTrace();
            e.handle(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = getPath(request);
        HttpSession session = request.getSession();
        Docente docente = (Docente) request.getSession().getAttribute("docenteSession");

        try {
            if ("/InvioGP".equals(path)) {
                SessioneDiValidazione sessioneDiValidazione = (SessioneDiValidazione) session.getAttribute("sessioneDiValidazione");
                Esito esitoValidazione = sessioneDiValidazione.validaGreenPass(request.getParameter("dgc"));
                esitoValidazione.setStringaGP("KTM");
                EsitoDAO edDao = new EsitoDAO();
                edDao.doCreateWithoutReport(esitoValidazione);
                request.setAttribute("sessionId", sessioneDiValidazione.getqRCode().replaceAll("\\D+",""));
                request.getRequestDispatcher(view("StudenteGUI/InvioEffettuato")).forward(request, response);

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

