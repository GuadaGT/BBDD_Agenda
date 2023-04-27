package DAO;
import Models.Contacto;
import java.sql.*;
import java.util.ArrayList;

public class ContactoDAO
{
    // dirección URL de la base de datos
    private String url = "jdbc:mysql://localhost:3306/contactos";
    // usuario de la base de datos
    private String user = "root";
    // contraseña de la base de datos
    private String password = "";

    private Connection getConnection () throws SQLException
    {
        // devuelve una conexión a la base de datos
        return DriverManager.getConnection(url,user,password);
    }

    public  ArrayList<Contacto> getAllContactos()
    {
        // crea una lista para almacenar los contactos
        ArrayList<Contacto> contactos = new ArrayList<>();

        try
        {
            // obtiene una conexión a la base de datos
            Connection connection = getConnection();
            // crea un objeto de declaración
            Statement statement = connection.createStatement();

            // ejecuta una consulta y obtiene un conjunto de resultados
            ResultSet rs = statement.executeQuery("SELECT * FROM contactos");

            // itera sobre los resultados
            while (rs.next())
            {
                // crea un nuevo objeto de contacto
                Contacto c = new Contacto();
                // establece el nombre y apellidos del contacto
                c.setNombreApellidos(rs.getString("Nombre_Apellidos"));
                // establece el correo electrónico del contacto
                c.setCorreoElectronico(rs.getString("Correo_Electrónico"));
                // establece el teléfono del contacto
                c.setTelefono(rs.getInt("Teléfono"));
                // establece la fecha de nacimiento del contacto
                c.setFechaNacimiento(rs.getDate("Fecha_Nacimiento"));

                // agrega el contacto a la lista
                contactos.add(c);
            }
            // cierra la conexión a la base de datos
            connection.close();
            // cierra el objeto de declaración
            statement.close();
        }
        // maneja cualquier excepción que se produzca
        catch (Exception e)
        {
            // imprime el rastro de la pila de la excepción
            e.printStackTrace();
        }
        // devuelve la lista de contactos
        return  contactos;
    }



    public boolean insertContacto(Contacto contacto)
    {
        try
        {
            String sql = "INSERT INTO contactos (Nombre_Apellidos, Correo_Electrónico, Teléfono, Fecha_Nacimiento) VALUES (?,?,?,?)";

            // obtiene una conexión a la base de datos
            Connection con = getConnection();
            // prepara una declaración
            PreparedStatement statement = con.prepareStatement(sql);

            // establece el nombre y apellidos del contacto
            statement.setString(1, contacto.getNombreApellidos());
            // establece el correo electrónico del contacto
            statement.setString(2, contacto.getCorreoElectronico());
            // establece el teléfono del contacto
            statement.setInt(3, contacto.getTelefono());
            // establece la fecha de nacimiento del contacto
            statement.setDate(4, new Date(contacto.getFechaNacimiento().getTime()));

            //int res = statement.executeUpdate();
            //if(res>0){return true;}
            //else{return false;}

            // ejecuta la declaración y devuelve true si se insertó correctamente, de lo contrario devuelve false
            return statement.executeUpdate() > 0;
        }
        // maneja cualquier excepción que se produzca
        catch (Exception e)
        {
            // imprime el rastro de la pila de la excepción
            e.printStackTrace();
            // devuelve false en caso de error
            return false;
        }
    }

    public Contacto getContacto(String nombre)
    {
        String sql = "SELECT * FROM contactos WHERE Nombre_Apellidos = ? ";
        // crea un nuevo objeto de la clase Contacto
        Contacto contacto = new Contacto();

        try
        {
            // obtiene una conexión a la base de datos
            Connection con = getConnection();
            // prepara una declaración
            PreparedStatement statement = con.prepareStatement(sql);
            // establece el valor del parámetro de la consulta SQL
            statement.setString(1,nombre);
            // ejecuta la consulta SQL y obtiene un conjunto de resultados
            ResultSet resultSet = statement.executeQuery();

            // itera a través de los resultados
            while(resultSet.next())
            {
                // establece los valores de los campos del objeto Contacto a partir de los valores en los resultados
                contacto.setNombreApellidos(resultSet.getString("Nombre_Apellidos"));
                contacto.setCorreoElectronico(resultSet.getString("Correo_Electrónico"));
                contacto.setTelefono(resultSet.getInt("Teléfono"));
                contacto.setFechaNacimiento(resultSet.getDate("Fecha_Nacimiento"));
            }
            // cierra la conexión a la base de datos
            con.close();
            // cierra la declaración
            statement.close();
        }
        // maneja cualquier excepción que se produzca
        catch(Exception e)
        {
            // imprime el rastro de la pila de la excepción
            e.printStackTrace();
        }
        // devuelve el objeto Contacto con los valores establecidos
        return  contacto;
    }

    public boolean deleteContacto(String nombre)
    {
        // define la consulta SQL para eliminar un registro de la tabla contactos
        String sql = "DELETE FROM contactos WHERE Nombre_Apellidos = ? ";

        try
        {
            // obtiene una conexión a la base de datos
            Connection con = getConnection();
            // prepara una declaración
            PreparedStatement statement = con.prepareStatement(sql);
            // establece el valor del parámetro de la consulta SQL
            statement.setString(1,nombre);

            // ejecuta la consulta SQL y devuelve verdadero si se eliminó al menos un registro
            return  statement.executeUpdate() > 0;
        }
        // maneja cualquier excepción de SQL que se produzca
        catch (SQLException e)
        {
            // imprime el mensaje de la excepción
            System.out.println(e.getMessage());
            // devuelve falso porque no se pudo eliminar el registro
            return false;
        }
        // maneja cualquier otra excepción que se produzca
        catch (Exception e)
        {
            // imprime el rastro de la pila de la excepción
            e.printStackTrace();
            // devuelve falso porque no se pudo eliminar el registro
            return false;
        }
    }


    public boolean updateContacto(Contacto contacto, String nombre)
    {
        // Se define la consulta SQL para actualizar un contacto en la base de datos
        String sql =  "UPDATE contactos SET Nombre_Apellidos = ?, Correo_Electrónico = ?, Teléfono = ?, Fecha_Nacimiento = ? WHERE Nombre_Apellidos = ?";

        try
        {
            // Se obtiene una conexión a la base de datos
            Connection connection = getConnection();

            // Se prepara la consulta SQL para su ejecución
            PreparedStatement statement = connection.prepareStatement(sql);

            // Se establecen los valores a actualizar en la consulta SQL
            statement.setString(1, contacto.getNombreApellidos());
            statement.setString(2, contacto.getCorreoElectronico());
            statement.setInt(3, contacto.getTelefono());
            statement.setDate(4, new Date(contacto.getFechaNacimiento().getTime()));

            // Se establece el valor de la cláusula WHERE en la consulta SQL
            statement.setString(5,nombre);

            // Se ejecuta la consulta SQL y se devuelve un valor booleano según si se actualizó o no un contacto
            return statement.executeUpdate() > 0;
        }
        catch (SQLException e)
        {
            // En caso de una excepción de tipo SQLException se imprime su mensaje y se devuelve un valor booleano falso
            System.out.println(e.getMessage());
            return  false;
        }
        catch (Exception e)
        {
            // En caso de una excepción general se imprime su pila de llamadas y se devuelve un valor booleano falso
            e.printStackTrace();
            return false;
        }
    }


}
