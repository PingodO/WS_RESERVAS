package dao;

import java.sql.*;
import java.util.*;
import modelo.Estacionamiento;
import util.conexion;

public class EstacionamientoDAO {

    public List<Estacionamiento> listar() {
        List<Estacionamiento> lista = new ArrayList<>();
        String sql = "SELECT * FROM estacionamiento";

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Estacionamiento e = new Estacionamiento(
                    rs.getInt("codEsta"),
                    rs.getString("numero"),
                    rs.getString("estado")
                );
                lista.add(e);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // NUEVO MÉTODO: Listar estacionamientos disponibles
    public List<Estacionamiento> listarDisponibles() {
        List<Estacionamiento> lista = new ArrayList<>();
        // Asumiendo que el estado 'disponible' es el que indica que se puede reservar
        String sql = "SELECT * FROM estacionamiento WHERE estado = 'disponible'"; 

        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Estacionamiento e = new Estacionamiento(
                    rs.getInt("codEsta"),
                    rs.getString("numero"),
                    rs.getString("estado")
                );
                lista.add(e);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }


    public Estacionamiento buscarPorId(int id) {
        String sql = "SELECT * FROM estacionamiento WHERE codEsta = ?";
        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Estacionamiento(
                    rs.getInt("codEsta"),
                    rs.getString("numero"),
                    rs.getString("estado")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean insertar(Estacionamiento e) {
        String sql = "INSERT INTO estacionamiento (numero, estado) VALUES (?, ?)";
        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, e.getNumero());
            ps.setString(2, e.getEstado());
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean actualizar(Estacionamiento e) {
        String sql = "UPDATE estacionamiento SET numero = ?, estado = ? WHERE codEsta = ?";
        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, e.getNumero());
            ps.setString(2, e.getEstado());
            ps.setInt(3, e.getCodEsta());
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    // NUEVO MÉTODO: Actualizar solo el estado de un estacionamiento
    public boolean actualizarEstado(int codEsta, String nuevoEstado) {
        String sql = "UPDATE estacionamiento SET estado = ? WHERE codEsta = ?";
        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nuevoEstado);
            ps.setInt(2, codEsta);
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }


    public boolean eliminar(int id) {
        String sql = "DELETE FROM estacionamiento WHERE codEsta = ?";
        try (Connection con = conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}