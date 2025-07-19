package WS;

import java.time.LocalDate;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import modelo.Estacionamiento;
import modelo.Reserva;
import servicio.EstacionamientoService;
import servicio.ReservaService; 

@WebService(serviceName = "ReservasWS")
public class ReservasWS {


    private ReservaService service2 = new ReservaService(); 
    
    private EstacionamientoService estacionamientoService = new EstacionamientoService();


    @WebMethod(operationName = "listarReservas")
    public List<Reserva> listarReservas() {
        return service2.listar();
    }

    @WebMethod(operationName = "buscarReservaPorId")
    public Reserva buscarReservaPorId(@WebParam(name = "id") int id) {
        return service2.buscarPorId(id);
    }
    
    @WebMethod(operationName = "listarReservasActualesDeHoyPorUsuario")
    public List<Reserva> listarReservasActualesDeHoyPorUsuario(@WebParam(name = "usuarioId") int usuarioId) {
        // Llama al método correspondiente en tu ReservaService
        return service2.listarReservasActualesDeHoyPorUsuario(usuarioId);
    }

    @WebMethod(operationName = "listarHistorialReservasPorUsuario")
    public List<Reserva> listarHistorialReservasPorUsuario(@WebParam(name = "usuarioId") int usuarioId) {
        // Llama al método correspondiente en tu ReservaService
        return service2.listarHistorialReservasPorUsuario(usuarioId);
    }

     @WebMethod(operationName = "generarReservaAutomatica")
    public Reserva generarReservaAutomatica( // El tipo de retorno es Reserva
            @WebParam(name = "usuarioId") int usuarioId,
            @WebParam(name = "fecha") String fecha,
            @WebParam(name = "horaInicio") String horaInicio,
            @WebParam(name = "horaFin") String horaFin) {
        // Llama al método del servicio que maneja la validación y deducción de puntos
        return service2.generarReservaAutomatica(usuarioId, fecha, horaInicio, horaFin);
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
    
    @WebMethod(operationName = "obtenerSaldoPuntosUsuario")
    public int obtenerSaldoPuntosUsuario(
            @WebParam(name = "usuarioId") int usuarioId) {
        return service2.obtenerSaldoPuntosUsuario(usuarioId);
    }
    
    @WebMethod(operationName = "obtenerPuntosPorDia")
    public int obtenerPuntosPorDia(
        @WebParam(name = "usuarioId") int usuarioId,
        @WebParam(name = "fechaObjetivo") String fechaObjetivoStr) { // Fecha como String para WS
        LocalDate fechaObjetivo = LocalDate.parse(fechaObjetivoStr);
        return service2.obtenerPuntosPorDia(usuarioId, fechaObjetivo);
    }
    
    
}