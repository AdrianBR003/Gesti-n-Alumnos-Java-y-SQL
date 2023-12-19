package def;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class Main {

    //#---VARIABLES GLOBALES---#
    //Funciones Scanner:
    static Scanner scI = new Scanner(System.in); //Scanner para Enteros (Integer)
    static Scanner scS = new Scanner(System.in); //Scanner para String
    static Scanner scB = new Scanner(System.in); //Scanner para Boolean

    public static void main(String[] args) throws SQLException { //Hay que añadir el SQLExepction al utilizar el metodo conn()
        // --------------- CONEXION CON LA BASE DE DATOS ---------------
        // Cargar el driver de MySQL (asegúrate de tener el conector MySQL JDBC en tu proyecto)
        //Conexion con la BD

        // --------------- INICIO MODIFICACION EN LA BASE DE DATOS ---------------
//          selectDispositivos(conn); //Prueba con tabla practica 3
        conn();
        System.out.println("Conectado a la base de datos");
        //Inicializamos e insertamos los objetos profesor
        iniProf();
        iniAlum();
        iniProyec();
        menuP();

        // --------------- FIN MODIFICACION EN LA BASE DE DATOS ---------------
    }

    // --------------- INICIO METODOS ---------------
    //CONN
    // {
    public static Connection conn() {
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/base_de_datos", "root", "usuario");
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

    //}
    //MENUS Y SWITCHS:{
    public static void menuP() throws SQLException { //Menu principal
        System.out.println("*** Bienvenido ProfesorX al menu de la base de datos del alumnado: ***\n\n");
        System.out.println("Seleccione la opción que desea ejecutar:\n");
        System.out.println("[1] Listar las tablas de la base de datos \n");
        System.out.println("[2] Filtrar por.. \n");
        System.out.println("[3] Listar.. \n");
        System.out.println("[4] Anadir.. \n");
        System.out.println("[5] Eliminar.. \n");
        System.out.println("[6] Anadir proyecto a un alumno \n");
        System.out.println("[7] Eliminar proyecto a un alumno \n");
        System.out.println("[8] Listar a todos los alumnos con sus respectivos proyectos \n");
        swP(scI.nextInt());
    }

    public static Runnable swP(int opcionP) throws SQLException {
        switch (opcionP) {
            case 1://Listar las tablas de la base de datos
                ListATab(conn());
                salir();
                break;
            case 2://Filtrar
                menuF();
                salir();
                break;
            case 3://Listar solo a los alumnos
                ListA(conn());
                salir();
                break;
            case 4://Anadir..
                //Variables:
                int opcion;
                //
                opcion = menuFAPA(false);
                if (opcion == 1) { // Devuelve 1 para Alum || 2 Prof || -1 ERR
                    Anadir(1);
                    salir();
                } else if (opcion == 2) {
                    Anadir(2);
                    salir();
                } else {
                    System.out.println("\nER opcion no valida - sWP\n");
                    menuFAPA(false); //va a volver a llamar al metodo
                    swP(4);
                }
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
        }
        return null;
    }

    public static void menuF() {
        System.out.println("Filtrar por: \n");
        System.out.println("[1] Curso: Se mostraran a los alumnos del curso que inserte \n");
        System.out.println("[2] Asignaturas: ...\n");
        System.out.println("[3] Fecha de Nacimiento: ...\n");
        System.out.println("[4] Antiguedad: Filtra a los profesores por la antiguedad que inserte\n");
        System.out.println("[5] OPCIONAL:\n");
        swF(scI.nextInt());
    }

    /*Nota sobre filtrar:
   - Podemos filtrar por:
       1) Curso: Donde filtramos a los alumnos de un curso determinado (1º ò 2º)
       2) Asignaturas: Tenemos 3 resultados posibles ->
           1.- Donde se muestran los alumnos que dan determinadas asignaturas (DNI alumno)
           2.- Donde se muestras a los profesores que imparten las determinadas asignaturas (DNI profesor)
           3.- Donde se muestran ambos (DNI alumno - DNI profesor, dependiendo de la asignatura)
           4.- Mostrar la tabla completa donde se muestren todas las relaciones con todas las asignaturas
       3) Fecha de nacimiento: Muestra o bien alumnos, o profesores, o ambos.
       4) Antiguedad: Filtra a los profesores por antiguedad (V.Unica P)
       5) OPCIONAL si da tiempo: Añadir atributo 'date' a proyectos y filtrarlos por fecha.
   */
    public static void swF(int opcionF) {
        switch (opcionF) { //Filtra por:
            case 1://Curso
                break;
            case 2://Asignaturas
                System.out.println("Filtra las asignaturas por: \n");
                menuFAPA(true); //Activada la opcion de ambos
                break;
            case 3://Fecha Nacimiento
                System.out.println("Filtra la fecha de nacimiento por: \n");
                menuFAPA(true);
                break;
            case 4://Antiguedad
                break;
            case 5://Opcional
                break;
        }
    }

    public static int menuFAPA(boolean apa) { //Alumnos-Profesor-Ambos
        System.out.println("[1] Alumno\n");
        System.out.println("[2] Profesor\n");
        if (apa) {
            System.out.println("[3] Ambos\n");
        }
        return scI.nextInt();
    }

    //}
    //METODOS PARA FILTRAR - LISTAR
    //{
    //Filtrar Dispositivos (predeterminado)
    public static void selectDispositivos(Connection conn) { // #Listar la tabla alumnos
        try {
            // Crear un objeto Statement para ejecutar consultas SQL
            Statement statement = conn.createStatement();
            // Ejemplo: insertar un nuevo registro en una tabla
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Dispositivos"); //Para los select, el resto de operaciones tienen otro formato buscar información de como funciona JDBC
            while (resultSet.next()) {
                // Aquí puedes acceder a los datos de cada fila
                int id = resultSet.getInt("idDispositivo"); //Se indica la variable donde se almacena y la columna de la que se extrae el dato
                String nombre = resultSet.getString("nombre");
                String tipo = resultSet.getString("tipo");
                String ip = resultSet.getString("ip");

                // Imprimir los datos (esto es solo un ejemplo, puedes hacer lo que necesites con los datos)
                System.out.println("ID: " + id + ", Columna1: " + nombre + ", Columna2: " + tipo + ", IP: " + ip);
            }
            // Cerrar la conexión y el statement
            statement.close();
            conn.close();
            ;
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos");
            e.printStackTrace();
        }
    }

    //Listar las tablas de la base de datos ([1] MenuP)
    public static void ListATab(Connection conn) {
        try {
            // Crear un objeto Statement para ejecutar consultas SQL
            Statement statement = conn.createStatement();
            ResultSet resultSetTables = statement.executeQuery("SHOW TABLES");

            while (resultSetTables.next()) { // Recorre las tablas
                String tableName = resultSetTables.getString(1); // Obtiene el nombre de la tabla
                System.out.println("Tabla: " + tableName);
                System.out.println("-----------------------");

                Statement statementForTable = conn.createStatement(); // Nuevo Statement para cada tabla
                ResultSet resultSetTableData = statementForTable.executeQuery("SELECT * FROM " + tableName);
                ResultSetMetaData resultMD = resultSetTableData.getMetaData();
                int columnCount = resultMD.getColumnCount();

                while (resultSetTableData.next()) { // Recorre los resultados de la tabla actual
                    for (int i = 1; i <= columnCount; i++) {
                        String columnValue = resultSetTableData.getString(i);
                        System.out.println(resultMD.getColumnName(i) + ":" + columnValue + "\t");
                    }
                    System.out.println();
                }
                resultSetTableData.close(); // Cierra el ResultSet de la tabla actual (se cerraba automaticamente con un resultSetTab)
                statementForTable.close(); // Cierra el Statement de la tabla actual
            }
            resultSetTables.close(); // Cierra el ResultSet de las tablas
            statement.close(); // Cierra el Statement principal
        } catch (SQLException e) {
            System.out.println("\nError en ListATab\n");
            e.printStackTrace();
        }
    }

    //Listar tabla Alumnos con sus atributos:
    public static void ListA(Connection conn) {
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSetTables = statement.executeQuery("SHOW TABLES");
            String tableName = null;

            while (resultSetTables.next()) {
                String currentTableName = resultSetTables.getString(1);
                if (currentTableName.equals("alumno")) {
                    tableName = currentTableName;
                    break;
                }
            }

            if (tableName != null) {
                System.out.println("Tabla: " + tableName);
                System.out.println("-----------------------");

                Statement statementForTable = conn.createStatement();
                ResultSet resultSetTableData = statementForTable.executeQuery("SELECT * FROM " + tableName);
                ResultSetMetaData resultMD = resultSetTableData.getMetaData();
                int columnCount = resultMD.getColumnCount();

                System.out.println("Datos de la tabla:");

                // Imprimir los nombres de las columnas
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print("||");
                    String columnName = resultMD.getColumnName(i);
                    System.out.print(columnName);
                    System.out.print("||");
                    System.out.print("\t");
                }
                System.out.println();

                // Imprimir los datos de la tabla
                while (resultSetTableData.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        Object value = resultSetTableData.getObject(i);
                        System.out.print(value + "\t\t");
                    }
                    System.out.println();
                }
            } else {
                System.out.println("La tabla 'alumno' no fue encontrada.");
            }
        } catch (SQLException e) {
            System.out.println("\nError en ListA\n");
            e.printStackTrace();
        }

    }
    //} Metodos para filtrar - listar

    //Metodos INICIALIZAR E INSERTAR
    //{
    public static void iniProf() throws SQLException { //PROFESOR
        //Creacion del profesor 1
        Profesor profesor1 = new Profesor("12345678A", new Date(80, 0, 15), 5);
        Set<String> asignaturasProfesor1 = new HashSet<>(); //Creamos el Hashset para el profesor1 usando Set para declarar que no se repita ningun String
        asignaturasProfesor1.add("Matemáticas");
        asignaturasProfesor1.add("Física");
        profesor1.setAsignaturas(asignaturasProfesor1);
        insertProf(conn(), profesor1); //Esto obliga a poner el SQLException
        // Creación de profesor 2
        Profesor profesor2 = new Profesor("98765432B", new Date(75, 2, 28), 8);
        Set<String> asignaturasProfesor2 = new HashSet<>();
        asignaturasProfesor2.add("Historia");
        asignaturasProfesor2.add("Literatura");
        profesor2.setAsignaturas(asignaturasProfesor2);
        insertProf(conn(), profesor2);
        // Creación de profesor 3
        Profesor profesor3 = new Profesor("56789012C", new Date(85, 5, 10), 3);
        Set<String> asignaturasProfesor3 = new HashSet<>();
        asignaturasProfesor3.add("Biología");
        asignaturasProfesor3.add("Química");
        profesor3.setAsignaturas(asignaturasProfesor3);
        insertProf(conn(), profesor3);
        // Creación de profesor 4
        Profesor profesor4 = new Profesor("34567890D", new Date(90, 10, 20), 7);
        Set<String> asignaturasProfesor4 = new HashSet<>();
        asignaturasProfesor4.add("Informática");
        asignaturasProfesor4.add("Programación");
        profesor4.setAsignaturas(asignaturasProfesor4);
        insertProf(conn(), profesor4);
        // Creación de profesor 5
        Profesor profesor5 = new Profesor("90123456E", new Date(79, 7, 5), 4);
        Set<String> asignaturasProfesor5 = new HashSet<>();
        asignaturasProfesor5.add("Arte");
        asignaturasProfesor5.add("Música");
        profesor5.setAsignaturas(asignaturasProfesor5);
        insertProf(conn(), profesor5);
    }

    public static void insertProf(Connection conn, Profesor profesor) throws SQLException {
        if (!existeP(conn(), profesor.getDNI())) {
            String query = "INSERT INTO profesor (DNI, fechaNacimiento, antiguedad) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, profesor.getDNI());
                pstmt.setDate(2, new Date(profesor.getFechaNacimiento().getTime()));
                pstmt.setInt(3, profesor.getAntiguedad());
                pstmt.executeUpdate();
            }
        }
    }

    public static void iniAlum() throws SQLException { //ALUMNO
        try {
            // Crear alumno 1
            Alumno alumno1 = new Alumno("12345678A", new Date(90, 0, 15), 2);
            alumno1.getAsignaturasPorCurso();
            insertarAlumno(conn(), alumno1);
            Alumno alumno2 = new Alumno("23456789B", new Date(88, 5, 20), 1);
            alumno2.getAsignaturasPorCurso();
            insertarAlumno(conn(), alumno2);
            Alumno alumno3 = new Alumno("34567890C", new Date(89, 9, 10), 2);
            alumno3.getAsignaturasPorCurso();
            insertarAlumno(conn(), alumno3);
            Alumno alumno4 = new Alumno("45678901D", new Date(91, 3, 25), 1);
            alumno4.getAsignaturasPorCurso();
            insertarAlumno(conn(), alumno4);
            Alumno alumno5 = new Alumno("56789012E", new Date(87, 11, 5), 2);
            alumno5.getAsignaturasPorCurso();
            insertarAlumno(conn(), alumno5);
        } catch (SQLException e) {
            System.out.println("Error al inicializar los alumnos.");
            e.printStackTrace();
        }
    }

    public static void insertarAlumno(Connection conn, Alumno alumno) throws SQLException {
        if (!existeA(conn(), alumno.getDNI())) {
            String query = "INSERT INTO alumno (DNI, fechaNacimiento, Curso) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, alumno.getDNI());
                pstmt.setDate(2, new Date(alumno.getFechaNacimiento().getTime()));
                pstmt.setInt(3, alumno.getCurso());
                pstmt.executeUpdate();
            }
        }
    }

    public static void iniProyec() throws SQLException { //ALUMNO
        try {
            //Proyecto1
            Proyectos proyecto1 = new Proyectos("12345678A", new Date(90, 0, 15), 2, "Título del Proyecto 1");
            insertarProyecto(conn(), proyecto1);
            //Proyecto2
            Proyectos proyecto2 = new Proyectos("23456789B", new Date(88, 5, 20), 1, "Título del Proyecto 2");
            insertarProyecto(conn(), proyecto2);
            //Proyecto3
            Proyectos proyecto3 = new Proyectos("34567890C", new Date(89, 9, 10), 3, "Título del Proyecto 3");
            insertarProyecto(conn(), proyecto3);

        } catch (SQLException e) {
            System.out.println("Error al inicializar los proyectos.");
            e.printStackTrace();
        }
    }

    public static void insertarProyecto(Connection conn, Proyectos proyecto) throws SQLException {
        if (!existePry(conn(), proyecto.getDNI())) {
            String query = "INSERT INTO proyectos (DNIAlumn, Titulo) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, proyecto.getDNI());
                pstmt.setString(2, proyecto.getTitulo());
                pstmt.executeUpdate();
            }
        }
    }

    //METODOS PARA ANADIR Y ELIMINAR
    //{
    public static Runnable Anadir(int op) throws SQLException { //Alumnos==1 || Profesor==2 || Proyecto==3
        //
        System.out.println("Escriba el DNI");
        String dni_a = scS.nextLine();
        System.out.println("Escriba el ano de nacimiento del alumno");
        int ano_a = scI.nextInt();
        System.out.println("Escriba el mes de nacimiento del alumno");
        int mes_a = scI.nextInt();
        System.out.println("Escriba el dia de nacimiento del alumno");
        int dia_a = scI.nextInt();
        Date datex = new Date(ano_a, mes_a, dia_a);
        if (op == 1) { //Alumno
            System.out.println("Indique el curso de alumno (Solo '1' y '2')");
            int curso_a = scI.nextInt();
            System.out.println("Escriba el nombre del proyecto del alumno");
            String proy_a = scS.nextLine();
            Alumno alumnox = new Alumno(dni_a, datex, curso_a);
            insertarAlumno(conn(), alumnox);
        } else if (op == 2) {
            System.out.println("Seleccione la asignatura del profesor\n");
            System.out.println("[1] Matematicas\n");
            System.out.println("[2] Historia\n");
            System.out.println("[3] Biologia\n");
            System.out.println("[4] Fisica\n");
            System.out.println("[5] Quimica\n");
            System.out.println("[6] Literatura\n");
            int asign = scI.nextInt();

        }
        return null;
    }


    //}
    //} INICIALIZAR E INSERTAR


    //METODOS COMPROBAR INSERCION
    //{
    //ALUMNO || Metodos para comprobar si hay datos ya insertados en las tablas (para que no de error por doble insercion)
    public static boolean existeA(Connection conn, String dni) {
        String query = "SELECT COUNT(*) FROM alumno WHERE DNI = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, dni);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // Devuelve true si hay al menos un registro con ese DNI
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar si existe el alumno.");
            e.printStackTrace();
        }
        return false; // Si hay algún error o no se encuentra el registro, devuelve false
    }

    //PROFESOR || Metodos para comprobar si hay datos ya insertados en las tablas (para que no de error por doble insercion)
    public static boolean existeP(Connection conn, String dni) {
        String query = "SELECT COUNT(*) FROM profesor WHERE DNI = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, dni);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // Devuelve true si hay al menos un registro con ese DNI
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar si existe el alumno.");
            e.printStackTrace();
        }
        return false; // Si hay algún error o no se encuentra el registro, devuelve false
    }

    public static boolean existePry(Connection conn, String dni) {
        String query = "SELECT COUNT(*) FROM proyectos WHERE DNIAlumn = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, dni);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // Devuelve true si hay al menos un registro con ese DNI
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar si existe el alumno.");
            e.printStackTrace();
        }
        return false; // Si hay algún error o no se encuentra el registro, devuelve false
    }
    //}METODO INSERCION

    //METODOS EXCEPCIONES:
    //{

    //}

    //OTROS METODOS:

    //Metodo para seguir en la maquina de estados:
    public static boolean salir() throws SQLException {
        System.out.println("\n ¿Desea volver al menu principal [S/N] ? \n");
        String condicion = scB.nextLine();
        if(condicion.equalsIgnoreCase("S")){
            menuP();
            return true;
        }
        return false;
    }
// --------------- FIN METODOS ---------------

}

