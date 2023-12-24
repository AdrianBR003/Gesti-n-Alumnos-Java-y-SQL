package def;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class Main {

    //#---VARIABLES GLOBALES---#
    //Funciones Scanner:
    static Scanner scI = new Scanner(System.in); //Scanner para Enteros (Integer)
    static Scanner scS = new Scanner(System.in); //Scanner para String
    //Variables Genericas:
    static List<Integer> AHist = new ArrayList<>();


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
        System.out.println("\n*** Bienvenido ProfesorX al menu de la base de datos del alumnado: ***\n\n");
        System.out.println("Seleccione la opción que desea ejecutar:\n");
        System.out.println("[1] Listar las tablas de la base de datos \n");
        System.out.println("[2] Filtrar por.. \n");
        System.out.println("[3] Listar.. \n");
        System.out.println("[4] Anadir.. \n");
        System.out.println("[5] Eliminar.. \n");
        System.out.println("[6] Anadir proyecto a un alumno \n");
        System.out.println("[7] Eliminar proyecto a un alumno \n");
        System.out.println("[8] Listar a todos los alumnos con sus respectivos proyectos \n");
        System.out.println("[0] Salir \n");
        int opc = rangI(verfI(),9,0);
        AHist.add(opc);
        swP(opc,AHist);
    }

    public static void swP(int opcionP, List<Integer> AHist) throws SQLException {
        switch (opcionP) {
            case 1://Listar las tablas de la base de datos
                ListTab(conn());
                menuP();
                break;
            case 2://Filtrar
                //No disponible por ahora
                menuF(AHist);
                menuP();
                break;
            case 3://Listar a profesores, alumnos o ambos
                Listar(conn(),menuFAPA(true,AHist));
                menuP();
                break;
            case 4://Anadir..
                int opcion = menuFAPA(false,AHist);
                if (opcion == 1) { // Devuelve 1 para Alum || 2 Prof || -1 ERR
                    Anadir(1);
                    menuP();
                } else if (opcion == 2) {
                    Anadir(2);
                    menuP();
                } else {//opcion=-1
                    System.out.println("\nER opcion no valida - sWP\n");
                    menuFAPA(false,AHist); //va a volver a llamar al metodo
                    swP(4,AHist);
                }
                break;
            case 5://Eliminar..
                Eliminar(menuFAPA(false,AHist));
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            default:
                salir(AHist);
        }
    }

    public static void menuF(List<Integer> AHist) {
        System.out.println("Filtrar por: \n");
        System.out.println("[1] Curso: Se mostraran a los alumnos del curso que inserte \n");
        System.out.println("[2] Asignaturas: ...\n");
        System.out.println("[3] Fecha de Nacimiento: ...\n");
        System.out.println("[4] Antiguedad: Filtra a los profesores por la antiguedad que inserte\n");
        System.out.println("[5] OPCIONAL:\n");
        int opc= rangI(verfI(),6,1);
        swF(opc,AHist);
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
    public static void swF(int opcionF, List<Integer> AHist) {
        switch (opcionF) { //Filtra por:
            case 1://Curso
                break;
            case 2://Asignaturas
                System.out.println("Filtra las asignaturas por: \n");
                menuFAPA(true,AHist); //Activada la opcion de ambos
                break;
            case 3://Fecha Nacimiento
                System.out.println("Filtra la fecha de nacimiento por: \n");
                menuFAPA(true,AHist);
                break;
            case 4://Antiguedad
                break;
            case 5://Opcional
                break;
        }
    }

    public static int menuFAPA(boolean apa, List<Integer> AHist) {
        System.out.println("[1] Alumno\n");
        System.out.println("[2] Profesor\n");
        if (apa) {
            System.out.println("[3] Ambos\n");
        }
        if(apa) {
            return rangI(verfI(),4,1);
        }else{
            return rangI(verfI(),1,0);
        }
    }


    //}
    //METODOS PARA FILTRAR - LISTAR
    //{

    //Listar las tablas de la base de datos ([1] MenuP)
    public static void ListTab(Connection conn) {
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
    public static void Listar(Connection conn, int opcion) {
            String Ntable;
            if (opcion == 1 || opcion == 3) {
                Ntable = "alumno";
            } else {
                Ntable = "profesor";
            }
            try {

                Statement statement = conn.createStatement();
                ResultSet resultSetTables = statement.executeQuery("SHOW TABLES");
                String tableName = null;

                while (resultSetTables.next()) {
                    String currentTableName = resultSetTables.getString(1);
                    if (currentTableName.equals(Ntable)) {
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
                    System.out.println("La tabla de" + Ntable + " no fue encontrada.");
                }
            } catch (SQLException e) {
                System.out.println("\nError en Listar\n");
                e.printStackTrace();
            }
            if (opcion == 3) {
                opcion = 2;
                System.out.println(" \n");
                Listar(conn, opcion);//Vuelve a imprimirlo pero con profesor
            }
    }
    //} Metodos para filtrar - listar

    //Metodos INICIALIZAR E INSERTAR
    //{

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
            System.out.println("Error al verificar si existe el profesor.");
            e.printStackTrace();
        }
        return false; // Si hay algún error o no se encuentra el registro, devuelve false
    }
    public static void iniProf() throws SQLException { //PROFESOR
        //Creacion del profesor 1
        Profesor profesor1 = new Profesor("12345678A", LocalDate.of(1980,5,13), 5,"Matematicas");
        insertProf(conn(), profesor1); //Esto obliga a poner el SQLException
        // Creación de profesor 2
        Profesor profesor2 = new Profesor("98765432B", LocalDate.of(1990,1,15), 8,"Fisica");
        insertProf(conn(), profesor2);
        // Creación de profesor 3
        Profesor profesor3 = new Profesor("56789012C", LocalDate.of(1992,7,3), 3,"Historia");
        insertProf(conn(), profesor3);
        // Creación de profesor 4
        Profesor profesor4 = new Profesor("34567890D", LocalDate.of(1985,12,11), 7,"Quimica");
        insertProf(conn(), profesor4);
        // Creación de profesor 5
        Profesor profesor5 = new Profesor("90123456E", LocalDate.of(1985,2,25), 4,"Biologia");
        insertProf(conn(), profesor5);
        Profesor profesor6 = new Profesor("90121111F", LocalDate.of(1993,10,25), 4,"Literatura");
        insertProf(conn(), profesor6);
    }
    public static void insertProf(Connection conn, Profesor profesor) throws SQLException {
        if (!existeP(conn(), profesor.getDNI())) {
            String query = "INSERT INTO profesor (DNI, fechaNacimiento, antiguedad) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, profesor.getDNI());
                pstmt.setDate(2, Date.valueOf(profesor.getFechaNacimiento()));
                pstmt.setInt(3, profesor.getAntiguedad());
                pstmt.executeUpdate();
            }
        }
    }
    public static void eliminarProfesor(Connection conn, String dni) throws SQLException {
        if (existeP(conn, dni)) {
            String query = "DELETE FROM profesor WHERE DNI = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, dni);
                pstmt.executeUpdate();
                System.out.println("Profesor eliminado correctamente.");
                salir(AHist);
            } catch (SQLException e) {
                System.out.println("\nError al intentar eliminar al profesor: " + e.getMessage());
                salir(AHist);
            }
        } else {
            System.out.println("El profesor con DNI " + dni + " no existe en la base de datos.");
            salir(AHist);
        }
    }

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
    public static void iniAlum() throws SQLException { //ALUMNO
        try {
            // Crear alumno 1
            Alumno alumno1 = new Alumno("12345678A", LocalDate.of(2003, 10, 15), 2);
            alumno1.getAsignaturasPorCurso();
            insertarAlumno(conn(), alumno1);
            Alumno alumno2 = new Alumno("23456789B", LocalDate.of(2001, 5, 20), 1);
            alumno2.getAsignaturasPorCurso();
            insertarAlumno(conn(), alumno2);
            Alumno alumno3 = new Alumno("34567890C", LocalDate.of(2002, 9, 10), 2);
            alumno3.getAsignaturasPorCurso();
            insertarAlumno(conn(), alumno3);
            Alumno alumno4 = new Alumno("45678901D", LocalDate.of(2002, 3, 25), 1);
            alumno4.getAsignaturasPorCurso();
            insertarAlumno(conn(), alumno4);
            Alumno alumno5 = new Alumno("56789012E", LocalDate.of(2000, 11, 5), 2);
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
                pstmt.setDate(2,Date.valueOf(alumno.getFechaNacimiento()));
                pstmt.setInt(3, alumno.getCurso());
                pstmt.executeUpdate();
            }catch (Exception e){
                System.out.println("\nER-No se ha podido insertar al alumno - insertarAlumno\n");
            }
        }
    }
    public static void eliminarAlumno(Connection conn, String dni) throws SQLException {
        String opc="";
        if(existePry(conn,dni)){
            System.out.println("\nExiste un proyecto asignado a este alumno, ¿deseas eliminarlo igualmente? [S/N]\n");
            opc = scS.nextLine();
            while(!opc.equalsIgnoreCase("S")&&!opc.equalsIgnoreCase("N")) {
                System.out.println("\n El valor insertado no es correcto \n");
                limpBuffL();
                opc = scS.nextLine();
            }
            if(opc.equalsIgnoreCase("S")){
                eliminarProyectosDeAlumno(conn,dni);//Eliminamos primero el proyecto del alumno en caso de que exista
            }
        }
        if (existeA(conn, dni)) {
            String query = "DELETE FROM alumno WHERE DNI = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, dni);
                pstmt.executeUpdate();
                System.out.println("\nAlumno eliminado correctamente.\n");
                menuP();
            } catch (SQLException e) {
                System.out.println("\nError al intentar eliminar al alumno: " + e.getMessage());
                menuP();
            }
        } else {
            System.out.println("\nEl alumno con DNI " + dni + " no existe en la base de datos.\n");
            menuP();
        }
    }

    //PROYECTO || Metodos para comprobar si hay datos ya insertados en las tablas (para que no de error por doble insercion)
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
    public static void iniProyec() throws SQLException { //ALUMNO
        try {
            //Proyecto1
            Proyectos proyecto1 = new Proyectos("12345678A", LocalDate.of(2020, 10, 15), 2, "Título del Proyecto 1");
            insertarProyecto(conn(), proyecto1);
            //Proyecto2
            Proyectos proyecto2 = new Proyectos("23456789B", LocalDate.of(2021, 5, 20), 1, "Título del Proyecto 2");
            insertarProyecto(conn(), proyecto2);
            //Proyecto3
            Proyectos proyecto3 = new Proyectos("34567890C", LocalDate.of(2019, 9, 10), 3, "Título del Proyecto 3");
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
    public static void eliminarProyectosDeAlumno(Connection conn, String dni) throws SQLException {
        String query = "DELETE FROM Proyectos WHERE DNIAlumn = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, dni);
            pstmt.executeUpdate();
            System.out.println("\nProyectos asociados al alumno eliminados correctamente.\n");
        } catch (SQLException e) {
            System.out.println("\nError al intentar eliminar los proyectos del alumno: " + e.getMessage());
        }
    }

    //METODOS PARA ANADIR Y ELIMINAR
    //{
    public static void Anadir(int op) throws SQLException { //Alumnos==1 || Profesor==2 || Proyecto==3
        //
        boolean condicion=false;
        System.out.println("Escriba el DNI \n");
        String dni = scS.nextLine();
        System.out.println("Escriba el ano de nacimiento\n");
        int ano = rangI(verfI(),2011,1949);
        System.out.println("Escriba el mes de nacimiento\n");
        int mes = rangI(verfI(),13,0);
        System.out.println("Escriba el dia de nacimiento\n");
        int dia = rangI(verfI(),32,0);
        LocalDate datex = LocalDate.of(ano, mes, dia);
        if (op == 1) { //Alumno
            System.out.println("Indique el curso de alumno (Solo '1' y '2')");
            int curso_a = rangI(verfI(),3,0);
            System.out.println("Escriba el nombre del proyecto del alumno");
            String proy_a = scS.nextLine();
            Alumno alumnox = new Alumno(dni, datex, curso_a);
            insertarAlumno(conn(), alumnox);
        } else if (op == 2) { //Profesor
            System.out.println("Escriba la antiguedad del profesor \n");
            int antig = rangI(verfI(),41,0);
            Profesor profesorx = new Profesor(dni,datex,antig,asignaturas());
            insertProf(conn(),profesorx);
        }
    }

    public static void Eliminar(int op) throws SQLException{
        String opcion;

        if(op==1){ //Alumno
            opcion="alumno";
        }else if(op==2){
            opcion="profesor";
        }else {
            System.out.println("\nER-Valor incorrecto-Eliminar\n");
            opcion = "ER";
            menuP();
        }
        if(!opcion.equals("ER")) {
            System.out.println("Escriba el DNI del " + opcion+ " que desea eliminar \n");
            String dni = scS.nextLine();
            if(existeA(conn(),dni)){ //ALUMNO
                eliminarAlumno(conn(),dni);
            }else{
                if(existeP(conn(),dni)){ //PROFESOR
                    eliminarProfesor(conn(),dni);
                }
            }
        }
    }
    //}
    //} INICIALIZAR E INSERTAR

    //METODOS UNICOS DE CLASES:
    //{

    public static String asignaturas(){
        System.out.println("Escriba el nombre de la asignatura del profesor\n");
        System.out.println("[1] Matematicas\n");
        System.out.println("[2] Historia\n");
        System.out.println("[3] Biologia\n");
        System.out.println("[4] Fisica\n");
        System.out.println("[5] Quimica\n");
        System.out.println("[6] Literatura\n");
        String asign = scS.nextLine();
        if(!asign.equalsIgnoreCase("Matematicas")&&!asign.equalsIgnoreCase("Historia")&&!asign.equalsIgnoreCase("Biologia")&&!asign.equalsIgnoreCase("Fisica")&&!asign.equalsIgnoreCase("Quimica")&&!asign.equalsIgnoreCase("Literatura")) {
            return ("N");
        }
        return asign;
    }

    //}

    //OTROS METODOS:

    //Metodo para seguir en la maquina de estados:
    public static boolean salir(List<Integer> AHist) throws SQLException {
        System.out.println("\n ¿Seguro que desea salir [S/N] ? \n");
        String condicion=scS.nextLine();
        boolean opc = false;
        //String condicion="123321";
        //int ajuste=0;
        while(!opc) {
            if(condicion.equalsIgnoreCase("S")||condicion.equalsIgnoreCase("N")){
                opc=true;
            }else {
                System.out.println("\nEl valor no es correcto, inserte 'S' o 'N'\n");
                limpBuffL();
                condicion=scS.nextLine();
            }
        }
        if(condicion.equalsIgnoreCase("N")){
            menuP();
        }else{
            System.out.print("\n\n Su proceso ha sido:  ");
            ImprHist(AHist);//Imprime los valores [****BORRAR*****]
            AHist.clear(); //Limpia los valores del arraylist ya que ha terminado de realizar un proceso
            return false;
        }
        return true;
    }
    public static void limpBuffL(){ //No es muy eficiente porque se crea constantemente un objeto Scanner, pero es la mejor forma por ahora.
        scI = new Scanner(System.in);
        scS = new Scanner(System.in);
    }
    public static int verfI(){
        if (scI.hasNextInt()) {
            int opc = scI.nextInt();
            limpBuffL();
            if(opc!=-1) {
                return opc;
            }else{
                System.out.println("Por favor, ingrese un número entero válido.");
                verfI(); // Como es un caso raro, no se va a abusar del recursivo
                return -999; //Nunca va a pasar porque antes se hace el recursivo
            }
        } else {
            System.out.println("Por favor, ingrese un número entero válido.");
            limpBuffL();
            return -1;
        }
    }
    public static int rangI(int variable, int max, int min) {
        while(variable>max || variable<min){
            System.out.println("\n El valor no está dentro del rango disponible\n");
            variable=scI.nextInt();
            limpBuffL();
        }
        return variable;
    }

    public static void ImprHist(List<Integer> lista) {
        for (Integer elemento : lista) {
            if(elemento==0){
                System.out.print(elemento);
            }else {
                System.out.print(elemento + " > ");
            }
        }
        System.out.println("\n");
    }
// --------------- FIN METODOS ---------------

}

