<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Registrar Reserva</title></head>
<body>
    <h2>Registrar Reserva</h2>
    <form action="ReservaServlet" method="post">
        <input type="hidden" name="accion" value="registrar">
        Usuario ID: <input type="number" name="usuarioId" required><br>
        Código Estacionamiento: <input type="number" name="codEsta" required><br>
        Fecha: <input type="date" name="fecha" required><br>
        Hora Inicio: <input type="time" name="horaInicio" required><br>
        Hora Fin: <input type="time" name="horaFin" required><br>
        Estado:
        <select name="estado">
            <option value="reservada">Reservada</option>
            <option value="cancelada">Cancelada</option>
            <option value="asistio">Asistió</option>
            <option value="no_asistio">No asistió</option>
        </select><br>
        <input type="submit" value="Registrar">
    </form>
    <a href="listarReservas.jsp">Ver Reservas</a>
</body>
</html>
