package com.reeb.estandar.presentation;

import com.reeb.estandar.application.CalcularTotalPedidoService;
import com.reeb.estandar.domain.LineaPedido;
import com.reeb.estandar.domain.Pedido;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * Capa de presentación: traduce HTTP ↔ dominio. Sin lógica de descuento.
 */
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private static final Logger log = LoggerFactory.getLogger(PedidoController.class);

    private final CalcularTotalPedidoService calcularTotalPedidoService;

    public PedidoController(CalcularTotalPedidoService calcularTotalPedidoService) {
        this.calcularTotalPedidoService = calcularTotalPedidoService;
    }

    @PostMapping("/total")
    public TotalResponse calcularTotal(@Valid @RequestBody CalcularTotalRequest request) {
        List<LineaPedido> lineas = request.lineas().stream()
                .map(l -> new LineaPedido(l.precioUnitario(), l.cantidad()))
                .toList();
        Pedido pedido = new Pedido(lineas, request.clienteVip());
        CalcularTotalPedidoService.ResultadoTotal resultado = calcularTotalPedidoService.calcular(pedido);
        log.info("Total calculado para pedido con {} líneas (vip={})", lineas.size(), request.clienteVip());
        return new TotalResponse(resultado.bruto(), resultado.descuento(), resultado.total());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorBody> handleBadRequest(IllegalArgumentException ex) {
        log.warn("Pedido inválido: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(new ErrorBody(ex.getMessage()));
    }

    public record CalcularTotalRequest(
            @NotEmpty List<@Valid LineaRequest> lineas,
            boolean clienteVip
    ) {
    }

    public record LineaRequest(
            @NotNull @DecimalMin("0.00") BigDecimal precioUnitario,
            @Min(1) int cantidad
    ) {
    }

    public record TotalResponse(BigDecimal bruto, BigDecimal descuento, BigDecimal total) {
    }

    public record ErrorBody(String mensaje) {
    }
}
