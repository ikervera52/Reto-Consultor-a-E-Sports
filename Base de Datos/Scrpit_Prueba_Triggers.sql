/* Script para la prueba de los Triggers */

/* Borrado de los datos para la prueba */

DELETE FROM jugadores;
DELETE FROM equipos;
DELETE FROM competiciones;
DELETE FROM jorandas;
DELETE FROM resultados;

/* tri_com_salario_smi */

INSERT INTO equipos (nombre, fecha_fundacion) VALUES ('eq1', '20/10/2005');

INSERT INTO jugadores (nombre,apellido,nacionalidad,fecha_nacimiento,nickname,
                       rol,sueldo,id_equipo)
    VALUES ('Jugador1','ap','es','20/10/2004','jug','AWPer','2000',
           (SELECT id
           FROM equipos
           WHERE UPPER(nombre) = 'EQ1'));
    
/* tri_max_jugadores_equipo */

INSERT INTO jugadores (nombre,apellido,nacionalidad,fecha_nacimiento,nickname,
                       rol,sueldo,id_equipo)
    VALUES ('Jugador1','ap','es','20/10/2004','jug1','AWPer','2000',
           (SELECT id
           FROM equipos
           WHERE UPPER(nombre) = 'EQ1'));

INSERT INTO jugadores (nombre,apellido,nacionalidad,fecha_nacimiento,nickname,
                       rol,sueldo,id_equipo)
    VALUES ('Jugador1','ap','es','20/10/2004','jug2','AWPer','2000',
    (SELECT id
    FROM equipos
    WHERE UPPER(nombre) = 'EQ1'));                                                                  

INSERT INTO jugadores (nombre,apellido,nacionalidad,fecha_nacimiento,nickname,
                       rol,sueldo,id_equipo)
    VALUES ('Jugador1','ap','es','20/10/2004','jug3','AWPer','2000',
           (SELECT id
           FROM equipos
           WHERE UPPER(nombre) = 'EQ1'));

INSERT INTO jugadores (nombre,apellido,nacionalidad,fecha_nacimiento,nickname,
                       rol,sueldo,id_equipo)
    VALUES ('Jugador1','ap','es','20/10/2004','jug4','AWPer','2000',
           (SELECT id
           FROM equipos
           WHERE UPPER(nombre) = 'EQ1'));

INSERT INTO jugadores (nombre,apellido,nacionalidad,fecha_nacimiento,nickname,
                       rol,sueldo,id_equipo)
    VALUES ('Jugador1','ap','es','20/10/2004','jug5','AWPer','2000',
           (SELECT id
           FROM equipos
           WHERE UPPER(nombre) = 'EQ1'));
                                                                  
INSERT INTO jugadores (nombre,apellido,nacionalidad,fecha_nacimiento,nickname,
                       rol,sueldo,id_equipo)
    VALUES ('Jugador1','ap','es','20/10/2004','jug6','AWPer','2000',
            (SELECT id
            FROM equipos
            WHERE UPPER(nombre) = 'EQ1'));                                                                  

INSERT INTO jugadores (nombre,apellido,nacionalidad,fecha_nacimiento,nickname,
                       rol,sueldo,id_equipo)
    VALUES ('Jugador1','ap','es','20/10/2004','jug7','AWPer','2000',
            (SELECT id
             FROM equipos
             WHERE UPPER(nombre) = 'EQ1')); 
    
SELECT * FROM jugadores;    

/* tri_cal_equipos */

INSERT INTO competiciones (etapa) VALUES ('inscripcion');

UPDATE competiciones 
SET etapa = 'competicion';

/* tri_cal_jugadores */

INSERT INTO equipos (nombre, fecha_fundacion) VALUES ('eq2','20/10/2004');

INSERT INTO jugadores (nombre,apellido,nacionalidad,fecha_nacimiento,nickname,
                       rol,sueldo,id_equipo)
    VALUES ('Jugador1','ap','es','20/10/2004','jug8','AWPer','2000',
           (SELECT id
            FROM equipos
            WHERE UPPER(nombre) = 'EQ2'));

UPDATE competiciones 
SET etapa = 'competicion';

/* tri_competicion_cerrada_equipos & tri_competicion_cerrada_jugadores */

INSERT INTO jugadores (nombre,apellido,nacionalidad,fecha_nacimiento,nickname,
                       rol,sueldo,id_equipo)
    VALUES ('Jugador1','ap','es','20/10/2004','jug9','AWPer','2000',
           (SELECT id
            FROM equipos
            WHERE UPPER(nombre) = 'EQ2'));
                                                                  
UPDATE competiciones 
SET etapa = 'competicion';

UPDATE jugadores
SET nombre = 'nombre';

UPDATE equipos
SET fecha_fundacion = '20/10/2005';


/* fin_competicion */

UPDATE competiciones
SET fecha_fin = '20/10/2027',
    fecha_inicio = '10/10/2026',
    tipo_puntuacion = 'mapas';

SELECT * FROM competiciones;
    
UPDATE competiciones
SET etapa = 'inscripcion';

UPDATE competiciones
SET fecha_fin = '20/03/2026';

UPDATE competiciones
SET etapa = 'inscripcion';

SELECT * FROM competiciones;



                                                                  