DROP TABLE resultados CASCADE CONSTRAINTS;
DROP TABLE jugadores CASCADE CONSTRAINTS;
DROP TABLE equipos;
DROP TABLE enfrentamientos CASCADE CONSTRAINTS;
DROP TABLE jornadas CASCADE CONSTRAINTS;
DROP TABLE competiciones;
DROP TABLE usuarios;


CREATE TABLE usuarios(
    id NUMBER GENERATED ALWAYS AS IDENTITY,
    nombre VARCHAR2(30)NOT NULL,
    contrasena VARCHAR2(30) NOT NULL,
    tipo_usuario VARCHAR2(10)NOT NULL,
    CONSTRAINT us_id_pk PRIMARY KEY (id),
    CONSTRAINT us_id_ck CHECK (tipo_usuario IN('admin','estandar'))
);

DESC usuarios;

SELECT constraint_name, constraint_type, table_name, search_condition
FROM user_constraints
WHERE UPPER(table_name) = 'USUARIOS';

SELECT constraint_name, table_name, column_name
FROM user_cons_columns
WHERE UPPER(table_name) = 'USUARIOS';

CREATE TABLE competiciones(
    id NUMBER GENERATED ALWAYS AS IDENTITY,
    fecha_inicio DATE,
    fecha_fin DATE,
    etapa VARCHAR2(20) NOT NULL,
    tipo_puntuacion VARCHAR2(20),
    CONSTRAINT co_id_pk PRIMARY KEY (id),
    CONSTRAINT co_etapa_ck CHECK (etapa IN('inscripcion','competicion'))
);

DESC competiciones;

SELECT constraint_name, constraint_type, table_name, search_condition
FROM user_constraints
WHERE UPPER(table_name) = 'COMPETICIONES';

SELECT constraint_name, table_name, column_name
FROM user_cons_columns
WHERE UPPER(table_name) = 'COMPETICIONES';

CREATE TABLE jornadas(
    id NUMBER GENERATED ALWAYS AS IDENTITY,
    num_jornada NUMBER NOT NULL,
    fecha DATE NOT NULL,
    id_competicion NUMBER,
    CONSTRAINT jo_ïd_pk PRIMARY KEY (id),
    CONSTRAINT jo_id_competicion_fk FOREIGN KEY (id_competicion) 
                                    REFERENCES competiciones(id)
);

DESC jornadas;

SELECT constraint_name, constraint_type, table_name, search_condition
FROM user_constraints
WHERE UPPER(table_name) = 'JORNADAS';

SELECT constraint_name, table_name, column_name
FROM user_cons_columns
WHERE UPPER(table_name) = 'JORNADAS';

CREATE TABLE enfrentamientos(
    id NUMBER GENERATED ALWAYS AS IDENTITY,
    hora_enfrentamiento TIMESTAMP,
    id_jornada NUMBER,
    CONSTRAINT en_id_pk PRIMARY KEY (id),
    CONSTRAINT en_id_jornada_fk FOREIGN KEY (id_jornada) 
                                REFERENCES jornadas(id)
                                ON DELETE CASCADE    
);

DESC enfrentamientos;

SELECT constraint_name, constraint_type, table_name, search_condition
FROM user_constraints
WHERE UPPER(table_name) = 'ENFRENTAMIENTOS';

SELECT constraint_name, table_name, column_name
FROM user_cons_columns
WHERE UPPER(table_name) = 'ENFRENTAMIENTOS';

CREATE TABLE equipos(
    id NUMBER GENERATED ALWAYS AS IDENTITY,
    nombre VARCHAR2(20) NOT NULL,
    fecha_fundacion DATE NOT NULL,
    CONSTRAINT eq_id_pk PRIMARY KEY (id),
    CONSTRAINT eq_nombre_uq UNIQUE (nombre)
);

DESC equipos;

SELECT constraint_name, constraint_type, table_name, search_condition
FROM user_constraints
WHERE UPPER(table_name) = 'EQUIPOS';

SELECT constraint_name, table_name, column_name
FROM user_cons_columns
WHERE UPPER(table_name) = 'EQUIPOS';

CREATE TABLE jugadores(
    id NUMBER GENERATED ALWAYS AS IDENTITY,
    nombre VARCHAR2(20) NOT NULL,
    apellido VARCHAR2(20) NOT NULL,
    nacionalidad VARCHAR2(20) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    nickname VARCHAR2(20) NOT NULL,
    rol VARCHAR2(20) NOT NULL,
    sueldo NUMBER(6,2) NOT NULL,
    id_equipo NUMBER,
    CONSTRAINT ju_id_pk PRIMARY KEY (id),
    CONSTRAINT ju_nickname_uq UNIQUE (nickname),
    CONSTRAINT ju_rol_ck CHECK (rol IN ('Suport', 
                                        'AWPer', 
                                        'IGL', 
                                        'Lurker', 
                                        'Rifler', 
                                        'Entry Fragger')),
    CONSTRAINT ju_id_equipo_fk FOREIGN KEY (id_equipo) 
                                REFERENCES equipos(id)
                                ON DELETE SET NULL
);

DESC jugadores;

SELECT constraint_name, constraint_type, table_name, search_condition
FROM user_constraints
WHERE UPPER(table_name) = 'JUGADORES';

SELECT constraint_name, table_name, column_name
FROM user_cons_columns
WHERE UPPER(table_name) = 'JUGADORES';

CREATE TABLE resultados(
    id_equipo NUMBER,
    id_enfrentamiento NUMBER,
    puntuacion NUMBER NOT NULL,
    CONSTRAINT pu_eq_en_pk PRIMARY KEY (id_equipo,id_enfrentamiento),
    CONSTRAINT pu_id_equipo_fk FOREIGN KEY (id_equipo) REFERENCES equipos(id),
    CONSTRAINT pu_id_enfrentamiento_fk FOREIGN KEY (id_enfrentamiento) REFERENCES enfrentamientos(id)
);

DESC resultados;

SELECT constraint_name, constraint_type, table_name, search_condition
FROM user_constraints
WHERE UPPER(table_name) = 'RESULTADOS';

SELECT constraint_name, table_name, column_name
FROM user_cons_columns
WHERE UPPER(table_name) = 'RESULTADOS';