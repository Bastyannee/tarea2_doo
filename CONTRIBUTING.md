# Guía de Contribución - Tarea 2 (Gestión de Reuniones)

Este documento define el flujo de trabajo de Git y las reglas de integración para mantener un repositorio limpio y evitar conflictos de código entre los integrantes del equipo (Bastián, Tomas y Maria Jose).

## 1. Regla de Oro
**NUNCA se debe hacer un `commit` o `push` directo a la rama `main`.** La rama `main` es sagrada y solo debe contener código funcional, compilable y testeado. Todo el desarrollo se realizará mediante *Feature Branches* (ramas de características).

## 2. Convención de Nombres para Ramas
Al crear una nueva rama desde `main`, utiliza la siguiente nomenclatura:

* `feature/nombre-de-la-tarea`: Para el desarrollo de nuevas funcionalidades (ej. `feature/sistema-reportes`, `feature/motor-tiempo`).
* `fix/nombre-del-error`: Para corregir un error (*bug*) en código existente (ej. `fix/calculo-asistencia`).
* `test/nombre-de-la-prueba`: Para la creación exclusiva de pruebas unitarias (ej. `test/reunion-presencial`).
* `docs/nombre-del-documento`: Para cambios en el README, UML o Javadoc.

**Comando para crear y cambiar a tu rama:**
\`git checkout -b tipo/nombre-de-tu-rama\`

## 3. Convención de Mensajes de Commit
Los *commits* deben ser atómicos (un solo cambio lógico a la vez) y sus mensajes deben ser descriptivos usando el siguiente formato:

* `feat: añade lógica de cálculo de tiempo real`
* `fix: corrige error de null pointer en asistencia`
* `test: añade pruebas unitarias para ReunionVirtual`
* `docs: actualiza diagrama UML con InvitadoExterno`
* `chore: limpia archivos temporales de configuración`

## 4. Flujo de Trabajo (Pull Requests)
Cuando termines tu tarea en tu rama local, sigue estos pasos para integrar el código:

1. **Actualiza tu rama:** Antes de subir nada, trae los últimos cambios de `main` a tu rama para resolver conflictos localmente.
   \`git pull origin main\`
2. **Sube tu rama a GitHub:**
   \`git push origin tipo/nombre-de-tu-rama\`
3. **Crea un Pull Request (PR):** Ve a GitHub y abre un PR apuntando hacia `main`.
4. **Revisión de Pares (*Code Review*):** Solicita a al menos un integrante del equipo que revise tu código.
5. **Merge:** Una vez aprobado, realiza el *merge* a `main` desde la interfaz de GitHub y borra la rama remota para mantener el repositorio limpio.

## 5. Pruebas y Calidad
No se aceptará ningún Pull Request que rompa las pruebas unitarias existentes. Asegúrate de ejecutar la suite de JUnit completa en tu entorno local (`mvn test`) antes de solicitar una revisión.
