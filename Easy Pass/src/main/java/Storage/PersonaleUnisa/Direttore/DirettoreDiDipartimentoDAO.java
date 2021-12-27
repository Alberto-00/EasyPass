package Storage.PersonaleUnisa.Direttore;

import ApplicationLogic.Utils.ConnectionSingleton;
import Storage.Esito.Esito;
import Storage.Esito.EsitoMapper;

import java.sql.*;
import java.util.ArrayList;

public class DirettoreDiDipartimentoDAO {
    public DirettoreDiDipartimento doRetieveByKey(String username) throws SQLException {
        if(username==null){
            throw new IllegalArgumentException("The username must not be null");
        }
        else{
            ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
            try(Connection connection = connectionSingleton.getConnection()) {
                String query = "SELECT * FROM direttore dir WHERE dir.Username_Dir=?";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    DirettoreDiDipartimento direttore = DirettoreDiDipartimentoMapper.extract(rs);
                    return direttore;
                }
                return null;
            }
        }
    }

    //Implementare doRetrieveWithRelations

    public ArrayList<DirettoreDiDipartimento> doRetieveAll() throws SQLException {
        ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
        try(Connection connection = connectionSingleton.getConnection()) {
            String query = "SELECT * FROM direttore dir";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            ArrayList<DirettoreDiDipartimento> direttori = new ArrayList<>();
            while (rs.next()) {
                direttori.add(DirettoreDiDipartimentoMapper.extract(rs));
            }
            return direttori;
        }
    }

    public boolean doCreate(DirettoreDiDipartimento direttore) throws SQLException {
        if(direttore==null){
            throw new IllegalArgumentException("Cannot save a null object");
        }
        else {
            ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
            try(Connection connection = connectionSingleton.getConnection()) {
                String query = "INSERT INTO direttore VALUES (?,?,?,?,?)";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, direttore.getUsername());
                ps.setString(2, direttore.getNome());
                ps.setString(3, direttore.getCognome());
                ps.setString(4, direttore.getPassword());
                ps.setString(5, direttore.getDipartimento().getCodice());
                if (ps.executeUpdate() == 1)
                    return true;
                else return false;
            }
        }
    }

    public boolean doUpdate(DirettoreDiDipartimento direttore) throws SQLException{
        if(direttore==null){
            throw new IllegalArgumentException("Cannot update a null object");
        }
        else{
            ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
            try(Connection connection = connectionSingleton.getConnection()) {
                String query = "UPDATE direttore SET Nome_Dir=?, Cognome_Dir=?, Password_Dir=?, Codice_Dip=? WHERE Username_Dir=?";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, direttore.getNome());
                ps.setString(2, direttore.getCognome());
                ps.setString(3, direttore.getPassword());
                ps.setString(4, direttore.getDipartimento().getCodice());
                if (ps.executeUpdate() == 1)
                    return true;
                else return false;
            }
        }
    }

    public boolean doDelete(DirettoreDiDipartimento direttore) throws SQLException{
        if(direttore==null){
            throw new IllegalArgumentException("Cannot delete a null object");
        }
        else{
            ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
            try(Connection connection = connectionSingleton.getConnection()) {
                String query = "DELETE FROM direttore WHERE Username_Dir=?";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, direttore.getUsername());
                if (ps.executeUpdate() == 1)
                    return true;
                else return false;
            }
        }
    }
}
