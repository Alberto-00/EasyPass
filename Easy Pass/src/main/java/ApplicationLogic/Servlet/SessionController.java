package ApplicationLogic.Servlet;
import ApplicationLogic.Utils.ServletLogic;
import Storage.Esito.Esito;
import Storage.Esito.EsitoDAO;
import Storage.PersonaleUnisa.Docente.Docente;
import Storage.PersonaleUnisa.Docente.DocenteDAO;
import Storage.SessioneDiValidazione.SessioneDiValidazione;
import Storage.SessioneDiValidazione.SessioneDiValidazioneDAO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.JDBCType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.text.ParseException;


@WebServlet(name = "SessionController", value = "/sessioneServlet/*")
public class SessionController extends ServletLogic {
    /*SessioneDiValidazione s;
    {
        try {
            s = new SessioneDiValidazione(true, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = getPath(request);
        SessioneDiValidazioneDAO sessioneDAO = new SessioneDiValidazioneDAO();
        HttpSession session=request.getSession();
        switch (path){
            case "/showQRCode":{
                System.out.println(request.getParameter("sessionId"));
                break;
            }
            case "/InvioGP":{
                request.getRequestDispatcher(view("StudenteGUI/InvioGP")).forward(request, response);
                break;
            }
            case "/AvvioSessione":{
                request.getRequestDispatcher(view("DocenteGUI/AvvioSessione")).forward(request, response);
                break;
            }
            case "/CreaNuovaSessione":{
                if(session!=null){
                    Docente docenteLoggato = (Docente) session.getAttribute("docenteSession");
                    if(docenteLoggato!=null){
                        SessioneDiValidazione s = null;
                        try {
                            s = docenteLoggato.avviaSessione();
                            sessioneDAO.doCreate(s);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        session.setAttribute("sessioneDiValidazione",s);
                        response.sendRedirect("ElencoEsiti?nStudents=" + request.getParameter("nStudents"));
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

            case "/ElencoEsiti":{
                if(session!=null) {
                    Docente docenteLoggato = (Docente) session.getAttribute("docenteSession");
                    if(docenteLoggato!=null) {
                        EsitoDAO esitoDAO = new EsitoDAO();
                        ArrayList<Esito> esiti = null;
                        SessioneDiValidazione s= (SessioneDiValidazione) session.getAttribute("sessioneDiValidazione");
                        if(s!=null){
                            try {
                                esiti = esitoDAO.doRetrieveAllBySession(s);
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                            request.setAttribute("esiti", esiti);
                            request.getRequestDispatcher(view("DocenteGUI/ElencoEsiti")).forward(request, response);

                        }
                        else{
                            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Errore. Riprovare.");
                        }
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
            case "/AnteprimaReport":{
                /*per prendere gli esiti bisogna prendere la sessioneDiValidazione dalla sessioneHttp
                  con nome "sessioneDiValidazione". Fatto ciò bisogna chiamare il metodo doRetrieveAllBySession
                  dell'EsitoDao, che ritorna un arrayList di esiti
                 */
                request.setAttribute("path", "report");
                request.getRequestDispatcher(view("DocenteGUI/AnteprimaReport")).forward(request, response);
                break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

            String path = getPath(request);

            switch (path) {
                case "/InvioGP": {
                    //TODO: Prendere la vera sessione a cui è collegato lo studente dal DB
                    SessioneDiValidazione s = new SessioneDiValidazione();
                    s.setqRCode("12345.jpg");
                    DocenteDAO docenteDAO= new DocenteDAO();
                    try {
                        s.setDocente(docenteDAO.doRetrieveByKey("aavella@unisa.it"));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    s.setInCorso(true);
                    Esito esitoValidazione = null;
                    try {
                        esitoValidazione = s.validaGreenPass(request.getParameter("dgc"));
                        esitoValidazione.setStringaGP("KTM");
                        System.out.println(esitoValidazione);
                        EsitoDAO edDao = new EsitoDAO();
                        edDao.doCreateWithoutReport(esitoValidazione);
                    } catch (ParseException | SQLException e) {
                        e.printStackTrace();
                    }
                    request.getRequestDispatcher(view("StudenteGUI/InvioEffettuato")).forward(request, response);
                    break;
                }
            }
        }
}

