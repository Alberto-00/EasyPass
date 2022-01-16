package Storage.Report;

import ApplicationLogic.Utils.JSONSerializable;
import Storage.Dipartimento.Dipartimento;
import Storage.Esito.Esito;
import Storage.Esito.EsitoDAO;
import Storage.Formato.Formato;
import Storage.PersonaleUnisa.Docente.Docente;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static ApplicationLogic.Utils.ServletLogic.getUploadPath;

/**
 * La classe crea degli oggetti {@code Report} e permette di creare nuovi
 * Report salvandoli nella cartella apposita di Tomcat.
 */
public class Report implements Comparable<Report>, JSONSerializable {

    private int id;
    private Date data;
    private Date orario;
    private String pathFile;
    private Dipartimento dip;
    private Docente docente;

    /**
     * * Crea un oggetto {@code Report} con le relative foreign key.
     *
     * @param data data generazione {@code Report}
     * @param orario orario generazione {@code Report}
     * @param pathFile path della cartella in cui si trova il {@code Report}
     * @param dip Dipartimento che gestisce il {@code Report}
     * @param docente Docente che ha generato il {@code Report}
     */
    public Report(Date data, Date orario,
                  String pathFile, Dipartimento dip, Docente docente) {
        this.data = data;
        this.orario = orario;
        this.pathFile = pathFile;
        this.dip = dip;
        this.docente = docente;
    }

    /**
     * Costruttore vuoto.
     */
    public Report() {
        id = 0;
        data = null;
        orario = null;
        pathFile = "";
        dip = null;
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

    public Date getOrario() {
        return orario;
    }

    public void setOrario(Date orario) {
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

    /**
     * Creazione di un {@code Report} in formato PDF.
     *
     * @param esiti Esiti associati al {@code Report}
     */
    public void creaFile(ArrayList<Esito> esiti) throws FileNotFoundException, DocumentException {
        EsitoDAO esitoDAO = new EsitoDAO();
        Formato formato = this.getDip().getFormato();
        Document document = new Document();
        SimpleDateFormat formatData = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatOrario = new SimpleDateFormat("HH:mm:ss");

        PdfWriter writer = PdfWriter.getInstance(document,
                new FileOutputStream(getUploadPath() + "Report" + File.separator + this.pathFile));
        document.open();

        Font font = new Font();
        font.setSize(18f);
        Paragraph p = new Paragraph(this.pathFile.replace(".pdf", "").
                replaceAll("\\s+", " "), font);
        p.setAlignment(Element.ALIGN_CENTER);
        p.setSpacingAfter(20f);
        document.add(p);
        Chunk glue = new Chunk(new VerticalPositionMark());
        Paragraph p1 = new Paragraph("Docente: "+ this.getDocente().getNome() + " " + this.getDocente().getCognome());
        p1.add(new Chunk(glue));
        p1.add("Data: "+ formatData.format(this.getData()));
        Paragraph p2 = new Paragraph("Orario: " + formatOrario.format(this.getOrario()));
        p2.setAlignment(Element.ALIGN_RIGHT);
        p2.setSpacingAfter(10f);
        document.add(p1);
        document.add(p2);

        if(formato.isNumGPValidi())
            document.add(new Paragraph("Il numero di Green Pass validi è: " + esitoDAO.contaEsitiValidi(getId(), true)));

        if(formato.isNumGPNonValidi())
            document.add(new Paragraph("Il numero di Green Pass non validi è: " + esitoDAO.contaEsitiValidi(getId(), false)));

        if(formato.isNumStudenti())
            document.add(new Paragraph("Il numero di Studenti controllati è: " + esitoDAO.numEsiti(getId())));

        Font fontTable = new Font();
        fontTable.setSize(13f); fontTable.setColor(BaseColor.WHITE); fontTable.setStyle(Font.BOLD);

        Font fontCell = new Font();
        fontCell.setSize(10f);

        PdfPCell cell1 = new PdfPCell(new Paragraph("Esito", fontTable));
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell1.setBackgroundColor(new BaseColor(62, 118, 42));
        cell1.setFixedHeight(24f);

        PdfPCell cell2 = new PdfPCell(new Paragraph("Validità", fontTable));
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell2.setBackgroundColor(new BaseColor(62, 118, 42));
        cell2.setFixedHeight(24f);

        PdfPCell cell3 = new PdfPCell(new Paragraph("Nome", fontTable));
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setBackgroundColor(new BaseColor(62, 118, 42));
        cell3.setFixedHeight(24f);

        PdfPCell cell4 = new PdfPCell(new Paragraph("Cognome", fontTable));
        cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell4.setBackgroundColor(new BaseColor(62, 118, 42));
        cell4.setFixedHeight(24f);

        PdfPCell cell5 = new PdfPCell(new Paragraph("Data di Nascita", fontTable));
        cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell5.setBackgroundColor(new BaseColor(62, 118, 42));
        cell5.setFixedHeight(24f);

        if(formato.isNomeCognome() && formato.isData()) {
            PdfPTable table = new PdfPTable(5);
            table.setSpacingBefore(23f);
            table.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.setWidthPercentage(100);
            table.addCell(cell1); table.addCell(cell2); table.addCell(cell3); table.addCell(cell4); table.addCell(cell5);
            
            for (Esito esito : esiti) {
                table.addCell(new Paragraph(String.valueOf(esito.getId()), fontCell));
                table.addCell(new Paragraph((Esito.getValidita(esito.isValidita())), fontCell));
                table.addCell(new Paragraph(esito.getNomeStudente(), fontCell));
                table.addCell(new Paragraph(esito.getCognomeStudente(), fontCell));
                table.addCell(new Paragraph(String.valueOf(esito.getDataDiNascitaStudente()), fontCell));
            }
            zebraRow(document, table);
        } 
        else if(formato.isNomeCognome() && !formato.isData()) {
            PdfPTable table = new PdfPTable(4);
            table.setWidths(new float[] {1f, 2f, 3f, 3f});
            table.setSpacingBefore(21f);
            table.addCell(cell1); table.addCell(cell2); table.addCell(cell3); table.addCell(cell4);

            for (Esito esito : esiti) {
                table.addCell(new Paragraph(String.valueOf(esito.getId())));
                table.addCell(new Paragraph((Esito.getValidita(esito.isValidita())), fontCell));
                table.addCell(new Paragraph(esito.getNomeStudente()));
                table.addCell(new Paragraph(esito.getCognomeStudente()));
            }
            zebraRow(document, table);
        }
        else if (!formato.isNomeCognome() && !formato.isData()){
            PdfPTable table = new PdfPTable(2);
            table.setSpacingBefore(21f);
            table.addCell(cell1); table.addCell(cell2);

            for (Esito esito : esiti) {
                table.addCell(new Paragraph(String.valueOf(esito.getId())));
                table.addCell(new Paragraph((Esito.getValidita(esito.isValidita())), fontCell));
            }
            zebraRow(document, table);
        }
        document.close();
        writer.close();
    }

    private void zebraRow(Document documento, PdfPTable tabella) throws DocumentException {
        boolean b = true;
        for (int i = 1; i < tabella.getRows().size(); i++){
            for(PdfPCell c: tabella.getRows().get(i).getCells()) {
                c.setBackgroundColor(b ? new BaseColor(218, 239, 211) :
                        new BaseColor(183, 223, 168));
                c.setHorizontalAlignment(Element.ALIGN_CENTER);
                c.setFixedHeight(21f);
            }
            b = !b;
        }
        documento.add(tabella);
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