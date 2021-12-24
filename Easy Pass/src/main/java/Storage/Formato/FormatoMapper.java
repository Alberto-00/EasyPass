package Storage.Formato;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FormatoMapper {

    public static Formato extract(ResultSet rs) throws SQLException {
        Formato formato = new Formato();
        formato.setId(rs.getInt("ID_formato"));
        formato.setNumStudenti(rs.getBoolean("N_studenti"));
        formato.setNumGPValidi(rs.getBoolean("GP_validi"));
        formato.setNumGPNonValidi(rs.getBoolean("GP_nonValidi"));
        formato.setNomeCognome(rs.getBoolean("Nome_Cognome"));
        formato.setData(rs.getBoolean("Ddn"));
        return formato;
    }

}
