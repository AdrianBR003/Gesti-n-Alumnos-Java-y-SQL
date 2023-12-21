-- Alumno: 
CREATE TABLE Alumno (
    DNI VARCHAR(20) PRIMARY KEY,
    fechaNacimiento DATE,
    Curso INT
);

-- Profesor: 
CREATE TABLE Profesor (
    DNI VARCHAR(20) PRIMARY KEY,
    fechaNacimiento DATE,
    Antiguedad INT
);

-- Proyectos: 
CREATE TABLE Proyectos (
    DNIAlumn VARCHAR(20),
    Titulo VARCHAR(100),
    FOREIGN KEY (DNIAlumn) REFERENCES Alumno(DNI)
);
