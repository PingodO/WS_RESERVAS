<%@ page import="java.util.*, modelo.Estacionamiento, servicio.EstacionamientoService" %>
<%
    EstacionamientoService service = new EstacionamientoService();
    List<Estacionamiento> lista = service.listar();
%>
<html>
<head><title>Lista de Estacionamientos</title></head>
<body>
    <h2>Estacionamientos Registrados</h2>
    <table border="1">
        <tr><th>ID</th><th>Número</th><th>Estado</th><th>Acciones</th></tr>
        <%
            for (Estacionamiento e : lista) {
        %>
        <tr>
            <td><%= e.getCodEsta() %></td>
            <td><%= e.getNumero() %></td>
            <td><%= e.getEstado() %></td>
            <td>
                <form action="EstacionamientoServlet" method="post" style="display:inline;">
                    <input type="hidden" name="accion" value="eliminar">
                    <input type="hidden" name="id" value="<%= e.getCodEsta() %>">
                    <input type="submit" value="Eliminar">
                </form>
            </td>
        </tr>
        <% } %>
    </table>
    <a href="estacionamiento.jsp">Registrar nuevo</a>
</body>
</html>
