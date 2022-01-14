package Storage.PersonaleUnisa.Direttore;

import ApplicationLogic.Utils.ConnectionSingleton;
import Storage.Dipartimento.DipartimentoDAO;
import Storage.Dipartimento.Dipartimento;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.sql.*;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target({TYPE, METHOD})
@interface Generated {}

/**
 * La classe effettua operazioni {@literal CRUD}, sulla tabella {@code direttore}, e di verifica
 * delle credenziali inserite dal Direttore di Dipartimento.
 */
public class DirettoreDiDipartimentoDAO {

    /**
     * Effettua una query al database restituendo il {@code DirettoreDiDipartimento}
     * con un determinato {@code username}.
     *
     * @param username username del Direttore di Dipartimento
     * @return {@code DirettoreDiDipartimento}
     */
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

    /**
     * Si verifica nel database se esiste un Direttore registrato con una
     * determinata email e password.
     *
     * @param direttore Direttore da controllare
     * @return {@code true} se esiste un {@code DirettoreDiDipartimento} con
     * quelle credenziali, altrimenti {@code false}
     */
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

    /**
     * Effettua una query al database restituendo un oggetto
     * {@code DirettoreDiDipartimento} con le foreign key associate.
     *
     * @param username username del Direttore da cercare
     * @return {@code DirettoreDiDipartimento} con le foreign key associate.
     */
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

    /**
     * Salva nel database un nuovo {@code DirettoreDiDipartimento}.
     *
     * @param direttore nuovo {@code DirettoreDiDipartimento} da salvare
     * @return {@code true} se il {@code DirettoreDiDipartimento} &egrave; stato creato,
     * {@code false} altrimenti
     */
    @Generated
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

    /**
     * Aggiorna nel database un {@code DirettoreDiDipartimento} esistente.
     *
     * @param direttore {@code DirettoreDiDipartimento} da aggiornare
     * @return {@code true} se il {@code DirettoreDiDipartimento} &egrave; stato aggiornato,
     * {@code false} altrimenti
     */
    @Generated
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

    /**
     * Elimina nel database un {@code DirettoreDiDipartimento} esistente.
     *
     * @param direttore {@code DirettoreDiDipartimento} da eliminare
     * @return {@code true} se il {@code DirettoreDiDipartimento} &egrave; stato eliminato,
     * {@code false} altrimenti
     */
    @Generated
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
