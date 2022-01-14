package Storage.SessioneDiValidazione;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * La classe permette di creare un oggetto {@code SessioneDiValidazione} e riempire i suoi attributi
 * con le informazioni estratte dal database dalla tabella {@code sessione}.
 * */
public class SessioneDiValidazioneMapper {

    /**
     * Crea un oggetto {@code SessioneDiValidazione} riempendo i suoi attributi
     * con gli attributi della tabella {@code sessione} escludendo le
     * foreign key.
     *
     * @param rs resultSet
     * @return oggetto {@code SessioneDiValidazione} con gli attributi impostati
     */
    public static SessioneDiValidazione extract(ResultSet rs) throws SQLException {
        SessioneDiValidazione sessione = new SessioneDiValidazione();
        sessione.setQRCode(rs.getString("ses.QRcode"));
        sessione.setInCorso(rs.getBoolean("ses.isInCorso"));
        return sessione;
    }
}
