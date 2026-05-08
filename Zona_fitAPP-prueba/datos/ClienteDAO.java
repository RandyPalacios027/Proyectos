package datos;

import dominio.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static Zona_FIt.Conexion.Conexion.getConexion;

public class ClienteDAO implements IClienteDAO {

    @Override
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        var sql = "SELECT * FROM cliente ORDER BY id";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                var cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setMembresia(rs.getInt("membresia"));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar clientes: " + e.getMessage());
        }
        finally {
            try {
                con.close();
            }catch (Exception e){
                System.out.println("Error al cerra conexion: " + e.getMessage());
            }
        }
        return clientes;
    }

    @Override
    public boolean buscarClienteporId(Cliente cliente) {
        PreparedStatement ps;
        ResultSet rs;
        var con = getConexion();
        var sql = "SELECT * FROM cliente WHERE id = ? ";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, cliente.getId());
            rs = ps.executeQuery();
            if (rs.next()){
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setMembresia(rs.getInt("membresia"));
                return true;
            }
        } catch (Exception e){
            System.out.println("Error al recuperar cliente por id: " + e.getMessage());
        }
        finally {
            try {
                con.close();
            } catch (Exception e){
                System.out.println("Error al cerra conexion:" + e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean agregarCliente(Cliente cliente) {
        PreparedStatement ps;
        Connection con = getConexion();
        String sql = "INSET INTO cliente(nombre, apellido, membresia)" +
                 "VALUES(?, ?, ?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setInt(3,cliente.getMembresia());
            ps.execute();
            return true;
        }catch (Exception e){
            System.out.println("Error al agregar cliente" + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean modificarCliente(Cliente cliente) {
        PreparedStatement ps;
        Connection con = getConexion();
        var sql = "UPDATE cliente SET nombre =?, apellido=?=, membresia=? " + "WHERE id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1,cliente.getNombre());
            ps.setString(2,cliente.getApellido());
            ps.setInt(3,cliente.getMembresia());
            ps.setInt(4,cliente.getId());

        }catch (Exception e){
            System.out.println("Error al modificar cliente " + e.getMessage());
        }finally {
            try {
                con.close();
            }catch (Exception e){
                System.out.println("Error al cerrar conexión " + e.getMessage());
            }
        }

        return false;
    }

    @Override
    public boolean eliminarCliente(Cliente cliente) {
        PreparedStatement ps;
        Connection con = getConexion();
        String sql = "DELETE FROM cliente WHERERE id  = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, cliente.getId());
            ps.execute();
        }catch (Exception e){
            System.out.println("Error al eliminar Cleinte: " + e.getMessage());
        }finally {
            try {
                con.close();
            }catch (Exception e){
                System.out.println("Errpr a; cerrar conexión: " + e.getMessage());
            }

        }
        return false;
    }

    public static void main(String[] args) {
        // Listar clientes
        IClienteDAO clienteDao = new clienteDAO();

        //Modificar clientes
        var modificarCliente = new Cliente(5, "Carlos Daniel","ortiz", 300);

//        System.out.println("*** Listar clientes ***");
//        IClienteDAO clienteDao = new ClienteDAO();
//        cIientes.forEach(System.out:: println);

//        //buscar por id
//        var cliente1 = new Cliente(2);
//        System.out.println("Cliente antes de la busqueda: " + cliente1);
//        var encontrado = clienteDao.buscarClienteporId(cliente1);
//        if (encontrado)
//            System.out.println("Cliente encontrado: " + cliente1);
//        else
//            System.out.println("No se encontro registro del cliente: " + cliente1.getId());
        //agregar cliente
        var nuevoCliente = new Cliente("Daniel","Aranda", 700);

        var agregado = clienteDao.agregarCliente(nuevoCliente);if (agregado)
            System.out.println("Cliente agregado: " + nuevoCliente);
        else
            System.out.println("No se agrego el cliente: " + nuevoCliente);

        //Eliminar cliente
        var clienteEliminar = new Cliente(5);
        var Eliminado = clienteDao.eliminarCliente(clienteEliminar);

        var eliminado = clienteDao.eliminarCliente(clienteEliminar);
        if (Eliminado)
            System.out.println("cliente eliminado exitosamente: " + clienteEliminar);
        else
            System.out.println("Error al eliminar Cliente" + clienteEliminar);
    }
}
