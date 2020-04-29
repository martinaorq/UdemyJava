package test;

import static datos.Conexion.getConexion;
import datos.PersonaDAO;
import datos.PersonaDAOJDBC;
import domain.PersonaDTO;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManejoPersonas {

    /**
     * Cosas a destacar: - Tendriamos que eliminar los catchs (de los try catch)
 de los metodos que usen executeUpdate en la clase PersonaDAOJDBC, ya que si
 estos siguen presentes, se va a ejecutar primero el try catch de estos y
 si ocurre una excepcion no se hará rollback en la transaccion ,ya que
 primero se ejecutaria el catch más cercano al error; en es caso, el catch
 del metodo.
     */

    public static void main(String[] args) {
        Connection con = null;
        try {
            con=getConexion();
            con.setAutoCommit(false);
            PersonaDAO personaDao=new PersonaDAOJDBC(con);
            personaDao.select();
            con.commit();

        } catch (SQLException ex) {
            try {
                /**
                 * Si hay un error durante la transaccion, ejecutamos un
                 * rollback, para que no se ejecute ningun cambio
                 */
                System.out.println("Entrando rollback");
                con.rollback();
                ex.printStackTrace();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
            }
        }

    }

    private static PersonaDTO crearPersona(String nombre, String apellido, String email, String telefono) {
        PersonaDTO p = new PersonaDTO();
        p.setNombre(nombre);
        p.setApellido(apellido);
        p.setEmail(email);
        p.setTelefono(telefono);
        return p;
    }
}
