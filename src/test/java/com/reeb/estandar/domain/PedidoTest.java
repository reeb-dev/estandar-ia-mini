package com.reeb.estandar.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PedidoTest {

    @Test
    void brutoSumaSubtotalesDeLineas() {
        Pedido pedido = new Pedido(List.of(
                new LineaPedido(new BigDecimal("10.00"), 2),
                new LineaPedido(new BigDecimal("5.50"), 1)
        ), false);

        assertEquals(new BigDecimal("25.50"), pedido.bruto());
    }

    @Test
    void pedidoSinLineasEsInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new Pedido(List.of(), false));
    }

    @Test
    void cantidadCeroEsInvalida() {
        assertThrows(IllegalArgumentException.class,
                () -> new LineaPedido(new BigDecimal("10.00"), 0));
    }
}
