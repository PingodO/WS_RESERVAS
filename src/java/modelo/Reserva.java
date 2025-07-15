package modelo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "reserva") // Anotación para indicar el elemento raíz en XML/JSON
public class Reserva {

    private int id;
    private int usuarioId;
    private int codEsta;
    private String nombreUsuario;         // Campo para el nombre del usuario (para listar)
    private String apellidoUsuario;       // Campo para el apellido del usuario (para listar)
    private String numeroEstacionamiento; // Campo para el número de estacionamiento (para listar)
    private String fecha;                 // Tipo String para compatibilidad con el WS
    private String horaInicio;            // Tipo String para compatibilidad con el WS
    private String horaFin;               // Tipo String para compatibilidad con el WS
    private String estado;

    public Reserva() {
        // Constructor vacío requerido por JAXB
    }

    // Constructor completo para el DAO (cuando se lee de la BD con JOINs)
    public Reserva(int id, int usuarioId, int codEsta, String nombreUsuario, String apellidoUsuario, String numeroEstacionamiento, String fecha, String horaInicio, String horaFin, String estado) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.codEsta = codEsta;
        this.nombreUsuario = nombreUsuario;
        this.apellidoUsuario = apellidoUsuario;
        this.numeroEstacionamiento = numeroEstacionamiento;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.estado = estado;
    }

    // Constructor para insertar/actualizar donde no se necesitan los detalles de usuario/estacionamiento
    // (o cuando no se obtienen del JOIN directo en la DB antes de crear el objeto)
    public Reserva(int id, int usuarioId, int codEsta, String fecha, String horaInicio, String horaFin, String estado) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.codEsta = codEsta;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.estado = estado;
    }

    // --- Getters y Setters con anotaciones @XmlElement donde corresponda ---

    @XmlElement // Se expone en el WS
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    // Estos no se exponen directamente si son solo IDs para el DAO o para la BD
    // Si el WS necesita recibirlos como parte del objeto Reserva en algunas operaciones,
    // se les debería poner @XmlElement. Depende de cómo se configure el @WebMethod.
    public int getUsuarioId() {
        return usuarioId;
    }
    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getCodEsta() {
        return codEsta;
    }
    public void setCodEsta(int codEsta) {
        this.codEsta = codEsta;
    }

    @XmlElement // Se expone en el WS
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    @XmlElement // Se expone en el WS
    public String getApellidoUsuario() {
        return apellidoUsuario;
    }
    public void setApellidoUsuario(String apellidoUsuario) {
        this.apellidoUsuario = apellidoUsuario;
    }

    @XmlElement // Se expone en el WS
    public String getNumeroEstacionamiento() {
        return numeroEstacionamiento;
    }
    public void setNumeroEstacionamiento(String numeroEstacionamiento) {
        this.numeroEstacionamiento = numeroEstacionamiento;
    }

    @XmlElement // Se expone en el WS
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @XmlElement // Se expone en el WS
    public String getHoraInicio() {
        return horaInicio;
    }
    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    @XmlElement // Se expone en el WS
    public String getHoraFin() {
        return horaFin;
    }
    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    @XmlElement // Se expone en el WS
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
}