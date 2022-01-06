package Storage.Dipartimento;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DipartimentoMapper {

    public static Dipartimento extract(ResultSet rs) throws SQLException {
        Dipartimento dip = new Dipartimento();
        dip.setCodice(rs.getString("dip.Codice_Dip"));
        dip.setNome(rs.getString("dip.Nome"));
        return dip;
    }
}
