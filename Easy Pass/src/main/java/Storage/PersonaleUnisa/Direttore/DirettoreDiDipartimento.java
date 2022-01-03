package Storage.PersonaleUnisa.Direttore;

import ApplicationLogic.Utils.InvalidRequestException;
import ApplicationLogic.Utils.ServletLogic;
import Storage.Dipartimento.Dipartimento;
import Storage.Formato.Formato;
import Storage.PersonaleUnisa.Docente.Docente;
import Storage.PersonaleUnisa.PersonaleUnisa;
import Storage.Report.Report;
import Storage.Report.ReportDAO;
import com.itextpdf.text.DocumentException;
import org.apache.commons.io.FileUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import static ApplicationLogic.Utils.ServletLogic.getUploadPath;

public class DirettoreDiDipartimento extends PersonaleUnisa {

    public DirettoreDiDipartimento() {
        this.setNome("");
        this.setCognome("");
        this.setUsername("");
        this.setPassword("");
        this.setDipartimento(null);
    }

    public DirettoreDiDipartimento(String nome, String cognome, String username, String password,Dipartimento dipartimento){
        this.setNome(nome);
        this.setCognome(cognome);
        this.setUsername(username);
        this.setPassword(password);
        this.setDipartimento(dipartimento);
    }

    public void eliminaReport(Report report) throws SQLException {
        if(report==null){
            throw new IllegalArgumentException("Cannot delete a null object");
        }
        else{
            this.getDipartimento().eliminaReport(report);
        }
    }

    public void impostaFormato(Formato formato) throws SQLException {
        if(formato==null){
            throw new IllegalArgumentException("The argument cannot be a null object");
        }
        else{
            this.getDipartimento().impostaFormato(formato);
        }
    }

    public boolean downloadReport(Report report) throws SQLException, IOException, DocumentException {
        ReportDAO reportDAO = new ReportDAO();
        String namePDF = reportDAO.doDownload(report.getId()).getPathFile();
        String inPath = getUploadPath() + "Report" + File.separator + namePDF + ".pdf";
        String outPath = ServletLogic.getDownloadPath();

       File srcFile = new File(inPath);
       if (srcFile.exists()) {
           FileUtils.copyFileToDirectory(srcFile, new File(outPath));
           return true;
       }
       else
           return false;
    }


    public TreeMap<Report, Docente> ricercaCompletaReport(Docente docente, Date primaData, Date secondaData) throws SQLException, InvalidRequestException {
        if(docente != null && primaData != null && secondaData != null){
            if (primaData.before(secondaData) || primaData.compareTo(secondaData) == 0){
               return this.getDipartimento().ricercaCompletaReport(docente, primaData, secondaData);
            } else
                throw new InvalidRequestException("La prima data è minore della seconda data.", List.of("La prima data è minore della seconda data."), HttpServletResponse.SC_BAD_REQUEST);
        } else
            throw new IllegalArgumentException("The arguments 'docente', 'primaData' and 'secondaData' cannot be null.");
    }

    public TreeMap<Report, Docente> ricercaReportSoloDocente(Docente docente) throws SQLException {
        if(docente != null)
            return this.getDipartimento().ricercaReportSoloDocente(docente);
        else
            throw new IllegalArgumentException("The arguments 'codDip' cannot be null.");
    }

    public TreeMap<Report, Docente> ricercaReportSoloData(Date date1, Date date2) throws SQLException {
        if(date1.before(date2) || date1.compareTo(date2) == 0)
           return this.getDipartimento().ricercaReportSoloData(date1, date2);
        else
            throw new IllegalArgumentException("The arguments 'codDip' cannot be null.");
    }

    public TreeMap<Report, Docente> ricercaReport(String id) throws SQLException {
        if(id != null && id.compareTo("") != 0)
            return this.getDipartimento().ricercaReport(id);
        else
            throw new IllegalArgumentException("The arguments 'codDip' cannot be null and empty.");
    }
}
