package com.reeb.estandar.infrastructure;

import com.reeb.estandar.application.port.DescuentoPolicy;
import com.reeb.estandar.domain.Pedido;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Política de descuento VIP leída desde configuración (no hardcodeada como secreto de negocio en código).
 */
@Component
public class VipDescuentoPolicy implements DescuentoPolicy {

    private final BigDecimal porcentajeVip;

    public VipDescuentoPolicy(@Value("${app.descuento.vip-porcentaje:0.10}") BigDecimal porcentajeVip) {
        if (porcentajeVip.signum() < 0 || porcentajeVip.compareTo(BigDecimal.ONE) > 0) {
            throw new IllegalArgumentException("app.descuento.vip-porcentaje debe estar entre 0 y 1");
        }
        this.porcentajeVip = porcentajeVip;
    }

    @Override
    public BigDecimal descuentoPara(Pedido pedido) {
        if (!pedido.isClienteVip()) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }
        return pedido.bruto()
                .multiply(porcentajeVip)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
