package Storage.PersonaleUnisa.Direttore;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * La classe permette di creare un oggetto {@code DirettoreDiDipartimento} e riempire i suoi attributi
 * con le informazioni estratte dal database dalla tabella {@code direttore}.
 * */
public class DirettoreDiDipartimentoMapper {

    /**
     * Crea un oggetto {@code DirettoreDiDipartimento} riempendo i suoi attributi
     * con gli attributi della tabella {@code direttore} escludendo le
     * foreign key.
     *
     * @param rs resultSet
     * @return oggetto {@code DirettoreDiDipartimento} con gli attributi impostati
     */
    public static DirettoreDiDipartimento extract(ResultSet rs) throws SQLException{
        DirettoreDiDipartimento direttore = new DirettoreDiDipartimento();
        direttore.setNome(rs.getString("dir.Nome_Dir"));
        direttore.setCognome(rs.getString("dir.Cognome_Dir"));
        direttore.setUsername(rs.getString("dir.Username_Dir"));
        direttore.setPassword(rs.getString("dir.Password_Dir"));
        return direttore;
    }
}
