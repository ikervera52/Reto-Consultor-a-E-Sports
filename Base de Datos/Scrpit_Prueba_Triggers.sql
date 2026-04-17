-- Autor: Iker Vera
-- Fecha Ultima Edición: 17/04/2026

/* Script para la prueba de los Triggers */

-- Borrado de los datos para la prueba 

DELETE FROM jugadores;
DELETE FROM equipos;
DELETE FROM competiciones;
DELETE FROM jorandas;
DELETE FROM resultados;

-- Prueba del Trigger: tri_com_salario_smi

INSERT INTO equipos (nombre, fecha_fundacion) VALUES ('eq1', '20/10/2005');

INSERT INTO jugadores (nombre,apellido,nacionalidad,fecha_nacimiento,nickname,
                       rol,sueldo,id_equipo)
    VALUES ('Jugador1','ap','es','20/10/2004','jug','AWPer','2000',
           (SELECT id
           FROM equipos
           WHERE UPPER(nombre) = 'EQ1'));
           
/* Comentario: El trigger salta ya que el salario minimo definido es mayor al
               que se esta haciendo la insert */
    
-- Prueba del Trigger: tri_max_jugadores_equipo

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

-- Comentario: Asta aqui el trigger no salta ya que son los 6 jugadores permitidos

INSERT INTO jugadores (nombre,apellido,nacionalidad,fecha_nacimiento,nickname,
                       rol,sueldo,id_equipo)
    VALUES ('Jugador1','ap','es','20/10/2004','jug7','AWPer','2000',
            (SELECT id
             FROM equipos
             WHERE UPPER(nombre) = 'EQ1')); 

/* Comentario: El trigger salta ya que se intenta insertar otro jugador a un 
               Equipo que ya tiene 6 jugadores*/
    
SELECT * FROM jugadores;  

/* Comentario: Hacemos un select para comprobar que efectivamente el septimo
               jugador no se ha insertado*/

--  Prueba del Trigger: tri_cal_equipos

INSERT INTO competiciones (etapa) VALUES ('inscripcion');

UPDATE competiciones 
SET etapa = 'competicion';

/* Comentario: El trigger salta porque los equipos tienen que ser pares para
               poder empezar la competición y solamente hay un equipo */

/* Prueba del Trigger: tri_cal_jugadores */

INSERT INTO equipos (nombre, fecha_fundacion) VALUES ('eq2','20/10/2004');

INSERT INTO jugadores (nombre,apellido,nacionalidad,fecha_nacimiento,nickname,
                       rol,sueldo,id_equipo)
    VALUES ('Jugador1','ap','es','20/10/2004','jug8','AWPer','2000',
           (SELECT id
            FROM equipos
            WHERE UPPER(nombre) = 'EQ2'));

UPDATE competiciones 
SET etapa = 'competicion';

/* Comentario: El trigger salta porque los equipos tienen que tener minimo dos
               jugadores para poder emepzar la competición y hay un equipo que 
               solamente tiene un jugador*/

/* Prueba de Triggeres: tri_competicion_cerrada_equipos & 
                        tri_competicion_cerrada_jugadores */

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

/* Comentario: Salta el trigger tri_competicion_cerrada_jugadores porque
               se intenta hacer una update de jugadores cuando la etapa
               de la competicion esta en 'competicion'*/

UPDATE equipos
SET fecha_fundacion = '20/10/2005';

/* Comentario: Salta el trigger tri_competicion_cerrada_equipos porque
               se intenta hacer una update de equipos cuando la etapa
               de la competicion esta en 'competicion'*/


/* Prueba de Trigger: fin_competicion */

UPDATE competiciones
SET fecha_fin = '20/10/2027',
    fecha_inicio = '10/10/2026',
    tipo_puntuacion = 'mapas';

SELECT * FROM competiciones;
    
UPDATE competiciones
SET etapa = 'inscripcion';

/* Comentario: Salta el trigger porque la fecha de fin de competicion es 
               anterior al la fecha de la base de datos*/

UPDATE competiciones
SET fecha_fin = '20/03/2026';

UPDATE competiciones
SET etapa = 'inscripcion';

SELECT * FROM competiciones;

/* Comentario: El se ejecuta y cambia todos las columnas de la competición a
               null*/


                                                                  