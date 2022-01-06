package Storage.Formato;

import ApplicationLogic.Utils.ConnectionSingleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FormatoDAO {

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

    public ArrayList<Formato> doRetrieveAll() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = ConnectionSingleton.getInstance().getConnection();
            String query = "SELECT * FROM formato form";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Formato> formati = new ArrayList<>();

            while (rs.next())
                formati.add(FormatoMapper.extract(rs));
            return formati;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionSingleton.closeConnection(conn, ps, rs);
        } return null;
    }


    public boolean doCreate (Formato formato) {
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

    public boolean doDelete (Formato formato) {
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
