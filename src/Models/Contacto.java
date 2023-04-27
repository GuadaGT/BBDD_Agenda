package Models;
import java.util.Date;

public class Contacto {
    private String nombreApellidos;
    private String correoElectronico;
    private int telefono;
    private Date fechaNacimiento;

    public Contacto()
    {
    }
    public Contacto(String nombreApellidos, String correoElectronico, int telefono, Date fechaNacimiento) {
        this.nombreApellidos = nombreApellidos;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNombreApellidos() {
        return nombreApellidos;
    }

    public void setNombreApellidos(String nombreApellidos) {
        this.nombreApellidos = nombreApellidos;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public String toString() {
        return "------- CONTACTO -------\n" + "Nombre: " + nombreApellidos
                + "\n" + "Email: " + correoElectronico + "\n" + "Telefono: " + telefono
                + "\n" + "Fecha de nacimiento: " + fechaNacimiento;
    }
}
