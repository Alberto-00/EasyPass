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

/**
 * La classe si occupa di gestire la logica per eseguire le funzioni di Login, Registrazione e Logout
 * da parte di un Direttore di Dipartimento o di un Docente. La classe permette le funzionalit&agrave; di Registrazione,
 * di Login e di Logout a un Docente, mentre, il Direttore può eseguire solo le funzionalità di Login
 * e di Logout. Per garantire la sicurezza tutti gli input sono validati.
 *
 * @author Alberto Montefusco
 * @version 0.1
 */
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
                /* Vengono prima validati gli input della funzione di Login: in caso di successo il
                * Direttore o il Docente viene rimandato alla rispettiva pagina iniziale,
                * altrimenti verrà inviato un messaggio di errore.
                * */
                case "/" -> {
                    validate(DirettoreValidator.validateSignIn(request));
                    validate(DocenteValidator.validateSignIn(request));

                    DirettoreDiDipartimentoDAO direttoreDAO = new DirettoreDiDipartimentoDAO();
                    DocenteDAO docenteDAO = new DocenteDAO();

                    DirettoreDiDipartimento direttore = new DirettoreDiDipartimento();
                    direttore.setUsername(request.getParameter("email"));
                    direttore.setPassword(request.getParameter("password"));

                    Docente docente = new Docente();
                    docente.setUsername(request.getParameter("email"));
                    docente.setPassword(request.getParameter("password"));

                    if (direttoreDAO.checkUserAndPassw(direttore)) {
                        session.setAttribute("direttoreSession", direttoreDAO.doRetrieveByKeyWithRelations(direttore.getUsername()));
                        response.sendRedirect("../reportServlet/HomePage");
                    } else if (docenteDAO.checkUserAndPassw(docente)) {
                        session.setAttribute("docenteSession", docenteDAO.doRetrieveByKeyWithRelations(docente.getUsername()));
                        response.sendRedirect("../sessioneServlet/AvvioSessione");
                    } else {
                        request.setAttribute("msg", "Credenziali errate.");
                        request.getRequestDispatcher(view("AutenticazioneGUI/Autenticazione")).forward(request, response);
                    }
                }

                /* Vengono prima validati gli input della funzione di Registrazione: in caso di
                 * successo le informazioni del Docente saranno memorizzate nel database e il Docente
                 * verrà rimandato alla sua pagina iniziale, altrimenti verrà inviato un messaggio di errore.
                 * */
                case "/registrazione" -> {
                    request = validateSignup(DocenteValidator.validateSignUp(request), request);

                    if (request.getAttribute("errorMsg") != null){
                        request.getRequestDispatcher(view("AutenticazioneGUI/Autenticazione")).forward(request, response);
                        break;
                    }

                    Docente docente = new Docente(upperCaseFirstLetter(request.getParameter("nome")),
                            request.getParameter("cognome"), request.getParameter("email2"),
                            request.getParameter("password2"), new Dipartimento());

                    docente.getDipartimento().setCodice(request.getParameter("dipartimento"));
                    DocenteDAO docenteDAO = new DocenteDAO();
                    DirettoreDiDipartimentoDAO dirDAO = new DirettoreDiDipartimentoDAO();

                    if (docenteDAO.doRetrieveByKey(docente.getUsername()) == null &&
                            dirDAO.doRetrieveByKey(request.getParameter("email2")) == null) {
                        docenteDAO.doCreate(docente);
                        request.getRequestDispatcher(view("DocenteGUI/AvvioSessione")).forward(request, response);
                    } else {
                        request.setAttribute("msg", "Email già registrata.");
                        request.getRequestDispatcher(view("AutenticazioneGUI/Autenticazione")).forward(request, response);
                    }
                }

                /* Il Docente o il Direttore di Dipartimento effettuano il Logout e vengono rimossi
                * dalla sessione HTTP.
                * */
                case "/logout" -> {
                    DirettoreDiDipartimento direttoreSession = (DirettoreDiDipartimento) session.getAttribute("direttoreSession");
                    Docente docente = (Docente) session.getAttribute("docenteSession");

                    if (direttoreSession != null) {
                        session.removeAttribute("direttoreSession");
                        session.invalidate();
                        response.sendRedirect("./");
                    } else if (docente != null) {
                        session.removeAttribute("docenteSession");
                        session.invalidate();
                        response.sendRedirect("./");
                    }
                }
            }
        } catch (InvalidRequestException e) {
            e.printStackTrace();
            e.handle(request, response);
        }
    }

    /**
     * Permette di salvare nel Servlet Context la lista di {@code Dipartimenti}
     * presi dal database.
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        DipartimentoDAO dipartimentoDAO = new DipartimentoDAO();
        getServletContext().setAttribute("dipartimenti", dipartimentoDAO.doRetrieveAll());
    }


    /* Il metodo verifica se la stringa passata possiede delle sottostringhe
     * separate da uno spazio bianco e, per ognuna di loro, il primo carattere
     * è impostato in maiuscolo.
     * */
    private String upperCaseFirstLetter(String str) {
        StringBuilder out = new StringBuilder();
        String[] token = str.split(" ");

        for (int i = 0; i < token.length; i++){
            if (i + 1 < token.length)
                out.append(token[i].substring(0, 1).toUpperCase()).append(token[i].substring(1)).append(" ");
            else out.append(token[i].substring(0, 1).toUpperCase()).append(token[i].substring(1));
        }
        return out.toString();
    }
}
