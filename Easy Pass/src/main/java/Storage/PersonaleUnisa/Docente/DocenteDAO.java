package Storage.PersonaleUnisa.Docente;

import ApplicationLogic.Utils.ConnectionSingleton;
import Storage.Dipartimento.Dipartimento;
import Storage.SessioneDiValidazione.SessioneDiValidazione;
import Storage.SessioneDiValidazione.SessioneDiValidazioneDAO;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DocenteDAO {

    public Docente doRetrieveByKey(String username) {
        if(username == null)
            throw new IllegalArgumentException("The username must not be null");
        else{
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "SELECT * FROM docente doc WHERE doc.Username_Doc=?";
                ps = conn.prepareStatement(query);
                ps.setString(1, username);
                rs = ps.executeQuery();

                if (rs.next())
                    return DocenteMapper.extract(rs);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, rs);
            } return null;
        }
    }

    public Docente doRetrieveByKeyWithRelations(String username) {
        if(username == null)
            throw new IllegalArgumentException("The username must not be null");
        else{
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "SELECT * FROM docente doc WHERE doc.Username_Doc=?";
                ps = conn.prepareStatement(query);
                ps.setString(1, username);
                rs = ps.executeQuery();

                if (rs.next()) {
                    Docente docente = DocenteMapper.extract(rs);
                    docente.setDipartimento(new Dipartimento());
                    docente.getDipartimento().setCodice(rs.getString("doc.Codice_Dip"));
                    //TODO: Mancano le sessioni
                    return docente;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, rs);
            } return null;
        }
    }

    public ArrayList<Docente> doRetrieveAllWithRelations(String codDip) {
        if(codDip == null)
            throw new IllegalArgumentException("The 'codDip' must not be null");
        else{
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "SELECT * FROM docente doc WHERE doc.Codice_Dip = ?";
                ps = conn.prepareStatement(query);
                ps.setString(1, codDip);
                rs = ps.executeQuery();
                ArrayList<Docente> docenti = new ArrayList<>();

                for (int i = 0; rs.next(); i++) {
                    docenti.add(DocenteMapper.extract(rs));
                    docenti.get(i).setDipartimento(new Dipartimento());
                    docenti.get(i).getDipartimento().setCodice(codDip);
                }
                return docenti;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, rs);
            } return null;
        }
    }

    public ArrayList<Docente> doRetrieveAll() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = ConnectionSingleton.getInstance().getConnection();
            String query = "SELECT * FROM docente doc";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Docente> docenti = new ArrayList<>();

            while (rs.next())
                docenti.add(DocenteMapper.extract(rs));
            return docenti;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionSingleton.closeConnection(conn, ps, rs);
        } return null;
    }

    public boolean checkUserAndPassw(Docente docente) {
        if(docente == null){
            throw new IllegalArgumentException("The docente must not be null");
        } else {
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "SELECT * FROM docente doc WHERE doc.Username_Doc = ? and doc.Password_Doc = ?";
                ps = conn.prepareStatement(query);
                ps.setString(1, docente.getUsername());
                ps.setString(2, docente.getPassword());
                rs = ps.executeQuery();
                return rs.next();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, rs);
            } return false;
        }
    }

    public boolean doCreate(Docente docente) {
        if(docente == null)
            throw new IllegalArgumentException("Cannot save a null object");
        else {
            Connection conn = null;
            PreparedStatement ps = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "INSERT INTO docente VALUES (?,?,?,?,?)";
                ps = conn.prepareStatement(query);
                ps.setString(1, docente.getUsername().toLowerCase());
                ps.setString(2, StringUtils.capitalize(docente.getNome()));
                ps.setString(3, docente.getCognome().toUpperCase());
                ps.setString(4, docente.getPassword());
                ps.setString(5, docente.getDipartimento().getCodice());
                if (docente.getSessioni() != null){
                    SessioneDiValidazioneDAO sessioneDao = new SessioneDiValidazioneDAO();
                    for (SessioneDiValidazione sessione : docente.getSessioni()) {
                        sessioneDao.doCreate(sessione);
                    }
                }
                return ps.executeUpdate() == 1;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, null);
            } return false;
        }
    }

    public boolean doUpdate(Docente docente) {
        if(docente == null)
            throw new IllegalArgumentException("Cannot update a null object");
        else{
            Connection conn = null;
            PreparedStatement ps = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "UPDATE docente SET Nome_Doc=?, Cognome_Doc=?, Password_Doc=?, Codice_Dip=? WHERE Username_Doc=?";
                ps = conn.prepareStatement(query);
                ps.setString(1, docente.getNome());
                ps.setString(2, docente.getCognome().toUpperCase());
                ps.setString(3, docente.getPassword());
                ps.setString(4, docente.getDipartimento().getCodice());
                SessioneDiValidazioneDAO sessioneDao = new SessioneDiValidazioneDAO();
                /*for(SessioneDiValidazione sessione: docente.getSessioni()){
                    sessioneDao.doUpdate(sessione);
                }
                */
                return ps.executeUpdate() == 1;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, null);
            } return false;
        }
    }

    public boolean doDelete(Docente docente) {
        if(docente == null){
            throw new IllegalArgumentException("Cannot delete a null object");
        }
        else{
            Connection conn = null;
            PreparedStatement ps = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "DELETE FROM docente WHERE Username_Doc=?";
                ps = conn.prepareStatement(query);
                ps.setString(1, docente.getUsername());
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
