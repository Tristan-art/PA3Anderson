<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Módulo de Reportes y Supervisión de Pedidos</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css">
</head>
<body>
    <div class="container">
        <h1>Módulo de Reportes y Supervisión</h1>
        <p><a href="${pageContext.request.contextPath}/pedidos">Volver al Panel Principal</a></p>

        <hr>

        <!-- Sección de Resumen Consolidado -->
        <h2>Resumen General</h2>
        <ul>
            <li>Total de Pedidos: <strong>${resumen.totalPedidos}</strong></li>
            <li>Total de Unidades: <strong>${resumen.totalUnidades}</strong></li>
            <li>Importe Acumulado: <strong>$${resumen.importeAcumulado}</strong></li>
            <li>Ticket Promedio: <strong>$${resumen.ticketPromedio}</strong></li>
        </ul>

        <hr>

        <!-- Sección de Búsqueda Avanzada -->
        <h2>Búsqueda Avanzada por Cliente y Monto Mínimo</h2>
        <form method="get" action="${pageContext.request.contextPath}/reportes/pedidos">
            <label>Cliente:</label>
            <input type="text" name="cliente" value="${clienteParam}">
            <label>Total Mínimo ($):</label>
            <input type="number" step="0.01" name="totalMinimo" value="${totalMinimoParam != null ? totalMinimoParam : 0}">
            <button type="submit">Buscar</button>
        </form>

        <c:if test="${not empty resultadosBusqueda}">
            <h3>Resultados de Búsqueda</h3>
            <table border="1">
                <tr>
                    <th>ID</th><th>Cliente</th><th>Producto</th><th>Cantidad</th><th>Total</th><th>Fecha</th>
                </tr>
                <c:forEach var="p" items="${resultadosBusqueda}">
                    <tr>
                        <td>${p.id}</td>
                        <td>${p.cliente}</td>
                        <td>${p.productoNombre}</td>
                        <td>${p.cantidad}</td>
                        <td>$${p.total}</td>
                        <td>${p.fecha}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>

        <hr>

        <!-- Sección de Clientes Frecuentes -->
        <h2>Clientes Frecuentes</h2>
        <table border="1">
            <tr>
                <th>Cliente</th><th>Total Pedidos</th><th>Total Unidades</th><th>Importe Acumulado</th><th>Ticket Promedio</th>
            </tr>
            <c:forEach var="cf" items="${clientesFrecuentes}">
                <tr>
                    <td>${cf.cliente}</td>
                    <td>${cf.totalPedidos}</td>
                    <td>${cf.totalUnidades}</td>
                    <td>$${cf.importeAcumulado}</td>
                    <td>$${cf.ticketPromedio}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>
