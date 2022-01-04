package Storage.Esito;

import ApplicationLogic.Utils.ConnectionSingleton;
import Storage.Formato.Formato;
import Storage.Report.Report;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static ApplicationLogic.Utils.ServletLogic.getUploadPath;

public class EsitoDAO {

    public Esito doRetrieveByKey(int id) throws SQLException {
        if(id<0){
            throw new IllegalArgumentException("The ID must not be a negative number");
        }
        else{
            ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
            try(Connection connection = connectionSingleton.getConnection()) {
                String query = "SELECT * FROM esito esi WHERE esi.ID_Esito=?";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    Esito esito = EsitoMapper.extract(rs);
                    return esito;
                }
                return null;
            }
        }
    }

    public List<Esito> doRetrieveWithRelations(int idReport) throws SQLException {
        if(idReport < 0){
            throw new IllegalArgumentException("The ID must not be a negative number");
        } else{
            try(Connection connection = ConnectionSingleton.getInstance().getConnection()) {
                String query = "SELECT esi.* FROM esito esi, report rep " +
                        "WHERE esi.ID_Report = rep.ID_report and rep.ID_report = ?";

                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1, idReport);
                ResultSet rs = ps.executeQuery();
                List<Esito> esiti = new ArrayList<>();

                while (rs.next())
                    esiti.add(EsitoMapper.extract(rs));
                return esiti;
            }
        }
    }

    public ArrayList<Esito> doRetrieveAll() throws SQLException {
        ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
        try(Connection connection = connectionSingleton.getConnection()) {
            String query = "SELECT * FROM esito esi";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            ArrayList<Esito> esiti = new ArrayList<>();
            while (rs.next()) {
                esiti.add(EsitoMapper.extract(rs));
            }
            return esiti;
        }
    }

    public void esitiReport(Report report) throws SQLException {
        if(report.getId() < 0){
            throw new IllegalArgumentException("The ID must not be a negative number");
        } else{
            try(Connection connection = ConnectionSingleton.getInstance().getConnection()) {
                String query = "SELECT esi.ID_Esito, esi.Valido, esi.Nome_Studente, " +
                        "esi.Cognome_Studente, esi.Ddn_Studente FROM esito esi, report rep " +
                        "WHERE esi.ID_Report = rep.ID_report and rep.ID_report = ?";

                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1, report.getId());
                ResultSet rs = ps.executeQuery();
                Formato formato = report.getDip().getFormato();

                Document document = new Document();
                String reportFile = getUploadPath() + report.getPathFile() + ".pdf";
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(reportFile));
                document.open();

                document.add(new Paragraph("Report")); //aggiustare grafica
                //document.add(new Paragraph("Data: "+ report.getData()).ALIGN_LEFT);
                //document.add(new Paragraph("Data: "+ report.getData()).ALIGN_RIGHT);

                if(formato.isNumGPValidi())
                    document.add(new Paragraph("Il numero di Green Pass Validi è: " + contaValidi(report.getId(), true)));

                if(formato.isNumGPNonValidi())
                    document.add(new Paragraph("Il numero di Green Pass non Validi è: " + contaValidi(report.getId(), false)));

                if(formato.isNumStudenti())
                    document.add(new Paragraph("Il numero di Studenti controllati è: " + numEsiti(report.getId())));

                if(formato.isNomeCognome() && formato.isData()) {
                    PdfPTable table = new PdfPTable(5);
                    table.setSpacingBefore(10f);
                    table.addCell((new Paragraph("Esito")));
                    table.addCell((new Paragraph("Validità")));
                    table.addCell((new Paragraph("Nome")));
                    table.addCell((new Paragraph("Cognome")));
                    table.addCell(new Paragraph("Data di Nascita"));
                    table.setWidths(new float[] {1f, 2f, 3f, 3f, 2f});
                    while(rs.next()) {
                        table.addCell(new Paragraph(rs.getString("ID_Esito")));
                        table.addCell(new Paragraph(rs.getString("Valido")));
                        table.addCell(new Paragraph(rs.getString("Nome_Studente")));
                        table.addCell(new Paragraph(rs.getString("Cognome_Studente")));
                        table.addCell(new Paragraph((rs.getDate("Ddn_Studente").toString())));
                    }
                    document.add(table);
                }

                else
                    if(formato.isNomeCognome() && !formato.isData()) {
                        PdfPTable table = new PdfPTable(4);
                        table.setWidths(new float[] {1f, 2f, 3f, 3f});
                        table.setSpacingBefore(10f);
                        table.addCell((new Paragraph("Esito")));
                        table.addCell((new Paragraph("Validità")));
                        table.addCell((new Paragraph("Nome")));
                        table.addCell((new Paragraph("Cognome")));
                        while(rs.next()){
                            table.addCell(new Paragraph(rs.getString("ID_Esito")));
                            table.addCell(new Paragraph(rs.getString("Valido")));
                            table.addCell(new Paragraph(rs.getString("Nome_Studente")));
                            table.addCell(new Paragraph(rs.getString("Cognome_Studente")));
                        }
                        document.add(table);
                    }

                else
                    if(!formato.isNomeCognome() && formato.isData()) {
                        PdfPTable table = new PdfPTable(3);
                        table.setSpacingBefore(10f);
                        table.addCell((new Paragraph("Esito")));
                        table.addCell((new Paragraph("Validità")));
                        table.addCell(new Paragraph("Data di Nascita"));
                        while(rs.next()){
                            table.addCell(new Paragraph(rs.getString("ID_Esito")));
                            table.addCell(new Paragraph(rs.getString("Valido")));
                            table.addCell(new Paragraph((rs.getDate("Ddn").toString())));
                        }
                        document.add(table);

                }
                    else {
                        PdfPTable table = new PdfPTable(2);
                        table.setSpacingBefore(10f);
                        table.addCell((new Paragraph("Esito")));
                        table.addCell((new Paragraph("Validità")));
                        while(rs.next()){
                            table.addCell(new Paragraph(rs.getString("ID_Esito")));
                            table.addCell(new Paragraph(rs.getString("Valido")));
                        }
                        document.add(table);
                    }

                document.close();
                writer.close();
            } catch (DocumentException | FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private int contaValidi(int idReport, boolean v) throws SQLException {
        if(idReport < 0){
            throw new IllegalArgumentException("The ID must not be a negative number");
        } else{
            try(Connection connection = ConnectionSingleton.getInstance().getConnection()) {
                String query = "SELECT COUNT(*) as Count FROM esito esi, report rep " +
                        "WHERE esi.ID_Report = rep.ID_report and rep.ID_report = ? AND Valido=?";

                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1, idReport);
                ps.setBoolean(2, v);
                ResultSet rs = ps.executeQuery();
                if (rs.next())
                    return rs.getInt("Count");
            }
            return 0;
        }
    }

    private int numEsiti (int idReport) throws SQLException {
        if(idReport < 0){
            throw new IllegalArgumentException("The ID must not be a negative number");
        } else{
            try(Connection connection = ConnectionSingleton.getInstance().getConnection()) {
                String query = "SELECT COUNT(*) as Count FROM esito esi, report rep " +
                        "WHERE esi.ID_Report = rep.ID_report and rep.ID_report = ?";

                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1, idReport);
                ResultSet rs = ps.executeQuery();
                if (rs.next())
                    return rs.getInt("Count");
                return 0;
            }
        }
    }

    public boolean doCreate(Esito esito) throws SQLException {
        if(esito==null){
            throw new IllegalArgumentException("Cannot save a null object");
        }
        else {
            ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
            try(Connection connection = connectionSingleton.getConnection()) {
                String query = "INSERT INTO esito (Valido, ID_Report, Nome_Studente, Cognome_Studente, Ddn_Studente) VALUES (?,?,?,?,?)";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setBoolean(1, esito.isValidita());
                ps.setInt(2, esito.getReport().getId());
                ps.setString(3, esito.getNomeStudente());
                ps.setString(4, esito.getNomeStudente());
                ps.setDate(5, (Date) esito.getDataDiNascitaStudente());
                if (ps.executeUpdate() == 1)
                    return true;
                else return false;
            }
        }
    }

    public boolean doUpdate(Esito esito) throws SQLException{
        if(esito==null){
            throw new IllegalArgumentException("Cannot update a null object");
        }
        else{
            ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
            try(Connection connection = connectionSingleton.getConnection()) {
                String query = "UPDATE esito SET Valido=?, ID_Report=?, Nome_Studente=?, Cognome_Studente=?, Ddn_Studente=? WHERE ID_Esito=?";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setBoolean(1, esito.isValidita());
                ps.setInt(2, esito.getReport().getId());
                ps.setString(3, esito.getNomeStudente());
                ps.setString(4, esito.getNomeStudente());
                ps.setDate(5, (Date) esito.getDataDiNascitaStudente());
                ps.setInt(6, esito.getId());
                if (ps.executeUpdate() == 1)
                    return true;
                else return false;
            }
        }
    }

    public boolean doDelete(Esito esito) throws SQLException{
        if(esito==null){
            throw new IllegalArgumentException("Cannot delete a null object");
        }
        else{
            ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
            try(Connection connection = connectionSingleton.getConnection()) {
                String query = "DELETE FROM esito WHERE ID_Esito=?";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1, esito.getId());
                if (ps.executeUpdate() == 1)
                    return true;
                else return false;
            }
        }
    }
}
