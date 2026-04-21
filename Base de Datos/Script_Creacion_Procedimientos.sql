-- Autor: Iker Vera
-- Fecha Ultima Edición: 20/04/2026

-- Procedimiento para ver estadisticas de equipos
-- Uso en Java: EquipoDAO función: verEquiposInforme();

CREATE OR REPLACE PROCEDURE estadisticas_equipos(
    p_cursor out SYS_REFCURSOR
)
AS

BEGIN 
    
    OPEN p_cursor FOR
        
    SELECT *
    FROM view_info_equipos;
    
    
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20009, 'No se ha encontrado ningun Equipo');
        
    WHEN ROWTYPE_MISMATCH THEN
        RAISE_APPLICATION_ERROR(-20009, 'Error: La vista se ha modificado,' ||
                                        'revisala.');
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20010,'Error' || SQLERRM);
        
END estadisticas_equipos;

/

-- Procedimiento para ver jugadores por equipo
-- Uso en Java: JugadorDAO función: verJugadoresPorEquipo();

CREATE OR REPLACE PROCEDURE jugadores_por_equipos(
    p_nombre_equipo in VARCHAR2,
    p_cursor out SYS_REFCURSOR
)
AS

    v_id_equipo NUMBER;
    e_eq_no_encontrado EXCEPTION;

BEGIN
        BEGIN
        
            SELECT id INTO v_id_equipo
            FROM equipos
            WHERE nombre = p_nombre_equipo;
            
            EXCEPTION
                WHEN NO_DATA_FOUND THEN
                    RAISE e_eq_no_encontrado;
        
        END;
    
    OPEN p_cursor FOR 
        
        SELECT id,nombre, apellido, nacionalidad, fecha_nacimiento, nickname,
                  rol, sueldo, id_equipo
        FROM jugadores
        WHERE id_equipo = v_id_equipo;
                           
EXCEPTION 

    WHEN e_eq_no_encontrado THEN
        RAISE_APPLICATION_ERROR(-20010, 'El equipo no existe');
        
    WHEN TOO_MANY_ROWS THEN
        RAISE_APPLICATION_ERROR(-20012, 'Varias equipos tienen el mismo nombre');
        
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20013,'Error' || SQLERRM);
        
END jugadores_por_equipos;                                       
        


    