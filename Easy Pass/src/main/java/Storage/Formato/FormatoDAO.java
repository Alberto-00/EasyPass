package Storage.Formato;

import ApplicationLogic.Utils.ConnectionSingleton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FormatoDAO {

    public Formato doRetrieveById(int id) throws SQLException {
        ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
        try (Connection connection = connectionSingleton.getConnection()) {
            String query = "SELECT * FROM formato form WHERE form.ID_formato=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Formato formato = FormatoMapper.extract(rs);
                return formato;
            }
        }
        return null;
    }

    //doCreate serve? dove si usa?

    public boolean doCreate (Formato formato) throws SQLException {
        if (formato == null)
            throw new IllegalArgumentException("Cannot save a null object");

        ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
        try (Connection connection = connectionSingleton.getConnection()) {
            String query = "INSERT INTO formato VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, formato.getId());
            ps.setBoolean(2, formato.isNumStudenti());
            ps.setBoolean(3, formato.isNumGPValidi());
            ps.setBoolean(4, formato.isNumGPNonValidi());
            ps.setBoolean(5, formato.isNomeCognome());
            ps.setBoolean(6, formato.isData());
            if (ps.executeUpdate() == 1)
                return true;
            else return false;
        }
    }

    //doUpdate
    public boolean doUpdate (Formato formato) throws SQLException {
        if (formato == null)
            throw new IllegalArgumentException("Cannot update a null object");

        ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
        try (Connection connection = connectionSingleton.getConnection()) {
            String query = "UPDATE formato form SET form.N_studenti=?, form.GP_validi=?," +
                    "form.GP_nonValidi=?, form.Nome_Cognome=?, form.Ddn=? WHERE form.ID_formato=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setBoolean(1, formato.isNumStudenti());
            ps.setBoolean(2, formato.isNumGPValidi());
            ps.setBoolean(3, formato.isNumGPNonValidi());
            ps.setBoolean(4, formato.isNomeCognome());
            ps.setBoolean(5, formato.isData());
            ps.setInt(6, formato.getId());

            if (ps.executeUpdate() == 1)
                return true;
            else return false;
        }
    }

    //doDelete serve? dove si usa?

    public boolean doDelete (Formato formato) throws SQLException {
        if (formato == null)
            throw new IllegalArgumentException("Cannot delete a null object");

        ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
        try (Connection connection = connectionSingleton.getConnection()) {
            String query = "DELETE FROM formato form WHERE form.ID_formato=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, formato.getId());

            if (ps.executeUpdate() == 1)
                return true;
            else return false;
        }
    }

}
