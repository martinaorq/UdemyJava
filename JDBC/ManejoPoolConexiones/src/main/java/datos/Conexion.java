package datos;

import java.sql.*;
import java.util.TimeZone;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

public class Conexion {

    /*
    Esta clase se encargará solo de manejar la conexión y sde cerrar los objetos luego creados para manejarla
     */
    public static final String JDBC_URL = "jdbc:mysql://localhost:3306/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=" + TimeZone.getDefault().getID();
    public static final String JDBC_USER = "root";
    public static final String JDBC_PASSWORD = "admin";

    public static DataSource getDataSource(){
        BasicDataSource ds=new BasicDataSource(); //Pool de conexiones
        //Proporcionamos valores tipicos para establecer la conexion
        ds.setUrl(JDBC_URL);
        ds.setUsername(JDBC_USER);    
        ds.setPassword(JDBC_PASSWORD);
        ds.setInitialSize(5);//Tamaño inicial de pool de conexiones :)
        return ds;
    }
    
    public static Connection getConexion() throws SQLException {
        return getDataSource().getConnection();
    }

    public static boolean closeObjects(Connection conexion, Statement sentencia, ResultSet resultado) {
        boolean operacionExitosa = false;
        try {
            conexion.close();
            sentencia.close();
            resultado.close();
            operacionExitosa = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            return operacionExitosa;
        }
    }
    
    public static boolean closeObjects(Connection conexion, Statement sentencia) {
        boolean operacionExitosa = false;
        try {
            conexion.close();
            sentencia.close();
            operacionExitosa = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            return operacionExitosa;
        }
    }

    
    public static boolean closeObjects(Statement sentencia) {
        boolean operacionExitosa = false;
        try {
            sentencia.close();
            operacionExitosa = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            return operacionExitosa;
        }
    }
}
