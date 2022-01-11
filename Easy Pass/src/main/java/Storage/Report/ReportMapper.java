package Storage.Report;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * La classe permette di creare un oggetto {@code Report} e riempire i suoi attributi
 * con le informazioni estratte dal database dalla tabella {@code report}.
 * */
public class ReportMapper {

    /**
     * Crea un oggetto {@code Report} riempendo i suoi attributi
     * con gli attributi della tabella {@code report} escludendo le
     * foreign key.
     *
     * @param rs resultSet
     * @return oggetto {@code Report} con gli attributi impostati
     */
    public static Report extract(ResultSet rs) throws SQLException {
        Report report = new Report();
        report.setId(rs.getInt("ID_report"));
        report.setOrario(rs.getTime("Orario"));
        report.setData(rs.getDate("Data_report"));
        report.setPathFile(rs.getString("PathFile"));
        return report;
    }
}
