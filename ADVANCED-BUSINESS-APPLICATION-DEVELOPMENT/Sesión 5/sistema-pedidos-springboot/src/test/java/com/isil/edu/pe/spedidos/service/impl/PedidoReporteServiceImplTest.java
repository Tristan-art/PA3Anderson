package com.isil.edu.pe.spedidos.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import com.isil.edu.pe.spedidos.repository.PedidoReporteJdbcRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;



class PedidoReporteServiceImplTest {

    private PedidoReporteServiceImpl pedidoReporteService;
    private PedidoReporteJdbcRepository jdbcRepositoryMock;

    @BeforeEach
    void setUp() {
        // Creamos un mock del repositorio JDBC para aislar la prueba unitaria del servicio
        jdbcRepositoryMock = mock(PedidoReporteJdbcRepository.class);
        pedidoReporteService = new PedidoReporteServiceImpl(jdbcRepositoryMock);
    }

    @Test
    @DisplayName("PA3 - Service: Debe lanzar IllegalArgumentException si minPedidos es menor a 1")
    void deberiaLanzarExcepcionCuandoMinPedidosEsInvalido() {
        // Arrange
        int minPedidosInvalido = 0;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> pedidoReporteService.listarClientesFrecuentes(minPedidosInvalido)
        );

        assertEquals(
                "El parámetro 'minPedidos' debe ser al menos 1.",
                exception.getMessage(),
                "El mensaje de la excepción debe coincidir con la validación de negocio"
        );
    }
}
