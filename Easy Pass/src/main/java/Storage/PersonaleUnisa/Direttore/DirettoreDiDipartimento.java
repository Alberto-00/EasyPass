package Storage.PersonaleUnisa.Direttore;

import ApplicationLogic.Utils.ServletLogic;
import Storage.Dipartimento.Dipartimento;
import Storage.Formato.Formato;
import Storage.PersonaleUnisa.PersonaleUnisa;
import Storage.Report.Report;
import Storage.Report.ReportDAO;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;

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

    public Report eliminaReport(Report report) throws SQLException {
        if(report==null){
            throw new IllegalArgumentException("Cannot delete a null object");
        }
        else{
            this.getDipartimento().eliminaReport(report);
            return report;
        }
    }
    //public ArrayList<Report> ricercaReport(String docente, Date primaData, Date secondaData){}

    public Formato impostaFormato(Formato formato) throws SQLException {
        if(formato==null){
            throw new IllegalArgumentException("The argument cannot be a null object");
        }
        else{
            this.getDipartimento().impostaFormato(formato);
            return formato;
        }
    }

    public boolean downloadReport(Report report) throws SQLException, IOException, DocumentException {
        ReportDAO reportDAO = new ReportDAO();
        String namePDF = reportDAO.doDownload(report.getId()).getPathFile();
        String inPath = getUploadPath() + namePDF + ".pdf";
        String outPath = ServletLogic.getDownloadPath();

       File srcFile = new File(inPath);
       if (srcFile.exists()) {
           FileUtils.copyFileToDirectory(srcFile, new File(outPath));
           return true;
       }
       else
           return false;
    }
}
