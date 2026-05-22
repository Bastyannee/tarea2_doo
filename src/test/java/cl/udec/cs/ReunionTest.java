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
        Nota notaEntregable = new Nota("Definir la arquitectura base antes del viernes.");
        reunionVirtual.getNotas().add(notaEntregable);

        assertEquals(1, reunionVirtual.getNotas().size(), "La lista de notas debería tener exactamente 1 elemento.");
        assertEquals("Definir la arquitectura base antes del viernes.", 
                reunionVirtual.getNotas().get(0).getContenido(), 
                "El contenido de la nota no coincide con el ingresado.");
    }

    @Test
    @DisplayName("Caso Extremo: Intentar finalizar una reunión que no ha iniciado")
    void testFinalizarReunionSinIniciarLanzaExcepcion() {
        // Verifica que tu nueva excepción se lance correctamente
        assertThrows(ReunionEstadoException.class, () -> {
            reunionVirtual.finalizar();
        }, "Debería lanzar ReunionEstadoException al finalizar sin iniciar.");
    }

    @Test
    @DisplayName("Lógica: Calcular porcentaje de asistencia correctamente")
    void testPorcentajeAsistencia() {
        // Arrange: Agregamos 2 invitaciones y solo 1 asistencia (50%)
        Empleado invitado2 = new Empleado("2", "Soto", "Ana", "ana@udec.cl", departamentoSistemas);
        
        reunionVirtual.getInvitaciones().add(new Invitacion(Instant.now(), organizador));
        reunionVirtual.getInvitaciones().add(new Invitacion(Instant.now(), invitado2));
        
        reunionVirtual.getAsistencias().add(new Asistencia(organizador));

        // Act: Calculamos el porcentaje
        float porcentaje = reunionVirtual.obtenerPorcentajeAsistencia();

        // Assert: Verificamos con un delta de 0.01 para precisión de flotantes
        assertEquals(50.0f, porcentaje, 0.01f, "El porcentaje de asistencia debería ser exactamente 50.0%");
    }
}
