package ApplicationLogic.Servlet;

import ApplicationLogic.Utils.InvalidRequestException;
import ApplicationLogic.Utils.RequestValidator;
import Storage.Dipartimento.Dipartimento;
import Storage.Dipartimento.DipartimentoDAO;
import Storage.PersonaleUnisa.Direttore.DirettoreDiDipartimento;
import Storage.PersonaleUnisa.Direttore.DirettoreDiDipartimentoDAO;
import Storage.PersonaleUnisa.Direttore.DirettoreValidator;
import Storage.PersonaleUnisa.Docente.Docente;
import Storage.PersonaleUnisa.Docente.DocenteDAO;
import Storage.PersonaleUnisa.Docente.DocenteValidator;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "AccessController", value = "/accessServlet/*")
public class AccessController extends RequestValidator {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = getPath(request);
        if ("/Autenticazione".equals(path)) {
            try {
                AccessController.riempiDipartimento(request);
                request.getRequestDispatcher(view("AutenticazioneGUI/Autenticazione")).forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String path = getPath(request);
        try {
            switch (path) {
                case "/Autenticazione": {
                    validate(DirettoreValidator.validateSigin(request));
                    validate(DocenteValidator.validateSigin(request));

                    DirettoreDiDipartimentoDAO direttoreDAO = new DirettoreDiDipartimentoDAO();
                    DocenteDAO docenteDAO = new DocenteDAO();
                    DirettoreDiDipartimento direttore = direttoreDAO.doRetrieveByKey(request.getParameter("email"));
                    Docente docente = docenteDAO.doRetrieveByKey(request.getParameter("email"));

                    if (direttore != null) {
                        session.setAttribute("direttoreSession", direttore);
                        response.sendRedirect("../reportServlet/HomePage");
                    } else if (docente != null){
                        session.setAttribute("docenteSession", docente);
                        response.sendRedirect("../sessioneServlet/AvvioSessione");
                    } else {
                        AccessController.riempiDipartimento(request);
                        request.setAttribute("msg", "Credenziali errate.");
                        request.getRequestDispatcher(view("AutenticazioneGUI/Autenticazione")).forward(request, response);
                    }
                    break;
                }

                case "/Registrazione": {
                    validate(DocenteValidator.validateSigup(request));
                    Dipartimento dipartimento = new Dipartimento();
                    dipartimento.setCodice(request.getParameter("dipartimento"));
                    Docente docente = new Docente(request.getParameter("nome"), request.getParameter("cognome"),
                            request.getParameter("email2"), request.getParameter("password2"), dipartimento, null);
                    DocenteDAO docenteDAO = new DocenteDAO();
                    if (docenteDAO.doRetrieveByKey(docente.getUsername()) == null){
                        docenteDAO.doCreate(docente);
                        request.getRequestDispatcher(view("DocenteGUI/AvvioSessione")).forward(request, response);
                    } else {
                        request.setAttribute("msg2", "Utente già registrato.");
                        request.getRequestDispatcher(view("AutenticazioneGUI/Autenticazione")).forward(request, response);
                    }
                    break;
                }

                case "/Logout":{
                    DirettoreDiDipartimento direttoreSession = (DirettoreDiDipartimento) session.getAttribute("direttoreSession");
                    Docente docente = (Docente) session.getAttribute("docenteSession");

                    if (direttoreSession != null) {
                        session.removeAttribute("direttoreSession");
                        session.invalidate();
                        response.sendRedirect("./Autenticazione");
                    }
                    else if (docente != null){
                        session.removeAttribute("docenteSession");
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

    private static void riempiDipartimento(HttpServletRequest request) throws SQLException {
        DipartimentoDAO dipartimentoDAO = new DipartimentoDAO();
        List<Dipartimento> dipartimenti = dipartimentoDAO.doRetrieveAll();
        request.setAttribute("dipartimenti", dipartimenti);
    }
}
