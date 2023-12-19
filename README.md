Características: 
1.- Uso de la librería .sql
2.- Se utilizan 'HashSet' y Herencia


Clases que se usan con sus atributos:

//Alumno:
public Alumno(String DNI, Date fechaNacimiento, int curso) {
        this.DNI = DNI;
        this.fechaNacimiento = fechaNacimiento;
        this.curso = curso;
    }

//Profesor:
public Profesor(String DNI, Date fechaNacimiento, int antiguedad) {
        this.DNI = DNI;
        this.fechaNacimiento = fechaNacimiento;
        this.antiguedad = antiguedad;
        this.asignaturas = new HashSet<>();
}

//Proyecto
public Proyectos(String DNI, Date fechaNacimiento, int Curso, String Titulo) {
        super(DNI,fechaNacimiento, Curso); //Hija de Alumno
        this.Titulo = Titulo;
    }
    
