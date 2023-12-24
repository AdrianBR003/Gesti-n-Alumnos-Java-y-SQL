package def;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;

public class Proyectos extends Alumno {
    //#---VARIABLES---#
    private String Titulo;

    //#---CONSTRUCTOR definido---#
    public Proyectos(String DNI, LocalDate fechaNacimiento, int Curso, String Titulo) {
        super(DNI,fechaNacimiento, Curso);
        this.Titulo = Titulo;
    }

    //#---GETTER AND SETTER---#
    //Titulo
    public String getTitulo() {
        return Titulo;
    }
    public void setTitulo(String titulo) {
        Titulo = titulo;
    }
    //#---METODOS---#


}
