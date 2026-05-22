package cl.udec.cs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ReunionTest {

    // Objetos base para el entorno de pruebas (Test Fixture)
    private Departamento departamentoSistemas;
    private Empleado organizador;
    private ReunionVirtual reunionVirtual;

    /**
     * Configuración inicial que se ejecuta ANTES de cada método de prueba.
     * Garantiza que cada test empiece con un estado limpio e independiente.
     */
    @BeforeEach
    void setUp() {
        // Arrange general: Instanciamos los objetos mínimos necesarios
        departamentoSistemas = new Departamento("Sistemas");
        
        organizador = new Empleado(
                "1", 
                "Pérez", 
                "Bastián", 
                "bastian@udec.cl", 
                departamentoSistemas
        );

        // Creamos una implementación concreta (ReunionVirtual) para probar los métodos abstractos de Reunion
        reunionVirtual = new ReunionVirtual(
                new Date(),
                Instant.now(),
                Duration.ofMinutes(60),
                TipoReunion.TECNICA,
                organizador,
                "https://meet.google.com/abc-defg-hij"
        );
    }

    @Test
    @DisplayName("Caso Normal: Agregar una nota exitosamente a la reunión")
    void testAgregarNotaCasoNormal() {
        // 1. Arrange (Preparar): Creamos la nota específica para este escenario
        Nota notaEntregable = new Nota("Definir la arquitectura base antes del viernes.");

        // 2. Act (Actuar): Ejecutamos la acción que queremos testear
        reunionVirtual.getNotas().add(notaEntregable);

        // 3. Assert (Verificar): Comprobamos que el resultado sea el esperado
        assertEquals(1, reunionVirtual.getNotas().size(), "La lista de notas debería tener exactamente 1 elemento.");
        assertEquals("Definir la arquitectura base antes del viernes.", 
                reunionVirtual.getNotas().get(0).getContenido(), 
                "El contenido de la nota no coincide con el ingresado.");
    }

    @Test
    @DisplayName("Caso Extremo/Excepción: Intentar finalizar una reunión que no ha iniciado")
    void testFinalizarReunionSinIniciarLanzaExcepcion() {
        // Arrange: El objeto reunionVirtual ya existe y su horaInicio es null por defecto.
        
        // Act & Assert: Verificamos que se lance la excepción correcta al ejecutar el método.
        // NOTA: Como aún no creamos las excepciones personalizadas, usamos IllegalStateException temporalmente.
        assertThrows(IllegalStateException.class, () -> {
            reunionVirtual.finalizar();
        }, "Debería lanzar IllegalStateException si se intenta finalizar una reunión sin haber sido iniciada.");
    }
}
