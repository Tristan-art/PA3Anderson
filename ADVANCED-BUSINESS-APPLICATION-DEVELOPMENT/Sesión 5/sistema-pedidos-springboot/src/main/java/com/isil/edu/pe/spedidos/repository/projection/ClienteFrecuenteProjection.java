package com.isil.edu.pe.spedidos.repository.projection;



import java.math.BigDecimal;

public record ClienteFrecuenteProjection(
        String cliente,
        Long totalPedidos,
        Long totalUnidades,
        BigDecimal importeAcumulado,
        BigDecimal ticketPromedio
) {
}
