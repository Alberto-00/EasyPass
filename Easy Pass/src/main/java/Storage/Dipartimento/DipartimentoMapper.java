package Storage.Dipartimento;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * La classe permette di creare un oggetto {@code Dipartimento} e riempire i suoi attributi
 * con le informazioni estratte dal database dalla tabella {@code dipartimento}.
 * */
public class DipartimentoMapper {

    /**
     * Crea un ogetto {@code Dipartimento} riempendo i suoi attributi
     * con gli attributi della tabella {@code dipartimento} escludendo le
     * foreign key.
     *
     * @param rs resultSet
     * @return un oggetto {@code Dipartimento} con gli attributi impostati
     */
    public static Dipartimento extract(ResultSet rs) throws SQLException {
        Dipartimento dip = new Dipartimento();
        dip.setCodice(rs.getString("dip.Codice_Dip"));
        dip.setNome(rs.getString("dip.Nome"));
        return dip;
    }
}
