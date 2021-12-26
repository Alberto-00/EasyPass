package Storage.Report;

import ApplicationLogic.Utils.ConnectionSingleton;

import java.sql.*;

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

    public boolean doCreate (Report report) throws SQLException {
        if (report == null)
            throw new IllegalArgumentException("Cannot save a null object");

        ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
        try (Connection connection = connectionSingleton.getConnection()) {
            String query = "INSERT INTO report VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);

            //L'id del report è auto increment, va gestito diversamente?
            ps.setInt(1, report.getId());

            ps.setTime(2, report.getOrario());
            ps.setDate(3, (Date) report.getData());
            ps.setString(4, report.getPathFile());
            ps.setString(5, report.getDip().getCodice());
            ps.setString(6, report.getSessione().getqRCode());
            if (ps.executeUpdate() == 1)
                return true;
            else return false;
        }
    }

    //doUpdate
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

}
