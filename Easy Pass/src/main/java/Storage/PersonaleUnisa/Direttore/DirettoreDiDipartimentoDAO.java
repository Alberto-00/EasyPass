package Storage.PersonaleUnisa.Direttore;

import ApplicationLogic.Utils.ConnectionSingleton;
import Storage.Dipartimento.DipartimentoDAO;
import Storage.Dipartimento.Dipartimento;

import java.sql.*;
import java.util.ArrayList;

public class DirettoreDiDipartimentoDAO {

    public DirettoreDiDipartimento doRetrieveByKey(String username) {
        if(username == null)
            throw new IllegalArgumentException("The username must not be null");
        else {
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "SELECT * FROM direttore dir WHERE dir.Username_Dir=?";
                ps = conn.prepareStatement(query);
                ps.setString(1, username);
                rs = ps.executeQuery();

                if (rs.next()) {
                    DirettoreDiDipartimento direttore = DirettoreDiDipartimentoMapper.extract(rs);
                    Dipartimento dipartimento = new Dipartimento();
                    dipartimento.setCodice(rs.getString("dir.Codice_Dip"));
                    direttore.setDipartimento(dipartimento);
                    return direttore;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, rs);
            } return null;
        }
    }

    public boolean checkUserAndPassw(DirettoreDiDipartimento direttore) {
        if(direttore == null)
            throw new IllegalArgumentException("The direttore must not be null");
        else {
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "SELECT * FROM direttore dir WHERE dir.Username_Dir = ? and dir.Password_Dir = ?";
                ps = conn.prepareStatement(query);
                ps.setString(1, direttore.getUsername());
                ps.setString(2, direttore.getPassword());
                rs = ps.executeQuery();
                return rs.next();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, rs);
            } return false;
        }
    }

    public DirettoreDiDipartimento doRetrieveByKeyWithRelations(String username) {
        if(username==null)
            throw new IllegalArgumentException("The username must not be null");
        else{
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "SELECT * FROM direttore dir WHERE dir.Username_Dir=?";
                ps = conn.prepareStatement(query);
                ps.setString(1, username);
                rs = ps.executeQuery();

                if (rs.next()) {
                    DirettoreDiDipartimento direttore = DirettoreDiDipartimentoMapper.extract(rs);
                    String codiceDip=rs.getString("dir.Codice_Dip");
                    direttore.setDipartimento(new DipartimentoDAO().doRetrieveByKeyWithRelations(codiceDip));
                    return direttore;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, rs);
            } return null;
        }
    }


    public ArrayList<DirettoreDiDipartimento> doRetieveAll() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = ConnectionSingleton.getInstance().getConnection();
            String query = "SELECT * FROM direttore dir";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<DirettoreDiDipartimento> direttori = new ArrayList<>();

            while (rs.next())
                direttori.add(DirettoreDiDipartimentoMapper.extract(rs));
            return direttori;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionSingleton.closeConnection(conn, ps, rs);
        } return null;
    }

    public boolean doCreate(DirettoreDiDipartimento direttore) {
        if(direttore == null)
            throw new IllegalArgumentException("Cannot save a null object");
        else {
            Connection conn = null;
            PreparedStatement ps = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "INSERT INTO direttore VALUES (?,?,?,?,?)";
                ps = conn.prepareStatement(query);
                ps.setString(1, direttore.getUsername());
                ps.setString(2, direttore.getNome());
                ps.setString(3, direttore.getCognome().toUpperCase());
                ps.setString(4, direttore.getPassword());
                ps.setString(5, direttore.getDipartimento().getCodice());
                return ps.executeUpdate() == 1;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, null);
            } return false;
        }
    }

    public boolean doUpdate(DirettoreDiDipartimento direttore) {
        if(direttore == null)
            throw new IllegalArgumentException("Cannot update a null object");
        else{
            Connection conn = null;
            PreparedStatement ps = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "UPDATE direttore SET Nome_Dir=?, Cognome_Dir=?, Password_Dir=?, Codice_Dip=? WHERE Username_Dir=?";
                ps = conn.prepareStatement(query);
                ps.setString(1, direttore.getNome());
                ps.setString(2, direttore.getCognome());
                ps.setString(3, direttore.getPassword());
                ps.setString(4, direttore.getDipartimento().getCodice());
                return ps.executeUpdate() == 1;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, null);
            } return false;
        }
    }

    public boolean doDelete(DirettoreDiDipartimento direttore) {
        if(direttore == null)
            throw new IllegalArgumentException("Cannot delete a null object");
        else{
            Connection conn = null;
            PreparedStatement ps = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "DELETE FROM direttore WHERE Username_Dir=?";
                ps = conn.prepareStatement(query);
                ps.setString(1, direttore.getUsername());
                if (ps.executeUpdate() == 1)
                    return true;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, null);
            } return false;
        }
    }
}
