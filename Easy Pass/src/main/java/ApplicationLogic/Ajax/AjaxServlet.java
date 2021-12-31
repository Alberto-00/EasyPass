package ApplicationLogic.Ajax;

import ApplicationLogic.Utils.InvalidRequestException;
import ApplicationLogic.Utils.RangeDateException;
import ApplicationLogic.Utils.ServletLogic;
import Storage.Dipartimento.Dipartimento;
import Storage.Dipartimento.DipartimentoDAO;
import Storage.Esito.Esito;
import Storage.Esito.EsitoDAO;
import Storage.PersonaleUnisa.Direttore.DirettoreDiDipartimento;
import Storage.PersonaleUnisa.Docente.Docente;
import Storage.PersonaleUnisa.Docente.DocenteDAO;
import Storage.Report.Report;
import Storage.Report.ReportDAO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

@WebServlet(name = "AjaxServlet", value = "/report/*")
public class AjaxServlet extends ServletLogic {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = getPath(request);
        DirettoreDiDipartimento direttore = (DirettoreDiDipartimento) request.getSession().getAttribute("direttoreSession");

        try {
            switch (path) {
                case "/search": {
                    DocenteDAO docenteDAO = new DocenteDAO();
                    List<Docente> docenti = docenteDAO.doRetrieveAllWithRelations();
                    JSONObject root = new JSONObject();
                    JSONArray arr = new JSONArray();
                    root.put("listName", arr);

                    for (int i = 1; i < docenti.size(); i++) {
                        if(docenti.get(i).getNome() != null){
                            JSONObject obj = new JSONObject();
                            obj.put("name", docenti.get(i).getNome() + " " + docenti.get(i).getCognome());
                            arr.add(obj);
                        }
                    }
                    sendJson(response, root);
                    break;
                }

                case "/search_report": {
                    String firstDate = request.getParameter("firstDate");
                    String secondDate = request.getParameter("SecondDate");
                    String nameDoc = request.getParameter("nameDoc");

                    DipartimentoDAO dipartimentoDAO = new DipartimentoDAO();
                    Dipartimento tmpDip = dipartimentoDAO.doRetrieveById(direttore.getDipartimento().getCodice());

                    JSONObject root = new JSONObject();
                    JSONArray arrRep  = new JSONArray();
                    JSONArray arrDoc = new JSONArray();

                    if (firstDate != null && secondDate != null){
                        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(firstDate);
                        Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(secondDate);

                        if (date1.before(date2) || date1.compareTo(date2) == 0){
                            if (nameDoc != null && nameDoc.compareTo("") != 0){
                                Docente docente = new Docente();
                                docente.setCognome(cognome(nameDoc));
                                docente.setNome(nome(nameDoc));
                                if (tmpDip.ricercaCompletaReport(docente, date1, date2) != null)
                                    root.put("empty", "empty");
                                else {
                                    root.put("listDoc", arrDoc);
                                    root.put("listRep", arrRep);
                                    for (Report r : tmpDip.ricercaCompletaReport(docente, date1, date2)){
                                        JSONObject obj = new JSONObject();
                                        obj.put("report", r);
                                        arrRep.add(obj);

                                        for (Report rep : tmpDip.ricercaReportSoloDocente(tmpDip.getCodice()).keySet()){
                                            JSONObject obj2 = new JSONObject();
                                            obj2.put("docenti", tmpDip.ricercaReportSoloDocente(tmpDip.getCodice()).get(rep));
                                            arrDoc.add(obj2);
                                        }
                                    }
                                }
                            } else {
                                //Si procede solo per data
                            }
                        } else throw new RangeDateException("The first date must be lower that second date.");
                    } else if (nameDoc != null && nameDoc.compareTo("") != 0){
                        if (!tmpDip.ricercaReportSoloDocente(tmpDip.getCodice()).isEmpty()){
                            root.put("listDoc", arrDoc);
                            root.put("listRep", arrRep);
                            for (Report rep : tmpDip.ricercaReportSoloDocente(tmpDip.getCodice()).keySet()){
                                JSONObject obj = new JSONObject();
                                JSONObject obj2 = new JSONObject();
                                obj.put("docenti", tmpDip.ricercaReportSoloDocente(tmpDip.getCodice()).get(rep));
                                obj2.put("report", rep);
                                arrDoc.add(obj);
                                arrRep.add(obj2);
                            }
                        } else
                            root.put("empty", "empty");
                    } root.put("empty", "empty");
                    sendJson(response, root);
                    break;
                }

                case "/delete": {
                    String str = request.getParameter("report");
                    JSONObject root = new JSONObject();

                    if (str.compareTo("") != 0){
                        ReportDAO reportDAO = new ReportDAO();
                        DipartimentoDAO dipartimentoDAO = new DipartimentoDAO();
                        String[] idReport = str.split(",");
                        Dipartimento tmpDip = dipartimentoDAO.doRetrieveById(direttore.getDipartimento().getCodice());
                        JSONArray arrRep = new JSONArray();
                        root.put("listReports", arrRep);

                        for (String id : idReport) {
                            tmpDip.eliminaReport(reportDAO.doRetrieveById(Integer.parseInt(id)));
                            JSONObject obj = new JSONObject();
                            obj.put("report", id);
                            arrRep.add(obj);
                        }
                    }else root.put("listReports", "empty");
                    sendJson(response, root);
                    break;
                }
            }
        } catch(SQLException ex){
            log(ex.getMessage());
        } catch (ParseException | RangeDateException e) {
            e.printStackTrace();
        }
    }

    private String nome(String str){
        String[] token = str.split(" ");
        for (int i = 0; i < token.length; i++)
            if (checkUppercase(token[i]))
                return token[i];
        return null;
    }

    private String cognome(String str){
        StringBuilder out = new StringBuilder();
        String[] token = str.split(" ");
        for (int i = 0; i < token.length; i++) {
            if (!checkUppercase(token[i]))
                out.append(token[i]);
            if ((i + 1) < token.length)
                out.append(" ");
        }
        return out.toString();
    }

    private boolean checkUppercase(String str){
        char[] charArray = str.toCharArray();
        for (char c : charArray) {
            //if any character is not in upper case, return false
            if (!Character.isUpperCase(c))
                return false;
        }
        return true;
    }
}
