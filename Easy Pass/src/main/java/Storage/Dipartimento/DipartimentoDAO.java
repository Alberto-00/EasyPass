package Storage.Dipartimento;

import ApplicationLogic.Utils.ConnectionSingleton;
import Storage.Esito.Esito;
import Storage.Esito.EsitoMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DipartimentoDAO {

    public Dipartimento doRetrieveById(int codice) throws SQLException {
            ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
            try (Connection connection = connectionSingleton.getConnection()) {
                String query = "SELECT * FROM dipartimento dip WHERE dip.Codice_Dip=?";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1, codice);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    Dipartimento dipartimento = DipartimentoMapper.extract(rs);
                    return dipartimento;
                }
            }
        return null;
    }

    public ArrayList<Dipartimento> doRetrieveAll() throws SQLException {
        ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
        try(Connection connection = connectionSingleton.getConnection()) {
            String query = "SELECT * FROM dipartimento dip";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            ArrayList<Dipartimento> dipartimenti = new ArrayList<>();
            while (rs.next()) {
                dipartimenti.add(DipartimentoMapper.extract(rs));
            }
            return dipartimenti;
        }
    }

    public boolean doCreate (Dipartimento dipartimento) throws SQLException {
        if (dipartimento == null)
            throw new IllegalArgumentException("Cannot save a null object");

        ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
        try (Connection connection = connectionSingleton.getConnection()) {
            String query = "INSERT INTO dipartimento VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, dipartimento.getCodice());
            ps.setString(2, dipartimento.getNome());
            ps.setInt(3, dipartimento.getFormato().getId());

            if (ps.executeUpdate() == 1)
                return true;
            else return false;
        }
    }

    public boolean doUpdate (Dipartimento dipartimento) throws SQLException {
        if (dipartimento == null)
            throw new IllegalArgumentException("Cannot update a null object");

        ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
        try (Connection connection = connectionSingleton.getConnection()) {
            String query = "UPDATE dipartimento dip SET dip.Nome=? WHERE dip.Codice_Dip=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, dipartimento.getNome());
            //ps.setInt(2, dipartimento.getFormato().getId()); Non cambiamo mmai formato ma aggiorniamo lo stesso
            ps.setString(2, dipartimento.getCodice());

            if (ps.executeUpdate() == 1)
                return true;
            else return false;
        }
    }

    public boolean doDelete (Dipartimento dipartimento) throws SQLException {
        if (dipartimento == null)
            throw new IllegalArgumentException("Cannot delete a null object");

        ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
        try (Connection connection = connectionSingleton.getConnection()) {
            String query = "DELETE FROM dipartimento dip WHERE dip.Codice_Dip=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, dipartimento.getCodice());

            if (ps.executeUpdate() == 1)
                return true;
            else return false;
        }
    }
}
