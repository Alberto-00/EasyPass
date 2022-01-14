package Storage.SessioneDiValidazione;

import ApplicationLogic.Utils.ConnectionSingleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@interface Generated {}

/**
 * La classe effettua operazioni {@literal CRUD}, sulla tabella {@code sessione}.
 */
public class SessioneDiValidazioneDAO {

    /**
     * Effettua una query al database restituendo la {@code SessioneDiValidazione}
     * con un determinato {@code codice}.
     *
     * @param codice codice della {@code SessioneDiValidazione}
     * @return {@code SessioneDiValidazione}
     */
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
                ps.setString(1, "%" + codice + "."+"%");
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

    /**
     * Salva nel database una nuova {@code SessioneDiValidazione}.
     *
     * @param sessione nuovo {@code SessioneDiValidazione} da salvare
     * @return {@code true} se la {@code SessioneDiValidazione} &egrave; stata creata,
     * {@code false} altrimenti
     */
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
                ps.setString(1, sessione.getQRCode());
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

    /**
     * Aggiorna nel database una {@code SessioneDiValidazione} esistente
     *
     * @param sessione {@code SessioneDiValidazione} da aggiornare
     * @return {@code true} se la {@code SessioneDiValidazione} &egrave; stata aggiornata,
     * {@code false} altrimenti
     */
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
                ps.setString(3, sessione.getQRCode());
                return ps.executeUpdate() == 1;

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, null);
            } return false;
        }
    }

    /**
     * Elimina nel database una {@code SessioneDiValidazione} esistente
     *
     * @param sessione {@code SessioneDiValidazione} da eliminare
     * @return {@code true} se la {@code SessioneDiValidazione} &egrave; stata eliminata,
     * {@code false} altrimenti
     */
    @Generated
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
                ps.setString(1, sessione.getQRCode());

                return ps.executeUpdate() == 1;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, null);
            } return false;
        }
    }
}
