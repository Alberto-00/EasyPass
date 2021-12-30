package Storage.Esito;

import ApplicationLogic.Utils.ConnectionSingleton;
import Storage.Report.Report;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EsitoDAO {

    public Esito doRetrieveByKey(int id) throws SQLException {
        if(id<0){
            throw new IllegalArgumentException("The ID must not be a negative number");
        }
        else{
            ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
            try(Connection connection = connectionSingleton.getConnection()) {
                String query = "SELECT * FROM esito esi WHERE esi.ID_Esito=?";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    Esito esito = EsitoMapper.extract(rs);
                    return esito;
                }
                return null;
            }
        }
    }

    public List<Esito> doRetrieveWithRelations(int idReport) throws SQLException {
        if(idReport < 0){
            throw new IllegalArgumentException("The ID must not be a negative number");
        } else{
            try(Connection connection = ConnectionSingleton.getInstance().getConnection()) {
                String query = "SELECT esi.* FROM esito esi, report rep " +
                        "WHERE esi.ID_Report = rep.ID_report and rep.ID_report = ?";

                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1, idReport);
                ResultSet rs = ps.executeQuery();
                List<Esito> esiti = new ArrayList<>();

                while (rs.next())
                    esiti.add(EsitoMapper.extract(rs));

                return esiti;
            }
        }
    }

    public ArrayList<Esito> doRetrieveAll() throws SQLException {
        ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
        try(Connection connection = connectionSingleton.getConnection()) {
            String query = "SELECT * FROM esito esi";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            ArrayList<Esito> esiti = new ArrayList<>();
            while (rs.next()) {
                esiti.add(EsitoMapper.extract(rs));
            }
            return esiti;
        }
    }

    public boolean doCreate(Esito esito) throws SQLException {
        if(esito==null){
            throw new IllegalArgumentException("Cannot save a null object");
        }
        else {
            ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
            try(Connection connection = connectionSingleton.getConnection()) {
                String query = "INSERT INTO esito (Valido, ID_Report, Nome_Studente, Cognome_Studente, Ddn_Studente) VALUES (?,?,?,?,?)";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setBoolean(1, esito.isValidita());
                ps.setInt(2, esito.getReport().getId());
                ps.setString(3, esito.getNomeStudente());
                ps.setString(4, esito.getNomeStudente());
                ps.setDate(5, (Date) esito.getDataDiNascitaStudente());
                if (ps.executeUpdate() == 1)
                    return true;
                else return false;
            }
        }
    }

    public boolean doUpdate(Esito esito) throws SQLException{
        if(esito==null){
            throw new IllegalArgumentException("Cannot update a null object");
        }
        else{
            ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
            try(Connection connection = connectionSingleton.getConnection()) {
                String query = "UPDATE esito SET Valido=?, ID_Report=?, Nome_Studente=?, Cognome_Studente=?, Ddn_Studente=? WHERE ID_Esito=?";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setBoolean(1, esito.isValidita());
                ps.setInt(2, esito.getReport().getId());
                ps.setString(3, esito.getNomeStudente());
                ps.setString(4, esito.getNomeStudente());
                ps.setDate(5, (Date) esito.getDataDiNascitaStudente());
                ps.setInt(6, esito.getId());
                if (ps.executeUpdate() == 1)
                    return true;
                else return false;
            }
        }
    }

    public boolean doDelete(Esito esito) throws SQLException{
        if(esito==null){
            throw new IllegalArgumentException("Cannot delete a null object");
        }
        else{
            ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
            try(Connection connection = connectionSingleton.getConnection()) {
                String query = "DELETE FROM esito WHERE ID_Esito=?";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1, esito.getId());
                if (ps.executeUpdate() == 1)
                    return true;
                else return false;
            }
        }
    }
}
