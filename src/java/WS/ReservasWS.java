package WS;

import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import modelo.Reserva;
import servicio.ReservaService; 

@WebService(serviceName = "ReservasWS")
public class ReservasWS {


    private ReservaService service2 = new ReservaService(); 

    @WebMethod(operationName = "listarReservas")
    public List<Reserva> listarReservas() {
        return service2.listar();
    }

    @WebMethod(operationName = "buscarReservaPorId")
    public Reserva buscarReservaPorId(@WebParam(name = "id") int id) {
        return service2.buscarPorId(id);
    }

    @WebMethod(operationName = "generarReservaAutomatica")
    public Reserva generarReservaAutomatica( // Cambiado el tipo de retorno a 'Reserva'
            @WebParam(name = "usuarioId") int usuarioId,
            @WebParam(name = "fecha") String fecha,
            @WebParam(name = "horaInicio") String horaInicio,
            @WebParam(name = "horaFin") String horaFin) {
        // Delega la lógica de generación a la capa de servicio
        return service2.generarReserva(usuarioId, fecha, horaInicio, horaFin);
    }

    @WebMethod(operationName = "actualizarReserva")
    public boolean actualizarReserva(@WebParam(name = "reserva") Reserva r) {
        return service2.actualizar(r);
    }

    @WebMethod(operationName = "eliminarReserva")
    public boolean eliminarReserva(@WebParam(name = "id") int id) {
        return service2.eliminar(id);
    }

    @WebMethod(operationName = "cambiarEstadoReserva")
    public boolean cambiarEstadoReserva(@WebParam(name = "idReserva") int idReserva, @WebParam(name = "nuevoEstado") String nuevoEstado) {
        return service2.cambiarEstadoReserva(idReserva, nuevoEstado);
    }
}