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

                System.out.println("nStudents="+request.getParameter("nStudents"));
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
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            String path = getPath(request);

            switch (path) {
                case "/InvioGP": {
                    SessioneDiValidazione s = new SessioneDiValidazione();
                    try {
                        s.validaGreenPass("HC1%3A6BFOXN%25TS3DHPVO13J+%2FG-%2F2YRVA.Q%2FR8JF62FC1J9M%24DI9C3K9A%2BOFLOS%24S%3ALC%2FGPWBILC9GGBYPLDXI25P-%2BR2YBV44PZBZH0A46EWMDS93O5RF6%24T61R6B46646VY9WC5PF6846A%24Q+76WW60T932Q5G3JZI%2BEBI.CHFTVJCL*SJYDGVT0ND7VC2ND0SSMEDRST%24GC%2B*TQ8C5HCSVDE6U6DLYZJ%2BPB%2FVSQOL9DLSWCZ3EBKDVIJGDB%2B6TYIJGDBQMI%25ZIKCIGOJ0UIM42PBJBKB%2FMJZ+J%24XI4OIMEDTJCJKDLEDL9C%24ZJ*DJ3Q4%2BY5%3AP4%2577%2FYQU96FV5BPOF%2F9BL5S4F84VMOELSUQK9FQGZUIQJAZGA2%3AUG%25UJMI%3ATU%2BMM0W5QX5.T1%2BZED+P6P396KRO1K*5A%3ACJLV34D%254V601Z1VU9MHWV45MU2VV-BY-J3X4%3A%2FI54OH%3AA3SO.-R-OILKK%249LPLQP%2B23ON%3AC7T%25GE1SXBWI0IQ69TY165AKWE");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    request.getRequestDispatcher(view("StudenteGUI/InvioEffettuato")).forward(request, response);
                    break;
                }
            }
        }
}

