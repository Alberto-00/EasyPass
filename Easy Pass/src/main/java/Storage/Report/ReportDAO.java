package Storage.Report;

import ApplicationLogic.Utils.ConnectionSingleton;
import Storage.Dipartimento.DipartimentoDAO;
import Storage.PersonaleUnisa.Docente.Docente;
import Storage.PersonaleUnisa.Docente.DocenteDAO;
import Storage.PersonaleUnisa.Docente.DocenteMapper;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TreeMap;

public class ReportDAO {

    public Report doRetrieveById(int id) {
        if(id < 0)
            throw new IllegalArgumentException("The id must not be negative");
        else {
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "SELECT * FROM report rep WHERE rep.ID_report=?";
                ps = conn.prepareStatement(query);
                ps.setInt(1, id);
                rs = ps.executeQuery();

                if (rs.next()) {
                    return ReportMapper.extract(rs);
                }
            } catch(SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, rs);
            }
        } return null;
    }

    public Report doRetrieveByIdWithRelations(int id) {
        if(id < 0)
            throw new IllegalArgumentException("The id must not be negative");
        else {
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "SELECT rep.* FROM report rep, dipartimento dip, docente doc " +
                        "WHERE rep.ID_report=? AND rep.Codice_Dip=dip.Codice_Dip AND doc.Username_Doc=rep.Username_Doc";
                ps = conn.prepareStatement(query);
                ps.setInt(1, id);
                rs = ps.executeQuery();

                if (rs.next()) {
                    Report report = ReportMapper.extract(rs);
                    String idDip = rs.getString("Codice_dip");
                    report.setDip(new DipartimentoDAO().doRetrieveByKeyWithRelations(idDip));
                    String doc = rs.getString("Username_Doc");
                    report.setDocente(new DocenteDAO().doRetrieveByKeyWithRelations(doc));
                    return report;
                }
            } catch(SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, rs);
            } return null;
        }
    }

    public TreeMap<Report, Docente> doRetrieveDocByReport(String idDip) {
        if(idDip == null)
            throw new IllegalArgumentException("The id must not be null");
        else {
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query="SELECT doc.*, rep.* FROM docente doc, report rep " +
                        "WHERE doc.Username_Doc = rep.Username_Doc " +
                        "and rep.Codice_Dip = ? ORDER BY rep.ID_report";
                ps = conn.prepareStatement(query);
                ps.setString(1, idDip);
                TreeMap<Report, Docente> reportDocenteHashMap = new TreeMap<>();
                rs = ps.executeQuery();

                while (rs.next())
                    reportDocenteHashMap.put(ReportMapper.extract(rs), DocenteMapper.extract(rs));
                return reportDocenteHashMap;
            } catch(SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, rs);
            } return null;
        }
    }

    public ArrayList<Report> doRetrieveAll() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = ConnectionSingleton.getInstance().getConnection();
            String query = "SELECT * FROM report rep";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Report> reports=new ArrayList<>();
            while(rs.next()) {
                reports.add(ReportMapper.extract(rs));
            }
            return reports;
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionSingleton.closeConnection(conn, ps, rs);
        } return null;
    }


    public boolean doCreate (Report report) {
        if (report == null)
            throw new IllegalArgumentException("Cannot save a null object");
        else {
            Connection conn = null;
            PreparedStatement ps = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "INSERT INTO report (Orario, Data_report, PathFile, Codice_Dip, Username_Doc) VALUES (?, ?, ?, ?, ?)";
                ps = conn.prepareStatement(query);
                ps.setTime(1, report.getOrario());
                ps.setDate(2, (Date) report.getData());
                ps.setString(3, report.getPathFile());
                ps.setString(4, report.getDip().getCodice());
                ps.setString(5, report.getDocente().getUsername());
                return ps.executeUpdate() == 1;
            } catch(SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, null);
            } return false;
        }
    }


    public boolean doUpdate (Report report) {
        if (report == null)
            throw new IllegalArgumentException("Cannot update a null object");
        else {
            Connection conn = null;
            PreparedStatement ps = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "UPDATE report rep SET rep.Orario=?, rep.Data_report=?, " +
                        "rep.PathFile=?, rep.Codice_Dip=?, rep.Username_Doc=? WHERE rep.ID_report=?";
                ps = conn.prepareStatement(query);
                ps.setTime(1, report.getOrario());
                ps.setDate(2, (Date) report.getData());
                ps.setString(3, report.getPathFile());
                ps.setString(4, report.getDip().getCodice());
                ps.setString(5, report.getDocente().getUsername());
                ps.setInt(6, report.getId());
                return ps.executeUpdate() == 1;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, null);
            }
        } return false;
    }

    public boolean doDelete (Report report) {
        if (report == null)
            throw new IllegalArgumentException("Cannot update a null object");
        else {
            Connection conn = null;
            PreparedStatement ps = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "DELETE FROM report rep WHERE rep.ID_report=?";
                ps = conn.prepareStatement(query);
                ps.setInt(1, report.getId());

                return ps.executeUpdate() == 1;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, null);
            }
        } return false;
    }

    public TreeMap<Report, Docente> doSearch(Docente docente, java.util.Date primaData, java.util.Date secondaData) {
        if(docente != null && primaData != null && secondaData != null){
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query="SELECT doc.*, rep.* FROM docente doc, report rep " +
                        "WHERE doc.Username_Doc = rep.Username_Doc " +
                        "and (rep.Data_report between ? and ?) " +
                        "and doc.Nome_Doc = ? and doc.Cognome_Doc = ?";

                ps = conn.prepareStatement(query);
                ps.setString(1, convertToString(primaData));
                ps.setString(2, convertToString(secondaData));
                ps.setString(3, docente.getNome());
                ps.setString(4, docente.getCognome());

                TreeMap<Report, Docente> treeMap = new TreeMap<>();
                rs = ps.executeQuery();
                while(rs.next())
                    treeMap.put(ReportMapper.extract(rs), DocenteMapper.extract(rs));
                return treeMap;
            }
            catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, rs);
            }
        }
        else
            throw new IllegalArgumentException("The argument 'primaData', 'secondaData' and 'docente' cannot be null");
        return null;
    }

    public TreeMap<Report, Docente> doSearchByDocName(Docente docente) {
        if(docente == null)
            throw new IllegalArgumentException("The argument 'docente' cannot be null");
        else{
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query="SELECT doc.*, rep.* FROM docente doc, report rep " +
                        "WHERE doc.Username_Doc = rep.Username_Doc " +
                        "and doc.Nome_Doc = ? and doc.Cognome_Doc = ?";

                ps = conn.prepareStatement(query);
                ps.setString(1,docente.getNome());
                ps.setString(2,docente.getCognome());
                TreeMap<Report, Docente> treeMap = new TreeMap<>();
                rs = ps.executeQuery();

                while(rs.next())
                    treeMap.put(ReportMapper.extract(rs), DocenteMapper.extract(rs));
                return treeMap;
            }  catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, rs);
            } return null;
        }
    }

    public TreeMap<Report, Docente> doSearchByDate(java.util.Date primaData, java.util.Date secondaData) {
        if(primaData == null && secondaData == null){
            throw new IllegalArgumentException("The argument 'primaData' and 'secondaData' cannot be null.");
        }
        else{
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query="SELECT doc.*, rep.* FROM docente doc, report rep " +
                        "WHERE doc.Username_Doc = rep.Username_Doc " +
                        "and (rep.Data_report between ? and ?)";

                ps = conn.prepareStatement(query);
                ps.setString(1, convertToString(primaData));
                ps.setString(2, convertToString(secondaData));
                TreeMap<Report, Docente> treeMap = new TreeMap<>();
                rs = ps.executeQuery();

                while(rs.next())
                    treeMap.put(ReportMapper.extract(rs), DocenteMapper.extract(rs));
                return treeMap;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, rs);
            } return null;
        }
    }

    public Report doDownload(int idReport) {
        if(idReport < 0){
            throw new IllegalArgumentException("The argument 'idReport' cannot be negative.");
        } else{
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query="SELECT rep.* FROM report rep " +
                        "WHERE rep.ID_report = ?";

                ps = conn.prepareStatement(query);
                ps.setInt(1, idReport);
                rs = ps.executeQuery();

                if(rs.next())
                    return ReportMapper.extract(rs);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, rs);
            } return null;
        }
    }

    private String convertToString(java.util.Date date){
        String pattern = "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }
}