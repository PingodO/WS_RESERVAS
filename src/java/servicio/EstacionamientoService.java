package servicio;

import dao.EstacionamientoDAO;
import java.util.List;
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
//+ in @RR 
    public boolean eliminar(int id, String nuevoEstado) {
        return dao.actualizarEstado(id, nuevoEstado);
    }
//+ fin @RR    
}
