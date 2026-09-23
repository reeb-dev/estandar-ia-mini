# Cómo usar la IA en este proyecto

Prácticas alineadas a la plantilla del curso (clases 2–5) y al estándar individual.

## Herramientas y límites

- Editor/agente con contexto del workspace (p. ej. Cursor) + este `AGENTS.md`.
- Motor local opcional para trabajo sensible.
- No pasar credenciales ni datos reales de clientes.

## Código nuevo

1. Acordar firma / puerto (`DescuentoPolicy` o método del service).
2. Implementar en la capa nombrada.
3. Tests en el mismo cambio.
4. Review humano: capa, imports, reglas inventadas, alcance.

## Refactor

Entender → Detectar → Blindar con tests → Un cambio → Validar comportamiento externo.  
No mezclar bugfix y refactor en el mismo commit.

## Debugging

Contexto mínimo: log, entorno, esperado. Pedir 3 hipótesis **sin código**. Verificar. Luego fix.

## Review (ISO 25010)

Nombrar si el cambio degradó mantenibilidad, seguridad o claridad — no solo “no me gusta”.
