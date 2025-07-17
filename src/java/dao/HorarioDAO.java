package dao;

import modelo.Horario;
import util.conexion;
import java.sql.*;
import java.time.*;
import java.time.format.TextStyle;
import java.util.*;

public class HorarioDAO {

    public List<Horario> obtenerHorariosPorUsuarioYFecha(int usuarioId, LocalDate fecha) {
        List<Horario> lista = new ArrayList<>();
        String diaSemana = fecha.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es")).toLowerCase();

        String sql = "SELECT * FROM horarios WHERE usuario_id = ? AND LOWER(dia_semana) = ?";
        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, usuarioId);
            ps.setString(2, diaSemana);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Horario h = new Horario();
                    h.setId(rs.getInt("id"));
                    h.setUsuarioId(rs.getInt("usuario_id"));
                    h.setDiaSemana(rs.getString("dia_semana"));
                    h.setHoraInicio(rs.getString("hora_inicio"));
                    h.setHoraFin(rs.getString("hora_fin"));
                    h.setClase(rs.getString("clase"));
                    h.setAula(rs.getString("aula"));
                    lista.add(h);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    
    /**
     * Obtiene el saldo actual de puntos de un usuario desde la tabla 'usuarios'.
     * @param usuarioId El ID del usuario.
     * @return El total de puntos del usuario, o 0 si no se encuentra.
     */
    /**
     * Obtiene el saldo actual de puntos de un usuario desde la tabla 'usuarios'.
     * @param usuarioId El ID del usuario.
     * @return El total de puntos del usuario, o 0 si no se encuentra.
     */
    public int obtenerSaldoPuntosUsuario(int usuarioId) {
        int saldo = 0;
        String sql = "SELECT puntos FROM usuarios WHERE id = ?"; // Asegúrate que tu tabla usuarios tiene esta columna
        try (Connection conn = conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    saldo = rs.getInt("puntos");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al obtener saldo de puntos del usuario: " + e.getMessage());
        }
        return saldo;
    }

    public int calcularPuntosPorFecha(int usuarioId, LocalDate fecha) {
        List<Horario> horarios = obtenerHorariosPorUsuarioYFecha(usuarioId, fecha);
        int puntos = 0;

        for (Horario h : horarios) {
            try {
                LocalTime inicio = LocalTime.parse(h.getHoraInicio());
                LocalTime fin = LocalTime.parse(h.getHoraFin());
                long minutos = Duration.between(inicio, fin).toMinutes();
                puntos += (int)(minutos / 90); // 1 punto por cada 90 minutos
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return puntos;
    }
    
    public static int obtenerPuntosPorDia(int usuarioId, LocalDate fechaObjetivo) {
        // La consulta SQL ha sido MODIFICADA.
        // Ahora selecciona la columna 'puntos' de la tabla 'usuarios'.
        String sql = "SELECT puntos FROM usuarios WHERE id = ?"; 

        // Las siguientes líneas relacionadas con 'dia_semana' y 'fechaObjetivo' ya no son necesarias
        // porque la nueva consulta no las utiliza.
        // String diaSemana = obtenerDiaSemana(fechaObjetivo); // ¡Esta línea ya no se usa!

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, usuarioId);
            // ps.setString(2, diaSemana); // ¡Esta línea ya no es necesaria con la nueva consulta!

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Ahora lee la columna 'puntos' de la tabla 'usuarios'
                    return rs.getInt("puntos"); 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al obtener puntos del usuario desde la tabla 'usuarios': " + e.getMessage());
        }
        return 0; // Retorna 0 si no encuentra el usuario o hay un error
    }


    private static String obtenerDiaSemana(LocalDate fecha) {
        DayOfWeek dayOfWeek = fecha.getDayOfWeek();
        switch (dayOfWeek) {
            case MONDAY: return "Lunes";
            case TUESDAY: return "Martes";
            case WEDNESDAY: return "Miércoles";
            case THURSDAY: return "Jueves";
            case FRIDAY: return "Viernes";
            case SATURDAY: return "Sábado";
            case SUNDAY: return "Domingo";
            default: return "";
        }
    }
    
    public void descontarPuntos(int usuarioId, int cantidad) {
        try (Connection conn = conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE usuarios SET puntos = puntos - ? WHERE id = ?")) {
            ps.setInt(1, cantidad);
            ps.setInt(2, usuarioId);
            ps.executeUpdate();
            System.out.println("Puntos descontados correctamente al usuario ID: " + usuarioId + ", cantidad: " + cantidad);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean tieneClasesAlDiaSiguiente(int usuarioId, LocalDate fechaActual) throws SQLException {
        DayOfWeek dia = fechaActual.plusDays(1).getDayOfWeek();
        String nombreDia = dia.name().substring(0, 1) + dia.name().substring(1).toLowerCase(); // Lunes, Martes, etc.
        Connection conn = conexion.getConnection();
        String sql = "SELECT 1 FROM horarios WHERE usuario_id = ? AND dia_semana = ? LIMIT 1";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            stmt.setString(2, nombreDia);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }
    
    

    
}
