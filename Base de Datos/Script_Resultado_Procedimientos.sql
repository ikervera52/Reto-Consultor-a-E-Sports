-- Autor: Iker Vera
-- Ultima Fecha Edicion: 17/04/2026

SET SERVEROUTPUT ON;

-- Bloque anonimo para la prueba del Procedimiento: estadisitcas_equipos
DECLARE
    v_cursor SYS_REFCURSOR;
    v_reg view_info_equipos%ROWTYPE;

BEGIN

    estadisticas_equipos(v_cursor);

    LOOP
        FETCH v_cursor INTO v_reg;
        EXIT WHEN v_cursor%NOTFOUND;

        DBMS_OUTPUT.PUT_LINE('Equipo: ' || v_reg.nombre_equipo);
        DBMS_OUTPUT.PUT_LINE('Fecha: ' || v_reg.fecha_fundacion);
        DBMS_OUTPUT.PUT_LINE('Jugadores: ' || v_reg.cantidad_jugadores);
        DBMS_OUTPUT.PUT_LINE('Max: ' || v_reg.sueldo_maximo);
        DBMS_OUTPUT.PUT_LINE('Min: ' || v_reg.sueldo_minimo);
        DBMS_OUTPUT.PUT_LINE('Media: ' || v_reg.sueldo_medio);
        DBMS_OUTPUT.PUT_LINE('------------------------');
    END LOOP;

    CLOSE v_cursor;

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;

-- Respuesta de este bloque anonimo
/*
Equipo: eq1
Fecha: 20/10/05
Jugadores: 2
Max: 2000
Min: 2000
Media: 2000
------------------------
Equipo: eq2
Fecha: 20/10/05
Jugadores: 3
Max: 3000
Min: 2000
Media: 2333,33
------------------------
Equipo: EquipoSi
Fecha: 20/10/04
Jugadores: 2
Max: 2000
Min: 2000
Media: 2000
------------------------
Equipo: EquipoNo
Fecha: 20/10/04
Jugadores: 2
Max: 2000
Min: 2000
Media: 2000
------------------------
*/

-- Bloque anonimo para la prueba del Procedimiento: jugadores_por_equipos

DECLARE
    v_cursor SYS_REFCURSOR;

    v_jugador jugadores%ROWTYPE;

BEGIN

    jugadores_por_equipos(
        p_nombre_equipo => 'EquipoSi',
        p_cursor => v_cursor
    );

    LOOP
        FETCH v_cursor INTO v_jugador;
        EXIT WHEN v_cursor%NOTFOUND;

        DBMS_OUTPUT.PUT_LINE('ID jugador: ' || v_jugador.id);
        DBMS_OUTPUT.PUT_LINE('Nombre: ' || v_jugador.nombre);
        DBMS_OUTPUT.PUT_LINE('Apellido: ' || v_jugador.apellido);
        DBMS_OUTPUT.PUT_LINE('Fecha Nacimiento: ' || v_jugador.fecha_nacimiento);
        DBMS_OUTPUT.PUT_LINE('Nickname: ' || v_jugador.nickname);
        DBMS_OUTPUT.PUT_LINE('Rol: ' || v_jugador.rol);
        DBMS_OUTPUT.PUT_LINE('Sueldo: ' || v_jugador.sueldo);
        DBMS_OUTPUT.PUT_LINE('--------------------');

    END LOOP;

    CLOSE v_cursor;

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);

END;

SELECT * FROM equipos;





