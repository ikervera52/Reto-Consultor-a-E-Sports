package Clases;

import java.time.LocalDate;

public class Jornada {
    private Integer id;
    private Integer num_jornada;
    private LocalDate fecha;

    public Jornada(Integer id, Integer num_jornada, LocalDate fecha) {
        this.id = id;
        this.num_jornada = num_jornada;
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNum_jornada() {
        return num_jornada;
    }

    public void setNum_jornada(Integer num_jornada) {
        this.num_jornada = num_jornada;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
