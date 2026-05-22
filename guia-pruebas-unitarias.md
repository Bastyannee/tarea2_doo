# Guía de Pruebas Unitarias — Tarea 2 (JUnit 5 y Maven)

Este documento establece las directrices, la estructura y las buenas prácticas para el diseño, implementación y ejecución de pruebas unitarias en el proyecto. El objetivo es asegurar la calidad del código (QA), cumplir rigurosamente con los criterios de evaluación del KPI 2.1 y evitar errores comunes de infraestructura.

---

# 1. Estructura de Directorios

Para que el compilador de Java y el motor de Maven reconozcan correctamente las pruebas, la estructura de carpetas del entorno de testing debe replicar exactamente la estructura del código de producción (`src/main`).

## Regla de Oro del Paquete

Toda clase de prueba debe residir estrictamente en:

```text
src/test/java/cl/udec/cs/
```

Si falta alguna carpeta intermedia (por ejemplo `cl` o `udec`), el compilador lanzará errores del tipo:

```text
package does not exist
```

## Nomenclatura de Archivos

El archivo de prueba debe llevar el mismo nombre de la clase a probar seguido del sufijo `Test`.

### Ejemplo

Si la clase es:

```text
MotorTiempo.java
```

El archivo de prueba debe llamarse:

```text
MotorTiempoTest.java
```

---

# 2. Ejecución Correcta de Maven

Maven gestiona el ciclo de vida del proyecto utilizando el archivo `pom.xml`, por lo que los comandos deben ejecutarse desde la raíz del repositorio.

## Ubicación Correcta

Nunca ejecutes comandos `mvn` dentro de subdirectorios como:

```text
src/main/
src/test/
```

Si lo haces, Maven puede lanzar la excepción:

```text
MissingProjectException
```

debido a la ausencia del archivo `pom.xml`.

## Regla General

Antes de ejecutar Maven, vuelve siempre a la raíz del proyecto.

### Ejemplo

```bash
cd ~/Doc/tarea2_doo
```

---

## Comandos Esenciales

### Ejecutar todas las pruebas

```bash
mvn clean test
```

Este comando:

- elimina archivos compilados previos,
- recompila el proyecto,
- ejecuta toda la suite de pruebas JUnit.

---

### Forzar actualización de dependencias

Si Maven no reconoce paquetes como:

```text
org.junit.jupiter.api
```

la caché local probablemente esté desactualizada.

Ejecuta:

```bash
mvn clean test -U
```

La opción `-U` obliga a Maven a descargar nuevamente las dependencias desde el repositorio central.

---

# 3. Anatomía de un Test Unitario Profesional (AAA)

Cada prueba debe seguir el patrón:

- **Arrange** → Preparar
- **Act** → Ejecutar
- **Assert** → Verificar

Además, cada test debe ser atómico: una prueba debe validar una sola responsabilidad.

---

## Uso de `@BeforeEach`

Evita duplicar código de inicialización creando un entorno limpio antes de cada prueba.

```java
private ReunionVirtual reunionVirtual;

@BeforeEach
void setUp() {
    Empleado organizador = new Empleado(
        "1",
        "Pérez",
        "Bastián",
        "bastian@udec.cl",
        depto
    );

    reunionVirtual = new ReunionVirtual(
        new Date(),
        Instant.now(),
        Duration.ofMinutes(60),
        TipoReunion.TECNICA,
        organizador,
        "https://meet.google.com/abc"
    );
}
```

---

## Ejemplo del Patrón AAA

```java
@Test
@DisplayName("Lógica: verificar cálculo correcto del porcentaje de asistencia")
void testPorcentajeAsistenciaCasoNormal() {

    // 1. Arrange
    Empleado invitado = new Empleado(
        "2",
        "Soto",
        "Ana",
        "ana@udec.cl",
        depto
    );

    reunionVirtual.getInvitaciones()
        .add(new Invitacion(Instant.now(), invitado));

    reunionVirtual.getAsistencias()
        .add(new Asistencia(invitado));

    // 2. Act
    float resultado = reunionVirtual.obtenerPorcentajeAsistencia();

    // 3. Assert
    assertEquals(
        100.0f,
        resultado,
        0.01f,
        "El porcentaje debería ser 100.0% con un invitado asistente."
    );
}
```

---

# 4. Pruebas de Excepciones y Reglas de Negocio

No basta con probar casos exitosos ("happy path"). También se deben validar estados inválidos y reglas de negocio mediante excepciones.

Para verificar excepciones utiliza `assertThrows`.

## Ejemplo

```java
@Test
@DisplayName("Excepción: finalizar una reunión no iniciada debe lanzar excepción")
void testFinalizarReunionSinIniciarLanzaExcepcion() {

    assertThrows(
        ReunionEstadoException.class,
        () -> {
            reunionVirtual.finalizar();
        },
        "Debería lanzar ReunionEstadoException si la reunión no fue iniciada previamente."
    );
}
```

---

# 5. Buenas Prácticas

## Uso de `@DisplayName`

Utiliza nombres descriptivos y legibles en español.

### Correcto

```java
@DisplayName("Lógica: verificar cálculo correcto del porcentaje de asistencia")
```

Esto mejora la legibilidad de los reportes y facilita la evaluación.

---

## Precisión con `float` y `double`

Cuando compares números de punto flotante usando `assertEquals`, siempre utiliza un delta de tolerancia.

### Correcto

```java
assertEquals(50.0f, resultado, 0.01f);
```

### Incorrecto

```java
assertEquals(50.0f, resultado);
```

La segunda opción puede fallar debido a errores de precisión binaria.

---

## Complejidad Algorítmica

Los datos utilizados en las pruebas no deben alterar las garantías de rendimiento del código de producción.

Por ejemplo, estructuras optimizadas para acceso constante deben mantener complejidad temporal:

```math
\mathcal{O}(1)
```

incluso bajo múltiples aserciones o ejecuciones consecutivas.

---

# 6. Recomendaciones Finales

- Mantén cada prueba independiente.
- Evita dependencias entre tests.
- Usa nombres descriptivos.
- Prioriza legibilidad sobre complejidad.
- Ejecuta la suite completa antes de cada entrega.
- Corrige warnings y errores inmediatamente.
- Mantén una estructura de paquetes consistente.

---

# 7. Estructura Recomendada del Proyecto

```text
tarea2_doo/
├── pom.xml
├── src/
│   ├── main/
│   │   └── java/
│   │       └── cl/
│   │           └── udec/
│   │               └── cs/
│   │                   └── ...
│   │
│   └── test/
│       └── java/
│           └── cl/
│               └── udec/
│                   └── cs/
│                       └── ...
```

---

# 8. Dependencias Mínimas Recomendadas para `pom.xml`

```xml
<dependencies>

    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>5.10.2</version>
        <scope>test</scope>
    </dependency>

</dependencies>
```

---

# 9. Plugin Recomendado de Maven Surefire

```xml
<build>
    <plugins>

        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>3.2.5</version>
        </plugin>

    </plugins>
</build>
```

---

# 10. Checklist Antes de Entregar

- [ ] Todas las pruebas compilan correctamente.
- [ ] `mvn clean test` ejecuta sin errores.
- [ ] No existen imports rotos.
- [ ] Las clases de prueba terminan en `Test`.
- [ ] Se utiliza `@BeforeEach` correctamente.
- [ ] Todas las excepciones relevantes son probadas.
- [ ] Los asserts usan tolerancia para `float` y `double`.
- [ ] Los tests poseen `@DisplayName`.
- [ ] La estructura de paquetes coincide con `src/main`.

---
