package Storage.Esito;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * La classe permette di creare un oggetto {@code Esito} e riempire i suoi attributi
 * con le informazioni estratte dal database dalla tabella {@code esito}.
 * */
public class EsitoMapper {

    /**
     * Crea un oggetto {@code Esito} riempendo i suoi attributi
     * con gli attributi della tabella {@code esito} escludendo le
     * foreign key.
     *
     * @param rs resultSet
     * @return oggetto {@code Esito} con gli attributi impostati
     */
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
