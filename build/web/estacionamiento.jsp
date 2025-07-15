<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head><title>Registrar Estacionamiento</title></head>
<body>
    <h2>Registrar Estacionamiento</h2>
        <form action="EstacionamientoServlet" method="post">
        <input type="hidden" name="accion" value="registrar">
        Número: <input type="text" name="numero" required><br>
        Estado:
        <select name="estado">
            <option value="disponible">Disponible</option>
            <option value="ocupado">Ocupado</option>
            <option value="reservado">Reservado</option>
        </select><br>
        <input type="submit" value="Registrar">
    </form>
    <a href="listarEstacionamientos.jsp">Ver Estacionamientos</a>
</body>
</html>
