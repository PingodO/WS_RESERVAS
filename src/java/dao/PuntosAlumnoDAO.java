
package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.conexion;

public class PuntosAlumnoDAO {

    private Connection conn = conexion.getConnection();

    public PuntosAlumnoDAO(Connection conn) {
        this.conn = conn;
    }

    public double obtenerPuntosPorUsuario(int usuarioId) throws SQLException {
        String sql = "SELECT total_puntos FROM puntos_alumno WHERE usuario_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total_puntos");
            }
        }
        return 0;
    }

    public boolean descontarPuntos(int usuarioId, double puntosADescontar) throws SQLException {
        double puntosActuales = obtenerPuntosPorUsuario(usuarioId);
        if (puntosActuales < puntosADescontar) return false;

        String sql = "UPDATE puntos_alumno SET total_puntos = total_puntos - ? WHERE usuario_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, puntosADescontar);
            stmt.setInt(2, usuarioId);
            return stmt.executeUpdate() > 0;
        }
    }
}

