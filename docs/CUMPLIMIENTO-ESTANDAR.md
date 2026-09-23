# Cumplimiento del estándar → dónde está en el proyecto

Trazabilidad entre el **estándar FSWITCH** (ingeniería), la **plantilla / Parte B** (uso de IA) y este repo.

## Parte A — Ingeniería (FSWITCH)

| Criterio del estándar | ¿Cumple? | Dónde en el proyecto |
| --- | --- | --- |
| Simplicidad / una responsabilidad por clase | Sí | `domain/LineaPedido`, `domain/Pedido`, `application/CalcularTotalPedidoService`, `presentation/PedidoController` |
| Separación de capas (presentación, aplicación, dominio, infraestructura) | Sí | Paquetes `presentation` / `application` / `domain` / `infrastructure` |
| Métodos acotados, sin mezclar capas | Sí | El controller solo traduce HTTP; el descuento vive en `VipDescuentoPolicy` |
| Nombres descriptivos (camelCase / PascalCase) | Sí | Todo el código Java del módulo |
| Inyección por **constructor** (no field `@Autowired`) | Sí | `CalcularTotalPedidoService`, `PedidoController`, `VipDescuentoPolicy`, `ApplicationConfig` |
| Depender de **contratos**, no de detalles | Sí | Puerto `application/port/DescuentoPolicy` + implementación en `infrastructure` |
| Unit tests rápidos e independientes | Sí | `src/test/.../domain/PedidoTest`, `.../application/CalcularTotalPedidoServiceTest` |
| Asserts claros + mocks (`@Mock` / `@InjectMocks`) | Sí | `CalcularTotalPedidoServiceTest` |
| Credenciales / parámetros fuera del código fuente | Sí | `application.properties` + env `APP_DESCUENTO_VIP`; plantilla `.env.example` |
| No loguear información sensible | Sí | `PedidoController`: solo cantidad de líneas y flag VIP |
| Errores logueados en un punto útil (WARN) | Sí | `handleBadRequest` en `PedidoController` |
| Rama `main` estable + commits claros con prefijo | Sí | Historial Git (`feat:`, docs en commits siguientes) |
| SemVer + changelog | Sí | `pom.xml` versión `1.0.0` / `1.0.1` + `CHANGELOG.md` |
| Gitflow completo (`develop` / `release` / `hotfix`) | Parcial / no | Repo educativo en `main` + cambios pequeños; no se fuerza Gitflow |
| Meta fija 80 % coverage en todo | Todavía no | Hay tests de dominio y aplicación; JaCoCo no es gate obligatorio |

## Parte B — Cómo usamos la IA (plantilla del curso)

| Criterio (clases 2–5) | ¿Cumple? | Dónde en el proyecto |
| --- | --- | --- |
| Contexto persistente para el asistente | Sí | [`AGENTS.md`](../AGENTS.md) |
| Herramientas, límites y “qué nunca pegar” | Sí | [`docs/COMO-USAR-IA.md`](COMO-USAR-IA.md) § Herramientas |
| Pedido en 4 capas (contexto / objetivo / restricciones / formato) | Sí | Plantilla de prompt en `AGENTS.md` |
| Nombrar la **capa** al pedir código | Sí | `AGENTS.md` reglas 1–3 |
| Código nuevo por pasos (firma → impl → tests → review) | Sí | `docs/COMO-USAR-IA.md` § Código nuevo |
| Refactor: entender → blindar → un cambio | Sí | `docs/COMO-USAR-IA.md` § Refactor |
| Debugging: 3 hipótesis sin código primero | Sí | `docs/COMO-USAR-IA.md` § Debugging |
| Review con checklist (capa, imports, reglas inventadas) + ISO 25010 | Sí | `docs/COMO-USAR-IA.md` § Review |
| Un afluente a la vez / no mezclar repos | Sí | `AGENTS.md` + README |

## API funcional (smoke)

| Comportamiento | Dónde |
| --- | --- |
| `POST /api/pedidos/total` | `presentation/PedidoController` |
| Descuento VIP configurable | `infrastructure/VipDescuentoPolicy` + `app.descuento.vip-porcentaje` |
| Validación de pedido vacío / líneas inválidas | Dominio + Bean Validation en el request |

Verificar:

```bash
mvn test
mvn spring-boot:run
```
