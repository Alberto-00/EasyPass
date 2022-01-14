package Storage.Report;

import ApplicationLogic.Utils.ConnectionSingleton;
import Storage.Dipartimento.Dipartimento;
import Storage.PersonaleUnisa.Docente.Docente;
import Storage.PersonaleUnisa.Docente.DocenteMapper;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TreeMap;

@interface Generated {}

/**
 * La classe effettua operazioni {@literal CRUD}, sulla tabella {@code report}, e di ricerca
 * applicando alcuni filtri.
 */
public class ReportDAO {

    /**
     * Effettua una query al database restituendo il {@code Report}
     * con un determinato {@code id}.
     *
     * @param id id del {@code Report}
     * @return {@code Report}
     */
    public Report doRetrieveById(int id) {
        if(id < 0)
            throw new IllegalArgumentException("The id must not be negative");
        else {
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "SELECT * FROM report rep WHERE rep.ID_report=?";
                ps = conn.prepareStatement(query);
                ps.setInt(1, id);
                rs = ps.executeQuery();

                if (rs.next()) {
                    return ReportMapper.extract(rs);
                }
            } catch(SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, rs);
            }
        } return null;
    }

    /**
     * Effettua una query al database restituendo un insieme di {@code Report}
     * e di {@code Docenti} di un determinato Dipartimento.
     *
     * @param idDip id del {@code Dipartimento}
     * @return {@code TreeMap} avente come key i {@code Report} e value i {@code Docenti}
     */
    public TreeMap<Report, Docente> doRetrieveDocByReport(String idDip) {
        if(idDip == null)
            throw new IllegalArgumentException("The id must not be null");
        else {
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query="SELECT doc.*, rep.* FROM docente doc, report rep " +
                        "WHERE doc.Username_Doc = rep.Username_Doc " +
                        "and rep.Codice_Dip = ? ORDER BY rep.ID_report";
                ps = conn.prepareStatement(query);
                ps.setString(1, idDip);
                TreeMap<Report, Docente> reportDocenteHashMap = new TreeMap<>();
                rs = ps.executeQuery();

                while (rs.next())
                    reportDocenteHashMap.put(ReportMapper.extract(rs), DocenteMapper.extract(rs));
                return reportDocenteHashMap;
            } catch(SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, rs);
            } return null;
        }
    }

     /**
     * Salva nel database un nuovo {@code Report}.
     *
     * @param report nuovo {@code Report} da salvare
     * @return {@code true} se il {@code Report} &egrave; stato creato,
     * {@code false} altrimenti
     */
    public int doCreate (Report report) {
        if (report == null)
            throw new IllegalArgumentException("Cannot save a null object");
        else {
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "INSERT INTO report (Orario, Data_report, PathFile, Codice_Dip, Username_Doc) VALUES (?, ?, ?, ?, ?)";
                ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setTime(1, new java.sql.Time(report.getOrario().getTime()));
                ps.setDate(2, new java.sql.Date(report.getData().getTime()));
                ps.setString(3, report.getPathFile());
                ps.setString(4, report.getDip().getCodice());
                ps.setString(5, report.getDocente().getUsername());
                ps.executeUpdate();
                rs = ps.getGeneratedKeys();
                rs.next();
                return rs.getInt(1);
            } catch(SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, rs);
            } return 0;
        }
    }

    /**
     * Aggiorna nel database un {@code Report} esistente
     *
     * @param report {@code Report} da aggiornare
     * @return {@code true} se il {@code Report} &egrave; stato aggiornato,
     * {@code false} altrimenti
     */
    @Generated
    public boolean doUpdate (Report report) {
        if (report == null)
            throw new IllegalArgumentException("Cannot update a null object");
        else {
            Connection conn = null;
            PreparedStatement ps = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "UPDATE report rep SET rep.Orario=?, rep.Data_report=?, " +
                        "rep.PathFile=?, rep.Codice_Dip=?, rep.Username_Doc=? WHERE rep.ID_report=?";
                ps = conn.prepareStatement(query);
                ps.setTime(1, (Time) report.getOrario());
                ps.setDate(2, (Date) report.getData());
                ps.setString(3, report.getPathFile());
                ps.setString(4, report.getDip().getCodice());
                ps.setString(5, report.getDocente().getUsername());
                ps.setInt(6, report.getId());
                return ps.executeUpdate() == 1;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, null);
            }
        } return false;
    }

    /**
     * Aggiorna nel database solo il path di un {@code Report} esistente
     *
     * @param report {@code Report} da aggiornare
     * @return {@code true} se il {@code Report} &egrave; stato aggiornato,
     * {@code false} altrimenti
     */
    public boolean doUpdatePath (Report report) {
        if (report == null)
            throw new IllegalArgumentException("Cannot update a null object");
        else {
            Connection conn = null;
            PreparedStatement ps = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "UPDATE report rep SET rep.PathFile=? WHERE rep.ID_report=?";
                ps = conn.prepareStatement(query);
                ps.setString(1, report.getPathFile());
                ps.setInt(2, report.getId());
                return ps.executeUpdate() == 1;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, null);
            }
        } return false;
    }

    /**
     * Elimina nel database un {@code Report} esistente
     *
     * @param report {@code Report} da eliminare
     * @return {@code true} se il {@code Report} &egrave; stato eliminato,
     * {@code false} altrimenti
     */
    public boolean doDelete (Report report) {
        if (report == null)
            throw new IllegalArgumentException("Cannot update a null object");
        else {
            Connection conn = null;
            PreparedStatement ps = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "DELETE FROM report rep WHERE rep.ID_report=?";
                ps = conn.prepareStatement(query);
                ps.setInt(1, report.getId());

                return ps.executeUpdate() == 1;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, null);
            }
        } return false;
    }

    /**
     * Effettua una query al database restituendo un insieme di {@code Report}
     * e di {@code Docenti} di un determinato Dipartimento
     * con un determinato nome e cognome e generati in un certo periodo di tempo.
     *
     * @param docente Docente che ha generato i {@code Report}
     * @param primaData la prima data dell'intervallo di tempo
     * @param secondaData la seconda data dell'intervallo di tempo
     * @return {@code TreeMap} avente come key i {@code Report} e value i {@code Docenti}
     */
    public TreeMap<Report, Docente> doSearch(Docente docente, java.util.Date primaData, java.util.Date secondaData) {
        if(docente != null && primaData != null && secondaData != null){
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query="SELECT doc.*, rep.* FROM docente doc, report rep " +
                        "WHERE doc.Username_Doc = rep.Username_Doc " +
                        "and (rep.Data_report between ? and ?) " +
                        "and doc.Nome_Doc = ? and doc.Cognome_Doc = ?";

                ps = conn.prepareStatement(query);
                ps.setString(1, convertToString(primaData));
                ps.setString(2, convertToString(secondaData));
                ps.setString(3, docente.getNome());
                ps.setString(4, docente.getCognome());

                TreeMap<Report, Docente> treeMap = new TreeMap<>();
                rs = ps.executeQuery();
                while(rs.next())
                    treeMap.put(ReportMapper.extract(rs), DocenteMapper.extract(rs));
                return treeMap;
            }
            catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, rs);
            }
        }
        else
            throw new IllegalArgumentException("The argument 'primaData', 'secondaData' and 'docente' cannot be null");
        return null;
    }

    /**
     * Effettua una query al database restituendo un insieme di {@code Report}
     * e di {@code Docenti} di un determinato Dipartimento
     * con un determinato nome e cognome.
     *
     * @param docente Docente che ha generato i {@code Report}
     * @return {@code TreeMap} avente come key i {@code Report} e value i {@code Docenti}
     */
    public TreeMap<Report, Docente> doSearchByDocName(Docente docente) {
        if(docente == null)
            throw new IllegalArgumentException("The argument 'docente' cannot be null");
        else{
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query="SELECT doc.*, rep.* FROM docente doc, report rep " +
                        "WHERE doc.Username_Doc = rep.Username_Doc " +
                        "and doc.Nome_Doc = ? and doc.Cognome_Doc = ?";

                ps = conn.prepareStatement(query);
                ps.setString(1,docente.getNome());
                ps.setString(2,docente.getCognome());
                TreeMap<Report, Docente> treeMap = new TreeMap<>();
                rs = ps.executeQuery();

                while(rs.next())
                    treeMap.put(ReportMapper.extract(rs), DocenteMapper.extract(rs));
                return treeMap;
            }  catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, rs);
            } return null;
        }
    }

    /**
     * Effettua una query al database restituendo un insieme di {@code Report}
     * e di {@code Docenti} di un determinato Dipartimento generati in un certo
     * periodo di tempo.
     *
     * @param primaData la prima data dell'intervallo di tempo
     * @param secondaData la seconda data dell'intervallo di tempo
     * @param dipartimento dipartimento in cui ricercare i {@code Report}
     * @return {@code TreeMap} avente come key i {@code Report} e value i {@code Docenti}
     */
    public TreeMap<Report, Docente> doSearchByDate(java.util.Date primaData, java.util.Date secondaData, Dipartimento dipartimento) {
        if(primaData == null && secondaData == null){
            throw new IllegalArgumentException("The argument 'primaData' and 'secondaData' cannot be null.");
        }
        else{
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query="SELECT doc.*, rep.* FROM docente doc, report rep " +
                        "WHERE doc.Username_Doc = rep.Username_Doc " +
                        "and doc.Codice_Dip = ? and (rep.Data_report between ? and ?)";

                ps = conn.prepareStatement(query);
                ps.setString(1, dipartimento.getCodice());
                ps.setString(2, convertToString(primaData));
                ps.setString(3, convertToString(secondaData));
                TreeMap<Report, Docente> treeMap = new TreeMap<>();
                rs = ps.executeQuery();

                while(rs.next())
                    treeMap.put(ReportMapper.extract(rs), DocenteMapper.extract(rs));
                return treeMap;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, rs);
            } return null;
        }
    }

    @Generated
    private String convertToString(java.util.Date date){
        String pattern = "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }
}