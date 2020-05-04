package datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

public class Conexion {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/control_clientes?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&useSSL=false&serverTimezone=" + TimeZone.getDefault().getID();
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "admin";
    private static BasicDataSource ds;
    //Pool de conexiones 

    public static BasicDataSource getDataSource() throws ClassNotFoundException {
        if (ds == null) {
            ds = new BasicDataSource();
            Class.forName("com.mysql.cj.jdbc.Driver");
            ds.setUrl(JDBC_URL);
            ds.setUsername(JDBC_USER);
            ds.setPassword(JDBC_PASSWORD);
            //DataSource seria le pool que maneja las conexiones a la base de datos
            ds.setInitialSize(50);
            //Cantidad inicial de conexiones que pueden haber
        }
        return ds;
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        return getDataSource().getConnection();
    }

    public static void close(AutoCloseable... rs) {
        for (AutoCloseable ac : rs) {
            try {
                ac.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
