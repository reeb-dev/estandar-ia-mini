# estandar-ia-mini

Mini proyecto de demostración para la **Diplomatura en IA aplicada al desarrollo (BP4 / UTN)**.

Aplica el estándar de ingeniería (base FSWITCH) y las prácticas de uso de IA del entregable individual (Parte B).

**Autor:** Manuel Jesús Reeb

## Qué cumple y en qué parte

Mapa completo: [docs/CUMPLIMIENTO-ESTANDAR.md](docs/CUMPLIMIENTO-ESTANDAR.md).

### Ingeniería (FSWITCH) — resumen

| Cumple | Parte del proyecto |
| --- | --- |
| Capas separadas | `domain` · `application` · `infrastructure` · `presentation` |
| DI por constructor | `CalcularTotalPedidoService`, `PedidoController`, `VipDescuentoPolicy` |
| Contrato vs implementación | `DescuentoPolicy` → `VipDescuentoPolicy` |
| Unit tests + Mockito | `PedidoTest`, `CalcularTotalPedidoServiceTest` (`mvn test`) |
| Parámetros fuera del código | `application.properties` / `APP_DESCUENTO_VIP` / `.env.example` |
| Logs sin datos sensibles | `PedidoController` |
| SemVer + changelog | `pom.xml` + `CHANGELOG.md` |

### Uso de IA (Parte B) — resumen

| Cumple | Parte del proyecto |
| --- | --- |
| Contexto persistente del equipo/repo | `AGENTS.md` |
| Límites, 4 capas del pedido, pasos, debug, review | `docs/COMO-USAR-IA.md` |

## Qué hace

Calcula el total de un pedido con descuento VIP configurable.

```bash
curl -s -X POST http://localhost:8080/api/pedidos/total \
  -H 'Content-Type: application/json' \
  -d '{"lineas":[{"precioUnitario":100.00,"cantidad":2}],"clienteVip":true}'
```

Respuesta esperada (con 10 % VIP por defecto):

```json
{"bruto":200.00,"descuento":20.00,"total":180.00}
```

## Capas (FSWITCH + curso)

| Capa | Paquete | Responsabilidad |
| --- | --- | --- |
| Dominio | `...domain` | `Pedido`, `LineaPedido` |
| Aplicación | `...application` | `CalcularTotalPedidoService` + puerto `DescuentoPolicy` |
| Infraestructura | `...infrastructure` | `VipDescuentoPolicy`, wiring Spring |
| Presentación | `...presentation` | `PedidoController` (HTTP) |

- Inyección por **constructor** (sin `@Autowired` en campos).
- Dependencia de **contratos** (`DescuentoPolicy`), no de detalles.
- Secrets / parámetros vía `application.properties` / env (`APP_DESCUENTO_VIP`).
- Logs sin datos sensibles (solo cantidad de líneas y flag VIP).

## Cómo correr

Requisitos: JDK 21+, Maven 3.9+.

```bash
mvn test
mvn spring-boot:run
```

Opcional:

```bash
export APP_DESCUENTO_VIP=0.15
mvn spring-boot:run
```

## Cómo usamos la IA en este repo (Parte B)

Ver [AGENTS.md](AGENTS.md) y [docs/COMO-USAR-IA.md](docs/COMO-USAR-IA.md).

Resumen:

1. Un afluente a la vez (una capa / un fallo).
2. Pedido en 4 capas: contexto, objetivo, restricciones, formato.
3. Código nuevo: firma → implementación → tests → review humano.
4. Debugging: 3 hipótesis sin código primero.
5. Nunca pegar credenciales ni datos de clientes reales.

## Versionado

SemVer — ver [CHANGELOG.md](CHANGELOG.md). Versión actual: **1.0.1**.

## Licencia

MIT (material educativo).
