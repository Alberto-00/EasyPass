package Storage.PersonaleUnisa.Direttore;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DirettoreDiDipartimentoMapper {

    public static DirettoreDiDipartimento extract(ResultSet rs) throws SQLException{
        DirettoreDiDipartimento direttore=new DirettoreDiDipartimento();
        direttore.setNome(rs.getString("dir.Nome_Dir"));
        direttore.setCognome(rs.getString("dir.Cognome_Dir"));
        direttore.setUsername(rs.getString("dir.Username_Dir"));
        direttore.setPassword(rs.getString("dir.Password_Dir"));
        return direttore;
    }
}
