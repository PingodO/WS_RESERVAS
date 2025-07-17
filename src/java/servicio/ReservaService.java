package servicio;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Duration;
import java.util.List;
import java.util.Random;

import modelo.Reserva;
import modelo.Estacionamiento;
import dao.ReservaDAO;
import dao.EstacionamientoDAO;
import dao.HorarioDAO;
// import dao.PuntosAlumnoDAO; // Es probable que ya no necesites usar este DAO para reservas diarias

public class ReservaService {

    private ReservaDAO reservaDAO = new ReservaDAO();
    private EstacionamientoDAO estacionamientoDAO = new EstacionamientoDAO();
    private HorarioDAO horarioDAO = new HorarioDAO(); 

    // Constructor si necesitas inyectar DAOs, aunque en tu ejemplo están instanciados directamente
    public ReservaService() {
        // Asegúrate de que los DAOs estén bien inicializados si no se pasan por constructor
    }

    public List<Reserva> listar() {
        return reservaDAO.listar();
    }

    public Reserva buscarPorId(int id) {
        return reservaDAO.buscarPorId(id);
    }
    
    // Este método maneja la creación base de la reserva, sin lógica de puntos
    private Reserva generarReservaBase(int usuarioId, String fechaStr, String horaInicioStr, String horaFinStr) {
        try {
            if (fechaStr == null || horaInicioStr == null || horaFinStr == null ||
                fechaStr.isEmpty() || horaInicioStr.isEmpty() || horaFinStr.isEmpty()) {
                System.out.println("Error: La fecha u hora están vacías o nulas.");
                return null;
            }
            
            // Asegurarse de que las horas tengan segundos para Time.valueOf
            if (horaInicioStr.length() == 5) horaInicioStr += ":00";
            if (horaFinStr.length() == 5) horaFinStr += ":00";
            
            Date fecha = Date.valueOf(fechaStr);
            Time horaInicio = Time.valueOf(horaInicioStr);
            Time horaFin = Time.valueOf(horaFinStr);

            List<Estacionamiento> disponibles = estacionamientoDAO.listarDisponibles();

            if (disponibles.isEmpty()) {
                System.out.println("No hay estacionamientos disponibles para reservar.");
                return null;
            }

            Random rand = new Random();
            Estacionamiento estacionamientoAsignado = disponibles.get(rand.nextInt(disponibles.size()));

            boolean estadoEstacionamientoActualizado = estacionamientoDAO.actualizarEstado(estacionamientoAsignado.getCodEsta(), "reservado");
            
            if (!estadoEstacionamientoActualizado) {
                System.out.println("Error al actualizar el estado del estacionamiento. No se pudo generar la reserva.");
                return null;
            }

            Reserva nuevaReserva = new Reserva();
            nuevaReserva.setUsuarioId(usuarioId);
            nuevaReserva.setCodEsta(estacionamientoAsignado.getCodEsta());
            nuevaReserva.setFecha(fechaStr);
            nuevaReserva.setHoraInicio(horaInicioStr);
            nuevaReserva.setHoraFin(horaFinStr);
            nuevaReserva.setEstado("reservada");
            nuevaReserva.setNumeroEstacionamiento(estacionamientoAsignado.getNumero());
            // Estos pueden ser nulos aquí y cargarse al listar/buscar si es necesario desde la DB
            nuevaReserva.setNombreUsuario(null); 
            nuevaReserva.setApellidoUsuario(null); 

            boolean reservaRegistrada = reservaDAO.insertar(nuevaReserva);

            if (!reservaRegistrada) {
                estacionamientoDAO.actualizarEstado(estacionamientoAsignado.getCodEsta(), "disponible"); // Revertir estado del estacionamiento
                System.out.println("Error al registrar la reserva. Estacionamiento revertido a 'disponible'.");
                return null;
            }
            
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
    
    // Este es el método central que maneja la validación de puntos y la deducción
    public Reserva realizarReservaConValidacionDePuntos(int usuarioId, String fechaStr, String horaInicioStr, String horaFinStr) {
        LocalDate fechaReserva = LocalDate.parse(fechaStr);
        // La fecha "mañana" es relativa al día actual
        LocalDate fechaManiana = LocalDate.now().plusDays(1); // Ahora es 2025-07-16 9:11 PM, así que mañana es 2025-07-17

        if (!fechaReserva.equals(fechaManiana)) {
            System.out.println("Solo puedes reservar para mañana.");
            return null;
        }

        // Obtener el saldo actual de puntos del usuario desde la tabla 'usuarios'
        int saldoPuntosActual = horarioDAO.obtenerSaldoPuntosUsuario(usuarioId);
        System.out.println("Saldo actual de puntos del usuario (desde tabla usuarios): " + saldoPuntosActual); // Para depuración

        // Calcular los puntos que esta reserva específica consumirá
        LocalTime inicio = LocalTime.parse(horaInicioStr);
        LocalTime fin = LocalTime.parse(horaFinStr);
        long minutosDuracion = Duration.between(inicio, fin).toMinutes();
        // 1 punto por cada 90 minutos, redondeando hacia arriba.
        // Ej: 60 mins -> ceil(0.66) = 1 punto; 90 mins -> ceil(1) = 1 punto; 100 mins -> ceil(1.11) = 2 puntos
        int puntosARestar = (int) Math.ceil((double) minutosDuracion / 90); 

        if (puntosARestar <= 0) {
            System.out.println("La duración de la reserva es muy corta para generar puntos o es inválida.");
            return null;
        }

        // *** CAMBIO CLAVE EN LA VALIDACIÓN ***
        // Ahora validamos contra el saldo real de puntos del usuario en la tabla 'usuarios'
        if (saldoPuntosActual < puntosARestar) {
             System.out.println("No tienes suficientes puntos en tu saldo para esta reserva.");
             return null;
        }
        
        // Proceder con la reserva real si los puntos son suficientes
        Reserva reservaGenerada = generarReservaBase(usuarioId, fechaStr, horaInicioStr, horaFinStr);
        
        if (reservaGenerada != null) {
            // Deducir puntos DESPUÉS de una reserva exitosa del saldo real del usuario en la tabla 'usuarios'
            horarioDAO.descontarPuntos(usuarioId, puntosARestar);
            System.out.println("Puntos descontados: " + puntosARestar + ". Nuevo saldo: " + (saldoPuntosActual - puntosARestar)); // Para depuración
        }
        
        return reservaGenerada;
    }

    // Este es el método que tu ReservarServlet llama
    public Reserva generarReservaAutomatica(int usuarioId, String fecha, String horaInicio, String horaFin) {
        return realizarReservaConValidacionDePuntos(usuarioId, fecha, horaInicio, horaFin);
    }
    
    // Este método obtiene los puntos del horario (potenciales), NO el saldo actual gastable
    public int obtenerPuntosPorDia(int usuarioId, LocalDate fechaObjetivo) {
        return horarioDAO.obtenerPuntosPorDia(usuarioId, fechaObjetivo);
    }

    // Método para obtener el saldo real de puntos del usuario desde la tabla 'usuarios'
    public int obtenerSaldoPuntosUsuario(int usuarioId) {
        return horarioDAO.obtenerSaldoPuntosUsuario(usuarioId);
    }
}