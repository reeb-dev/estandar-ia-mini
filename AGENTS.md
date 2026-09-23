# AGENTS.md — contexto para asistentes de IA

Este archivo es el contexto persistente del repo (recomendación clase 4 / plantilla diplomatura).

## Objetivo del proyecto

Demo mínima de cálculo de total de pedido con descuento VIP, organizada por capas, para ilustrar el estándar de trabajo individual (ingeniería + uso de IA).

Mapa de cumplimiento: [docs/CUMPLIMIENTO-ESTANDAR.md](docs/CUMPLIMIENTO-ESTANDAR.md).

## Stack

- Java 21, Spring Boot 3.4, Maven
- Tests: JUnit 5 + Mockito (`@Mock` / `@InjectMocks`)

## Reglas de arquitectura

1. Nombrar siempre la **capa** al pedir cambios (`domain` / `application` / `infrastructure` / `presentation`).
2. No mezclar lógica de descuento en el controller.
3. Nuevas políticas de descuento: implementar `DescuentoPolicy`, no alargar el service con `if`s de infraestructura.
4. Constructor injection únicamente.
5. Un PR / commit = un cambio lógico (`feat:`, `fix:`, `test:`, `docs:`, `refactor:`).

## Qué no hacer

- No inventar reglas de negocio (descuentos, impuestos, monedas) que no estén en código o docs.
- No commits ni push sin pedido explícito del autor.
- No pegar secretos, tokens ni datos de clientes.

## Pedido sugerido a la IA (plantilla)

```
Contexto: capa application, CalcularTotalPedidoService y DescuentoPolicy.
Objetivo: …
Restricciones: sin tocar presentation; tests verdes; constructor injection.
Formato: diff mínimo + test que falle/pase.
```
