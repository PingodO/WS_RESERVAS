package servicio;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Random;

import modelo.Reserva;
import modelo.Estacionamiento;
import dao.ReservaDAO;
import dao.EstacionamientoDAO;

public class ReservaService {

    private ReservaDAO reservaDAO = new ReservaDAO();
    private EstacionamientoDAO estacionamientoDAO = new EstacionamientoDAO();

    public List<Reserva> listar() {
        return reservaDAO.listar();
    }

    public Reserva buscarPorId(int id) {
        return reservaDAO.buscarPorId(id);
    }

    // MODIFICACIÓN: Ahora retorna un objeto Reserva (o null si falla)
    public Reserva generarReserva(int usuarioId, String fechaStr, String horaInicioStr, String horaFinStr) {
        try {
            Date fecha = Date.valueOf(fechaStr);
            Time horaInicio = Time.valueOf(horaInicioStr);
            Time horaFin = Time.valueOf(horaFinStr);

            List<Estacionamiento> disponibles = estacionamientoDAO.listarDisponibles();

            if (disponibles.isEmpty()) {
                System.out.println("No hay estacionamientos disponibles para reservar.");
                return null; // Retorna null si no hay estacionamientos
            }

            Random rand = new Random();
            Estacionamiento estacionamientoAsignado = disponibles.get(rand.nextInt(disponibles.size()));

            boolean estadoEstacionamientoActualizado = estacionamientoDAO.actualizarEstado(estacionamientoAsignado.getCodEsta(), "reservado");
            
            if (!estadoEstacionamientoActualizado) {
                System.out.println("Error al actualizar el estado del estacionamiento. No se pudo generar la reserva.");
                return null; // Retorna null si falla la actualización del estacionamiento
            }

            Reserva nuevaReserva = new Reserva();
            nuevaReserva.setUsuarioId(usuarioId);
            nuevaReserva.setCodEsta(estacionamientoAsignado.getCodEsta());
            nuevaReserva.setFecha(fechaStr);
            nuevaReserva.setHoraInicio(horaInicioStr);
            nuevaReserva.setHoraFin(horaFinStr);
            nuevaReserva.setEstado("reservada");
            // Estos campos son útiles si vas a retornar el objeto completo
            nuevaReserva.setNumeroEstacionamiento(estacionamientoAsignado.getNumero());
            // Para nombre/apellido del usuario, necesitarías consultar el DAO de Usuario
            // o pasarlos como parámetros si ya los tienes en esta capa.
            // Por simplicidad, los dejo en null aquí, se cargarán al hacer un listar() o buscarPorId() desde BD
            nuevaReserva.setNombreUsuario(null); 
            nuevaReserva.setApellidoUsuario(null); 

            boolean reservaRegistrada = reservaDAO.insertar(nuevaReserva);

            if (!reservaRegistrada) {
                estacionamientoDAO.actualizarEstado(estacionamientoAsignado.getCodEsta(), "disponible"); // Revertir
                System.out.println("Error al registrar la reserva. Estacionamiento revertido a 'disponible'.");
                return null; // Retorna null si la reserva no se pudo insertar
            }
            
            // Si la inserción fue exitosa, la reserva ya tendrá un ID (si tu DAO lo obtiene)
            // o necesitarás buscarla por sus atributos si la base de datos genera el ID.
            // Para simplificar, asumiremos que si 'insertar' devuelve true,
            // podemos retornar el objeto 'nuevaReserva' con los datos que ya le seteamos.
            // NOTA: Si el ID de la reserva es autogenerado por la BD y necesitas retornarlo,
            // tu ReservaDAO.insertar debería modificar el objeto Reserva que recibe para setearle el ID.
            // Si no lo hace, necesitarías buscar la reserva recién creada por usuarioId, fecha, horas.
            // Por ahora, retornamos la instancia que creamos con el estacionamiento asignado.
            
            return nuevaReserva; // Retorna el objeto Reserva creado

        } catch (IllegalArgumentException e) {
            System.err.println("Error de formato en fecha/hora: " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean actualizar(Reserva r) {
        return reservaDAO.actualizar(r);
    }

    public boolean eliminar(int id) {
        return reservaDAO.eliminar(id);
    }

    public boolean cambiarEstadoReserva(int idReserva, String nuevoEstado) {
        return reservaDAO.actualizarEstadoReserva(idReserva, nuevoEstado);
    }
}