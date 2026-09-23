package com.reeb.estandar.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

/**
 * Agregado de dominio: agrupa líneas y expone el bruto sin descuentos.
 */
public final class Pedido {

    private final List<LineaPedido> lineas;
    private final boolean clienteVip;

    public Pedido(List<LineaPedido> lineas, boolean clienteVip) {
        Objects.requireNonNull(lineas, "lineas");
        if (lineas.isEmpty()) {
            throw new IllegalArgumentException("El pedido debe tener al menos una línea");
        }
        this.lineas = List.copyOf(lineas);
        this.clienteVip = clienteVip;
    }

    public List<LineaPedido> getLineas() {
        return lineas;
    }

    public boolean isClienteVip() {
        return clienteVip;
    }

    public BigDecimal bruto() {
        return lineas.stream()
                .map(LineaPedido::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
