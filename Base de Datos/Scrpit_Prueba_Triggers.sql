-- Autor: Iker Vera
-- Fecha Ultima Edición: 20/04/2026

/* Script para la prueba de los Triggers */

-- Borrado de los datos para la prueba
DELETE FROM jugadores;
DELETE FROM equipos;
DELETE FROM jornadas;
DELETE FROM resultados;


-- Prueba del Trigger: tri_com_salario_smi

INSERT INTO equipos (nombre, fecha_fundacion) VALUES ('eq1', '20/10/2005');

INSERT INTO jugadores (nombre,apellido,nacionalidad,fecha_nacimiento,nickname,
                       rol,sueldo,id_equipo)
    VALUES ('Jugador1','ap','es','20/10/2004','jug','AWPer','1000',
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

-- Comentario: Asta aqui el trigger no salta ya que son los 6 jugadores validos

INSERT INTO jugadores (nombre,apellido,nacionalidad,fecha_nacimiento,nickname,
                       rol,sueldo,id_equipo)
    VALUES ('Jugador1','ap','es','20/10/2004','jug7','AWPer','2000',
            (SELECT id
             FROM equipos
             WHERE UPPER(nombre) = 'EQ1')); 

/* Comentario: El trigger salta ya que se intenta insertar otro jugador a un 
               Equipo que ya tiene 6 jugadores
               
   Informe de error -
Error SQL: ORA-20002: El equipo esta completo
ORA-06512: en "EQDAW02.TRI_MAX_JUGADORES_EQUIPO", línea 40
ORA-06512: en "EQDAW02.TRI_MAX_JUGADORES_EQUIPO", línea 33
ORA-04088: error durante la ejecución del disparador 
                            'EQDAW02.TRI_MAX_JUGADORES_EQUIPO'*/
    
SELECT * FROM jugadores;  

/* Comentario: Hacemos un select para comprobar que efectivamente el septimo
               jugador no se ha insertado*/

--  Prueba del Trigger: tri_cal_equipos

UPDATE competiciones 
SET etapa = 'competicion';

/* Comentario: El trigger salta porque los equipos tienen que ser pares para
               poder empezar la competición y solamente hay un equipo 
   
   Informe de error -
Error SQL: ORA-20005: Los equipos tienen que ser pares para comenzar la 
                      Competición
ORA-06512: en "EQDAW02.TRI_CAL_EQUIPOS", línea 16
ORA-04088: error durante la ejecución del disparador 'EQDAW02.TRI_CAL_EQUIPOS'*/


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
               solamente tiene un jugador
   
   Informe de error -
ORA-20003: Los equipos tienen que tener al menos 2 jugadores para comenzar la 
           competición
ORA-06512: en "EQDAW02.TRI_CAL_JUGADORES", línea 21
ORA-04088: error durante la ejecución del disparador 'EQDAW02.TRI_CAL_JUGADORES'
*/


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
               de la competicion esta en 'competicion'
               
   Informe de error -
Error SQL: ORA-20004: La Competición esta actualmente cerrada
ORA-06512: en "EQDAW02.TRI_COMPETICION_CERRADA_JUGADORES", línea 16
ORA-04088: error durante la ejecución del disparador 
           'EQDAW02.TRI_COMPETICION_CERRADA_JUGADORES'*/

UPDATE equipos
SET fecha_fundacion = '20/10/2005';

/* Comentario: Salta el trigger tri_competicion_cerrada_equipos porque
               se intenta hacer una update de equipos cuand
               o la etapa
               de la competicion esta en 'competicion'
   Informe de error -
Error SQL: ORA-20004: La Competición esta actualmente cerrada
ORA-06512: en "EQDAW02.TRI_COMPETICION_CERRADA_EQUIPOS", línea 16
ORA-04088: error durante la ejecución del disparador 
           'EQDAW02.TRI_COMPETICION_CERRADA_EQUIPOS'*/



/* Prueba de Trigger: fin_competicion */

UPDATE competiciones
SET fecha_fin = '20/10/2027',
    fecha_inicio = '10/10/2026',
    tipo_puntuacion = 'mapas';

SELECT * FROM competiciones;
    
UPDATE competiciones
SET etapa = 'inscripcion';

/* Comentario: Salta el trigger porque la fecha de fin de competicion es 
               anterior al la fecha de la base de datos
   Informe de error -           
   Error SQL: ORA-20007: La Competición no ha terminado todavía
ORA-06512: en "EQDAW02.FIN_COMPETICION", línea 19
ORA-06512: en "EQDAW02.FIN_COMPETICION", línea 6
ORA-04088: error durante la ejecución del disparador 'EQDAW02.FIN_COMPETICION'*/

UPDATE competiciones
SET fecha_fin = '20/03/2026';

UPDATE competiciones
SET etapa = 'inscripcion';

SELECT * FROM competiciones;

SELECT * FROM jornadas;

SELECT * FROM enfrentamientos;

SELECT * FROM resultados;

/* Comentario: El Trigger se ejecuta y cambia todos las columnas de la 
               competición a null y borrra las jornadas, los enfrentamientos y
               los resutlados*/
               
ROLLBACK;


                                                                  