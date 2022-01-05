package ApplicationLogic.Servlet;
import ApplicationLogic.Utils.ServletLogic;
import Storage.Esito.Esito;
import Storage.Esito.EsitoDAO;
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

                //TODO: da impostare con il docente connesso alla sessione
                SessioneDiValidazione s = null;
                try {
                    s = new SessioneDiValidazione(true, null);
                    s.setDocente(new DocenteDAO().doRetrieveByKey("aavella@unisa.it"));
                    sessioneDAO.doCreate(s);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                response.sendRedirect("ElencoEsiti?nStudents=" + request.getParameter("nStudents"));
                break;
            }

            case "/ElencoEsiti":{
                EsitoDAO esitoDAO=new EsitoDAO();
                ArrayList<Esito> esiti=null;
                try {
                    esiti=esitoDAO.doRetrieveAll();
                    System.out.println("Esiti="+esiti);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                request.setAttribute("esiti",esiti);
                request.getRequestDispatcher(view("DocenteGUI/ElencoEsiti")).forward(request, response);
                break;
            }
            case "/AnteprimaReport":{
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

