package ApplicationLogic.Servlet;

import ApplicationLogic.Utils.InvalidRequestException;

import ApplicationLogic.Utils.ServletLogic;
import Storage.Formato.Formato;
import Storage.Formato.FormatoDAO;

import Storage.PersonaleUnisa.Direttore.DirettoreDiDipartimento;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ReportController", value = "/reportServlet/*")
public class ReportController extends ServletLogic {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = getPath(request);
        DirettoreDiDipartimento direttore = (DirettoreDiDipartimento) request.getSession().getAttribute("direttoreSession");

        try {
            switch (path) {
                case "/HomePage" -> {
                    if (direttore != null)
                        request.getRequestDispatcher(view("DirettoreDiDipartimentoGUI/HomePage")).forward(request, response);
                    else
                        throw new InvalidRequestException("Non sei Autorizzato", List.of("Non sei Autorizzato"), HttpServletResponse.SC_FORBIDDEN);
                }

                case "/GestioneFormato" -> {
                    if (direttore != null) {
                        Formato formato = direttore.getDipartimento().getFormato();
                        gestioneFormato(request, formato);
                        request.getRequestDispatcher(view("DirettoreDiDipartimentoGUI/GestioneFormato")).forward(request, response);
                    } else
                        throw new InvalidRequestException("Non sei Autorizzato", List.of("Non sei Autorizzato"), HttpServletResponse.SC_FORBIDDEN);
                }

                case "/GestioneReport" -> {
                    if (direttore != null) {
                        request.setAttribute("treeMap", direttore.ricercaReport());
                        request.getRequestDispatcher(view("DirettoreDiDipartimentoGUI/GestioneReport")).forward(request, response);
                    } else
                        throw new InvalidRequestException("Non sei Autorizzato", List.of("Non sei Autorizzato"), HttpServletResponse.SC_FORBIDDEN);
                }
            }
        } catch (InvalidRequestException e) {
            e.printStackTrace();
            e.handle(request,response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = getPath(request);
        DirettoreDiDipartimento direttore = (DirettoreDiDipartimento) request.getSession().getAttribute("direttoreSession");

        try {
            if ("/salvaFormato".equals(path)) {
                String anagrafica = request.getParameter("anagrafica");
                String ddn = request.getParameter("ddn");
                String numValidazioni = request.getParameter("numValidazioni");
                String gpValidi = request.getParameter("gpValidi");
                String gpNonValidi = request.getParameter("gpNonValidi");
                String messaggi = "";

                if (direttore != null) {
                    if (anagrafica == null && ddn == null && numValidazioni == null
                            && gpValidi == null && gpNonValidi == null) {
                        messaggi = messaggi + "Selezionare almeno un campo\n";
                    } else if (ddn != null && anagrafica == null) {
                        messaggi = messaggi + "Impossibile salvare il formato! Se è selezionata la data deve " +
                                "essere selezionata anche l'anagrafica\n";
                    } else {
                        FormatoDAO formatoDAO = new FormatoDAO();
                        Formato formato = formatoDAO.doRetrieveById(direttore.getDipartimento().getFormato().getId());

                        if (formato == null) {
                            formato = new Formato();
                            formato.setId(direttore.getDipartimento().getCodice());
                        }
                        formato.setNomeCognome(anagrafica != null);
                        formato.setData(ddn != null);
                        formato.setNumStudenti(numValidazioni != null);
                        formato.setNumGPValidi(gpValidi != null);
                        formato.setNumGPNonValidi(gpNonValidi != null);
                        direttore.impostaFormato(formato);
                        messaggi = "Il formato è stato salvato correttamente";
                        gestioneFormato(request, formato);
                    }
                    request.setAttribute("messaggiUtente", messaggi);
                    request.getRequestDispatcher(view("DirettoreDiDipartimentoGUI/GestioneFormato")).forward(request, response);
                } else
                    throw new InvalidRequestException("Non sei Autorizzato", List.of("Non sei Autorizzato"), HttpServletResponse.SC_FORBIDDEN);
            }
        } catch (InvalidRequestException e) {
            e.printStackTrace();
            e.handle(request,response);
        }
    }

    private void gestioneFormato(HttpServletRequest request, Formato formato) {
        String value;

        if (formato.isNomeCognome()) value = "checked";
        else value = "";
        request.setAttribute("actualNomeCognome", value);

        if (formato.isNumStudenti()) value = "checked";
        else value = "";
        request.setAttribute("actualNumStudenti", value);

        if (formato.isData()) value = "checked";
        else value = "";
        request.setAttribute("actualDataDiNascita", value);

        if (formato.isNumGPValidi()) value = "checked";
        else value = "";
        request.setAttribute("actualGPValidi", value);

        if (formato.isNumGPNonValidi()) value = "checked";
        else value = "";
        request.setAttribute("actualGPNonValidi", value);
    }
}
