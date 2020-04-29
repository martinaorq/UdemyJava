package test;

import static datos.Conexion.getConexion;
import datos.PersonaJDBC;
import domain.Persona;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManejoPersonas {

    /**
     * Cosas a destacar: - Tendriamos que eliminar los catchs (de los try catch)
     * de los metodos que usen executeUpdate en la clase PersonaJDBC, ya que si
     * estos siguen presentes, se va a ejecutar primero el try catch de estos y
     * si ocurre una excepcion no se hará rollback en la transaccion ,ya que
     * primero se ejecutaria el catch más cercano al error; en es caso, el catch
     * del metodo.
     */

    public static void main(String[] args) {
        Connection con = null;
        try {
            //Vamos a crear un objeto conexion, recordemos que ahora 
            //vamos a manejar la conexion desde fuera de la clase
            con = getConexion();
            //Nos fijamos si la conexion tiene AutoCommit, 
            //si es así ,cambiamos su valor a falso:
            if (con.getAutoCommit()) {
                con.setAutoCommit(false);
                //Necesitmos que el commit no se haga de forma automatica
            }
            PersonaJDBC personaJDBC = new PersonaJDBC(con);
            /**
             * Si el construtor estuviese vacío NO se ejecutaria el manejo
             * transaccional,así lo definimos en los métodos Algo característico
             * de las transacciones es que se pueden ejecutar varias sentencias
             * sin la necesidad de abrir y cerrar la cone -xion de vuelta, es
             * decir, hace uso de una sola conexion que puede ser pasada por
             * parametros, por poner un ejemplo. Ahora vamos a hacer una
             * transaccion con un update y un insert dentro de nuestra tabla
             * persona.
             */

            Persona p1 = crearPersona("Zamira", "Guevara", "zg@gmail.com", "1234");
            personaJDBC.update(p1, 1);

            Persona p2 = crearPersona("Rose", "Quartz", "rs@gmail.co", "5545454444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444");
            personaJDBC.insert(p2);

            con.commit();
            /**
             * Cuando estamos seguros de la transaccion, hacemos commit para
             * guardar los cambios
             */

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

    private static Persona crearPersona(String nombre, String apellido, String email, String telefono) {
        Persona p = new Persona();
        p.setNombre(nombre);
        p.setApellido(apellido);
        p.setEmail(email);
        p.setTelefono(telefono);
        return p;
    }
}
