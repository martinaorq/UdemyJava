package datos;

import java.sql.*;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {

    /*
    Esta clase se encargará solo de manejar la conexión y sde cerrar los objetos luego creados para manejarla
     */
    public static final String JDBC_URL = "jdbc:mysql://localhost:3306/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=" + TimeZone.getDefault().getID();
    public static final String JDBC_USER = "root";
    public static final String JDBC_PASSWORD = "admin";

    public static Connection getConexion() throws SQLException {
        Connection conexion = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        
        return conexion;
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
