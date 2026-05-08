package Presentacion;

import datos.ClienteDAO;
import datos.IClienteDAO;
import dominio.Cliente;

import java.util.Scanner;

public class ZonaFitAPP {
    public static void main(String[] args) {
        zonaFitApp();
    }

    private static void zonaFitApp(){
        var salir = false;
        var consola = new Scanner(System.in);
        //Crear el objeto de la clase ClienteDao
        var clienteDao = new ClienteDAO();
        while(!salir){
            try {
                var opcion = mostrarMenu(consola);
                salir = ejecutarOpciones(consola,opcion, clienteDao);
            }catch (Exception e){
                System.out.println("Error al ejecutar las opciones " + e.getMessage());
            }
            System.out.println();
        }
    }

private static int mostrarMenu(Scanner consola){
    System.out.println("""
            *** Zona Fit (Gym)
            1. Listar Cliente
            2. Buscar Cliente
            3. Agregar Cliente
            4. Modificar Cleintes
            5. Eliminar Cliente
            6. Salir
            Elije una opción:\s""");
    return Integer.parseInt(consola.nextLine());
}

private static boolean ejecutarOpciones(Scanner consola, int opcion,
                                        IClienteDAO clienteDAO){
        var salir = false;
        switch (opcion){
            case 1 -> { //case 1 Listar clientes
                System.out.print("--- Listando clientes---");
                var clientes = clienteDAO.listarClientes();
                clientes.forEach(System.out::println);
            }
            case 2 -> { //2. Buscar cliente por ID
                System.out.print("Introduce el id del cliente ");
                var idCliente = Integer.parseInt(consola.nextLine());
                var cliente = new Cliente(idCliente);
                var encontrado = clienteDAO.buscarClienteporId(cliente);
                if (encontrado)
                    System.out.println("cliente encontrado" + cliente);
                else
                    System.out.println("cliente no encontrado");
            }
            case 3 -> { //3 Agregar cliente
                System.out.println("--- Agregar Cliente ---");
                System.out.println("Nombre: ");
                var nombre = consola.nextLine();
                System.out.println("apellido: ");
                var apellido = consola.nextLine();
                System.out.println("Membresia: ");
                var membresia = Integer.parseInt(consola.nextLine());
                //crear el objeto cliente (sin id)
                var cliente = new Cliente(nombre, apellido, membresia);
                var agregado = clienteDAO.agregarCliente(cliente);
                if (agregado)
                    System.out.println("Cliente agregado exitosamente " + cliente);
                else
                    System.out.println("Error al agregegar cliente" + cliente);


            }
            case 4 -> {//4 Modificar cliente
                System.out.println("--- Modificar cliente ---");
                System.out.println("id Cliente: ");
                var idCliente = Integer.parseInt(consola.nextLine());
                System.out.println("Nombre: ");
                var nombre = consola.nextLine();
                System.out.println("apellido: ");
                var apellido = consola.nextLine();
                System.out.println("Membresia: ");
                var membresia = Integer.parseInt(consola.nextLine());
                var cliente = new Cliente(idCliente,nombre, apellido, membresia);
                var modificado = clienteDAO.modificarCliente(cliente);
                if (modificado)
                    System.out.println("Cliente modificado: " + cliente);
                else
                    System.out.println("Error al modificar cliente " + cliente);

            }
            case 5 -> { //5. eliminar cliente
                // System.out.println("--- Eliminar cliente ---");
            System.out.println("id Cliente: ");
            var idCliente = Integer.parseInt(consola.nextLine());
            var cliente = new Cliente(idCliente);
            var eliminado = clienteDAO.eliminarCliente(cliente);
            if (eliminado)
                System.out.println("Cliente eliminado: " + cliente);
            else
                System.out.println("Error al eliminar cliente " + cliente);

        }
        case 6 -> { //case 6 Salir
            System.out.println("¡Hasta pronto!");
            salir = true;
        }
            default -> System.out.println("Opcion no reconocida");
        }

        return salir;
    }
}
