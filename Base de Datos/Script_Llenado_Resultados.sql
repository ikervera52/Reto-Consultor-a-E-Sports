-- Autor: Iker Vera
-- Fecha Ultima Edición: 20/04/2026

-- Script para rellenar todos los datos para hacer la prueba 
-- de terminar la competición

UPDATE competiciones SET fecha_fin = '18/04/2026';

UPDATE jornadas SET fecha = '01/01/2026';

/

-- Llenado de puntuaciones

DECLARE
BEGIN

    UPDATE resultados SET puntuacion = 0;
    
    FOR r IN (
        SELECT id_equipo, id_enfrentamiento, puntuacion
        FROM resultados
    ) LOOP
    
        IF MOD(r.id_equipo, 2) = 0 THEN
            UPDATE resultados
            SET puntuacion = puntuacion + 3
            WHERE id_equipo = r.id_equipo
              AND id_enfrentamiento = r.id_enfrentamiento;
            
        ELSE
            UPDATE resultados
            SET puntuacion = puntuacion + 1
            WHERE id_equipo = r.id_equipo
              AND id_enfrentamiento = r.id_enfrentamiento;
              
        END IF;
        
    END LOOP;

    COMMIT;
END;
/




