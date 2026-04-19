-- Autor: Iker Vera
-- Fecha Ultima Edición: 17/04/2026

-- Procedimiento para ver estadisticas de equipos

CREATE OR REPLACE PROCEDURE estadisticas_equipos(
    p_cursor out SYS_REFCURSOR
)
AS

BEGIN 
    
    OPEN p_cursor FOR
        
    SELECT *
    FROM view_info_equipos;
    
    
EXCEPTION
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20010,'Error' || SQLERRM);
        
END estadisticas_equipos;

/

-- Procedimiento para ver jugadores por equipo

CREATE OR REPLACE PROCEDURE jugadores_por_equipos(
    p_nombre_equipo in VARCHAR2,
    p_cursor out SYS_REFCURSOR
)
AS

BEGIN
    
    OPEN p_cursor FOR 
        
        SELECT * 
        FROM jugadores
        WHERE id_equipo = (SELECT id
                           FROM equipos
                           WHERE nombre = p_nombre_equipo);
                           
EXCEPTION 
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20010,'Error' || SQLERRM);
END jugadores_por_equipos;                                       
        


    