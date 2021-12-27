package Storage.Esito;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EsitoMapper {
    public static Esito extract(ResultSet rs) throws SQLException {
        Esito esito=new Esito();
        esito.setId(rs.getInt("esi.ID_Esito"));
        esito.setNomeStudente(rs.getString("esi.Nome_Studente"));
        esito.setCognomeStudente(rs.getString("esi.Cognome_Studente"));
        esito.setDataDiNascitaStudente(rs.getDate("esi.Ddn_Studente"));
        esito.setValidita(rs.getBoolean("esi.Valido"));
        return esito;
    }
}
