package ApplicationLogic.Ajax;

import ApplicationLogic.Utils.InvalidRequestException;
import ApplicationLogic.Utils.ServletLogic;

import Storage.Esito.Esito;
import Storage.Esito.EsitoDAO;
import Storage.PersonaleUnisa.Direttore.DirettoreDiDipartimento;
import Storage.PersonaleUnisa.Docente.Docente;
import Storage.PersonaleUnisa.Docente.DocenteDAO;
import Storage.Report.Report;
import Storage.Report.ReportDAO;
import Storage.SessioneDiValidazione.SessioneDiValidazione;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Pattern;

/**
 * La classe si occupa della gestione dei Report e degli Esiti laddove \u00E8
 * stata utilizzata la tecnica Ajax, in particolare sono implementate le
 * funzionalit&agrave; di:
 * <ul>
 *     <li>ricerca Report, applicando i seguenti filtri:<br>
 *      1) nome e cognome Docente<br>
 *      2) range di date<br>
 *      3) nome e cognome Docente + range di date
 *     </li>
 *     <li>cancellazione di uno/pi&ugrave; Report;</li>
 *     <li>download di uno/pi&ugrave; Report;</li>
 *     <li>aggiornamento run-time degli Esiti.</li>
 * </ul>
 *
 * @version 0.1
 * @author Alberto Montefusco, Martina Mulino
 */
@WebServlet(name = "AjaxServlet", value = "/report/*")
public class AjaxServlet extends ServletLogic {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = getPath(request);
        DirettoreDiDipartimento direttore = (DirettoreDiDipartimento) request.getSession().getAttribute("direttoreSession");
        Docente docenteSession = (Docente) request.getSession().getAttribute("docenteSession");

        try {
            switch (path) {
                /* Vengono cercati tutti i Report generati dai Docenti appartenenti
                 * al Dipartimento del Direttore registrato nella sessione HTTP.
                 * */
                case "/search" -> {
                    if (direttore != null){
                        DocenteDAO docenteDAO = new DocenteDAO();
                        List<Docente> docenti = docenteDAO.doRetrieveAllWithRelations(direttore.getDipartimento().getCodice());
                        JSONObject root = new JSONObject();
                        JSONArray arr = new JSONArray();
                        root.put("listName", arr);

                        for (Docente docente : docenti) {
                            if (docente.getNome() != null) {
                                JSONObject obj = new JSONObject();
                                obj.put("name", docente.getNome() + " " + docente.getCognome());
                                arr.add(obj);
                            }
                        }
                        sendJson(response, root);
                    } else
                        throw new InvalidRequestException("Non sei Autorizzato", List.of("Non sei Autorizzato"), HttpServletResponse.SC_FORBIDDEN);
                }

                /* Vengono cercati tutti i Report generati dai Docenti appartenenti
                 * al Dipartimento del Direttore registrato nella sessione HTTP,
                 * secondo dei filtri.
                 * */
                case "/search_report" -> {
                    if (direttore != null){
                        String firstDate = request.getParameter("firstDate");
                        String secondDate = request.getParameter("secondDate");
                        String nameDoc = request.getParameter("nameDoc");

                        JSONObject root = new JSONObject();
                        JSONArray arrRep = new JSONArray();
                        JSONArray arrDoc = new JSONArray();


                        if (firstDate.compareTo("") != 0 && secondDate.compareTo("") != 0) {
                            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(firstDate);
                            Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(secondDate);
                            boolean checkDate = date1.compareTo(date2) < 0 || date1.compareTo(date2) == 0;

                            if (checkDate)
                                search(direttore, date1, date2, nameDoc, root, arrRep, arrDoc, response);
                            else {
                                root.put("dateError", "La prima data deve essere minore della seconda data.");
                                sendJson(response, root);
                            }
                            break;
                        } else if (firstDate.compareTo("") != 0 && secondDate.compareTo("") == 0) {
                            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(firstDate);
                            search(direttore, date1, date1, nameDoc, root, arrRep, arrDoc, response);
                            break;
                        } else if (firstDate.compareTo("") == 0 && secondDate.compareTo("") != 0) {
                            root.put("dateError", "Inserire la prima data.");
                            sendJson(response, root);
                            break;
                        }
                        if (nameDoc != null && nameDoc.compareTo("") != 0) {
                            Docente docente = new Docente();
                            docente.setCognome(cognome(nameDoc));
                            docente.setNome(nome(nameDoc));

                            TreeMap<Report, Docente> treeMap = direttore.ricercaReportSoloDocente(docente);
                            searchReport(root, arrRep, arrDoc, treeMap);
                        } else root.put("emptyy", "empty");
                        sendJson(response, root);
                    } else
                        throw new InvalidRequestException("Non sei Autorizzato", List.of("Non sei Autorizzato"), HttpServletResponse.SC_FORBIDDEN);
                }

                /* Vengono eliminati i Report selezionati dal Direttore. */
                case "/delete" -> {
                    if (direttore != null){
                        String str = request.getParameter("report");
                        JSONObject root = new JSONObject();

                        if (str.compareTo("") != 0) {
                            ReportDAO reportDAO = new ReportDAO();
                            String[] idReport = str.split(",");
                            JSONArray arrRep = new JSONArray();
                            root.put("listReports", arrRep);

                            for (String id : idReport) {
                                direttore.eliminaReport(reportDAO.doRetrieveById(Integer.parseInt(id)));
                                JSONObject obj = new JSONObject();
                                obj.put("report", id);
                                arrRep.add(obj);
                            }
                        } else root.put("listReports", null);
                        sendJson(response, root);
                    } else
                        throw new InvalidRequestException("Non sei Autorizzato", List.of("Non sei Autorizzato"), HttpServletResponse.SC_FORBIDDEN);
                }

                /* Viene effettuato il download dei Report selezionati dal Direttore. */
                case "/download_report" -> {
                    if (direttore != null){
                        String str = request.getParameter("report");
                        JSONObject root = new JSONObject();
                        ReportDAO reportDAO = new ReportDAO();

                        if (str.compareTo("") != 0) {
                            String[] idReport = str.split(",");
                            JSONArray arr = new JSONArray();
                            root.put("listDownload", arr);

                            for (String id : idReport) {
                                JSONObject obj = new JSONObject();
                                obj.put("report", (reportDAO.doRetrieveById(Integer.parseInt(id))).getPathFile());
                                arr.add(obj);
                            }
                        } else root.put("noFile", "Selezionare almeno un report da scaricare.");
                        sendJson(response, root);
                    } else
                        throw new InvalidRequestException("Non sei Autorizzato", List.of("Non sei Autorizzato"), HttpServletResponse.SC_FORBIDDEN);
                }

                /* Viene effettuato l'aggiornamento degli Esiti nella pagina Docente dopo che
                 * lo Studente ha inviato il suo Green Pass e questo è stato validato.
                 **/
                case "/aggiornaElencoEsiti" -> {
                    if (docenteSession != null){
                        JSONArray arr = new JSONArray();
                        JSONObject root = new JSONObject();
                        HttpSession session = request.getSession();
                        EsitoDAO esitoDAO = new EsitoDAO();
                        SessioneDiValidazione s= (SessioneDiValidazione) session.getAttribute("sessioneDiValidazione");
                        ArrayList<Esito> esiti = esitoDAO.doRetrieveAllBySession(s);

                        for(Esito e: esiti){
                            JSONObject esitoJson = new JSONObject();
                            esitoJson.put("esitoJson",e.toJson());
                            arr.add(esitoJson);
                        }
                        root.put("listaEsiti", arr);
                        sendJson(response,root);
                    } else
                        throw new InvalidRequestException("Non sei Autorizzato", List.of("Non sei Autorizzato"), HttpServletResponse.SC_FORBIDDEN);
                }
            }
        } catch (SQLException | ParseException exception) {
                exception.printStackTrace();
        } catch (InvalidRequestException exception) {
            exception.printStackTrace();
            exception.handle(request, response);
        }
    }


    /* Il metodo permette di cercare tutti i Report generati da un Docente
     * (appartenente al Dipartimento del Direttore che esegue tale ricerca)
     * e inviarli al client tramite un JSON; in particolare, se il nome e il
     * cognome non corrispondono al Docente in questione, il JSON invierà la
     * lista dei Report generata dai Docenti nel periodo di tempo indicato.
     * */
    private void search(DirettoreDiDipartimento direttore, Date date1, Date date2, String nameDoc,
                        JSONObject root, JSONArray arrRep, JSONArray arrDoc, HttpServletResponse response)
            throws SQLException, InvalidRequestException, IOException {

        if (nameDoc != null && nameDoc.compareTo("") != 0){
            Docente docente = new Docente();
            docente.setCognome(cognome(nameDoc));
            docente.setNome(nome(nameDoc));

            TreeMap<Report, Docente> treeMap = direttore.ricercaCompletaReport(docente, date1, date2);
            searchReport(root, arrRep, arrDoc, treeMap);
        } else {
            TreeMap<Report, Docente> treeMap = direttore.ricercaReportSoloData(date1, date2);
            searchReport(root, arrRep, arrDoc, treeMap);
        }
        sendJson(response, root);
    }


    /* Il metodo permette di memorizzare all'interno di un JSON due liste, tramite
     * l'aiuto di un TreeMap. Il TreeMap contiene come chiave gli ID dei Report
     * e come valore i Docenti che hanno generato tali Report, di conseguenza, la prima
     * lista del JSON conterrà gli oggetti Report interessati, analogamente, la seconda lista
     * conterrà gli oggetti Docente.
     * */
    private void searchReport(JSONObject root, JSONArray arrRep, JSONArray arrDoc,
                              TreeMap<Report, Docente> treeMap) {
        if (treeMap.isEmpty())
            root.put("emptyy", "empty");
        else {
            root.put("listDoc", arrDoc);
            root.put("listRep", arrRep);

            for (Report r : treeMap.keySet()){
                JSONObject obj = new JSONObject();
                JSONObject obj2 = new JSONObject();
                obj.put("report", r.toJson());
                arrRep.add(obj);
                obj2.put("docenti", treeMap.get(r).toJson());
                arrDoc.add(obj2);
            }
        }
    }


    /* Il metodo estrapola dalla stringa, una sottostringa formata solo
     * dal nome (o dai nomi) che possiede il Docente.
     * */
    private String nome(String str){
        String[] token = str.split(" ");
        StringBuilder out = new StringBuilder();

        for (int i = 0; i < token.length; i++) {
            if (!checkUppercase(token[i])) {
                out.append(token[i]);
                if ((i + 1) < token.length)
                    if (!checkUppercase(token[i + 1]))
                        out.append(" ");
            }
        }
        return out.toString();
    }


    /* Il metodo estrapola dalla stringa, una sottostringa formata solo
     * dal cognome che possiede il Docente.
     * */
    private String cognome(String str){
        StringBuilder out = new StringBuilder();
        String[] token = str.split(" ");

        for (int i = 0; i < token.length; i++) {
            if (checkUppercase(token[i])) {
                out.append(token[i]);
                if ((i + 1) < token.length)
                    if (checkUppercase(token[i + 1]))
                        out.append(" ");
            }
        }
        return out.toString();
    }


    /* Il metodo verifica se la stringa, passata in input,
     * è formata solo da caratteri UpperCase o meno.
     * */
    private boolean checkUppercase(String str){
        char[] charArray = str.toCharArray();
        for (char c : charArray) {
            //if any character is not in upper case, return false
            if (!Character.isUpperCase(c))
                return false;
        }
        return true;
    }

    public boolean validaFormRicercaReport(Date data1, Date data2, String nomeCompleto, JSONObject root, HttpServletResponse response) throws IOException {
        //Caso in cui il nome del docente non rispetta il formato
        if(!((Pattern.compile("^[a-zA-Z .']+$")).matcher(nomeCompleto).matches())){
            root.put("dateError", "La prima data deve essere minore della seconda data.");
            sendJson(response, root);
            return false;
        }
        return true;
    }
}



