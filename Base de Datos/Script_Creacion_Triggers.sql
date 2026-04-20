-- Autor: Iker Vera
-- Fecha de Ultima Edicion: 20/04/2026


-- Trigger para controlar que el salario sea superior al SMI

CREATE OR REPLACE TRIGGER tri_com_salario_smi
BEFORE INSERT OR UPDATE OF sueldo ON jugadores
FOR EACH ROW

DECLARE
    v_smi NUMBER := 1221;
    e_sueldo_invalido EXCEPTION;   

BEGIN
   
    IF :new.sueldo < v_smi THEN
    RAISE e_sueldo_invalido;
    END IF;
   
EXCEPTION
    WHEN e_sueldo_invalido THEN
    RAISE_APPLICATION_ERROR(-20001, 'El sueldo tiene que ser superior o igual a '
                                    || TO_CHAR(v_smi) || '€');
    WHEN OTHERS THEN
        RAISE;
   
END tri_com_salario_smi;

/

-- Trigger para que los equipos tengan 6 jugadores Maximo

CREATE OR REPLACE TRIGGER tri_max_jugadores_equipo
FOR INSERT OR UPDATE OF id_equipo ON jugadores
COMPOUND TRIGGER

    v_num_jugadores NUMBER;
    v_id_equipo NUMBER;
    v_id_equipo_old NUMBER;

BEFORE STATEMENT IS
BEGIN
    NULL;
END BEFORE STATEMENT;

BEFORE EACH ROW IS
BEGIN 
    v_id_equipo_old := :OLD.id_equipo;
    v_id_equipo := :NEW.id_equipo;
END BEFORE EACH ROW;

AFTER STATEMENT IS
BEGIN 

    IF v_id_equipo IS NULL THEN
    
        NULL;
    
    ELSE
    
        SELECT COUNT(*) INTO v_num_jugadores
        FROM jugadores
        WHERE id_equipo = v_id_equipo;
    
    
        IF v_num_jugadores > 6 THEN
            RAISE_APPLICATION_ERROR(-20002, 'El equipo esta completo');
        END IF;
        
    END IF;
    
    EXCEPTION
    WHEN OTHERS THEN
        RAISE;
        
END AFTER STATEMENT;

END tri_max_jugadores_equipo;

/

-- Trigger para controlar que los equipos tengan al menos
-- dos jugadores al cerrar la competición

CREATE OR REPLACE TRIGGER tri_cal_jugadores
BEFORE UPDATE OF etapa ON competiciones
FOR EACH ROW

DECLARE
    e_equipos_incompletos EXCEPTION;
    v_equipos_invalidos NUMBER;
   
BEGIN
   
    SELECT COUNT(*) INTO v_equipos_invalidos
    FROM equipos e
    WHERE (
        SELECT COUNT(*)
        FROM jugadores j
        WHERE j.id_equipo = e.id
        ) < 2;
       
    IF v_equipos_invalidos > 0 THEN
    RAISE e_equipos_incompletos;
    END IF;

EXCEPTION
    WHEN e_equipos_incompletos THEN
    RAISE_APPLICATION_ERROR(-20003,'Los equipos tienen que tener al menos ' || 
                                    '2 jugadores para comenzar la competición');
    WHEN OTHERS THEN
    RAISE;
   
END tri_cal_jugadores;

/

-- Trigger para no se puedan editar los equipos si la comp. esta cerrada

CREATE OR REPLACE TRIGGER tri_competicion_cerrada_equipos
BEFORE INSERT OR UPDATE ON equipos
FOR EACH ROW

DECLARE
    v_etapa_actual competiciones.etapa%TYPE;
    e_competicion_cerrada EXCEPTION;
   
BEGIN
   
    SELECT etapa INTO v_etapa_actual
    FROM competiciones;
   
    IF v_etapa_actual = 'competicion' THEN
        RAISE e_competicion_cerrada;
    END IF;
   
EXCEPTION
    WHEN e_competicion_cerrada THEN
        RAISE_APPLICATION_ERROR(-20004,'La Competición esta actualmente cerrada');
    
    WHEN OTHERS THEN 
    RAISE;
   
END tri_competicion_cerrada_equipos;

/

-- Trigger para no se puedan editar los jugadores si la comp. esta cerrada


CREATE OR REPLACE TRIGGER tri_competicion_cerrada_jugadores
BEFORE INSERT OR UPDATE ON jugadores
FOR EACH ROW

DECLARE
    v_etapa_actual competiciones.etapa%TYPE;
    e_competicion_cerrada EXCEPTION;
   
BEGIN
   
    SELECT etapa INTO v_etapa_actual
    FROM competiciones;
   
    IF v_etapa_actual = 'competicion' THEN
        RAISE e_competicion_cerrada;
    END IF;
   
EXCEPTION
    WHEN e_competicion_cerrada THEN
    RAISE_APPLICATION_ERROR(-20004,'La Competición esta actualmente cerrada');
    
    WHEN OTHERS THEN
    RAISE;
   
END tri_competicion_cerrada_jugadores;

/

-- Trigger para controlar que los equipos sean pares al cerrar la competición


CREATE OR REPLACE TRIGGER tri_cal_equipos
BEFORE UPDATE OF etapa ON competiciones
FOR EACH ROW

DECLARE
    v_num_equipos NUMBER;
    e_equipos_impares EXCEPTION;
   
BEGIN
   
    SELECT count(*) INTO v_num_equipos
    FROM equipos;
   
    IF MOD(v_num_equipos, 2) != 0 THEN
        RAISE e_equipos_impares;
    END IF;
   
EXCEPTION
    WHEN e_equipos_impares THEN
        RAISE_APPLICATION_ERROR(-20005,'Los equipos tienen que ser pares ' ||
                                        'para comenzar la Competición');
                                        
    WHEN OTHERS THEN
    RAISE ;
       
END tri_cal_equipos;

/

-- Trigger para controlar si la competición a terminado o no:
-- Si no ha terminado salta error y no pasa nada
-- Si a terminado se cambia los datos de la competicion y se borran las jornadas
-- que tienen un ON DELETE CASCADE y asi se borran los enfrentamientos y las
-- puntuaciones tambien.


CREATE OR REPLACE TRIGGER fin_competicion 
BEFORE UPDATE OF etapa ON competiciones
FOR EACH ROW

DECLARE

BEGIN
    
    IF SYSDATE < :NEW.fecha_fin AND :NEW.etapa = 'inscripcion' THEN
        RAISE_APPLICATION_ERROR(-20007, 
        'La Competición no ha terminado todavía');
    
    ELSIF :NEW.etapa = 'inscripcion' THEN
        :NEW.fecha_inicio := NULL;
        :NEW.fecha_fin := NULL;
        :NEW.tipo_puntuacion := NULL;
        DELETE FROM jornadas;
        
    END IF;

EXCEPTION
    WHEN OTHERS THEN
        RAISE;

END fin_competicion;

