package Storage.SessioneDiValidazione;

import ApplicationLogic.Utils.ConnectionSingleton;
import Storage.Esito.Esito;
import Storage.Esito.EsitoMapper;
import Storage.PersonaleUnisa.Docente.Docente;
import Storage.PersonaleUnisa.Docente.DocenteMapper;
import Storage.Report.Report;
import Storage.Report.ReportMapper;

import javax.print.Doc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SessioneDiValidazioneDAO {

    public SessioneDiValidazione doRetrieveById(int codice) throws SQLException {
        ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
        try (Connection connection = connectionSingleton.getConnection()) {
            String query = "SELECT * FROM sessione ses WHERE ses.QRcode LIKE ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, "%"+codice+"%");
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                SessioneDiValidazione sessione = SessioneDiValidazioneMapper.extract(rs);
                return sessione;
            }
        }
        return null;
    }

    public ArrayList<SessioneDiValidazione> doRetrieveAll() throws SQLException {
        ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
        try(Connection connection = connectionSingleton.getConnection()) {
            String query = "SELECT * FROM sessione ses";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            ArrayList<SessioneDiValidazione> sessioni = new ArrayList<>();
            while (rs.next()) {
                sessioni.add(SessioneDiValidazioneMapper.extract(rs));
            }
            return sessioni;
        }
    }

    public boolean doCreate (SessioneDiValidazione sessione) throws SQLException {
        if (sessione == null)
            throw new IllegalArgumentException("Cannot save a null object");

        ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
        try (Connection connection = connectionSingleton.getConnection()) {
            String query = "INSERT INTO sessione VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, sessione.getqRCode());
            ps.setString(2, sessione.getDocente().getUsername());
            ps.setBoolean(3, sessione.isInCorso());

            if (ps.executeUpdate() == 1)
                return true;
            else return false;
        }
    }

    public boolean doUpdate (SessioneDiValidazione sessione) throws SQLException {
        if (sessione == null)
            throw new IllegalArgumentException("Cannot update a null object");

        ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
        try (Connection connection = connectionSingleton.getConnection()) {
            String query = "UPDATE sessione ses SET ses.Username_Doc=?, ses.isInCorso=? " +
                    "WHERE ses.QRcode=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, sessione.getDocente().getUsername());
            ps.setBoolean(2, sessione.isInCorso());
            ps.setString(3, sessione.getqRCode());

            if (ps.executeUpdate() == 1)
                return true;
            else return false;
        }
    }

    //doDelete serve? dove si usa?

    public boolean doDelete (SessioneDiValidazione sessione) throws SQLException {
        if (sessione == null)
            throw new IllegalArgumentException("Cannot delete a null object");

        ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
        try (Connection connection = connectionSingleton.getConnection()) {
            String query = "DELETE FROM sessione ses WHERE ses.QRcode=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, sessione.getqRCode());

            if (ps.executeUpdate() == 1)
                return true;
            else return false;
        }
    }
}
