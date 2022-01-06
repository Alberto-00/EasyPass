package Storage.Formato;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FormatoMapper {

    public static Formato extract(ResultSet rs) throws SQLException {
        Formato formato = new Formato();
        formato.setId(rs.getString("form.ID_formato"));
        formato.setNumStudenti(rs.getBoolean("form.N_studenti"));
        formato.setNumGPValidi(rs.getBoolean("form.GP_validi"));
        formato.setNumGPNonValidi(rs.getBoolean("form.GP_nonValidi"));
        formato.setNomeCognome(rs.getBoolean("form.Nome_Cognome"));
        formato.setData(rs.getBoolean("form.Ddn"));
        return formato;
    }
}
