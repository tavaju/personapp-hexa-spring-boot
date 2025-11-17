# Guía de Tests Automatizados CRUD

## Script de Pruebas Automatizadas

Se ha creado un script que automáticamente:
1. ✅ Verifica que los contenedores Docker estén corriendo
2. ✅ Arranca la aplicación CLI
3. ✅ Ejecuta todas las operaciones CRUD para cada entidad
4. ✅ Analiza los resultados
5. ✅ Reporta qué tests pasaron y cuáles fallaron

## Ejecutar los Tests

```bash
./scripts/test_crud_automated.sh
```

## Tests que se Ejecutan

### Profesiones (3 tests)
1. ✅ Crear Profesión (ID: 10, Nombre: "Ingeniero de Sistemas")
2. ✅ Buscar Profesión por ID
3. ✅ Eliminar Profesión

### Teléfonos (4 tests)
1. ✅ Crear Teléfono (Número: 3001234567, Operador: Claro, Dueño: 123456789)
2. ✅ Buscar Teléfono por Número
3. ✅ Editar Teléfono (Cambiar operador a Movistar)
4. ✅ Eliminar Teléfono

### Estudios (6 tests)
1. ✅ Crear Estudio (Persona: 123456789, Profesión: 1, Fecha: 2020-06-15, Universidad: Javeriana)
2. ✅ Buscar Estudio por ID (Persona + Profesión)
3. ✅ Buscar Estudios por Persona
4. ✅ Buscar Estudios por Profesión
5. ✅ Editar Estudio (Cambiar universidad a Nacional)
6. ✅ Eliminar Estudio

**Total: 13 tests automatizados**

## Requisitos

- Docker y Docker Compose instalados
- Contenedores de MariaDB y MongoDB corriendo
- Datos iniciales cargados en las bases de datos

## Salida del Script

El script muestra:
- ✅ Tests que pasaron (en verde)
- ❌ Tests que fallaron (en rojo)
- Resumen final con estadísticas
- Ubicación del archivo de salida completo

## Ejemplo de Salida

```
==========================================
RESULTADOS DE LAS PRUEBAS
==========================================

✓ Test pasado: Crear Profesión (ID: 10)
✓ Test pasado: Buscar Profesión por ID
✓ Test pasado: Eliminar Profesión
✓ Test pasado: Crear Teléfono (3001234567)
✓ Test pasado: Buscar Teléfono por Número
✓ Test pasado: Editar Teléfono
✓ Test pasado: Eliminar Teléfono
✓ Test pasado: Crear Estudio (Persona: 123456789, Profesión: 1)
✓ Test pasado: Buscar Estudio por ID
✓ Test pasado: Buscar Estudios por Persona
✓ Test pasado: Buscar Estudios por Profesión
✓ Test pasado: Editar Estudio
✓ Test pasado: Eliminar Estudio

==========================================
RESUMEN FINAL
==========================================
Total de tests ejecutados: 13
Tests pasados: 13
Tests fallidos: 0
Porcentaje de éxito: 100%

╔════════════════════════════════════╗
║  ¡TODOS LOS TESTS PASARON! ✓       ║
╚════════════════════════════════════╝
```

## Notas

- El script tiene un timeout de 5 minutos
- Si algún test falla, revisa el archivo de salida completo
- Los datos de prueba se crean y eliminan durante la ejecución
- Asegúrate de que existan personas y profesiones en la BD antes de probar teléfonos y estudios

