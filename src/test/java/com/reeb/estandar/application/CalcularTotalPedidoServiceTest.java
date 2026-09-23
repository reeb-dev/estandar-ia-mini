package com.reeb.estandar.application;

import com.reeb.estandar.application.port.DescuentoPolicy;
import com.reeb.estandar.domain.LineaPedido;
import com.reeb.estandar.domain.Pedido;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalcularTotalPedidoServiceTest {

    @Mock
    private DescuentoPolicy descuentoPolicy;

    @InjectMocks
    private CalcularTotalPedidoService service;

    @Test
    void calcularAplicaDescuentoAlBruto() {
        Pedido pedido = new Pedido(List.of(new LineaPedido(new BigDecimal("100.00"), 1)), true);
        when(descuentoPolicy.descuentoPara(any(Pedido.class))).thenReturn(new BigDecimal("10.00"));

        CalcularTotalPedidoService.ResultadoTotal resultado = service.calcular(pedido);

        assertEquals(new BigDecimal("100.00"), resultado.bruto());
        assertEquals(new BigDecimal("10.00"), resultado.descuento());
        assertEquals(new BigDecimal("90.00"), resultado.total());
    }

    @Test
    void descuentoNoPuedeSuperarElBruto() {
        Pedido pedido = new Pedido(List.of(new LineaPedido(new BigDecimal("50.00"), 1)), true);
        when(descuentoPolicy.descuentoPara(any(Pedido.class))).thenReturn(new BigDecimal("80.00"));

        CalcularTotalPedidoService.ResultadoTotal resultado = service.calcular(pedido);

        assertEquals(new BigDecimal("50.00"), resultado.descuento());
        assertEquals(new BigDecimal("0.00"), resultado.total());
    }
}
