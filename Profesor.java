package def;
import java.time.LocalDate;
import java.util.*;

public class Profesor {

    //#---VARIABLES---#
    private String DNI;
    private String asignatura;  //1 asign por profesor -> Relacion 1:N (darClase)
    private LocalDate fechaNacimiento;
    private int antiguedad;

    //#---CONSTRUCTOR definido---#
    public Profesor(String DNI, LocalDate fechaNacimiento, int antiguedad, String asignatura) {
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
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
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
