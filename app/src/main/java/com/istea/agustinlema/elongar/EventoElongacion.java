package com.istea.agustinlema.elongar;

/**
 * Created by KyA on 29/1/2018.
 */

public class EventoElongacion {
    private String fecha;
    private String hora;

    public EventoElongacion(String fecha, String hora) {
        this.fecha = fecha;
        this.hora = hora;
    }

    public String getFecha() {

        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
