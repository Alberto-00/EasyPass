package ApplicationLogic.Utils;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TimeZone;

/**
 * La classe crea le connessioni al database MySQL tramite {@code JDBC}.
 * Viene utilizzato il design pattern {@code Singleton} per creare un'unica
 * connessione e riutilizzarla nel momento in cui vengono effettuate
 * le query.
 */
public class ConnectionSingleton {

    private volatile static ConnectionSingleton conPool = null;
    private static DataSource datasource;
    private static final String USERNAME = "root";
    private static final String PASSWORD = "alberto";
    private static final String URL = "jdbc:mysql://localhost:3306/easypass?serverTimezone=";
    private static final String CLASSNAME = "com.mysql.cj.jdbc.Driver";

    private ConnectionSingleton(){}

    /**
     * Verifica se &egrave; stato già creato un oggetto di tipo
     * {@code ConnectionSingleton} in caso contrario lo instanzia.
     *
     * @return ConnectionSingleton
     */
    public static ConnectionSingleton getInstance() {
        if (conPool == null) {
            synchronized (ConnectionSingleton.class) {
                if (conPool == null)
                    conPool = new ConnectionSingleton();
            }
        }
        return conPool;
    }


    /**
     * Configura le proprietà di una connessione.
     *
     * @return Connection
     */
    public Connection getConnection() throws SQLException {
        if (datasource == null) {
            PoolProperties p = new PoolProperties();
            p.setUrl(URL + TimeZone.getDefault().getID());
            p.setDriverClassName(CLASSNAME);
            p.setUsername(USERNAME);
            p.setPassword(PASSWORD);
            p.setMaxActive(100);
            p.setInitialSize(10);
            p.setMinIdle(10);
            p.setMaxIdle(10);
            p.setRemoveAbandonedTimeout(60);
            p.setRemoveAbandoned(true);
            datasource = new DataSource();
            datasource.setPoolProperties(p);
        }
        return datasource.getConnection();
    }


    /**
     * &Egrave; utilizzato per chiudere la {@code Connection}, il
     * {@code PreparedStatement} e il {@code ResultSet} utilizzati per la
     * formulazione di una query.
     *
     * @param conn connection
     * @param ps preparedStatement
     * @param rs resultSet
     */
    public static void closeConnection(Connection conn, PreparedStatement ps, ResultSet rs) {
        try { if (rs != null) rs.close(); } catch (SQLException e) {e.printStackTrace();}
        try { if (ps != null) ps.close(); } catch (SQLException e) {e.printStackTrace();}
        try { if (conn != null) conn.close(); } catch (SQLException e) {e.printStackTrace();}
    }
}
