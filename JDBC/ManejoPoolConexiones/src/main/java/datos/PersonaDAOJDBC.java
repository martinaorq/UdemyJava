package datos;

import domain.PersonaDTO;
import java.util.List;
import java.sql.*;
import static datos.Conexion.*;
import java.util.ArrayList;

public class PersonaDAOJDBC implements PersonaDAO{

    /**
     * En las transacciones se maneja lo que seran el commit y el rollback, las
     * transacciones son instrucciones agrupadas en las cuales se puede decidir
     * si guardar o no los cambios con lo mecionado anteriormente, pero para eso
     * tenemos que ser capaces de manejar la conexion por separado, ya que cada
     * vez que se cierra se hace commit (por mas de que autocommit= falso) y asi
     * no podremos decidir si guardar o no cambios, asi que esta clase se
     * centrará en eso.
     */
    /**
     * La conexion no se va a cerrar, se mantendrá abierta y se controlará la
     * transaccion desde fuera de los métodos
     */
    private static final String SQL_SELECT = "SELECT id_persona, nombre , apellido, email, telefono FROM persona";
    private static final String SQL_INSERT = "INSERT INTO persona(nombre , apellido, email, telefono) VALUES (?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE persona set nombre=? ,apellido=?, email=?, telefono=? WHERE id_persona = ?";
    private static final String SQL_DELETE = "DELETE FROM persona WHERE id_persona= ?";
    private static Connection conexionTransaccional;

    public PersonaDAOJDBC() {
    }

    public PersonaDAOJDBC(Connection conexionTransaccional) {
        this.conexionTransaccional = conexionTransaccional;
    }

    public List<PersonaDTO> select() throws SQLException{
        //Primero preparamos las variables que vamos a utilizar
        Connection con = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        PersonaDTO persona = null;
        List<PersonaDTO> personas = new ArrayList();
        try {
            con = conexionTransaccional != null ? conexionTransaccional : getConexion();
            sentencia = con.prepareStatement(SQL_SELECT);
            resultado = sentencia.executeQuery();
            while (resultado.next()) {
                persona = new PersonaDTO();
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
            if (conexionTransaccional == null) {
                closeObjects(con, sentencia);
            } else {
                closeObjects(sentencia);
            }
        }
        return personas;
    }

    /**
     * Método que permite agregar un registro, agregando una nueva persona
     *
     * @param persona Objeto persona que se va a agregar al registro
     * @return Devuelve cantidad de registros modificaos
     */
    public int insert(PersonaDTO persona) throws SQLException{
        //Preparamos variables a utilizar
        Connection con = null;
        PreparedStatement sentencia = null;
        int cantModificados = 0;

        try {
            con = conexionTransaccional != null ? conexionTransaccional : getConexion();
            sentencia = con.prepareStatement(SQL_INSERT);
            sentencia.setString(1, persona.getNombre());
            sentencia.setString(2, persona.getApellido());
            sentencia.setString(3, persona.getEmail());
            sentencia.setString(4, persona.getTelefono());
            cantModificados = sentencia.executeUpdate();
            System.out.println("Cantidad modificada= " + cantModificados);
        }finally {
            if (conexionTransaccional == null) {
                closeObjects(con, sentencia);
            } else {
                closeObjects(sentencia);
            }
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
    public int update(PersonaDTO persona, int registroACambiar) throws SQLException{
        //Variables que vamos a utilizar
        Connection con = null;
        PreparedStatement sentencia = null;
        ResultSet rs = null;
        int cantModificada = 0;

        try {
            con = conexionTransaccional != null ? conexionTransaccional : getConexion();
            sentencia = con.prepareStatement(SQL_UPDATE);
            sentencia.setString(1, persona.getNombre());
            sentencia.setString(2, persona.getApellido());
            sentencia.setString(3, persona.getEmail());
            sentencia.setString(4, persona.getTelefono());
            sentencia.setInt(5, registroACambiar);

            cantModificada = sentencia.executeUpdate();
            System.out.println("cantModificada = " + cantModificada);
        }finally {
            if (conexionTransaccional == null) {
                closeObjects(con, sentencia);

            } else {
                closeObjects(sentencia);
            }
        }
        return cantModificada;
    }

    public int delete(int idPersonaABorrar) throws SQLException{
        //Declaramos vriables a utilizar
        Connection con = null;
        PreparedStatement sentencia = null;
        int cantidadModificada = 0;

        try {
            con = conexionTransaccional != null ? conexionTransaccional : getConexion();
            sentencia = con.prepareStatement(SQL_DELETE);
            sentencia.setInt(1, idPersonaABorrar);
            cantidadModificada = sentencia.executeUpdate();
            System.out.println("cantidadModificada = " + cantidadModificada);
        }finally {
            if (conexionTransaccional == null) {
                closeObjects(con, sentencia);
            }else{
                closeObjects(sentencia);
            }
            return cantidadModificada;
        }

    }
}
