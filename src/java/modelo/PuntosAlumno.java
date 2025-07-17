
package modelo;

import java.sql.Timestamp;

public class PuntosAlumno {
    
    private int id;
    private int usuarioId;
    private double totalPuntos;
    private Timestamp fechaActualizacion;

    public PuntosAlumno() {
    }

    public PuntosAlumno(int id, int usuarioId, double totalPuntos, Timestamp fechaActualizacion) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.totalPuntos = totalPuntos;
        this.fechaActualizacion = fechaActualizacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public double getTotalPuntos() {
        return totalPuntos;
    }

    public void setTotalPuntos(double totalPuntos) {
        this.totalPuntos = totalPuntos;
    }

    public Timestamp getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Timestamp fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
    
    
}
