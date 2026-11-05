package com.isil.edu.pe.spedidos.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.isil.edu.pe.spedidos.entity.Pedido;
import com.isil.edu.pe.spedidos.repository.projection.ClienteFrecuenteProjection;
import com.isil.edu.pe.spedidos.repository.projection.ProductoVendidoProjection;
import com.isil.edu.pe.spedidos.repository.projection.ResumenPedidosProjection;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class PedidoReporteJdbcRepositoryTest {

  @Autowired
  private PedidoReporteJdbcRepository repository;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @BeforeEach
  void setUp() {
    jdbcTemplate.update("DELETE FROM pedido");

    insertarPedido(
        "Ana Torres", "LAP-001", "Laptop",
        new BigDecimal("2500.00"), 2, new BigDecimal("5000.00")
    );

    insertarPedido(
        "Carlos Perez", "MOU-001", "Mouse",
        new BigDecimal("50.00"), 3, new BigDecimal("150.00")
    );

    insertarPedido(
        "Ana Torres", "MOU-001", "Mouse",
        new BigDecimal("50.00"), 2, new BigDecimal("100.00")
    );
  }

  @Test
  @DisplayName("Return consolidated summary from PedidoReporteJdbcRepository when orders exist")
  void returnConsolidatedSummaryFromPedidoReporteJdbcRepositoryWhenOrdersExist() {
    // Arrange: los pedidos se insertan en setUp.

    // Act
    ResumenPedidosProjection resumen = repository.obtenerResumen();

    // Assert
    assertEquals(3L, resumen.totalPedidos());
    assertEquals(7L, resumen.totalUnidades());
    assertEquals(0, new BigDecimal("5250.00").compareTo(resumen.importeAcumulado()));
  }

  @Test
  @DisplayName("Return matching orders from PedidoReporteJdbcRepository when customer name is provided")
  void returnMatchingOrdersFromPedidoReporteJdbcRepositoryWhenCustomerNameIsProvided() {
    // Arrange
    String cliente = "Ana";

    // Act
    List<Pedido> pedidos = repository.buscarPorCliente(cliente);

    // Assert
    assertEquals(2, pedidos.size());
    assertTrue(pedidos.stream().allMatch(pedido -> "Ana Torres".equals(pedido.getCliente())));
  }

  @Test
  @DisplayName("Return products ordered by sold units from PedidoReporteJdbcRepository")
  void returnProductsOrderedBySoldUnitsFromPedidoReporteJdbcRepository() {
    // Arrange: Mouse acumula 5 unidades y Laptop 2 unidades.

    // Act
    List<ProductoVendidoProjection> productos = repository.listarProductosMasVendidos();

    // Assert
    assertEquals(2, productos.size());
    assertEquals("MOU-001", productos.get(0).productoCodigo());
    assertEquals(5L, productos.get(0).totalUnidades());
    assertEquals("LAP-001", productos.get(1).productoCodigo());
  }
  /// MODIFICACION MIA PARA EL PROJECTO DE PA3
    @Test
    @DisplayName("PA3 - Repositorio JDBC: Debe retornar únicamente los pedidos que cumplan con cliente y total mínimo")
    void deberiaBuscarPedidosPorClienteYTotalMinimoCorrectamente() {
        // Arrange
        String clienteBusqueda = "Ana Torres";
        BigDecimal totalMinimo = new BigDecimal("200.00");

        // Act
        List<Pedido> resultados = repository.buscarPedidosPorClienteYTotalMinimo(clienteBusqueda, totalMinimo);

        // Assert
        assertEquals(1, resultados.size(), "Debe retornar exactamente un pedido que cumpla con el filtro de cliente y monto mínimo");
        assertEquals("Ana Torres", resultados.get(0).getCliente());
        assertTrue(resultados.get(0).getTotal().compareTo(totalMinimo) >= 0, "El total debe ser mayor o igual al mínimo");
    }

    /// Modificacion Mia
    @Test
    @DisplayName("PA3 - Repositorio JDBC: Debe listar clientes frecuentes según el umbral mínimo de pedidos")
    void deberiaListarClientesFrecuentesCorrectamente() {
        // Arrange: En setUp(), "Ana Torres" tiene 2 pedidos y "Carlos Perez" tiene 1 pedido.
        int minPedidos = 2;

        // Act
        List<ClienteFrecuenteProjection> clientesFrecuentes = repository.listarClientesFrecuentes(minPedidos);

        // Assert
        assertEquals(1, clientesFrecuentes.size(), "Debe retornar únicamente a los clientes que tengan al menos 2 pedidos");
        assertEquals("Ana Torres", clientesFrecuentes.get(0).cliente());
        assertEquals(2L, clientesFrecuentes.get(0).totalPedidos());
    }
    /// MODIFICACION MIA PARA EL PROJECTO DE PA3
  private void insertarPedido(
      String cliente,
      String productoCodigo,
      String productoNombre,
      BigDecimal precioUnitario,
      int cantidad,
      BigDecimal total) {
    jdbcTemplate.update(
        """
        INSERT INTO pedido (
          cliente,
          producto_codigo,
          producto_nombre,
          precio_unitario,
          cantidad,
          total,
          fecha
        ) VALUES (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)
        """,
        cliente,
        productoCodigo,
        productoNombre,
        precioUnitario,
        cantidad,
        total
    );
  }
}
