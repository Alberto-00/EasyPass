package Storage.Report;

import ApplicationLogic.Utils.ConnectionSingleton;
import Storage.Esito.Esito;
import Storage.Esito.EsitoMapper;

import java.sql.*;
import java.util.ArrayList;

public class ReportDAO {

    public Report doRetrieveById(int id) throws SQLException {
        ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
        try (Connection connection = connectionSingleton.getConnection()) {
            String query = "SELECT * FROM report rep WHERE rep.ID_report=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Report report = ReportMapper.extract(rs);
                return report;
            }
        }
        return null;
    }

    public ArrayList<Report> doRetrieveAll() throws SQLException {
        ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
        Connection connection = connectionSingleton.getConnection();
        String query = "SELECT * FROM report rep";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        ArrayList<Report> reports=new ArrayList<>();
        while(rs.next()) {
            reports.add(ReportMapper.extract(rs));
        }
        return reports;
    }


    public boolean doCreate (Report report) throws SQLException {
        if (report == null)
            throw new IllegalArgumentException("Cannot save a null object");
        ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
        try (Connection connection = connectionSingleton.getConnection()) {
            String query = "INSERT INTO report (Orario, Data_report, PathFile, Codice_Dip, QRcode_session) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setTime(1, report.getOrario());
            ps.setDate(2, (Date) report.getData());
            ps.setString(3, report.getPathFile());
            ps.setString(4, report.getDip().getCodice());
            ps.setString(5, report.getSessione().getqRCode());
            if (ps.executeUpdate() == 1)
                return true;
            else return false;
        }
    }


    public boolean doUpdate (Report report) throws SQLException {
        if (report == null)
            throw new IllegalArgumentException("Cannot update a null object");

        ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
        try (Connection connection = connectionSingleton.getConnection()) {
            String query = "UPDATE report rep SET rep.Orario=?, rep.Data_report=?, " +
                    "rep.PathFile=?, rep.Codice_Dip=?, rep.QRcode_session=? WHERE rep.ID_report=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setTime(1, report.getOrario());
            ps.setDate(2, (Date) report.getData());
            ps.setString(3, report.getPathFile());
            ps.setString(4, report.getDip().getCodice());
            ps.setString(5, report.getSessione().getqRCode());
            ps.setInt(6, report.getId());

            if (ps.executeUpdate() == 1)
                return true;
            else return false;
        }
    }

    public boolean doDelete (Report report) throws SQLException {
        if (report == null)
            throw new IllegalArgumentException("Cannot update a null object");

        ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
        try (Connection connection = connectionSingleton.getConnection()) {
            String query = "DELETE FROM report rep WHERE rep.ID_report=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, report.getId());

            if (ps.executeUpdate() == 1)
                return true;
            else return false;
        }
    }

    //Testare il funzionamento della query
    public ArrayList<Report> doSearch(String docente, Date primaData, Date secondaData) throws SQLException {
        if(primaData==null && secondaData!=null){
            throw new IllegalArgumentException("If the argument 'primaData' is null, the argument 'secondaData' cannot be not null");
        }
        else{
            ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
            try(Connection connection = connectionSingleton.getConnection()){
                String query="SELECT * FROM docente doc, report rep, dipartimento dip WHERE rep.Codice_Dip=dip.Codice_Dip and doc.Codice_Dip=dip.Codice_Dip and (doc.Nome_Doc like ?) and (doc.Cognome_Doc like ?) and (rep.Data_report between ? and ?) ";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1,"%"+docente+"%");
                ps.setString(2,"%"+docente+"%");
                ps.setDate(3,primaData);
                ps.setDate(4,secondaData);
                ArrayList<Report> reports=new ArrayList<>();
                ResultSet rs = ps.executeQuery();
                while(rs.next()) {
                    reports.add(ReportMapper.extract(rs));
                }
                return reports;
            }
        }
    }

}