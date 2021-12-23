package Storage.Esito;

import ApplicationLogic.Utils.ConnectionSingleton;

import java.sql.*;
import java.util.ArrayList;

public class EsitoDAO {

    public Esito doRetieveByKey(int id) throws SQLException {
        if(id<0){
            throw new IllegalArgumentException("The ID must not be a negative number");
        }
        else{
            ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
            Connection connection = connectionSingleton.getConnection();
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

    //Implementare doRetrieveWithRelations oppure fare il retrieve del Report nel metodo doRetrieveById

    public ArrayList<Esito> doRetieveAll() throws SQLException {
        ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
        Connection connection = connectionSingleton.getConnection();
        String query = "SELECT * FROM esito esi";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        ArrayList<Esito> esiti=new ArrayList<>();
        while(rs.next()) {
            esiti.add(EsitoMapper.extract(rs));
        }
        return esiti;
    }

    public boolean doSave(Esito esito) throws SQLException {
        if(esito==null){
            throw new IllegalArgumentException("Cannot save a null object");
        }
        else {
            ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
            Connection connection = connectionSingleton.getConnection();
            String query ="INSERT INTO esito VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, esito.getId());
            ps.setBoolean(2,esito.isValidita());
            ps.setInt(3,esito.getReport().getId());
            ps.setString(4,esito.getNomeStudente());
            ps.setString(5,esito.getNomeStudente());
            ps.setDate(6, (Date) esito.getDataDiNascitaStudente());
            if (ps.executeUpdate()==1)
                return true;
            else return false;
        }
    }

    public boolean doUpdate(Esito esito) throws SQLException{
        if(esito==null){
            throw new IllegalArgumentException("Cannot update a null object");
        }
        else{
            ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
            Connection connection = connectionSingleton.getConnection();
            String query="UPDATE esito SET Valido=?, ID_Report=?, Nome_Studente=?, Cognome_Studente=?, Ddn_Studente=? WHERE ID_Esito=?";
            PreparedStatement ps=connection.prepareStatement(query);
            ps.setBoolean(1,esito.isValidita());
            ps.setInt(2,esito.getReport().getId());
            ps.setString(3,esito.getNomeStudente());
            ps.setString(4,esito.getNomeStudente());
            ps.setDate(5, (Date) esito.getDataDiNascitaStudente());
            ps.setInt(6,esito.getId());
            if(ps.executeUpdate()==1)
                return true;
            else return false;
        }
    }

    public boolean doDelete(Esito esito) throws SQLException{
        if(esito==null){
            throw new IllegalArgumentException("Cannot delete a null object");
        }
        else{
            ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
            Connection connection = connectionSingleton.getConnection();
            String query="DELETE FROM esito WHERE ID_Esito=?";
            PreparedStatement ps=connection.prepareStatement(query);
            ps.setInt(1,esito.getId());
            if(ps.executeUpdate()==1)
                return true;
            else return false;
        }
    }
}
