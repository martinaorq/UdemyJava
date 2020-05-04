package web;

import datos.ClienteDaoJDBC;
import dominio.Cliente;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "servletControlador", urlPatterns = {"/servletControlador"})
public class servletControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if(accion==null){
            accion="default";
        }
        switch (accion) {
            case "editar":
                editarCliente(request, response);
                break;
            case "eliminar":
                eliminarCliente(request,response);
                break;
            case "default":
                accionDefault(request, response);
                break;
        }

    }

    private void accionDefault(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Cliente> clientes = new ClienteDaoJDBC().listar();
        System.out.println("clientes = " + clientes);
        HttpSession sesion=request.getSession();
        sesion.setAttribute("clientes", clientes);
        sesion.setAttribute("totalClientes", clientes.size());
        sesion.setAttribute("saldoTotal", calcularSaldoTotal(clientes));
        //request.getRequestDispatcher("clientes.jsp").forward(request, response);
        response.sendRedirect("clientes.jsp");
    }

    private double calcularSaldoTotal(List<Cliente> clientes) {
        double saldoTotal = 0;
        for (Cliente c : clientes) {
            saldoTotal += c.getSaldo();
        }
        return saldoTotal;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion != null) {
            switch (accion) {
                case "insertar":
                    insertarCliente(request, response);
                    break;
                case "modificar":
                    modificarCliente(request,response);
                    break;
                default:
                    accionDefault(request, response);
                    break;
            }
        }
    }

    private void insertarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        Double sueldo = 0.0;
        String sue = request.getParameter("saldo");
        if (sue != null && !sue.equals("")) {
            sueldo = Double.parseDouble(sue);
        }

        Cliente c = new Cliente(nombre, apellido, email, telefono, sueldo);
        ClienteDaoJDBC clientes = new ClienteDaoJDBC();
        clientes.insertar(c);
        this.accionDefault(request, response);
    }

    private void editarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        Cliente cliente = new ClienteDaoJDBC().encontrarCliente(idCliente);
        System.out.println("cliente encontado: = " + cliente);
        request.setAttribute("cliente", cliente);
        request.getRequestDispatcher("/WEB-INF/paginas/clientes/editarCliente.jsp").forward(request, response);
        System.out.println("cliente seleccionado");
    }
    
    private void modificarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, ServletException, IOException{
        System.out.println("Editando cliente");
        int idCliente=Integer.parseInt(request.getParameter("idCliente"));
        System.out.println("idCliente = " + idCliente);
        String nombre=request.getParameter("nombre");
        String apellido=request.getParameter("apellido");
        String email=request.getParameter("email"); 
        String telefono =request.getParameter("telefono");
        String saldoString=request.getParameter("saldo");
        double saldo=0;
        if(!"".equals(saldoString)&& saldoString!=null){
            saldo=Double.parseDouble(saldoString);
        }
        Cliente cliente=new Cliente(idCliente,nombre,apellido,email,telefono,saldo);
        ClienteDaoJDBC manejoCliente=new ClienteDaoJDBC();
        manejoCliente.actualizar(cliente);
        accionDefault(request,response);
    }
    
    private void eliminarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        int idCliente=Integer.parseInt(request.getParameter("idCliente"));
        ClienteDaoJDBC manejador=new ClienteDaoJDBC();
        manejador.eliminar(idCliente);
        this.accionDefault(request,response);
    }
}
