package Storage.Dipartimento;

import ApplicationLogic.Utils.ConnectionSingleton;
import Storage.Formato.FormatoDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * La classe effettua operazioni {@literal CRUD} sulla tabella {@code dipartimento}
 */

@interface Generated {}
public class DipartimentoDAO {

    /**
     * Effettua una query al database restituendo il {@code Dipartimento}
     * con un determinato {@code codice}: il {@code Dipartimento} non &egrave; riempito
     * con le foreign key a cui &egrave; associato.
     *
     * @param codice identificativo del {@code Dipartimento}
     * @return {@code Dipartimento}
     */
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


    /**
     * Effettua una query al database restituendo il {@code Dipartimento}
     * con un determinato {@code codice}: il {@code Dipartimento} &egrave; riempito
     * anche con la foreign key {@code Formato}a cui &egrave; associato.
     *
     * @param codice identificativo del {@code Dipartimento}
     * @return {@code Dipartimento}
     */
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
                    dipartimento.setFormato(new FormatoDAO().doRetrieveById(rs.getString("dip.ID_Formato")));
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

    /**
     * Ritorna tutti i {@code Dipartimenti} situati nel database:
     * ogni {@code Dipartimento} &egrave; riempito solo con gli attributi propri
     * della classe, senza le foreign key a cui &egrave; associato.
     *
     * @return lista di {@code Dipartimenti}
     */
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


    /**
     * Salva nel database un nuovo {@code Dipartimento}.
     *
     * @param dipartimento nuovo {@code Dipartimento} da salvare
     * @return {@code true} se il {@code Dipartimento} &egrave; stato creato,
     * {@code false} altrimenti
     */
    @Generated
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

    /**
     * Viene modificato un {@code Dipartimento} già presente nel
     * database.
     *
     * @param dipartimento {@code Dipartimento} da modificare
     * @return {@code true} se il {@code Dipartimento} &egrave; stato aggiornato,
     * {@code false} altrimenti
     */
    @Generated
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

    /**
     * Viene eliminato un {@code Dipartimento} dal database.
     *
     * @param dipartimento {@code Dipartimento} da eliminare
     * @return {@code true} se il {@code Dipartimento} &egrave; stato eliminato,
     * {@code false} altrimenti
     */
    @Generated
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
