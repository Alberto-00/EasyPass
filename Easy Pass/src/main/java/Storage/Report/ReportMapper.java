package Storage.Report;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportMapper {

    public static Report extract(ResultSet rs) throws SQLException {
        Report report = new Report();
        report.setId(rs.getInt("rep.ID_report"));
        report.setOrario(rs.getTime("rep.Orario"));
        report.setData(rs.getDate("rep.Data_report"));
        report.setPathFile(rs.getString("rep.PathFile"));
        return report;
    }

}
