package com.reeb.estandar.application.port;

import com.reeb.estandar.domain.Pedido;

import java.math.BigDecimal;

/**
 * Contrato de infraestructura: cómo se obtiene el descuento aplicable a un pedido.
 */
public interface DescuentoPolicy {

    BigDecimal descuentoPara(Pedido pedido);
}
