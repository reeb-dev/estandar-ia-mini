package com.reeb.estandar.application;

import com.reeb.estandar.application.port.DescuentoPolicy;
import com.reeb.estandar.domain.Pedido;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Caso de uso: calcular total = bruto − descuento.
 * Dependencias por constructor (sin field injection).
 */
public class CalcularTotalPedidoService {

    private final DescuentoPolicy descuentoPolicy;

    public CalcularTotalPedidoService(DescuentoPolicy descuentoPolicy) {
        this.descuentoPolicy = Objects.requireNonNull(descuentoPolicy, "descuentoPolicy");
    }

    public ResultadoTotal calcular(Pedido pedido) {
        Objects.requireNonNull(pedido, "pedido");
        BigDecimal bruto = pedido.bruto();
        BigDecimal descuento = descuentoPolicy.descuentoPara(pedido)
                .setScale(2, RoundingMode.HALF_UP);
        if (descuento.signum() < 0) {
            throw new IllegalStateException("El descuento no puede ser negativo");
        }
        if (descuento.compareTo(bruto) > 0) {
            descuento = bruto;
        }
        BigDecimal total = bruto.subtract(descuento).setScale(2, RoundingMode.HALF_UP);
        return new ResultadoTotal(bruto, descuento, total);
    }

    public record ResultadoTotal(BigDecimal bruto, BigDecimal descuento, BigDecimal total) {
    }
}
