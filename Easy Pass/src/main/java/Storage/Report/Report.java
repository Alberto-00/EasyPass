package Storage.Report;

import ApplicationLogic.Utils.JSONSerializable;
import Storage.Dipartimento.Dipartimento;
import Storage.Esito.Esito;
import Storage.Esito.EsitoDAO;
import Storage.Formato.Formato;
import Storage.PersonaleUnisa.Docente.Docente;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import static ApplicationLogic.Utils.ServletLogic.getUploadPath;

public class Report implements Comparable<Report>, JSONSerializable {

    private int id;
    private Date data;
    private Time orario;
    private String pathFile;
    private Dipartimento dip;
    private Docente docente;

    public Report(int id, Date data, Time orario,
                  String pathFile, Dipartimento dip, Docente docente) {
        this.id = id;
        this.data = data;
        this.orario = orario;
        this.pathFile = pathFile;
        this.dip = dip;
        this.docente = docente;
    }

    public Report() {
        id=0;
        data=null;
        orario=null;
        pathFile="";
        dip=null;
        docente = null;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Time getOrario() {
        return orario;
    }

    public void setOrario(Time orario) {
        this.orario = orario;
    }

    public String getPathFile() {
        return pathFile;
    }

    public void setPathFile(String pathFile) {
        this.pathFile = pathFile;
    }

    public Dipartimento getDip() {
        return dip;
    }

    public void setDip(Dipartimento dip) {
        this.dip = dip;
    }

    public void creaFile() throws SQLException, FileNotFoundException, DocumentException {
        EsitoDAO esitoDAO = new EsitoDAO();
        ArrayList<Esito> esiti = esitoDAO.doRetrieveAllByReport(this);

        Formato formato = this.getDip().getFormato();
        Document document = new Document();
        
        String reportFile = getUploadPath() + getPathFile() + ".pdf";
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(reportFile));
        document.open();

        document.add(new Paragraph("Report")); //aggiustare grafica
        //document.add(new Paragraph("Data: "+ report.getData()).ALIGN_LEFT);
        //document.add(new Paragraph("Data: "+ report.getData()).ALIGN_RIGHT);

        if(formato.isNumGPValidi())
            document.add(new Paragraph("Il numero di Green Pass Validi è: " + esitoDAO.contaEsitiValidi(getId(), true)));

        if(formato.isNumGPNonValidi())
            document.add(new Paragraph("Il numero di Green Pass non Validi è: " + esitoDAO.contaEsitiValidi(getId(), false)));

        if(formato.isNumStudenti())
            document.add(new Paragraph("Il numero di Studenti controllati è: " + esitoDAO.numEsiti(getId())));


        if(formato.isNomeCognome() && formato.isData()) {
            PdfPTable table = new PdfPTable(5);
            table.setSpacingBefore(10f);
            table.addCell((new Paragraph("Esito")));
            table.addCell((new Paragraph("Validità")));
            table.addCell((new Paragraph("Nome")));
            table.addCell((new Paragraph("Cognome")));
            table.addCell(new Paragraph("Data di Nascita"));
            table.setWidths(new float[] {1f, 2f, 3f, 3f, 2f});
            
            for (Esito esito : esiti) {
                table.addCell(new Paragraph(esito.getId()));
                table.addCell(new Paragraph(String.valueOf(esito.isValidita())));
                table.addCell(new Paragraph(esito.getNomeStudente()));
                table.addCell(new Paragraph(esito.getCognomeStudente()));
                table.addCell(new Paragraph(String.valueOf((esito.getDataDiNascitaStudente()))));
            }
            document.add(table);
        } 
        else if(formato.isNomeCognome() && !formato.isData()) {
            PdfPTable table = new PdfPTable(4);
            table.setWidths(new float[] {1f, 2f, 3f, 3f});
            table.setSpacingBefore(10f);
            table.addCell((new Paragraph("Esito")));
            table.addCell((new Paragraph("Validità")));
            table.addCell((new Paragraph("Nome")));
            table.addCell((new Paragraph("Cognome")));
            for (Esito esito : esiti) {
                table.addCell(new Paragraph(esito.getId()));
                table.addCell(new Paragraph(String.valueOf(esito.isValidita())));
                table.addCell(new Paragraph(esito.getNomeStudente()));
                table.addCell(new Paragraph(esito.getCognomeStudente()));
            }
            document.add(table);
        }
        else if(!formato.isNomeCognome() && formato.isData()) {
            PdfPTable table = new PdfPTable(3);
            table.setSpacingBefore(10f);
            table.addCell((new Paragraph("Esito")));
            table.addCell((new Paragraph("Validità")));
            table.addCell(new Paragraph("Data di Nascita"));
            for (Esito esito : esiti) {
                table.addCell(new Paragraph(esito.getId()));
                table.addCell(new Paragraph(String.valueOf(esito.isValidita())));
                table.addCell(new Paragraph(String.valueOf((esito.getDataDiNascitaStudente()))));
            }
            document.add(table);
        }
        else {
            PdfPTable table = new PdfPTable(2);
            table.setSpacingBefore(10f);
            table.addCell((new Paragraph("Esito")));
            table.addCell((new Paragraph("Validità")));
            for (Esito esito : esiti) {
                table.addCell(new Paragraph(esito.getId()));
                table.addCell(new Paragraph(String.valueOf(esito.isValidita())));
            }
            document.add(table);
        }
        document.close();
        writer.close();
    }
    
    @Override
    public int compareTo(Report o) {
        return this.id - o.getId();
    }

    @Override
    public JSONObject toJson(){
        JSONObject object = new JSONObject();
        object.put("id", this.id);
        object.put("data", this.data.toString());
        object.put("orario", this.orario.toString());
        object.put("path", this.pathFile);
        return object;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", data=" + data +
                ", orario=" + orario +
                ", pathFile='" + pathFile + '\'' +
                ", dip=" + dip +
                ", docente=" + docente +
                '}';
    }
}