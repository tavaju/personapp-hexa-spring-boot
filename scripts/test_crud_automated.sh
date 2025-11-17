#!/bin/bash

# Script automatizado de pruebas CRUD para PersonApp
# Este script arranca la aplicación y ejecuta pruebas automáticas

set -e

echo "=========================================="
echo "TEST AUTOMATIZADO CRUD - PersonApp"
echo "=========================================="
echo ""

# Colores para output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Contadores de tests
TESTS_PASSED=0
TESTS_FAILED=0
TOTAL_TESTS=0

# Función para contar tests
increment_test() {
    TOTAL_TESTS=$((TOTAL_TESTS + 1))
}

# Función para marcar test como pasado
test_passed() {
    TESTS_PASSED=$((TESTS_PASSED + 1))
    echo -e "${GREEN}✓ Test pasado: $1${NC}"
}

# Función para marcar test como fallido
test_failed() {
    TESTS_FAILED=$((TESTS_FAILED + 1))
    echo -e "${RED}✗ Test fallido: $1${NC}"
}

# Verificar que docker compose esté disponible
if ! command -v docker &> /dev/null; then
    echo -e "${RED}Error: Docker no está instalado o no está en el PATH${NC}"
    exit 1
fi

# Verificar que los contenedores estén corriendo
echo -e "${BLUE}Verificando contenedores Docker...${NC}"
if ! docker ps | grep -q mariadb; then
    echo "Iniciando contenedores Docker..."
    docker compose up -d
    echo "Esperando a que las bases de datos estén listas..."
    sleep 15
fi

echo ""
echo "=========================================="
echo "INICIANDO PRUEBAS AUTOMATIZADAS"
echo "=========================================="
echo ""

# Crear archivo de entrada automatizado con todas las operaciones CRUD
# Formato: cada línea es una entrada que el usuario haría
INPUT_FILE=$(mktemp)
cat > "$INPUT_FILE" << 'EOF'
2
1
2
10
Ingeniero de Sistemas
Ingeniería de sistemas y computación
5
10
4
10
0
2
1
2
3001234567
Claro
123456789
5
3001234567
3
3001234567
Movistar
123456789
4
3001234567
0
4
1
2
123456789
1
2020-06-15
Pontificia Universidad Javeriana
5
123456789
1
6
123456789
7
1
3
123456789
1
2021-06-15
Universidad Nacional
4
123456789
1
0
0
0
EOF

echo -e "${BLUE}Ejecutando aplicación CLI con pruebas automatizadas...${NC}"
echo -e "${YELLOW}Esto puede tomar varios minutos...${NC}"
echo ""

# Ejecutar la aplicación con entrada automatizada y capturar salida
OUTPUT_FILE=$(mktemp)
TIMEOUT=300

# Mostrar progreso
echo -e "${BLUE}Progreso:${NC}"
echo "  - Iniciando aplicación..."
echo "  - Ejecutando pruebas CRUD..."
echo ""

# Ejecutar con timeout y capturar salida
# Usar unbuffered para ver salida en tiempo real si es necesario
if timeout $TIMEOUT docker compose run --rm personapp-cli-service < "$INPUT_FILE" > "$OUTPUT_FILE" 2>&1; then
    echo -e "${GREEN}✓ Aplicación ejecutada exitosamente${NC}"
    echo ""
    OUTPUT=$(cat "$OUTPUT_FILE")
    
    echo "=========================================="
    echo "RESULTADOS DE LAS PRUEBAS"
    echo "=========================================="
    echo ""
    
    # Analizar salida para determinar qué tests pasaron
    # Tests de Persona
    increment_test
    if echo "$OUTPUT" | grep -qi "Persona creada exitosamente\|999999999.*Juan"; then
        test_passed "Crear Persona (CC: 999999999)"
    else
        test_failed "Crear Persona (CC: 999999999)"
    fi
    
    increment_test
    if echo "$OUTPUT" | grep -qi "999999999.*Juan\|Persona.*999999999"; then
        test_passed "Buscar Persona por Cédula"
    else
        test_failed "Buscar Persona por Cédula"
    fi
    
    increment_test
    if echo "$OUTPUT" | grep -qi "Persona actualizada exitosamente\|Juan Carlos"; then
        test_passed "Editar Persona"
    else
        test_failed "Editar Persona"
    fi
    
    increment_test
    if echo "$OUTPUT" | grep -qi "Persona eliminada exitosamente"; then
        test_passed "Eliminar Persona"
    else
        test_failed "Eliminar Persona"
    fi
    
    # Tests de Profesión
    increment_test
    if echo "$OUTPUT" | grep -qi "Profesión creada exitosamente\|Ingeniero de Sistemas"; then
        test_passed "Crear Profesión (ID: 10)"
    else
        test_failed "Crear Profesión (ID: 10)"
    fi
    
    # Test 5: Buscar Profesión por ID
    increment_test
    if echo "$OUTPUT" | grep -qi "Profesión.*10\|Ingeniero.*10"; then
        test_passed "Buscar Profesión por ID"
    else
        test_failed "Buscar Profesión por ID"
    fi
    
    # Test 6: Eliminar Profesión
    increment_test
    if echo "$OUTPUT" | grep -qi "Profesión eliminada exitosamente"; then
        test_passed "Eliminar Profesión"
    else
        test_failed "Eliminar Profesión"
    fi
    
    # Test 7: Crear Teléfono
    increment_test
    if echo "$OUTPUT" | grep -qi "Teléfono creado exitosamente\|3001234567.*Claro"; then
        test_passed "Crear Teléfono (3001234567)"
    else
        test_failed "Crear Teléfono (3001234567)"
    fi
    
    # Test 8: Buscar Teléfono por Número
    increment_test
    if echo "$OUTPUT" | grep -qi "3001234567.*Claro\|Teléfono.*3001234567"; then
        test_passed "Buscar Teléfono por Número"
    else
        test_failed "Buscar Teléfono por Número"
    fi
    
    # Test 9: Editar Teléfono
    increment_test
    if echo "$OUTPUT" | grep -qi "Teléfono actualizado exitosamente\|3001234567.*Movistar"; then
        test_passed "Editar Teléfono"
    else
        test_failed "Editar Teléfono"
    fi
    
    # Test 10: Eliminar Teléfono
    increment_test
    if echo "$OUTPUT" | grep -qi "Teléfono eliminado exitosamente"; then
        test_passed "Eliminar Teléfono"
    else
        test_failed "Eliminar Teléfono"
    fi
    
    # Test 11: Crear Estudio
    increment_test
    if echo "$OUTPUT" | grep -qi "Estudio creado exitosamente\|Pontificia Universidad Javeriana"; then
        test_passed "Crear Estudio (Persona: 123456789, Profesión: 1)"
    else
        test_failed "Crear Estudio (Persona: 123456789, Profesión: 1)"
    fi
    
    # Test 12: Buscar Estudio por ID
    increment_test
    if echo "$OUTPUT" | grep -qi "123456789.*1\|Estudio.*123456789"; then
        test_passed "Buscar Estudio por ID"
    else
        test_failed "Buscar Estudio por ID"
    fi
    
    # Test 13: Buscar Estudios por Persona
    increment_test
    if echo "$OUTPUT" | grep -qi "123456789"; then
        test_passed "Buscar Estudios por Persona"
    else
        test_failed "Buscar Estudios por Persona"
    fi
    
    # Test 14: Buscar Estudios por Profesión
    increment_test
    if echo "$OUTPUT" | grep -qi "Profesión.*1\|estudios.*profesión"; then
        test_passed "Buscar Estudios por Profesión"
    else
        test_failed "Buscar Estudios por Profesión"
    fi
    
    # Test 15: Editar Estudio
    increment_test
    if echo "$OUTPUT" | grep -qi "Estudio actualizado exitosamente\|Universidad Nacional"; then
        test_passed "Editar Estudio"
    else
        test_failed "Editar Estudio"
    fi
    
    # Test 16: Eliminar Estudio
    increment_test
    if echo "$OUTPUT" | grep -qi "Estudio eliminado exitosamente"; then
        test_passed "Eliminar Estudio"
    else
        test_failed "Eliminar Estudio"
    fi
    
    echo ""
    echo "=========================================="
    echo "RESUMEN FINAL"
    echo "=========================================="
    echo -e "Total de tests ejecutados: ${TOTAL_TESTS}"
    echo -e "${GREEN}Tests pasados: ${TESTS_PASSED}${NC}"
    echo -e "${RED}Tests fallidos: ${TESTS_FAILED}${NC}"
    echo ""
    
    # Calcular porcentaje
    if [ $TOTAL_TESTS -gt 0 ]; then
        PERCENTAGE=$((TESTS_PASSED * 100 / TOTAL_TESTS))
        echo -e "Porcentaje de éxito: ${PERCENTAGE}%"
    fi
    
    echo ""
    
    if [ $TESTS_FAILED -eq 0 ]; then
        echo -e "${GREEN}╔════════════════════════════════════╗${NC}"
        echo -e "${GREEN}║  ¡TODOS LOS TESTS PASARON! ✓       ║${NC}"
        echo -e "${GREEN}╚════════════════════════════════════╝${NC}"
        EXIT_CODE=0
    else
        echo -e "${RED}╔════════════════════════════════════╗${NC}"
        echo -e "${RED}║  ALGUNOS TESTS FALLARON ✗          ║${NC}"
        echo -e "${RED}╚════════════════════════════════════╝${NC}"
        EXIT_CODE=1
    fi
    
    echo ""
    echo -e "${YELLOW}Salida completa guardada en: $OUTPUT_FILE${NC}"
    echo ""
    echo "Para ver la salida completa, ejecuta:"
    echo "  cat $OUTPUT_FILE"
    echo ""
    
else
    TIMEOUT_EXIT=$?
    if [ $TIMEOUT_EXIT -eq 124 ]; then
        echo -e "${RED}Error: La aplicación excedió el tiempo límite (${TIMEOUT}s)${NC}"
    else
        echo -e "${RED}Error: La aplicación falló con código de salida: $TIMEOUT_EXIT${NC}"
    fi
    echo "Salida guardada en: $OUTPUT_FILE"
    echo ""
    echo "Últimas líneas de la salida:"
    tail -20 "$OUTPUT_FILE"
    EXIT_CODE=1
fi

# Limpiar archivos temporales
rm -f "$INPUT_FILE"

exit $EXIT_CODE
