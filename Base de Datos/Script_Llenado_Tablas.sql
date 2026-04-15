/* Script de llenado de datos */

/* Creación del usuario Admin */
INSERT INTO usuarios (nombre, contrasena, tipo_usuario)
    VALUES ('admin', 'admin', 'admin');
    
/* Creación del usuario Estandar */
INSERT INTO usuarios (nombre, contrasena, tipo_usuario)
    VALUES ('estandar', 'estandar', 'estandar');

/* Creación de la competición*/
INSERT INTO competiciones (etapa) 
    VALUES ('inscripcion');
    
/* Creación de jugadores para desdes de la creación de tablas*/
DECLARE 
    
    v_nombre_equipo_numero NUMBER;
    v_nombre_equipo VARCHAR2(30);
    v_nickname_numero NUMBER := 1;

BEGIN

    FOR i IN 1 .. 2 LOOP
        
        v_nombre_equipo := 'eq' || TO_CHAR(i);
    
        INSERT INTO equipos (nombre, fecha_fundacion)
            VALUES (v_nombre_equipo, '20/10/2005');
            
        FOR e IN 0 ..1 LOOP
            
            INSERT INTO jugadores (nombre, apellido, nacionalidad, 
                                   fecha_nacimiento, nickname, rol, sueldo, 
                                   id_equipo)
                VALUES ('Jugador', 'Apellido','España',
                        TO_DATE('20/10/2005', 'DD/MM/YYYY'),  
                        'jug' || TO_CHAR(v_nickname_numero), 'AWPer', 2000, i);
                
                v_nickname_numero := v_nickname_numero + 1;
                
        END LOOP;
    END LOOP;
    
    COMMIT;
    
END;

    
