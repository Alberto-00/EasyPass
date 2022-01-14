package Storage.PersonaleUnisa.Docente;

import ApplicationLogic.Utils.ConnectionSingleton;
import Storage.Dipartimento.Dipartimento;
import Storage.Dipartimento.DipartimentoDAO;
import Storage.SessioneDiValidazione.SessioneDiValidazione;
import Storage.SessioneDiValidazione.SessioneDiValidazioneDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@interface Generated {}

/**
 * La classe effettua operazioni {@literal CRUD}, sulla tabella {@code docente}, e di verifica
 * delle credenziali inserite dal Docente.
 */
public class DocenteDAO {

    /**
     * Effettua una query al database restituendo il {@code Docente}
     * con un determinato {@code username}.
     *
     * @param username username del Docente
     * @return {@code Docente}
     */
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

    /**
     * Effettua una query al database restituendo il {@code Docente}
     * con un determinato {@code nome} e {@code cognome}.
     *
     * @param nome nome del Docente
     * @param cognome cognome del Docente
     * @return {@code Docente}
     */
    public Docente doRetrieveByNameSurname(String nome, String cognome){
        if(nome == null || cognome == null)
            throw new IllegalArgumentException("The username must not be null");
        else{
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "SELECT * FROM docente doc WHERE doc.Nome_Doc=? and " +
                        "doc.Cognome_Doc = ?";
                ps = conn.prepareStatement(query);
                ps.setString(1, nome);
                ps.setString(2, cognome);
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

    /**
     * Effettua una query al database restituendo un oggetto
     * {@code Docente} senza la foreign key della Sessione di validazione associata.
     *
     * @param username username del Docente da cercare
     * @return {@code Docente}
     */
    public Docente doRetrieveByKeyWithRelations(String username) {
        if(username == null)
            throw new IllegalArgumentException("The username must not be null");
        else{
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "SELECT doc.* FROM docente doc, dipartimento dip WHERE doc.Username_Doc=? " +
                        "AND doc.Codice_Dip=dip.Codice_Dip";
                ps = conn.prepareStatement(query);
                ps.setString(1, username);
                rs = ps.executeQuery();

                if (rs.next()) {
                    Docente docente = DocenteMapper.extract(rs);
                    docente.setDipartimento(new Dipartimento());
                    docente.getDipartimento().setCodice(rs.getString("doc.Codice_Dip"));
                    DipartimentoDAO dip = new DipartimentoDAO();
                    docente.setDipartimento(dip.doRetrieveByKeyWithRelations(docente.getDipartimento().getCodice()));
                    return docente;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, rs);
            } return null;
        }
    }

    /**
     * Effettua una query al database restituendo una lista di
     * {@code Docenti} appartenenti a un determinato Dipartimento.
     *
     * @param codDip codice di Dipartimento dei Docenti da cercare
     * @return lista di {@code Docenti}
     */
    public ArrayList<Docente> doRetrieveAllWithRelations(String codDip) {
        if(codDip == null)
            throw new IllegalArgumentException("The 'codDip' must not be null");
        else{
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "SELECT doc.* FROM docente doc, dipartimento dip WHERE doc.Codice_Dip = ? " +
                        "AND doc.Codice_Dip=dip.Codice_Dip";
                ps = conn.prepareStatement(query);
                ps.setString(1, codDip);
                rs = ps.executeQuery();
                ArrayList<Docente> docenti = new ArrayList<>();

                for (int i = 0; rs.next(); i++) {
                    docenti.add(DocenteMapper.extract(rs));
                    docenti.get(i).setDipartimento(new Dipartimento());
                    docenti.get(i).getDipartimento().setCodice(codDip);
                    DipartimentoDAO dip = new DipartimentoDAO();
                    docenti.get(i).setDipartimento(dip.doRetrieveByKeyWithRelations(docenti.get(i).getDipartimento().getCodice()));
                }
                return docenti;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, rs);
            } return null;
        }
    }

    /**
     * Si verifica nel database se esiste un Docente registrato con una
     * determinata email e password.
     *
     * @param docente Docente
     * @return {@code true} se esiste un {@code Docente} con
     * quelle credenziali, altrimenti {@code false}
     */
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

    /**
     * Salva nel database un nuovo {@code Docente}.
     *
     * @param docente nuovo {@code Docente} da salvare
     * @return {@code true} se il {@code Docente} &egrave; stato creato,
     * {@code false} altrimenti
     */
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
                ps.setString(1, docente.getUsername());
                ps.setString(2, docente.getNome());
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

    /**
     * Aggiorna nel database un {@code Docente} esistente.
     *
     * @param docente {@code Docente} da aggiornare
     * @return {@code true} se il {@code Docente} &egrave; stato aggiornato,
     * {@code false} altrimenti
     */
    @Generated
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

                for(SessioneDiValidazione sessione: docente.getSessioni())
                    sessioneDao.doUpdate(sessione);

                return ps.executeUpdate() == 1;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, null);
            } return false;
        }
    }

    /**
     * Elimina nel database un {@code Docente} esistente.
     *
     * @param docente {@code Docente} da eliminare
     * @return {@code true} se il {@code Docente} &egrave; stato eliminato,
     * {@code false} altrimenti
     */
    @Generated
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
