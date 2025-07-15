<%@ page import="java.util.*, modelo.Reserva, servicio.ReservaService" %>
<%
    ReservaService service = new ReservaService();
    List<Reserva> lista = service.listar();
%>
<html>
<head><title>Lista de Reservas</title></head>
<body>
    <h2>Reservas Registradas</h2>
    <table border="1">
        <tr><th>ID</th><th>Usuario</th><th>Estacionamiento</th><th>Fecha</th><th>Inicio</th><th>Fin</th><th>Estado</th><th>Acciones</th></tr>
        <%
            for (Reserva r : lista) {
        %>
        <tr>
            <td><%= r.getId() %></td>
            <td><%= r.getUsuarioId() %></td>
            <td><%= r.getCodEsta() %></td>
            <td><%= r.getFecha() %></td>
            <td><%= r.getHoraInicio() %></td>
            <td><%= r.getHoraFin() %></td>
            <td><%= r.getEstado() %></td>
            <td>
                <form action="ReservaServlet" method="post" style="display:inline;">
                    <input type="hidden" name="accion" value="eliminar">
                    <input type="hidden" name="id" value="<%= r.getId() %>">
                    <input type="submit" value="Eliminar">
                </form>
            </td>
        </tr>
        <% } %>
    </table>
    <a href="reserva.jsp">Registrar nueva</a>
</body>
</html>
