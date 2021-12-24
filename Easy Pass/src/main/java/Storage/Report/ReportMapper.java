package Storage.Report;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportMapper {

    public static Report extract(ResultSet rs) throws SQLException {
        Report report = new Report();
        report.setId(rs.getInt("ID_report"));
        report.setOrario(rs.getTime("Orario"));
        report.setData(rs.getDate("Data_report"));
        report.setPathFile(rs.getString("PathFile"));
        return report;
    }

}
