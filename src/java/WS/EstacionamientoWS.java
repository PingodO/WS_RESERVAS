package WS;

import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import modelo.Estacionamiento;
import servicio.EstacionamientoService;

@WebService(serviceName = "EstacionamientoWS")
public class EstacionamientoWS {

    private EstacionamientoService service = new EstacionamientoService();

    @WebMethod(operationName = "listarEstacionamientos")
    public List<Estacionamiento> listarEstacionamientos() {
        System.out.println("DEBUG (ReservasWS): Recibida solicitud para listar estacionamientos.");
        return service.listar();
    }

    @WebMethod(operationName = "buscarEstacionamientoPorId")
    public Estacionamiento buscarEstacionamientoPorId(@WebParam(name = "id") int id) {
        return service.buscarPorId(id);
    }

    @WebMethod(operationName = "registrarEstacionamiento")
    public boolean registrarEstacionamiento(@WebParam(name = "estacionamiento") Estacionamiento e) {
        return service.registrar(e);
    }

    @WebMethod(operationName = "actualizarEstacionamiento")
    public boolean actualizarEstacionamiento(@WebParam(name = "estacionamiento") Estacionamiento e) {
        return service.actualizar(e);
    }

    @WebMethod(operationName = "eliminarEstacionamiento")
    public boolean eliminarEstacionamiento(@WebParam(name = "id") int id) {
        return service.eliminar(id);
    }
}
