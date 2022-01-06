package Storage.SessioneDiValidazione;

import ApplicationLogic.Utils.ConnectionSingleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SessioneDiValidazioneDAO {

    public SessioneDiValidazione doRetrieveById(int codice) {
        if(codice < 0)
            throw new IllegalArgumentException("The 'codice' must not be null");
        else {
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "SELECT * FROM sessione ses WHERE ses.QRcode LIKE ?";
                ps = conn.prepareStatement(query);
                ps.setString(1, "%" + codice + "%");
                rs = ps.executeQuery();

                if (rs.next())
                    return SessioneDiValidazioneMapper.extract(rs);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, rs);
            } return null;
        }
    }

    public ArrayList<SessioneDiValidazione> doRetrieveAll() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = ConnectionSingleton.getInstance().getConnection();
            String query = "SELECT * FROM sessione ses";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<SessioneDiValidazione> sessioni = new ArrayList<>();

            while (rs.next())
                sessioni.add(SessioneDiValidazioneMapper.extract(rs));
            return sessioni;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionSingleton.closeConnection(conn, ps, rs);
        } return null;
    }

    public boolean doCreate (SessioneDiValidazione sessione) {
        if (sessione == null)
            throw new IllegalArgumentException("Cannot save a null object");
        else {
            Connection conn = null;
            PreparedStatement ps = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "INSERT INTO sessione VALUES (?, ?, ?)";
                ps = conn.prepareStatement(query);
                ps.setString(1, sessione.getqRCode());
                ps.setString(2, sessione.getDocente().getUsername());
                ps.setBoolean(3, sessione.isInCorso());
                return ps.executeUpdate() == 1;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, null);
            } return false;
        }
    }

    public boolean doUpdate (SessioneDiValidazione sessione) {
        if (sessione == null)
            throw new IllegalArgumentException("Cannot update a null object");
        else {
            Connection conn = null;
            PreparedStatement ps = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "UPDATE sessione ses SET ses.Username_Doc=?, ses.isInCorso=? " +
                        "WHERE ses.QRcode=?";
                ps = conn.prepareStatement(query);
                ps.setString(1, sessione.getDocente().getUsername());
                ps.setBoolean(2, sessione.isInCorso());
                ps.setString(3, sessione.getqRCode());
                return ps.executeUpdate() == 1;

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, null);
            } return false;
        }
    }

    //doDelete serve? dove si usa?

    public boolean doDelete (SessioneDiValidazione sessione) {
        if (sessione == null)
            throw new IllegalArgumentException("Cannot delete a null object");
        else {
            Connection conn = null;
            PreparedStatement ps = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "DELETE FROM sessione ses WHERE ses.QRcode=?";
                ps = conn.prepareStatement(query);
                ps.setString(1, sessione.getqRCode());

                return ps.executeUpdate() == 1;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, null);
            } return false;
        }
    }
}
