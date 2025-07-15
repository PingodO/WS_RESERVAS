package controlador;

import modelo.Estacionamiento;
import servicio.EstacionamientoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/EstacionamientoServlet")
public class EstacionamientoServlet extends HttpServlet {

    private EstacionamientoService service = new EstacionamientoService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("registrar".equals(accion)) {
            String numero = request.getParameter("numero");
            String estado = request.getParameter("estado");

            Estacionamiento e = new Estacionamiento();
            e.setNumero(numero);
            e.setEstado(estado);

            service.registrar(e);
            response.sendRedirect("listarEstacionamientos.jsp");

        } else if ("eliminar".equals(accion)) {
            int id = Integer.parseInt(request.getParameter("id"));
            service.eliminar(id);
            response.sendRedirect("listarEstacionamientos.jsp");
        }
    }
}
