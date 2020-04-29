package datos;

import domain.Persona;
import java.util.List;
import java.sql.*;
import static datos.Conexion.*;
import java.util.ArrayList;

public class PersonaJDBC {

    /*
    OPERACIONES CON MYSQL
    Esta clase está encargada de tener escritos ya los Querys que se van a utlizar luego cuando se ejecuten sentencias
    para cambiar, eliminar y crear datos en la tabla :)
    Constantes= no podrán ser modificadas y todas las clases que usen estos valores tendran los mismos valores de cadenas
     */
    private static final String SQL_SELECT = "SELECT id_persona, nombre , apellido, email, telefono FROM persona";
    //Luego los signos de pregunta serán reemplazados en el Prepares Statement que se va a crear cuando se hag la conexion
    private static final String SQL_INSERT = "INSERT INTO persona(nombre , apellido, email, telefono) VALUES (?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE persona set nombre=? ,apellido=?, email=?, telefono=? WHERE id_persona = ?";
    private static final String SQL_DELETE = "DELETE FROM persona WHERE id_persona= ?";

    /**
     * Método que devuelve una lista con todas las personas existentes en el
     * registro
     *
     * @return Devuelve lista de personas del registro
     */
    public static List<Persona> select() {
        //Primero preparamos las variables que vamos a utilizar
        Connection con = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        Persona persona = null;
        List<Persona> personas = new ArrayList();
        try {
            con = getConexion();
            sentencia = con.prepareStatement(SQL_SELECT);
            resultado = sentencia.executeQuery();
            while (resultado.next()) {
                persona = new Persona();
                //Vamos a hacer un objeto persona por cada registro que exista 
                //Asignamos el valor dependiendo de los registros de la tabla
                persona.setIdPersona(resultado.getInt("id_persona"));
                persona.setNombre(resultado.getString("nombre"));
                persona.setApellido(resultado.getString("apellido"));
                persona.setEmail(resultado.getString("email"));
                persona.setTelefono(resultado.getString("telefono"));
                //Agregamos persona a la lista
                personas.add(persona);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            closeObjects(con, sentencia);
        }
        return personas;
    }

    /**
     * Método que permite agregar un registro, agregando una nueva persona
     *
     * @param persona Objeto persona que se va a agregar al registro
     * @return Devuelve cantidad de registros modificaos
     */
    public static int insert(Persona persona) {
        //Preparamos variables a utilizar
        Connection con = null;
        PreparedStatement sentencia = null;
        int cantModificados = 0;

        try {
            con = getConexion();
            sentencia = con.prepareStatement(SQL_INSERT);
            /*
            Ahora es cuando vamos a hacer uso de nuestro PreparedStatement de forms correcta, vamos
            a rellenar los "?" que habíamos puesto en nuestra sentencia, para recordarla , aquí va:
            vamos a rellenar esos espacios con signos de pregunta con el metodo set, vamos a ir agregando los datos en el mismo orden que hay arriba.
            "INSERT INTO persona(nombre , apellido, email, telefono) VALUES (?,?,?,?)"
             */
            sentencia.setString(1, persona.getNombre());    //Primer "?"
            sentencia.setString(2, persona.getApellido());  //Segundo "?"
            sentencia.setString(3, persona.getEmail());
            sentencia.setString(4, persona.getTelefono());
            //Ejecutamos sentencia y de paso guardmos la cant de registros afectados
            cantModificados = sentencia.executeUpdate();
            System.out.println("Cantidad modificada= " + cantModificados);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            closeObjects(con, sentencia);
        }
        return cantModificados;
    }

    /**
     * Método que permite modificar un registro con el id de la persona
     *
     * @param persona nObjeto persona con los nuevos datos a modificar
     * @param registroACambiar El id_persona de la persona que se va a modificar
     * @return
     */
    public static int update(Persona persona, int registroACambiar) {
        //Variables que vamos a utilizar
        Connection con = null;
        PreparedStatement sentencia = null;
        ResultSet rs = null;
        int cantModificada = 0;

        try {
            con = getConexion();
            sentencia = con.prepareStatement(SQL_UPDATE);
            //Vamos a preparar nuestra sentencia
            //"UPDATE persona set nombre=? ,apellido=?, email=?, telefono=? WHERE id_persona = ?"
            sentencia.setString(1, persona.getNombre());
            sentencia.setString(2, persona.getApellido());
            sentencia.setString(3, persona.getEmail());
            sentencia.setString(4, persona.getTelefono());
            sentencia.setInt(5, registroACambiar);

            cantModificada = sentencia.executeUpdate();
            System.out.println("cantModificada = " + cantModificada);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            closeObjects(con, sentencia);
        }
        return cantModificada;
    }

    public static int delete(int idPersonaABorrar) {
        //Declaramos vriables a utilizar
        Connection con = null;
        PreparedStatement sentencia = null;
        int cantidadModificada = 0;
        
        try {
            con=getConexion();
            sentencia=con.prepareStatement(SQL_DELETE);
            //Preparamos sentencia
            //"DELETE FROM persona WHERE id_persona= ?"
            sentencia.setInt(1, idPersonaABorrar);
            cantidadModificada=sentencia.executeUpdate();
            System.out.println("cantidadModificada = " + cantidadModificada);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }finally{
            closeObjects(con,sentencia);
        }
        return cantidadModificada;
    }

}
