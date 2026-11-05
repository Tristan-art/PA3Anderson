package com.isil.edu.pe.spedidos.controller;


import com.isil.edu.pe.spedidos.entity.Pedido;
import com.isil.edu.pe.spedidos.repository.projection.ClienteFrecuenteProjection;
import com.isil.edu.pe.spedidos.repository.projection.ResumenPedidosProjection;
import com.isil.edu.pe.spedidos.service.PedidoService;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PedidoReporteWebController {

    private final PedidoService pedidoService;

    public PedidoReporteWebController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping("/reportes/pedidos")
    public String verReportesPedidos(
            @RequestParam(required = false) String cliente,
            @RequestParam(required = false, defaultValue = "0") BigDecimal totalMinimo,
            @RequestParam(required = false, defaultValue = "1") int minPedidos,
            Model model) {

        // Cargar resumen general
        ResumenPedidosProjection resumen = pedidoService.obtenerResumen();
        model.addAttribute("resumen", resumen);

        // Cargar clientes frecuentes
        List<ClienteFrecuenteProjection> clientesFrecuentes = pedidoService.listarClientesFrecuentes(minPedidos);
        model.addAttribute("clientesFrecuentes", clientesFrecuentes);
        model.addAttribute("minPedidosParam", minPedidos);

        // Cargar búsqueda avanzada si se envió el parámetro cliente
        if (cliente != null && !cliente.trim().isEmpty()) {
            List<Pedido> resultadosBusqueda = pedidoService.buscarPedidosPorClienteYTotalMinimo(cliente, totalMinimo);
            model.addAttribute("resultadosBusqueda", resultadosBusqueda);
            model.addAttribute("clienteParam", cliente);
            model.addAttribute("totalMinimoParam", totalMinimo);
        }

        return "reportes-pedidos";
    }
}
