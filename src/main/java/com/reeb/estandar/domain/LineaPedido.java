package com.reeb.estandar.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Línea de un pedido. Responsabilidad única: validar y calcular su subtotal.
 */
public final class LineaPedido {

    private final BigDecimal precioUnitario;
    private final int cantidad;

    public LineaPedido(BigDecimal precioUnitario, int cantidad) {
        Objects.requireNonNull(precioUnitario, "precioUnitario");
        if (precioUnitario.signum() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }
        this.precioUnitario = precioUnitario.setScale(2, RoundingMode.HALF_UP);
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public BigDecimal subtotal() {
        return precioUnitario.multiply(BigDecimal.valueOf(cantidad))
                .setScale(2, RoundingMode.HALF_UP);
    }
}
