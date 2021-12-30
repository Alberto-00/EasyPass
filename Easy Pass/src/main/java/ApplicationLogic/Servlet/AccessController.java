package ApplicationLogic.Servlet;

import ApplicationLogic.Utils.InvalidRequestException;
import ApplicationLogic.Utils.ServletLogic;
import Storage.Dipartimento.Dipartimento;
import Storage.Dipartimento.DipartimentoDAO;
import Storage.PersonaleUnisa.Direttore.DirettoreDiDipartimento;
import Storage.PersonaleUnisa.Direttore.DirettoreDiDipartimentoDAO;
import ApplicationLogic.Utils.Validator.DirettoreValidator;
import Storage.PersonaleUnisa.Docente.Docente;
import Storage.PersonaleUnisa.Docente.DocenteDAO;
import ApplicationLogic.Utils.Validator.DocenteValidator;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AccessController", value = "/autenticazione/*", loadOnStartup = 0)
public class AccessController extends ServletLogic {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = getPath(request);
        if ("/".equals(path)) {
            request.getRequestDispatcher(view("AutenticazioneGUI/Autenticazione")).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String path = getPath(request);
        try {
            switch (path) {
                case "/": {
                    validate(DirettoreValidator.validateSigin(request));
                    validate(DocenteValidator.validateSigin(request));

                    DirettoreDiDipartimentoDAO direttoreDAO = new DirettoreDiDipartimentoDAO();
                    DocenteDAO docenteDAO = new DocenteDAO();
                    DirettoreDiDipartimento direttore = direttoreDAO.doRetrieveByKeyWithRelations(request.getParameter("email"));
                    Docente docente = docenteDAO.doRetrieveByKey(request.getParameter("email"));

                    if (direttore != null) {
                        session.setAttribute("direttoreSession", direttore);
                        response.sendRedirect("../reportServlet/HomePage");
                    } else if (docente != null){
                        session.setAttribute("docenteSession", docente);
                        response.sendRedirect("../sessioneServlet/AvvioSessione");
                    } else {
                        request.setAttribute("msg", "Credenziali errate.");
                        request.getRequestDispatcher(view("AutenticazioneGUI/Autenticazione")).forward(request, response);
                    }
                    break;
                }

                case "/registrazione": {
                    validate(DocenteValidator.validateSigup(request));

                    Docente docente = new Docente(request.getParameter("nome"), request.getParameter("cognome"),
                            request.getParameter("email2"), request.getParameter("password2"), new Dipartimento());

                    docente.getDipartimento().setCodice(request.getParameter("dipartimento"));
                    DocenteDAO docenteDAO = new DocenteDAO();

                    if (docenteDAO.doRetrieveByKey(docente.getUsername()) == null){
                        docenteDAO.doCreate(docente);
                        request.getRequestDispatcher(view("DocenteGUI/AvvioSessione")).forward(request, response);
                    } else {
                        request.setAttribute("msg2", "Docente già registrato.");
                        request.getRequestDispatcher(view("AutenticazioneGUI/Autenticazione")).forward(request, response);
                    }
                    break;
                }

                case "/logout":{
                    DirettoreDiDipartimento direttoreSession = (DirettoreDiDipartimento) session.getAttribute("direttoreSession");
                    Docente docente = (Docente) session.getAttribute("docenteSession");

                    if (direttoreSession != null) {
                        session.removeAttribute("direttoreSession");
                        session.invalidate();
                        response.sendRedirect("./");
                    }
                    else if (docente != null){
                        session.removeAttribute("docenteSession");
                        session.invalidate();
                        response.sendRedirect("./");
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

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        DipartimentoDAO dipartimentoDAO = new DipartimentoDAO();
        try {
            getServletContext().setAttribute("dipartimenti", dipartimentoDAO.doRetrieveAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
