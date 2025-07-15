package modelo;

public class Estacionamiento {
    private int codEsta;
    private String numero;
    private String estado; // disponible, ocupado, reservado

    public Estacionamiento() {
    }

    public Estacionamiento(int codEsta, String numero, String estado) {
        this.codEsta = codEsta;
        this.numero = numero;
        this.estado = estado;
    }

    public int getCodEsta() {
        return codEsta;
    }

    public void setCodEsta(int codEsta) {
        this.codEsta = codEsta;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
