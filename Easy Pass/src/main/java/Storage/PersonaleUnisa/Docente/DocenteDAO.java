package Storage.PersonaleUnisa.Docente;

import ApplicationLogic.Utils.ConnectionSingleton;
import Storage.PersonaleUnisa.Direttore.DirettoreDiDipartimento;
import Storage.PersonaleUnisa.Direttore.DirettoreDiDipartimentoMapper;

import javax.print.Doc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DocenteDAO {
    public Docente doRetieveByKey(String username) throws SQLException {
        if(username==null){
            throw new IllegalArgumentException("The username must not be null");
        }
        else{
            ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
            Connection connection = connectionSingleton.getConnection();
            String query = "SELECT * FROM docente doc WHERE doc.Username_Doc=?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Docente docente = DocenteMapper.extract(rs);
                return docente;
            }
            return null;
        }
    }

    //Implementare doRetrieveWithRelations oppure fare il retrieve delle relazioni nel metodo doRetrieveById?

    public ArrayList<Docente> doRetieveAll() throws SQLException {
        ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
        Connection connection = connectionSingleton.getConnection();
        String query = "SELECT * FROM docente doc";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        ArrayList<Docente> docenti=new ArrayList<>();
        while(rs.next()) {
            docenti.add(DocenteMapper.extract(rs));
        }
        return docenti;
    }

    public boolean doSave(Docente docente) throws SQLException {
        if(docente==null){
            throw new IllegalArgumentException("Cannot save a null object");
        }
        else {
            ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
            Connection connection = connectionSingleton.getConnection();
            String query ="INSERT INTO direttore VALUES (?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,docente.getUsername());
            ps.setString(2,docente.getNome());
            ps.setString(3, docente.getCognome());
            ps.setString(4, docente.getPassword());
            ps.setString(5,docente.getDipartimento().getCodice());
            //Manca il salvataggio delle sessioni del docente, va fatto con il metodo doSave del DAO delle sessioni,
            // appena viene implementato quello si può fare questo
            if (ps.executeUpdate()==1)
                return true;
            else return false;
        }
    }

    public boolean doUpdate(Docente docente) throws SQLException{
        if(docente==null){
            throw new IllegalArgumentException("Cannot update a null object");
        }
        else{
            ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
            Connection connection = connectionSingleton.getConnection();
            String query="UPDATE docente SET Nome_Doc=?, Cognome_Doc=?, Password_Doc=?, Codice_Dip=? WHERE Username_Doc=?";
            PreparedStatement ps=connection.prepareStatement(query);
            ps.setString(1,docente.getNome());
            ps.setString(2,docente.getCognome());
            ps.setString(3,docente.getPassword());
            ps.setString(4,docente.getDipartimento().getCodice());
            //Manca l'update delle sessioni che va fatto sempre con il doSave del DAO delle sessioni come sopra
            if(ps.executeUpdate()==1)
                return true;
            else return false;
        }
    }

    public boolean doDelete(Docente docente) throws SQLException{
        if(docente==null){
            throw new IllegalArgumentException("Cannot delete a null object");
        }
        else{
            ConnectionSingleton connectionSingleton = ConnectionSingleton.getInstance();
            Connection connection = connectionSingleton.getConnection();
            String query="DELETE FROM docente WHERE Username_Doc=?";
            PreparedStatement ps=connection.prepareStatement(query);
            ps.setString(1,docente.getUsername());
            if(ps.executeUpdate()==1)
                return true;
            else return false;
        }
    }
}
