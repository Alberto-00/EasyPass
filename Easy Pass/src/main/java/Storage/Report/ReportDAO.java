package Storage.Report;

import ApplicationLogic.Utils.ConnectionSingleton;
import Storage.PersonaleUnisa.Docente.Docente;
import Storage.PersonaleUnisa.Docente.DocenteMapper;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TreeMap;

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

    public TreeMap<Report, Docente> doRetrieveDocByReport(String idDip) throws SQLException {
        try(Connection connection = ConnectionSingleton.getInstance().getConnection()){
            String query="SELECT doc.*, rep.* FROM docente doc, report rep " +
                    "WHERE doc.Username_Doc = rep.Username_Doc " +
                    "and rep.Codice_Dip = ? ORDER BY rep.ID_report";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, idDip);
            TreeMap<Report, Docente> reportDocenteHashMap = new TreeMap<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                reportDocenteHashMap.put(ReportMapper.extract(rs), DocenteMapper.extract(rs));

            return reportDocenteHashMap;
        }
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
            String query = "INSERT INTO report (Orario, Data_report, PathFile, Codice_Dip, Username_Doc) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setTime(1, report.getOrario());
            ps.setDate(2, (Date) report.getData());
            ps.setString(3, report.getPathFile());
            ps.setString(4, report.getDip().getCodice());
            ps.setString(5, report.getDocente().getUsername());
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
                    "rep.PathFile=?, rep.Codice_Dip=?, rep.Username_Doc=? WHERE rep.ID_report=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setTime(1, report.getOrario());
            ps.setDate(2, (Date) report.getData());
            ps.setString(3, report.getPathFile());
            ps.setString(4, report.getDip().getCodice());
            ps.setString(5, report.getDocente().getUsername());
            ps.setInt(6, report.getId());

            return ps.executeUpdate() == 1;
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

            return ps.executeUpdate() == 1;
        }
    }

    public TreeMap<Report, Docente> doSearch(Docente docente, java.util.Date primaData, java.util.Date secondaData) throws SQLException {
        if(docente != null && primaData != null && secondaData != null){
            try(Connection connection = ConnectionSingleton.getInstance().getConnection()){
                String query="SELECT doc.*, rep.* FROM docente doc, report rep " +
                        "WHERE doc.Username_Doc = rep.Username_Doc " +
                        "and (rep.Data_report between ? and ?) " +
                        "and doc.Nome_Doc = ? and doc.Cognome_Doc = ?";

                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, convertToString(primaData));
                ps.setString(2, convertToString(secondaData));
                ps.setString(3, docente.getNome());
                ps.setString(4, docente.getCognome());

                TreeMap<Report, Docente> treeMap = new TreeMap<>();
                ResultSet rs = ps.executeQuery();
                while(rs.next())
                    treeMap.put(ReportMapper.extract(rs), DocenteMapper.extract(rs));
                return treeMap;
            }
        }
        else
            throw new IllegalArgumentException("The argument 'primaData', 'secondaData' and 'docente' cannot be null");
    }

    public TreeMap<Report, Docente> doSearchByDocName(Docente docente) throws SQLException {
        if(docente == null){
            throw new IllegalArgumentException("The argument 'docente' cannot be null");
        }
        else{
            try(Connection connection = ConnectionSingleton.getInstance().getConnection()){
                String query="SELECT doc.*, rep.* FROM docente doc, report rep " +
                        "WHERE doc.Username_Doc = rep.Username_Doc " +
                        "and doc.Nome_Doc = ? and doc.Cognome_Doc = ?";

                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1,docente.getNome());
                ps.setString(2,docente.getCognome());
                TreeMap<Report, Docente> treeMap = new TreeMap<>();
                ResultSet rs = ps.executeQuery();

                while(rs.next())
                    treeMap.put(ReportMapper.extract(rs), DocenteMapper.extract(rs));
                return treeMap;
            }
        }
    }

    public TreeMap<Report, Docente> doSearchByDate(java.util.Date primaData, java.util.Date secondaData) throws SQLException {
        if(primaData == null && secondaData == null){
            throw new IllegalArgumentException("The argument 'primaData' and 'secondaData' cannot be null.");
        }
        else{
            try(Connection connection = ConnectionSingleton.getInstance().getConnection()){
                String query="SELECT doc.*, rep.* FROM docente doc, report rep " +
                        "WHERE doc.Username_Doc = rep.Username_Doc " +
                        "and (rep.Data_report between ? and ?)";

                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, convertToString(primaData));
                ps.setString(2, convertToString(secondaData));
                TreeMap<Report, Docente> treeMap = new TreeMap<>();
                ResultSet rs = ps.executeQuery();

                while(rs.next())
                    treeMap.put(ReportMapper.extract(rs), DocenteMapper.extract(rs));
                return treeMap;
            }
        }
    }

    public Report doDownload(int idReport) throws SQLException {
        if(idReport < 0){
            throw new IllegalArgumentException("The argument 'idReport' cannot be negative.");
        } else{
            try(Connection connection = ConnectionSingleton.getInstance().getConnection()){
                String query="SELECT rep.* FROM report rep " +
                        "WHERE rep.ID_report = ?";

                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1, idReport);
                ResultSet rs = ps.executeQuery();

                if(rs.next())
                    return ReportMapper.extract(rs);
            }
        } return null;
    }

    private String convertToString(java.util.Date date){
        String pattern = "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }
}
