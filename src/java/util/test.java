
package util;

import java.sql.Connection;
import java.sql.SQLException;


public class test {
    public static void main(String[] args) {
        System.out.println("--- Iniciando prueba de conexión a la base de datos ---");

        Connection conn = null;
        try {
            conn = conexion.getConnection(); // Intenta obtener una conexión usando tu método
            if (conn != null && !conn.isClosed()) {
                System.out.println("¡EXITO! La conexión a la base de datos se estableció correctamente.");
                System.out.println("Conexión: " + conn.toString());
            } else {
                System.out.println("FALLO: La conexión a la base de datos es nula o está cerrada.");
            }
        } catch (SQLException e) {
            System.out.println("FALLO: Ocurrió una SQLException al probar la conexión.");
            System.out.println("Mensaje de error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close(); // Siempre cierra la conexión al finalizar la prueba
                    System.out.println("Conexión cerrada.");
                } catch (SQLException e) {
                    System.err.println("Error al cerrar la conexión: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        System.out.println("--- Prueba de conexión finalizada ---");
    }
}
