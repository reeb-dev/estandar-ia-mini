package com.reeb.estandar.infrastructure;

import com.reeb.estandar.application.CalcularTotalPedidoService;
import com.reeb.estandar.application.port.DescuentoPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Wiring explícito: el caso de uso recibe el puerto por constructor.
 */
@Configuration
public class ApplicationConfig {

    @Bean
    public CalcularTotalPedidoService calcularTotalPedidoService(DescuentoPolicy descuentoPolicy) {
        return new CalcularTotalPedidoService(descuentoPolicy);
    }
}
