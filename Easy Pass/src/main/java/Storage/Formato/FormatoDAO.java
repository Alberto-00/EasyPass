package Storage.Formato;

import ApplicationLogic.Utils.ConnectionSingleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@interface Generated {}

/**
 * La classe effettua operazioni {@literal CRUD} sulla tabella {@code formato}
 */
public class FormatoDAO {

    /**
     * Effettua una query al database restituendo il {@code Formato}
     * con un determinato {@code ID}.
     *
     * @param id identificativo del {@code Formato}
     * @return {@code Formato}
     */
    public Formato doRetrieveById(String id) {
        if(id == null)
            throw new IllegalArgumentException("The id must not be null");
        else {
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "SELECT * FROM formato form WHERE form.ID_formato=?";
                ps = conn.prepareStatement(query);
                ps.setString(1, id);
                rs = ps.executeQuery();

                if (rs.next())
                    return FormatoMapper.extract(rs);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, rs);
            } return null;
        }
    }

    /**
     * Salva nel database un nuovo {@code Formato}.
     *
     * @param formato nuovo {@code Formato} da salvare
     * @return {@code true} se il {@code Formato} &egrave; stato creato,
     * {@code false} altrimenti
     */
    @Generated
     public boolean doCreate (Formato formato) { //Non viene testato perchè non è usato da nessuna parte
        if (formato == null)
            throw new IllegalArgumentException("Cannot save a null object");
        else {
            Connection conn = null;
            PreparedStatement ps = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "INSERT INTO formato VALUES (?,?, ?, ?, ?, ?)";
                ps = conn.prepareStatement(query);
                ps.setString(1,formato.getId());
                ps.setBoolean(2, formato.isNumStudenti());
                ps.setBoolean(3, formato.isNumGPValidi());
                ps.setBoolean(4, formato.isNumGPNonValidi());
                ps.setBoolean(5, formato.isNomeCognome());
                ps.setBoolean(6, formato.isData());
                return ps.executeUpdate() == 1;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, null);
            } return false;
        }
    }

    /**
     * Salva nel database un nuovo {@code Formato}.
     *
     * @param formato nuovo {@code Formato} da salvare
     * @return {@code true} se il {@code Formato} &egrave; stato creato,
     * {@code false} altrimenti
     */
    public boolean doUpdate (Formato formato) {
        if (formato == null)
            throw new IllegalArgumentException("Cannot update a null object");
        else {
            Connection conn = null;
            PreparedStatement ps = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "UPDATE formato form SET form.N_studenti=?, form.GP_validi=?," +
                        "form.GP_nonValidi=?, form.Nome_Cognome=?, form.Ddn=? WHERE form.ID_formato=?";
                ps = conn.prepareStatement(query);
                ps.setBoolean(1, formato.isNumStudenti());
                ps.setBoolean(2, formato.isNumGPValidi());
                ps.setBoolean(3, formato.isNumGPNonValidi());
                ps.setBoolean(4, formato.isNomeCognome());
                ps.setBoolean(5, formato.isData());
                ps.setString(6, formato.getId());

                return ps.executeUpdate() == 1;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, null);
            } return false;
        }
    }

    /**
     * Viene eliminato un {@code Formato} dal database.
     *
     * @param formato {@code Formato} da eliminare
     * @return {@code true} se il {@code Formato} &egrave; stato eliminato,
     * {@code false} altrimenti
     */
    @Generated
    public boolean doDelete (Formato formato) { //Non viene testato perchè non è usato da nessuna parte
        if (formato == null)
            throw new IllegalArgumentException("Cannot delete a null object");
        else {
            Connection conn = null;
            PreparedStatement ps = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "DELETE FROM formato form WHERE form.ID_formato=?";
                ps = conn.prepareStatement(query);
                ps.setString(1, formato.getId());
                return ps.executeUpdate() == 1;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, null);
            } return false;
        }
    }

}
