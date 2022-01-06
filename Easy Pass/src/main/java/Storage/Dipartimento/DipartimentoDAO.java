package Storage.Dipartimento;

import ApplicationLogic.Utils.ConnectionSingleton;
import Storage.Formato.FormatoDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DipartimentoDAO {

    public Dipartimento doRetrieveById(String codice) {
        if(codice == null)
            throw new IllegalArgumentException("The id must not be null");
        else {
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "SELECT * FROM dipartimento dip WHERE dip.Codice_Dip=?";
                ps = conn.prepareStatement(query);
                ps.setString(1, codice);
                rs = ps.executeQuery();

                if (rs.next())
                    return DipartimentoMapper.extract(rs);
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, rs);
            }
        }
         return null;
    }

    public Dipartimento doRetrieveByKeyWithRelations(String codice) {
        if(codice == null)
            throw new IllegalArgumentException("The id must not be null");
        else {
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "SELECT * FROM dipartimento dip, formato form" +
                        " WHERE dip.Codice_Dip=? and dip.ID_formato = form.ID_formato";
                ps = conn.prepareStatement(query);
                ps.setString(1, codice);
                rs = ps.executeQuery();

                if (rs.next()) {
                    Dipartimento dipartimento = DipartimentoMapper.extract(rs);
                    String idFormato=rs.getString("dip.ID_Formato");
                    dipartimento.setFormato(new FormatoDAO().doRetrieveById(idFormato));
                    //TODO: settare anhe l'array di report facendo il retrieve dal db
                    return dipartimento;
                }
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, rs);
            }
            return null;
        }
    }

    public ArrayList<Dipartimento> doRetrieveAll() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = ConnectionSingleton.getInstance().getConnection();
            String query = "SELECT * FROM dipartimento dip";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Dipartimento> dipartimenti = new ArrayList<>();

            while (rs.next())
                dipartimenti.add(DipartimentoMapper.extract(rs));

            return dipartimenti;
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            ConnectionSingleton.closeConnection(conn, ps, rs);
        } return null;
    }

    public boolean doCreate (Dipartimento dipartimento) {
        if (dipartimento == null)
            throw new IllegalArgumentException("Cannot save a null object");
        else {
            Connection conn = null;
            PreparedStatement ps = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "INSERT INTO dipartimento VALUES (?, ?, ?)";
                ps = conn.prepareStatement(query);
                ps.setString(1, dipartimento.getCodice());
                ps.setString(2, dipartimento.getNome());
                ps.setString(3, dipartimento.getFormato().getId());

                return ps.executeUpdate() == 1;
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, null);
            } return false;
        }
    }

    public boolean doUpdate (Dipartimento dipartimento) {
        if (dipartimento == null)
            throw new IllegalArgumentException("Cannot update a null object");
        else {
            Connection conn = null;
            PreparedStatement ps = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "UPDATE dipartimento dip SET dip.Nome=? WHERE dip.Codice_Dip=?";
                ps = conn.prepareStatement(query);
                ps.setString(1, dipartimento.getNome());
                ps.setString(2, dipartimento.getCodice());

                return ps.executeUpdate() == 1;
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, null);
            } return false;
        }
    }

    public boolean doDelete (Dipartimento dipartimento)  {
        if (dipartimento == null)
            throw new IllegalArgumentException("Cannot delete a null object");

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConnectionSingleton.getInstance().getConnection();
            String query = "DELETE FROM dipartimento dip WHERE dip.Codice_Dip=?";
            ps = conn.prepareStatement(query);
            ps.setString(1, dipartimento.getCodice());

            return ps.executeUpdate() == 1;
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            ConnectionSingleton.closeConnection(conn, ps, null);
        } return false;
    }
}
