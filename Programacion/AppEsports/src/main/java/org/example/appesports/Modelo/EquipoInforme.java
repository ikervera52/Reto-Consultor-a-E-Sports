package org.example.appesports.Modelo;

import java.time.LocalDate;
/** Clase que representa un informe de equipo en el sistema de gestión de esports.
 * Contiene información sobre el nombre del equipo, la fecha de fundación, la cantidad de jugadores, el salario máximo, mínimo y promedio de los jugadores del equipo.
 * Se crea esta clase para hacer el uso del procedimiento en Base de Datos 'estadisticas_equipos' y no manchar la clase Equipos con más variables
 */
public class EquipoInforme {

    // Clase creada para el uso de un Procedimiento en base de datos
    // Se ha creado para no manchar la clase Equipos con más variables

    private String nombre;
    private LocalDate fechaFundacion;
    private int cantJugadores;
    private int maxSalario;
    private int minSalario;
    private int avgSalario;

    public EquipoInforme(String nombre, LocalDate fechaFundacion, int cantJugadores, int maxSalario, int minSalario, int avgSalario) {
        this.nombre = nombre;
        this.fechaFundacion = fechaFundacion;
        this.cantJugadores = cantJugadores;
        this.maxSalario = maxSalario;
        this.minSalario = minSalario;
        this.avgSalario = avgSalario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaFundacion() {
        return fechaFundacion;
    }

    public void setFechaFundacion(LocalDate fechaFundacion) {
        this.fechaFundacion = fechaFundacion;
    }

    public int getCantJugadores() {
        return cantJugadores;
    }

    public void setCantJugadores(int cantJugadores) {
        this.cantJugadores = cantJugadores;
    }

    public int getMaxSalario() {
        return maxSalario;
    }

    public void setMaxSalario(int maxSalario) {
        this.maxSalario = maxSalario;
    }

    public int getMinSalario() {
        return minSalario;
    }

    public void setMinSalario(int minSalario) {
        this.minSalario = minSalario;
    }

    public int getAvgSalario() {
        return avgSalario;
    }

    public void setAvgSalario(int avgSalario) {
        this.avgSalario = avgSalario;
    }
}
