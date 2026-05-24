# Tarea 2: Sistema de Gestión de Reuniones (DOO)

Este repositorio contiene la implementación del sistema de gestión de reuniones, desarrollado como parte de la asignatura de Diseño Orientado a Objetos (DOO).

## Documentación del Proyecto

Para mantener un desarrollo ordenado y profesional, hemos organizado la información de la siguiente manera:

* **[CONTRIBUTING.md](CONTRIBUTING.md):** Define nuestro flujo de trabajo, convenciones de *branching* y los protocolos para realizar integraciones seguras mediante *Pull Requests*.
* **[guia-pruebas-unitarias.md](guia-pruebas-unitarias.md):** Guía técnica sobre el diseño de pruebas unitarias, uso de JUnit 5, ejecución con Maven y buenas prácticas para asegurar la calidad del código.

## Tecnologías y Herramientas
* **Lenguaje:** Java 17+
* **Gestión de Dependencias:** Apache Maven
* **Framework de Testing:** JUnit 5


## Equipo de Desarrollo
* Bastián Pérez
* Tomas Francisco Garrido Fierro
* Maria Jose Norambuena Meza
## Detalle de la Arquitectura y Relaciones del Diagrama UML

El diseño arquitectónico del sistema se rige estrictamente por los estándares de modelado de la especificación UML y los principios del Diseño Orientado a Objetos (DOO), estructurándose en base a las siguientes definiciones funcionales:

### 1. Jerarquías de Herencia e Implementación (Generalization / Realization)
* **`ReunionVirtual` y `ReunionPresencial` (Herencia):** Especializan a la clase abstracta base `Reunion` mediante una relación de generalización (`--|>`), heredando toda la lógica de control de estados temporales y encapsulamiento de participantes, añadiendo sus atributos específicos (`enlace` y `sala` respectivamente).
* **`Retraso` (Herencia):** Especializa a la clase concreta `Asistencia` (`--|>`), permitiendo extender el registro regular de un empleado presente con una marca de tiempo específica (`Instant hora`).
* **`Empleado` e `InvitadoExterno` (Realización):** Implementan de forma polimórfica la interfaz `Invitable` (`..|>`). Esto desacopla el núcleo del sistema de reuniones de los detalles de la organización interna, permitiendo despachar invitaciones a entidades internas o externas indistintamente a través del método `invitar()`.
* **Jerarquía de Excepciones:** `ReunionEstadoException` y `DatosIncompletosException` extienden la jerarquía estándar de Java para encapsular de forma segura los errores de control de flujo y validación de datos del negocio.

### 2. Relaciones de Composición Fuerte (Strong Composition)
Se modelan con el rombo relleno (`*--`) hacia la clase abstracta `Reunion`, indicando una pertenencia estricta y dependencia total del ciclo de vida:
* **`Reunion "1" *-- "*" Nota` (`-notas`):** Las notas tomadas pertenecen exclusivamente a una única reunión. Si la instancia de la reunión se destruye, sus notas asociadas dejan de existir.
* **`Reunion "1" *-- "*" Invitacion` (`-invitaciones`):** Las invitaciones emitidas están acopladas existencialmente al ciclo de vida de la reunión.
* **`Reunion "1" *-- "*" Asistencia` (`-asistencias`):** Las hojas de asistencia y registros de presencia/atraso no tienen sentido conceptual fuera del contexto de la reunión evaluada.

### 3. Asociaciones Directas y Multiplicidades (Roles y Navegabilidad)
Las asociaciones (`-->`) indican la presencia de atributos privados que conectan las clases con una navegabilidad unidireccional para resguardar el bajo acoplamiento:
* **`Reunion "*" --> "1" Empleado` (`-organizador`):** Una reunión contiene una referencia obligatoria a exactamente un empleado que actúa en el rol de organizador. Un empleado puede organizar de cero a muchas (`*`) reuniones.
* **`Reunion "*" --> "1" TipoReunion` (`-tipoReunion`):** Clasificación taxonómica de la reunión a través del tipo enumerado (`TECNICA`, `MARKETING`, `OTRO`).
* **`Invitacion "*" --> "1" Invitable` (`-invitado`):** Cada invitación apunta a una abstracción `Invitable` por medio de polimorfismo, sin importar si el destino final se trata de un empleado de la corporación o un agente externo.
* **`Asistencia "*" --> "1" Empleado` (`-empleado`):** Vinculación directa que mapea qué empleado concreto corresponde a la asistencia registrada.
* **`Empleado "*" --> "1" Departamento` (`-departamento`):** Navegabilidad directa que asocia a un empleado con su departamento base.

### 4. Relaciones de Agregación (Aggregation)
* **`Departamento "1" o-- "*" Empleado` (`agrupa`):** Se modela con un rombo vacío (`o--`) para indicar una relación de agregación por partes. Un departamento agrupa a un conjunto de empleados; sin embargo, el ciclo de vida de los empleados es independiente al del departamento (si el departamento se disuelve, las instancias de los empleados persisten en el sistema).

### 5. Relaciones de Dependencia (Dependency / Usage)
Representadas por líneas punteadas con flechas abiertas (`..>`) y tipificadas con el estereotipo `<<use>>`. Indican una relación de uso transitorio donde las clases no se almacenan como atributos estructurales de largo plazo:
* **`Informe ..> Reunion`:** La clase de utilidad `Informe` (o `GeneradorInforme`) requiere recibir temporalmente por parámetro una instancia de `Reunion` para leer sus colecciones y procesar el archivo de texto de salida `.txt`.
* **`Reunion ..> Excepciones`:** Los métodos de negocio de la reunión instancian y lanzan de forma efímera las excepciones personalizadas ante la detección de estados inconsistentes (ej. finalizar antes de iniciar).
---
classDiagram
    direction BT

    %% Interfaces y Enumeraciones
    class Invitable {
        <<interface>>
        +invitar() void
    }

    class TipoReunion {
        <<enumeration>>
        TECNICA
        MARKETING
        OTRO
    }

    %% Clases del Modelo
    class Empleado {
        -id: String
        -apellidos: String
        -nombre: String
        -correo: String
        +invitar() void
        +getId() String
        +getApellidos() String
        +getNombre() String
        +getCorreo() String
        +getDepartamento() Departamento
    }

    class InvitadoExterno {
        -nombreCompleto: String
        -correo: String
        +invitar() void
        +getNombreCompleto() String
        +getCorreo() String
    }

    class Departamento {
        -nombre: String
        +obtenerCantidadEmpleados() int
        +invitar(Reunion) void
        +getNombre() String
    }

    %% Clases Core
    class Reunion {
        <<abstract>>
        -fecha: Date
        -horaPrevista: Instant
        -duracionPrevista: Duration
        -horaInicio: Instant
        -horaFin: Instant
        -iniciada: boolean
        -finalizada: boolean
        +obtenerAsistencias() List~Asistencia~
        +obtenerAusencias() List~Empleado~
        +obtenerRetrasos() List~Retraso~
        +obtenerTotalAsistencia() int
        +obtenerPorcentajeAsistencia() float
        +calcularTiempoReal() float
        +iniciar() void
        +finalizar() void
    }

    class ReunionVirtual {
        -enlace: String
        +getEnlace() String
    }

    class ReunionPresencial {
        -sala: String
        +getSala() String
    }

    %% Entidades de Apoyo
    class Invitacion {
        -hora: Instant
        +getHora() Instant
    }

    class Asistencia {
        -empleado: Empleado
        +getEmpleado() Empleado
    }

    class Retraso {
        -hora: Instant
        +getHora() Instant
    }

    class Nota {
        -contenido: String
        +getContenido() String
    }

    %% Utilidades y Excepciones
    class Informe {
        +generarInforme(Reunion, String) void
    }

    class ReunionEstadoException {
        <<exception>>
    }

    class DatosIncompletosException {
        <<exception>>
    }

    %% Relaciones de Herencia e Implementación (Generalization / Realization)
    ReunionVirtual --|> Reunion
    ReunionPresencial --|> Reunion
    Retraso --|> Asistencia
    Empleado ..|> Invitable
    InvitadoExterno ..|> Invitable

    %% Relaciones de Composición Fuerte
    Reunion "1" *-- "*" Nota : -notas
    Reunion "1" *-- "*" Invitacion : -invitaciones
    Reunion "1" *-- "*" Asistencia : -asistencias

    %% Relaciones de Asociación Directa
    Reunion "*" --> "1" Empleado : -organizador
    Reunion "*" --> "1" TipoReunion : -tipoReunion
    Invitacion "*" --> "1" Invitable : -invitado

    %% Asociación navegable Empleado → Departamento
    Empleado "*" --> "1" Departamento : -departamento

    %% Relaciones de Dependencia
    Informe ..> Reunion : <<use>>
    Reunion ..> ReunionEstadoException : <<use>>
    Reunion ..> DatosIncompletosException : <<use>>