package Storage.Formato;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * La classe permette di creare un oggetto {@code Formato} e riempire i suoi attributi
 * con le informazioni estratte dal database dalla tabella {@code formato}.
 * */
public class FormatoMapper {

    /**
     * Crea un oggetto {@code Formato} riempendo i suoi attributi
     * con gli attributi della tabella {@code formato}.
     *
     * @param rs resultSet
     * @return oggetto {@code Formato} con gli attributi impostati
     */
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
