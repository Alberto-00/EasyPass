package Storage.Esito;

import ApplicationLogic.Utils.ConnectionSingleton;
import Storage.SessioneDiValidazione.SessioneDiValidazione;

import java.sql.*;
import java.util.ArrayList;
@interface Generated {}
/**
 * La classe effettua operazioni {@literal CRUD} sulla tabella {@code esito}
 */
public class EsitoDAO {

    /**
     * Effettua una query al database restituendo l'{@code Esito}
     * con un determinato {@code ID}: l'{@code Esito} non &egrave; riempito
     * con le foreign key a cui &egrave; associato.
     *
     * @param id identificativo dell'{@code Esito}
     * @return {@code Esito}
     */
    public Esito doRetrieveByKey(int id) {
        if(id < 0)
            throw new IllegalArgumentException("The ID must not be a negative number");
        else{
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "SELECT * FROM esito esi WHERE esi.ID_Esito=?";
                ps = conn.prepareStatement(query);
                ps.setInt(1, id);
                rs = ps.executeQuery();

                if (rs.next())
                    return EsitoMapper.extract(rs);
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, rs);
            } return null;
        }
    }

    /**
     * Effettua una query al database restituendo gli {@code Esiti}
     * appartenenti a una determinata {@code Sessione di Validazione}.
     *
     * @param sessione Sessione di Validazione a cui &egrave; associato l'{@code Esito}
     * @return lista di {@code Esiti} appartenenti a quella Sessione di Validaizione
     */
    public ArrayList<Esito> doRetrieveAllBySession(SessioneDiValidazione sessione) {
        if(sessione == null)
            throw new IllegalArgumentException("The parameter 'sessione' must not be null.");
        else{
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "SELECT esi.* FROM esito esi, sessione ses " +
                        "WHERE esi.QRcodeSession = ses.QRcode and ses.QRcode = ?";
                ps = conn.prepareStatement(query);
                ps.setString(1, sessione.getQRCode());
                rs = ps.executeQuery();
                ArrayList<Esito> esiti = new ArrayList<>();

                while (rs.next())
                    esiti.add(EsitoMapper.extract(rs));
                return esiti;
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, rs);
            } return null;
        }
    }

    /**
     * Effettua una query al database restituendo gli {@code Esiti}
     * appartenenti a una determinata {@code Sessione di Validazione}
     * e a un determinato {@code Studente}.
     *
     * @param esito {@code Esito} da ricercare
     * @return lista di {@code Esiti} di un determinato {@code Studente}
     */
    public ArrayList<Esito> doRetrieveAllByPersonalData(Esito esito) {
        if(esito == null)
            throw new IllegalArgumentException("The parameter 'esito' must not be null.");
        else{
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "SELECT esi.* FROM esito esi, sessione ses " +
                        "WHERE esi.QRcodeSession = ses.QRcode and ses.QRcode = ?" +
                        "AND esi.Cognome_Studente = ?" +
                        "AND esi.Nome_Studente = ?" +
                        "AND esi.Ddn_Studente = ?";
                ps = conn.prepareStatement(query);
                ps.setString(1, esito.getSessione().getQRCode());
                ps.setString(2, esito.getCognomeStudente());
                ps.setString(3, esito.getNomeStudente());
                ps.setDate(4, new java.sql.Date(esito.getDataDiNascitaStudente().getTime()));
                rs = ps.executeQuery();
                ArrayList<Esito> esiti = new ArrayList<>();

                while (rs.next())
                    esiti.add(EsitoMapper.extract(rs));
                return esiti;
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, rs);
            } return null;
        }
    }

    /**
     * Effettua una query al database ritornando il numero di {@code Esiti}
     * validi o non validi
     *
     * @param idReport identificativo del {@code Report} contenente gli {@code Esiti}
     * @param flag booleano impostato su {@code true} per ritornare il numero di {@code Esiti validi},
     *             altrimenti impostato su {@code false} per ritornare il numero di {@code Esiti non validi},
     * @return numero di {@code Esiti} validi o non validi
     */
    public int contaEsitiValidi(int idReport, boolean flag) {
        if(idReport < 0){
            throw new IllegalArgumentException("The ID must not be a negative number");
        } else{
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "SELECT COUNT(*) as Count FROM esito esi, report rep " +
                        "WHERE esi.ID_Report = rep.ID_report and rep.ID_report = ? AND Valido=?";

                ps = conn.prepareStatement(query);
                ps.setInt(1, idReport);
                ps.setBoolean(2, flag);
                rs = ps.executeQuery();
                if (rs.next())
                    return rs.getInt("Count");
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, rs);
            } return -1;
        }
    }

    /**
     * Ritorna il numero di {@code Esiti} che contiene un {@code Report}
     *
     * @param idReport Report da analizzare
     * @return numero di {@code Esiti}
     */
    public int numEsiti (int idReport) {
        if(idReport < 0){
            throw new IllegalArgumentException("The ID must not be a negative number");
        } else{
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "SELECT COUNT(*) as Count FROM esito esi, report rep " +
                        "WHERE esi.ID_Report = rep.ID_report and rep.ID_report = ?";

                ps = conn.prepareStatement(query);
                ps.setInt(1, idReport);
                rs = ps.executeQuery();
                if (rs.next())
                    return rs.getInt("Count");
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, rs);
            } return -1;
        }
    }

    /**
     * Salva nel database un nuovo {@code Esito}.
     *
     * @param esito nuovo {@code Esito} da salvare
     * @return {@code true} se l'{@code Esito} &egrave; stato creato,
     * {@code false} altrimenti
     */
    public int doCreate(Esito esito) {
        if(esito == null)
            throw new IllegalArgumentException("Cannot save a null object");
        else {
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "INSERT INTO esito (Valido, ID_Report, Nome_Studente, " +
                        "Cognome_Studente, Ddn_Studente, QRcodeSession) VALUES (?,?,?,?,?,?)";
                ps = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
                ps.setBoolean(1, esito.isValidita());
                ps.setInt(2, esito.getReport().getId());
                ps.setString(3, esito.getNomeStudente());
                ps.setString(4, esito.getCognomeStudente());
                ps.setDate(5, new java.sql.Date(esito.getDataDiNascitaStudente().getTime()));
                ps.setString(6, esito.getSessione().getQRCode());
                ps.executeUpdate();

                rs = ps.getGeneratedKeys();
                rs.next();
                return rs.getInt(1);


            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, rs);
            } return -1;
        }
    }

    /**
     * Salva nel database un nuovo {@code Esito} senza la foreign key
     * del {@code Report}.
     *
     * @param esito nuovo {@code Esito} da salvare
     * @return {@code true} se l'{@code Esito} &egrave; stato creato,
     * {@code false} altrimenti
     */
    public int doCreateWithoutReport(Esito esito) {
        if(esito == null){
            throw new IllegalArgumentException("Cannot save a null object");
        }
        else{
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "INSERT INTO esito (Valido, Nome_Studente, " +
                        "Cognome_Studente, Ddn_Studente, QRcodeSession) VALUES (?,?,?,?,?)";
                ps = conn.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
                ps.setBoolean(1, esito.isValidita());
                ps.setString(2, esito.getNomeStudente());
                ps.setString(3, esito.getCognomeStudente());
                ps.setDate(4, new java.sql.Date(esito.getDataDiNascitaStudente().getTime()));
                ps.setString(5, esito.getSessione().getQRCode());
                ps.executeUpdate();

                rs = ps.getGeneratedKeys();
                rs.next();
                return rs.getInt(1);
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, rs);
            } return -1;
        }
    }

    /**
     * Viene modificato un {@code Esito} già presente nel
     * database.
     *
     * @param esito {@code Esito} da modificare
     * @return {@code true} se l'{@code Esito} &egrave; stato aggiornato,
     * {@code false} altrimenti
     */
    @Generated
    public boolean doUpdate(Esito esito) {
        if(esito == null)
            throw new IllegalArgumentException("Cannot update a null object");
        else{
            Connection conn = null;
            PreparedStatement ps = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "UPDATE esito SET Valido=?, ID_Report=?, " +
                        "Nome_Studente=?, Cognome_Studente=?, Ddn_Studente=?, QRcodeSession = ? WHERE ID_Esito=?";
                ps = conn.prepareStatement(query);
                ps.setBoolean(1, esito.isValidita());
                ps.setInt(2, esito.getReport().getId());
                ps.setString(3, esito.getNomeStudente());
                ps.setString(4, esito.getNomeStudente());
                ps.setDate(5, (Date) esito.getDataDiNascitaStudente());
                ps.setString(6, esito.getSessione().getQRCode());
                ps.setInt(7, esito.getId());
                return ps.executeUpdate() == 1;
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, null);
            } return false;
        }
    }

    /**
     * Viene modificata solo la foreign key {@code Report} associata all'{@code Esito}.
     *
     * @param esito {@code Esito} da modificare
     * @return {@code true} se l'{@code Esito} &egrave; stato aggiornato,
     * {@code false} altrimenti
     */
    public boolean doUpdateOnlyReport(Esito esito) {
        if(esito == null)
            throw new IllegalArgumentException("Cannot update a null object");
        else{
            Connection conn = null;
            PreparedStatement ps = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "UPDATE esito SET ID_Report=? WHERE ID_Esito=?";
                ps = conn.prepareStatement(query);
                ps.setInt(1, esito.getReport().getId());
                ps.setInt(2, esito.getId());
                return ps.executeUpdate() == 1;
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, null);
            } return false;
        }
    }

    /**
     * Viene eliminato un {@code Esito} dal database.
     *
     * @param esito {@code Esito} da eliminare
     * @return {@code true} se l'{@code Esito} &egrave; stato eliminato,
     * {@code false} altrimenti
     */
    @Generated
    public boolean doDelete(Esito esito) {
        if(esito==null)
            throw new IllegalArgumentException("Cannot delete a null object");
        else{
            Connection conn = null;
            PreparedStatement ps = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "DELETE FROM esito WHERE ID_Esito=?";
                ps = conn.prepareStatement(query);
                ps.setInt(1, esito.getId());
                return ps.executeUpdate() == 1;
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, null);
            } return false;
        }
    }
}
