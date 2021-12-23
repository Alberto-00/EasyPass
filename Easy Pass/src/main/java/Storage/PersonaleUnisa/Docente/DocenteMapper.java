package Storage.PersonaleUnisa.Docente;


import java.sql.ResultSet;
import java.sql.SQLException;

public class DocenteMapper {
    public static Docente extract(ResultSet rs) throws SQLException {
        Docente docente=new Docente();
        docente.setNome(rs.getString("doc.Nome_Doc"));
        docente.setCognome(rs.getString("doc.Cognome_Doc"));
        docente.setUsername(rs.getString("doc.Username_Doc"));
        docente.setPassword(rs.getString("doc.Password_Doc"));
        return docente;
    }
}
