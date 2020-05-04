package datos;

import dominio.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteDaoJDBC {

    private static final String SQL_SELECT = "SELECT id_cliente, nombre , apellido, email, telefono, saldo"
            + " FROM cliente";
    private static final String SQL_INSERT = "INSERT INTO cliente(nombre, apellido, email, telefono, saldo)"
            + "VALUES(?,?,?,?,?)";
    private static final String SQL_SELECT_BY_ID = "SELECT id_cliente, nombre , apellido, email, telefono, saldo"
            + " FROM cliente WHERE id_cliente=?";
    private static final String SQL_UPDATE = "UPDATE cliente"
            + " SET nombre= ?, apellido= ?, email= ?, telefono= ?, saldo=? WHERE id_cliente= ?";
    private static final String SQL_DELETE = "DELETE FROM cliente WHERE id_cliente=?";

    public List<Cliente> listar() {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Cliente> clientes = new ArrayList();
        try {
            con = Conexion.getConnection();
            stmt = con.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int clienteId = rs.getInt("id_cliente");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String email = rs.getString("email");
                String telefono = rs.getString("telefono");
                Double saldo = rs.getDouble("saldo");
                Cliente cliente = new Cliente(clienteId, nombre, apellido, email, telefono, saldo);
                clientes.add(cliente);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            Conexion.close(con, stmt, rs);
            return clientes;
        }
    }

    public Cliente encontrarCliente(int idCliente) {
        //"SELECT id_cliente, nombre , apellido, email, telefono, saldo  FROM cliente WHERE id_cliente=?";
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Cliente cliente = null;
        try {
            con = Conexion.getConnection();
            stmt = con.prepareStatement(SQL_SELECT_BY_ID);
            stmt.setInt(1,idCliente);
            rs = stmt.executeQuery();
            rs.absolute(1);//Nos posicionamos en el primer registro
            
            String nombre = rs.getString("nombre");
            String apellido = rs.getString("apellido");
            String email = rs.getString("email");
            String telefono = rs.getString("telefono");
            double saldo = rs.getDouble("saldo");
            cliente=new Cliente(idCliente,nombre,apellido,email,telefono,saldo);

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Problema en ClienteDaoJDBC");
        } finally {
            Conexion.close(con, stmt, rs);
            return cliente;
        }
    }
    
    public int insertar (Cliente cliente){
        Connection con = null;
        PreparedStatement stmt = null;
        int modified=0;
        try {
            con = Conexion.getConnection();
            stmt = con.prepareStatement(SQL_INSERT);
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getTelefono());
            stmt.setDouble(5, cliente.getSaldo());
            
            modified=stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            Conexion.close(con, stmt);
            return modified;
        }
    }
    
    public int actualizar(Cliente cliente){
        Connection con = null;
        PreparedStatement stmt = null;
        int modified=0;
        try {
            con = Conexion.getConnection();
            stmt = con.prepareStatement(SQL_UPDATE);
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getTelefono());
            stmt.setDouble(5, cliente.getSaldo());
            stmt.setInt(6, cliente.getIdCliente());
            modified=stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            Conexion.close(con, stmt);
            return modified;
        }
    }
    
    public int eliminar(int idCliente){
        Connection con = null;
        PreparedStatement stmt = null;
        int modified=0;
        try {
            con = Conexion.getConnection();
            stmt = con.prepareStatement(SQL_DELETE);
            stmt.setInt(1, idCliente);
            modified=stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            Conexion.close(con, stmt);
            return modified;
        }
    }
}
