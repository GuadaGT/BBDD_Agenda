import DAO.ContactoDAO;
import Models.Contacto;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {
    // Definimos una instancia estática de la clase Scanner para leer desde la entrada estándar
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args)
    {
        // Creamos una instancia de ContactoDAO para interactuar con la base de datos
        ContactoDAO contactoDAO = new ContactoDAO();

        // Definimos una variable para almacenar la opción elegida por el usuario
        int opcion = 0;

        // Creamos un bucle que se ejecuta hasta que el usuario elige la opción de salir del programa
        do {
            // Mostramos el menú de opciones al usuario
            menu();

            // Leemos la opción elegida por el usuario
            opcion = sc.nextInt();

            // Limpiamos el buffer del Scanner
            sc.nextLine();

            // Evaluamos la opción elegida por el usuario mediante una estructura switch
            switch (opcion)
            {
                case 1:
                    agregarContacto(contactoDAO);
                    break;
                case 2:
                    eliminarContacto(contactoDAO);
                    break;
                case 3:
                    modificarContacto(contactoDAO);
                    break;
                case 4:
                    listarContactos(contactoDAO);
                    break;
                case 5:
                    buscarContacto(contactoDAO);
                    break;
                case 6:
                    System.out.println("Saliendo del programa...");
                    sc.close();
                    break;
                default:
                    System.out.println("Introduce una opcion valida, por favor.");
            }
        }
        // Salimos del bucle si el usuario elige la opción de salir del programa
        while (opcion != 6) ;
    }

    private static void agregarContacto(ContactoDAO contactoDAO)
    {
        System.out.println("Vas a agregar un nuevo contacto");
        // Se piden los datos del contacto al usuario y se almacenan en variables locales.
        System.out.println("Indica nombre y apellidos: ");
        String nombre = sc.nextLine();
        System.out.println("Indica correo electronico: ");
        String correo = sc.nextLine();
        System.out.println("Indica telefono: ");
        int telefono = sc.nextInt();
        sc.nextLine();
        System.out.println("Indica fecha de nacimiento (dd-MM-yyyy): ");
        String fecha = sc.nextLine();
        Date fecha_nacimiento;

        try
        {
            // Se convierte la fecha introducida en un objeto de tipo Date utilizando un objeto SimpleDateFormat.
            fecha_nacimiento = new SimpleDateFormat("dd-MM-yyyy").parse(fecha);

            // Se crea una instancia de la clase Contacto con los datos introducidos.
            Contacto contacto = new Contacto(nombre,correo,telefono,fecha_nacimiento);

            // Se llama al método insertContacto de la clase ContactoDAO para insertar el contacto en la base de datos.
            if(contactoDAO.insertContacto(contacto))
            {
                // Si la inserción es exitosa, se imprime un mensaje indicando que el contacto se ha agregado con éxito.
                System.out.println("Contacto agregado con exito.");
            }
            else
            {
                // Si la inserción falla, se imprime un mensaje indicando que hubo una incidencia.
                System.out.println("Incidencia al agregar el contacto. ");
            }
        }
        catch(Exception e)
        {
            // Si se produce una excepción al convertir la fecha introducida en un objeto de tipo Date, se imprime
            // un mensaje indicando que la fecha de nacimiento es incorrecta y no se agrega el contacto.
            e.printStackTrace();
            System.out.println("Fecha de nacimiento incorrecta. No se ha agregado el contacto.");
        }
    }

    private static void modificarContacto(ContactoDAO contactoDAO)
    {
        // Se muestra un mensaje al usuario indicando que se va a modificar un contacto
        System.out.println("Vamos a modificar un contacto. ");
        // Se solicita al usuario que indique el nombre y apellidos del contacto a modificar
        System.out.println("Indica el nombre y los apellidos del contacto a modificar:");
        String nombreBusqueda = sc.nextLine();
        // Se busca el contacto en la base de datos a través de su nombre y apellidos
        Contacto contacto = contactoDAO.getContacto(nombreBusqueda);

        // Si el contacto existe en la base de datos
        if(contacto.getNombreApellidos() != null)
        {
            // Se solicita al usuario que introduzca los nuevos datos del contacto
            System.out.println("Indica el nuevo nombre y apellidos para el contacto: ");
            String nombreNuevo = sc.nextLine();
            System.out.println("Indica el nuevo email para el contacto: ");
            String emailNuevo = sc.nextLine();
            System.out.println("Indica el nuevo teléfono para el contacto: ");
            int telefonoNuevo = sc.nextInt();
            sc.nextLine();
            System.out.println("Indica la nueva fecha de nacimiento para el contacto (dd-MM-yyyy): ");
            String fecha = sc.nextLine();
            try
            {
                // Se convierte la fecha introducida por el usuario a un objeto Date
                Date fechaNueva = new SimpleDateFormat("dd-MM-yyyy").parse(fecha);

                // Se crea un nuevo objeto Contacto con los datos introducidos por el usuario
                Contacto c = new Contacto(nombreNuevo, emailNuevo, telefonoNuevo, fechaNueva);

                // Se actualiza el contacto en la base de datos
                if(contactoDAO.updateContacto(c, nombreBusqueda))
                {
                    System.out.println("Contacto modificado con éxito. ");
                }
            }
            catch(Exception e)
            {
                // Si hay algún error al convertir la fecha, se muestra el mensaje de error correspondiente
                e.printStackTrace();
            }
        }
    }

    public static void buscarContacto(ContactoDAO contactoDAO)
    {
        // Se pide el nombre del contacto a buscar
        System.out.println("Indica el nombre y los apellidos del contacto a buscar:");
        String nombre = sc.nextLine();

        // Se busca el contacto con el nombre indicado
        Contacto contacto = contactoDAO.getContacto(nombre);

        // Si se encontró un contacto, se muestra su información
        if (contacto.getNombreApellidos() != null && contacto.getCorreoElectronico() != null && contacto.getTelefono() != 0 && contacto.getFechaNacimiento() != null)
        {
            System.out.println("Contacto encontrado: " + contacto);
        }
        // Si no se encontró un contacto, se muestra un mensaje indicándolo
        else
        {
            System.out.println("Contacto no encontrado.");
        }
    }

    private static void listarContactos(ContactoDAO contactoDAO)
    {
        // Se obtienen todos los contactos guardados en la base de datos
        ArrayList<Contacto> contactos = contactoDAO.getAllContactos();

        // Se muestra la lista de contactos
        System.out.println("Lista de contactos: ");
        //bucle for-each para recorrer cada objeto Contacto en la lista contactos. Para cada objeto Contacto en la lista,
        //se llama al método toString() implícitamente al imprimir c con la función System.out.println().
        // El método toString() de la clase Contacto está diseñado para devolver una representación de cadena legible
        // para humanos del objeto Contacto, que incluye información como el nombre, el correo electrónico, el teléfono y la fecha de nacimiento del contacto.
        for (Contacto c : contactos)
        {
            System.out.println(c);
        }
    }

    public static void eliminarContacto(ContactoDAO contactoDAO)
    {
        // Se muestra un mensaje para indicar que se va a eliminar un contacto
        System.out.println("Vas a eliminar un contacto.");

        // Se solicita al usuario el nombre del contacto que desea eliminar
        System.out.println("Indica el nombre y los apellidos del contacto a buscar: ");
        String nombre = sc.nextLine();

        // Se llama al método deleteContacto de la clase ContactoDAO para eliminar el contacto con el nombre indicado
        if (contactoDAO.deleteContacto(nombre))
        {
            // Si se elimina el contacto correctamente, se muestra un mensaje indicando que ha sido eliminado con éxito
            System.out.println("Contacto eliminado con éxito,");
        }
        else
        {
            // Si no se encuentra el contacto que se desea eliminar, se muestra un mensaje indicando que no se ha encontrado
            System.out.println("No se ha encontrado el contacto que indica. Por favor, indique la opcion del menu que desea realizar. ");
        }
    }

    public static void menu()
    {
        System.out.println("Seleccione una opción:");
        System.out.println("1. Agrega un contacto.");
        System.out.println("2. Elimina un contacto.");
        System.out.println("3. Modifica un contacto.");
        System.out.println("4. Muestra todos los contactos.");
        System.out.println("5. Busca un contacto.");
        System.out.println("6. Salir");
        System.out.print("Opción: ");
        }
}
