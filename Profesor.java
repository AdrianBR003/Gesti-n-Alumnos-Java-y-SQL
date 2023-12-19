package def;
import java.util.*;

public class Profesor {

    //#---VARIABLES---#
    private String DNI;
    private Set<String> asignaturas;  //Estructura de datos perfecta para almacenar datos elementos unicos sin un orden
    private Date fechaNacimiento;
    private int antiguedad;

    //#---CONSTRUCTOR definido---#
    public Profesor(String DNI, Date fechaNacimiento, int antiguedad) {
        this.DNI = DNI;
        this.fechaNacimiento = fechaNacimiento;
        this.antiguedad = antiguedad;
        this.asignaturas = new HashSet<>();
    }
    //#---GETTER AND SETTER---#
    //Dni
    public String getDNI() {
        return DNI;
    }
    public void setDNI(String DNI) {
        this.DNI = DNI;
    }
    //Asignaturas
        public Set<String> getAsignaturas() {
            return asignaturas;
        }
        public void setAsignaturas(Set<String> asignaturas) {
            this.asignaturas = asignaturas;
    }
    //FechaNacimiento
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    //Antiguedad
    public int getAntiguedad() {
        return antiguedad;
    }
    public void setAntiguedad(int antiguedad) {
        this.antiguedad = antiguedad;
    }

    //#---METODOS---#

}
