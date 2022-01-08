package Storage.Esito;

import ApplicationLogic.Utils.ConnectionSingleton;
import Storage.Report.Report;
import Storage.SessioneDiValidazione.SessioneDiValidazione;

import java.sql.*;
import java.util.ArrayList;

public class EsitoDAO {

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

    public ArrayList<Esito> doRetrieveAllByReport(Report report) {
        if(report == null)
            throw new IllegalArgumentException("The 'report' must not be null.");
        else{
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "SELECT esi.* FROM esito esi, report rep " +
                        "WHERE esi.ID_Report = rep.ID_report and rep.ID_report = ?";
                ps = conn.prepareStatement(query);
                ps.setInt(1, report.getId());
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
                ps.setString(1, sessione.getqRCode());
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
                ps.setString(1, esito.getSessione().getqRCode());
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

    public ArrayList<Esito> doRetrieveAll() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = ConnectionSingleton.getInstance().getConnection();
            String query = "SELECT * FROM esito esi";
            ps = conn.prepareStatement(query);
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
            } return 0;
        }
    }

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
            } return 0;
        }
    }

    public boolean doCreate(Esito esito) {
        if(esito == null)
            throw new IllegalArgumentException("Cannot save a null object");
        else {
            Connection conn = null;
            PreparedStatement ps = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "INSERT INTO esito (Valido, ID_Report, Nome_Studente, " +
                        "Cognome_Studente, Ddn_Studente, QRcodeSession) VALUES (?,?,?,?,?,?)";
                ps = conn.prepareStatement(query);
                ps.setBoolean(1, esito.isValidita());
                ps.setInt(2, esito.getReport().getId());
                ps.setString(3, esito.getNomeStudente());
                ps.setString(4, esito.getCognomeStudente());
                ps.setDate(5, (Date) esito.getDataDiNascitaStudente());
                ps.setString(6, esito.getSessione().getqRCode());
                return ps.executeUpdate() == 1;
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, null);
            } return false;
        }
    }

    public boolean doCreateWithoutReport(Esito esito) {
        if(esito == null){
            throw new IllegalArgumentException("Cannot save a null object");
        }
        else{
            Connection conn = null;
            PreparedStatement ps = null;
            try {
                conn = ConnectionSingleton.getInstance().getConnection();
                String query = "INSERT INTO esito (Valido, Nome_Studente, " +
                        "Cognome_Studente, Ddn_Studente, QRcodeSession) VALUES (?,?,?,?,?)";
                ps = conn.prepareStatement(query);
                ps.setBoolean(1, esito.isValidita());
                ps.setString(2, esito.getNomeStudente());
                ps.setString(3, esito.getCognomeStudente());
                ps.setDate(4, new java.sql.Date(esito.getDataDiNascitaStudente().getTime()));
                ps.setString(5, esito.getSessione().getqRCode());
                return ps.executeUpdate() == 1;
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, null);
            } return false;
        }
    }

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
                ps.setString(6, esito.getSessione().getqRCode());
                ps.setInt(7, esito.getId());
                return ps.executeUpdate() == 1;
            } catch (SQLException e){
                e.printStackTrace();
            } finally {
                ConnectionSingleton.closeConnection(conn, ps, null);
            } return false;
        }
    }

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
