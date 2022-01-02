package ApplicationLogic.Servlet;
import ApplicationLogic.Utils.ServletLogic;
import Storage.PersonaleUnisa.Docente.DocenteDAO;
import Storage.SessioneDiValidazione.SessioneDiValidazione;
import Storage.SessioneDiValidazione.SessioneDiValidazioneDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

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
                System.out.println("sono nella sevlet giusta");
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
                SessioneDiValidazione s = null;
                try {
                    s = new SessioneDiValidazione(true, null);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                //TODO: da impostare con il docente connesso alla sessione
                try {
                    s.setDocente(new DocenteDAO().doRetrieveByKey("aavella@unisa.it"));
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                try {
                    sessioneDAO.doCreate(s);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                response.sendRedirect("ElencoEsiti?nStudents=" + request.getParameter("nStudents"));
                break;
            }
            case "/ElencoEsiti":{

                request.getRequestDispatcher(view("DocenteGUI/ElencoEsiti")).forward(request, response);
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
                    request.getRequestDispatcher(view("StudenteGUI/InvioEffettuato")).forward(request, response);
                    break;
                }
            }
        }
}

