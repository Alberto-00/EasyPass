package Storage.PersonaleUnisa.Docente;


import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * La classe permette di creare un oggetto {@code Docente} e riempire i suoi attributi
 * con le informazioni estratte dal database dalla tabella {@code docente}.
 * */
public class DocenteMapper {

    /**
     * Crea un oggetto {@code Docente} riempendo i suoi attributi
     * con gli attributi della tabella {@code docente} escludendo le
     * foreign key.
     *
     * @param rs resultSet
     * @return oggetto {@code Docente} con gli attributi impostati
     */
    public static Docente extract(ResultSet rs) throws SQLException {
        Docente docente = new Docente();
        docente.setNome(rs.getString("doc.Nome_Doc"));
        docente.setCognome(rs.getString("doc.Cognome_Doc"));
        docente.setUsername(rs.getString("doc.Username_Doc"));
        docente.setPassword(rs.getString("doc.Password_Doc"));
        return docente;
    }
}
