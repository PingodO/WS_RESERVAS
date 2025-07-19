package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date; // Importación específica para java.sql.Date
import java.sql.Time; // Importación específica para java.sql.Time
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import modelo.Reserva;
import util.conexion;

public class ReservaDAO {

    public List<Reserva> listar() {
        List<Reserva> lista = new ArrayList<>();
        // SQL con JOINs para obtener datos de usuario y estacionamiento
        String sql = "SELECT r.id, r.usuario_id, r.codEsta, r.fecha, r.hora_inicio, r.hora_fin, r.estado, " +
                     "u.nombre AS nombreUsuario, u.apellido AS apellidoUsuario, " +
                     "e.numero AS numeroEstacionamiento " +
                     "FROM reservas r " +
                     "JOIN usuarios u ON r.usuario_id = u.id " +
                     "JOIN estacionamiento e ON r.codEsta = e.codEsta";

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Reserva r = new Reserva();
                r.setId(rs.getInt("id"));
                r.setUsuarioId(rs.getInt("usuario_id"));
                r.setCodEsta(rs.getInt("codEsta"));
                r.setNombreUsuario(rs.getString("nombreUsuario"));
                r.setApellidoUsuario(rs.getString("apellidoUsuario"));
                r.setNumeroEstacionamiento(rs.getString("numeroEstacionamiento"));
                
                // Mapear Date/Time de la BD (java.sql.*) a String para el objeto Reserva (modelo.Reserva)
                Date fechaSql = rs.getDate("fecha");
                Time horaInicioSql = rs.getTime("hora_inicio");
                Time horaFinSql = rs.getTime("hora_fin");
                
                r.setFecha(fechaSql != null ? fechaSql.toString() : null);
                r.setHoraInicio(horaInicioSql != null ? horaInicioSql.toString() : null);
                r.setHoraFin(horaFinSql != null ? horaFinSql.toString() : null);
                
                r.setEstado(rs.getString("estado"));
                lista.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    //aca comineza el listar reserva actual del usuario
    public List<Reserva> listarReservasActualesDeHoyPorUsuario(int usuarioId) {
        List<Reserva> lista = new ArrayList<>();
        // **IMPORTANTE**: Usa CURDATE() y CURTIME() para que las consultas sean dinámicas.
        // Si tu DB no es MySQL, cambia estas funciones por las equivalentes (ej. GETDATE() para SQL Server).
        String sql = "SELECT r.id, u.nombre, u.apellido, e.numero, r.fecha, r.hora_inicio, r.hora_fin, r.estado " +
                     "FROM reservas r " +
                     "JOIN usuarios u ON r.usuario_id = u.id " +
                     "JOIN estacionamiento e ON r.codEsta = e.codEsta " +
                     "WHERE u.id = ? " +
                     "AND r.fecha = CURDATE() " + // Fecha actual del servidor
                     "AND r.hora_fin > CURTIME() " + // Hora actual del servidor
                     "AND r.estado = 'reservada'";

        try (Connection conn = conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(crearReservaDesdeResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Reserva> listarTodasLasReservasPorUsuario(int usuarioId) {
        List<Reserva> lista = new ArrayList<>();

        String sql = "SELECT r.id, r.usuario_id, r.codEsta, u.nombre AS nombreUsuario, " +
                     "u.apellido AS apellidoUsuario, e.numero AS numeroEstacionamiento, " +
                     "r.fecha, r.hora_inicio, r.hora_fin, r.estado " +
                     "FROM reservas r " +
                     "JOIN usuarios u ON r.usuario_id = u.id " +
                     "JOIN estacionamiento e ON r.codEsta = e.codEsta " +
                     "WHERE u.id = ? " +
                     "ORDER BY r.fecha DESC, r.hora_inicio DESC";

        try (Connection conn = conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, usuarioId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Reserva reserva = new Reserva();
                reserva.setId(rs.getInt("id"));
                reserva.setUsuarioId(rs.getInt("usuario_id"));
                reserva.setCodEsta(rs.getInt("codEsta"));
                reserva.setNombreUsuario(rs.getString("nombreUsuario"));
                reserva.setApellidoUsuario(rs.getString("apellidoUsuario"));
                reserva.setNumeroEstacionamiento(rs.getString("numeroEstacionamiento"));
                reserva.setFecha(rs.getString("fecha"));
                reserva.setHoraInicio(rs.getString("hora_inicio"));
                reserva.setHoraFin(rs.getString("hora_fin"));
                reserva.setEstado(rs.getString("estado"));
                lista.add(reserva);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
    
    private Reserva crearReservaDesdeResultSet(ResultSet rs) throws SQLException {
        Reserva r = new Reserva();
        r.setId(rs.getInt("id"));
        r.setNombreUsuario(rs.getString("nombre"));
        r.setApellidoUsuario(rs.getString("apellido"));
        r.setNumeroEstacionamiento(rs.getString("numero"));

        Date fechaSql = rs.getDate("fecha");
        Time horaInicioSql = rs.getTime("hora_inicio");
        Time horaFinSql = rs.getTime("hora_fin");

        r.setFecha(fechaSql != null ? fechaSql.toString() : null);
        r.setHoraInicio(horaInicioSql != null ? horaInicioSql.toString() : null);
        r.setHoraFin(horaFinSql != null ? horaFinSql.toString() : null);

        r.setEstado(rs.getString("estado"));
        return r;
    }

    private Reserva crearReservaDesdeResultSetConTodo(ResultSet rs) throws SQLException {
        Reserva r = new Reserva();
        r.setId(rs.getInt("id"));
        r.setUsuarioId(rs.getInt("usuario_id"));
        r.setCodEsta(rs.getInt("codEsta"));
        r.setNombreUsuario(rs.getString("nombreUsuario"));
        r.setApellidoUsuario(rs.getString("apellidoUsuario"));
        r.setNumeroEstacionamiento(rs.getString("numeroEstacionamiento"));

        Date fechaSql = rs.getDate("fecha");
        Time horaInicioSql = rs.getTime("hora_inicio");
        Time horaFinSql = rs.getTime("hora_fin");

        r.setFecha(fechaSql != null ? fechaSql.toString() : null);
        r.setHoraInicio(horaInicioSql != null ? horaInicioSql.toString() : null);
        r.setHoraFin(horaFinSql != null ? horaFinSql.toString() : null);

        r.setEstado(rs.getString("estado"));
        return r;
    }
    //aca termina el listar reserva actual del usuario
    
   public boolean actualizarEstadoReserva(int idReserva, String nuevoEstado) {
    try (Connection conn = conexion.getConnection()) {
        String sqlReserva = "UPDATE reservas SET estado = ? WHERE id = ?";
        try (PreparedStatement psReserva = conn.prepareStatement(sqlReserva)) {
            psReserva.setString(1, nuevoEstado);
            psReserva.setInt(2, idReserva);

            int filasAfectadas = psReserva.executeUpdate();

            if (filasAfectadas > 0) {
                String sqlCodEsta = "SELECT codEsta, usuario_id FROM reservas WHERE id = ?";
                try (PreparedStatement psCodEsta = conn.prepareStatement(sqlCodEsta)) {
                    psCodEsta.setInt(1, idReserva);
                    try (ResultSet rs = psCodEsta.executeQuery()) {
                        if (rs.next()) {
                            int codEsta = rs.getInt("codEsta");
                            int usuarioId = rs.getInt("usuario_id");
                            String nuevoEstadoEstacionamiento = determinarEstadoEstacionamiento(nuevoEstado);

                            if (nuevoEstadoEstacionamiento != null) {
                                // Actualizar estado del estacionamiento
                                String sqlEstacionamiento = "UPDATE estacionamiento SET estado = ? WHERE codEsta = ?";
                                try (PreparedStatement psEsta = conn.prepareStatement(sqlEstacionamiento)) {
                                    psEsta.setString(1, nuevoEstadoEstacionamiento);
                                    psEsta.setInt(2, codEsta);
                                    psEsta.executeUpdate();
                                }
                            }

                            // Si el estado de la reserva es "no_asistio", bloquear al usuario
                            if ("no_asistio".equalsIgnoreCase(nuevoEstado)) {
                                // Actualizar estado del usuario a "bloqueado"
                                String sqlUsuario = "UPDATE usuarios SET estado = 'bloqueado' WHERE id = ?";
                                try (PreparedStatement psUsuario = conn.prepareStatement(sqlUsuario)) {
                                    psUsuario.setInt(1, usuarioId);
                                    psUsuario.executeUpdate();
                                }
                            }
                        }
                    }
                }
                return true;
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}


    private String determinarEstadoEstacionamiento(String estadoReserva) {
        switch (estadoReserva.toLowerCase()) {
            case "reservada":
                return "reservado";
            case "asistio":
                return "ocupado";
            case "cancelada":
            case "no_asistio":
            case "culmino_tiempo":
                return "disponible";
            default:
                return null;
        }
    }
    
    public Reserva buscarPorId(int id) {
        String sql = "SELECT r.id, r.usuario_id, r.codEsta, r.fecha, r.hora_inicio, r.hora_fin, r.estado, " +
                     "u.nombre AS nombreUsuario, u.apellido AS apellidoUsuario, " +
                     "e.numero AS numeroEstacionamiento " +
                     "FROM reservas r " +
                     "JOIN usuarios u ON r.usuario_id = u.id " +
                     "JOIN estacionamiento e ON r.codEsta = e.codEsta " +
                     "WHERE r.id = ?";
        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Reserva r = new Reserva();
                    r.setId(rs.getInt("id"));
                    r.setUsuarioId(rs.getInt("usuario_id"));
                    r.setCodEsta(rs.getInt("codEsta"));
                    r.setNombreUsuario(rs.getString("nombreUsuario"));
                    r.setApellidoUsuario(rs.getString("apellidoUsuario"));
                    r.setNumeroEstacionamiento(rs.getString("numeroEstacionamiento"));
                    
                    // Mapear Date/Time de la BD (java.sql.*) a String para el objeto Reserva (modelo.Reserva)
                    Date fechaSql = rs.getDate("fecha");
                    Time horaInicioSql = rs.getTime("hora_inicio");
                    Time horaFinSql = rs.getTime("hora_fin");
                    
                    r.setFecha(fechaSql != null ? fechaSql.toString() : null);
                    r.setHoraInicio(horaInicioSql != null ? horaInicioSql.toString() : null);
                    r.setHoraFin(horaFinSql != null ? horaFinSql.toString() : null);
                    
                    r.setEstado(rs.getString("estado"));
                    return r;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    public Integer obtenerEstacionamientoDisponibleAleatorio() {
        String sql = "SELECT codEsta FROM estacionamiento WHERE estado = 'disponible'";
        List<Integer> disponibles = new ArrayList<>();
        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                disponibles.add(rs.getInt("codEsta"));
            }
            if (!disponibles.isEmpty()) {
                int index = (int) (Math.random() * disponibles.size());
                return disponibles.get(index);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertar(Reserva r) {
        String sql = "INSERT INTO reservas (usuario_id, codEsta, fecha, hora_inicio, hora_fin, estado) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, r.getUsuarioId());
            ps.setInt(2, r.getCodEsta());
            
            // Convierte String de Reserva (modelo.Reserva) a java.sql.Date/Time para la BD
            ps.setDate(3, r.getFecha() != null ? Date.valueOf(r.getFecha()) : null);
            ps.setTime(4, r.getHoraInicio() != null ? Time.valueOf(r.getHoraInicio()) : null);
            ps.setTime(5, r.getHoraFin() != null ? Time.valueOf(r.getHoraFin()) : null);
            
            ps.setString(6, r.getEstado());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) { // Se usa 'e' aquí
            e.printStackTrace();
            return false;
        } catch (IllegalArgumentException e) { // Se usa 'e' aquí
            System.err.println("Error de formato de fecha/hora al insertar: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Reserva r) {
        String sql = "UPDATE reservas SET usuario_id = ?, codEsta = ?, fecha = ?, hora_inicio = ?, hora_fin = ?, estado = ? WHERE id = ?";
        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, r.getUsuarioId());
            ps.setInt(2, r.getCodEsta());
            
            // Convierte String de Reserva (modelo.Reserva) a java.sql.Date/Time para la BD
            ps.setDate(3, r.getFecha() != null ? Date.valueOf(r.getFecha()) : null);
            ps.setTime(4, r.getHoraInicio() != null ? Time.valueOf(r.getHoraInicio()) : null);
            ps.setTime(5, r.getHoraFin() != null ? Time.valueOf(r.getHoraFin()) : null);
            
            ps.setString(6, r.getEstado());
            ps.setInt(7, r.getId());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) { // Se usa 'e' aquí
            e.printStackTrace();
            return false;
        } catch (IllegalArgumentException e) { // Se usa 'e' aquí
            System.err.println("Error de formato de fecha/hora al actualizar: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM reservas WHERE id = ?";
        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) { // Se usa 'e' aquí
            e.printStackTrace();
            return false;
        }
    }
    
    public int contarReservasDelDia(int usuarioId, LocalDate fecha) {
        int cantidad = 0;
        String sql = "SELECT COUNT(*) FROM reservas WHERE usuario_id = ? AND fecha = ?";

        try (Connection conn = conexion.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ps.setDate(2, java.sql.Date.valueOf(fecha));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cantidad = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cantidad;
    } 
    
}