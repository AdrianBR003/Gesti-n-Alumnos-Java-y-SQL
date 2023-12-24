package def;
import java.time.LocalDate;
import java.util.*;

public class Alumno {
    //#---VARIABLES---#
    private String DNI;
    private Map<Integer, List<String>> asignaturasPorCurso = new HashMap<>();
    private LocalDate fechaNacimiento;
    private int curso;
    //El alumno no tiene declarado el proyecto, ya que puede tenerlo o no tenerlo. Directamente se declara con la clase proyecto que hereda de la Alumno

    //#---CONSTRUCTOR definido---#
    public Alumno(String DNI, LocalDate fechaNacimiento, int curso) {
        this.DNI = DNI;
        this.fechaNacimiento = fechaNacimiento;
        this.curso = curso;
    }

    //#---GETTER AND SETTER---#
    //Dni
    public String getDNI() {
        return DNI;
    }
    public void setDNI(String DNI) {
        this.DNI = DNI;
    }
    //AsignaturasPorCurso
    public void getAsignaturasPorCurso() {
        if (this.curso == 1) {
            asignaturasPorCurso.put(curso, Arrays.asList("Matemáticas", "Historia", "Biología"));
        } else if (this.curso == 2) {
            asignaturasPorCurso.put(curso, Arrays.asList("Física", "Química", "Literatura"));
        } else {
            System.out.println("Curso no válido");
        }
    }
    public void setAsignaturasPorCurso(Map<Integer, List<String>> asignaturasPorCurso) {
        this.asignaturasPorCurso = asignaturasPorCurso;
    }
    //Fecha de Nacimiento
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    //Curso
    public int getCurso() {
        return curso;
    }
    public void setCurso(int curso) {
        this.curso = curso;
    }

}
