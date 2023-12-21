package def;
import java.util.*;

public class Profesor {

    //#---VARIABLES---#
    private String DNI;
    private String asignatura;  //1 asign por profesor -> Relacion 1:N (darClase)
    private Date fechaNacimiento;
    private int antiguedad;

    //#---CONSTRUCTOR definido---#
    public Profesor(String DNI, Date fechaNacimiento, int antiguedad, String asignatura) {
        this.DNI = DNI;
        this.fechaNacimiento = fechaNacimiento;
        this.antiguedad = antiguedad;
        this.asignatura = asignatura;
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
        public String getAsignaturas() {
            return asignatura;
        }
        public void setAsignaturas(String asignatura) {
            this.asignatura = asignatura;
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
