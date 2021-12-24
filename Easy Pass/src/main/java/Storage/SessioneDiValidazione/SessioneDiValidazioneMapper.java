package Storage.SessioneDiValidazione;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SessioneDiValidazioneMapper {

    public static SessioneDiValidazione extract(ResultSet rs) throws SQLException {
        SessioneDiValidazione sessione = new SessioneDiValidazione();
        sessione.setqRCode(rs.getString("QRcode"));
        sessione.setInCorso(rs.getBoolean("isInCorso"));
        return sessione;
    }

}
