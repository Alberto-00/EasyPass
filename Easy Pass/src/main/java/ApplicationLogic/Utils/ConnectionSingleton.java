package ApplicationLogic.Utils;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.TimeZone;

public class ConnectionSingleton {

    private volatile static ConnectionSingleton conPool = null;
    private static DataSource datasource;
    private static final String USERNAME = "root";
    private static final String PASSWORD = "alberto";
    private static final String URL = "jdbc:mysql://localhost:3306/easypass?serverTimezone=";
    private static final String CLASSNAME = "com.mysql.cj.jdbc.Driver";

    private ConnectionSingleton(){}

    public static ConnectionSingleton getInstance() {
        if (conPool == null) {
            synchronized (ConnectionSingleton.class) {
                if (conPool == null)
                    conPool = new ConnectionSingleton();
            }
        }
        return conPool;
    }

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
}
