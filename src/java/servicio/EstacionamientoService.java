package servicio;

import dao.EstacionamientoDAO;
import java.util.List;
import javax.jws.WebMethod;
import modelo.Estacionamiento;

public class EstacionamientoService {
    
    private EstacionamientoDAO dao = new EstacionamientoDAO();

    public List<Estacionamiento> listar() {
        return dao.listar();
    }

    public Estacionamiento buscarPorId(int id) {
        return dao.buscarPorId(id);
    }

    public boolean registrar(Estacionamiento e) {
        // Validación simple: no permitir campos vacíos
        if (e.getNumero() == null || e.getNumero().isEmpty()) return false;
        return dao.insertar(e);
    }

    public boolean actualizar(Estacionamiento e) {
        return dao.actualizar(e);
    }

    public boolean eliminar(int id) {
        return dao.eliminar(id);
    }
    
    
    
}
