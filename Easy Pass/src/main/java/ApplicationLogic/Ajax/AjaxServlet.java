package ApplicationLogic.Ajax;

import ApplicationLogic.Utils.InvalidRequestException;
import ApplicationLogic.Utils.ServletLogic;
import Storage.Esito.Esito;
import Storage.Esito.EsitoDAO;
import Storage.PersonaleUnisa.Direttore.DirettoreDiDipartimento;
import Storage.PersonaleUnisa.Docente.Docente;
import Storage.Report.Report;
import Storage.Report.ReportDAO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
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
                /*case "/api-searchBar": {
                    SQLArticoloDAO sqlArticoloDAO = new SQLArticoloDAO();
                    List<String> nomi = sqlArticoloDAO.doRetrieveProductByNome();
                    JSONObject root = new JSONObject();
                    JSONArray arr = new JSONArray();
                    root.put("listName", arr);

                    for (int i = 1; i < nomi.size(); i++) {
                        if(nomi.get(i) != null){
                            JSONObject obj = new JSONObject();
                            obj.put("name", nomi.get(i));
                            arr.add(obj);
                        }
                    }
                    sendJson(response, root);
                    break;
                }*/

                case "/delete": {
                    String str = request.getParameter("report");
                    JSONObject root = new JSONObject();

                    if (str.compareTo("") != 0){
                        ReportDAO reportDAO = new ReportDAO();
                        EsitoDAO esitoDAO = new EsitoDAO();
                        String[] idReport = str.split(",");

                        JSONArray arrRep = new JSONArray();
                        root.put("listReports", arrRep);

                        for (String id : idReport) {
                            for (Esito esito : esitoDAO.doRetrieveWithRelations(Integer.parseInt(id))) {
                                esitoDAO.doDelete(esito);
                            }
                            JSONObject obj = new JSONObject();
                            obj.put("report", id);
                            arrRep.add(obj);
                            reportDAO.doDelete(reportDAO.doRetrieveById(Integer.parseInt(id)));
                        }
                    } else root.put("listReports", "empty");
                    sendJson(response, root);
                    break;
                }
            }
        } catch(SQLException ex){
            log(ex.getMessage());
        }
    }
}
