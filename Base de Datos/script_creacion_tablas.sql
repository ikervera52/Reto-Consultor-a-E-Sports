-- Autor: Iker Poza 
-- Fecha Ultima Edicion : 16/04/2026


DROP TABLE resultados CASCADE CONSTRAINTS;
DROP TABLE jugadores CASCADE CONSTRAINTS;
DROP TABLE equipos;
DROP TABLE enfrentamientos CASCADE CONSTRAINTS;
DROP TABLE jornadas CASCADE CONSTRAINTS;
DROP TABLE competiciones;
DROP TABLE usuarios;


CREATE TABLE usuarios(
    id NUMBER GENERATED ALWAYS AS IDENTITY,
    nombre VARCHAR2(30)NOT NULL CONSTRAINT us_nombre_uq UNIQUE,
    contrasena VARCHAR2(30) NOT NULL,
    tipo_usuario VARCHAR2(10)NOT NULL,
    CONSTRAINT us_id_pk PRIMARY KEY (id),
    CONSTRAINT us_id_ck CHECK (tipo_usuario IN('admin','estandar'))
);

CREATE TABLE competiciones(
    id NUMBER GENERATED ALWAYS AS IDENTITY,
    fecha_inicio DATE,
    fecha_fin DATE,
    etapa VARCHAR2(20) NOT NULL,
    tipo_puntuacion VARCHAR2(20),
    CONSTRAINT co_id_pk PRIMARY KEY (id),
    CONSTRAINT co_etapa_ck CHECK (etapa IN('inscripcion','competicion'))
);

CREATE TABLE jornadas(
    id NUMBER GENERATED ALWAYS AS IDENTITY,
    num_jornada NUMBER NOT NULL,
    fecha DATE NOT NULL,
    id_competicion NUMBER,
    CONSTRAINT jo_id_pk PRIMARY KEY (id),
    CONSTRAINT jo_id_competicion_fk FOREIGN KEY (id_competicion) 
                                    REFERENCES competiciones(id)
);

CREATE TABLE enfrentamientos(
    id NUMBER GENERATED ALWAYS AS IDENTITY,
    hora_enfrentamiento TIMESTAMP,
    id_jornada NUMBER,
    CONSTRAINT en_id_pk PRIMARY KEY (id),
    CONSTRAINT en_id_jornada_fk FOREIGN KEY (id_jornada) 
                                REFERENCES jornadas(id)
                                ON DELETE CASCADE    
);

CREATE TABLE equipos(
    id NUMBER GENERATED ALWAYS AS IDENTITY,
    nombre VARCHAR2(20) NOT NULL,
    fecha_fundacion DATE NOT NULL,
    CONSTRAINT eq_id_pk PRIMARY KEY (id),
    CONSTRAINT eq_nombre_uq UNIQUE (nombre)
);

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

CREATE TABLE resultados(
    id_equipo NUMBER,
    id_enfrentamiento NUMBER,
    puntuacion NUMBER,
    CONSTRAINT pu_eq_en_pk PRIMARY KEY (id_equipo,id_enfrentamiento),
    CONSTRAINT pu_id_equipo_fk FOREIGN KEY (id_equipo) REFERENCES equipos(id),
    CONSTRAINT pu_id_enfrentamiento_fk FOREIGN KEY (id_enfrentamiento) REFERENCES enfrentamientos(id)
);

