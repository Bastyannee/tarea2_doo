package cl.udec.cs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias para la clase Reunion que se encarga de verificar el correcto comportamiento de la gestión de notas, el control de los estados de la reunión y el cálculo de porcentajes de asistencia.
 */
class ReunionTest {

    private Departamento depto;
    private Empleado organizador;
    private ReunionVirtual reunionVirtual;
    private Duration duration;
    private Instant inst;

    /**
     * Configura el entorno de pruebas antes de la ejecución de cada test. Donde inicializa el departamento, el organizador y la reunión virtual con datos válidos.
     * @throws DatosIncompletosException Si los parámetros de los datos son nulos o inválidos.
     */
    @BeforeEach
    void setUp() throws DatosIncompletosException {
        inst = Instant.now();
        duration = Duration.ofMinutes(60);
        depto = new Departamento("Departamento de Ingeniería Informática y Ciencias de la Computación");
        
        organizador = new Empleado(
                "003",
                "Pérez Aguayo",
                "Bastián Antonio",
                "baperez2024@udec.cl",
                depto
        );

        reunionVirtual = new ReunionVirtual(inst, duration, organizador, "https://meet.google.com/abc-defg-hij", tipoReunion.TECNICA);
    }

    /**
     * Prueba el caso normal de añadir una nota de contenido a la reunión. Se verifica que la nota se incorpore correctamente a la lista y que su contenido sea el esperado.
     */
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

    /**
     * Prueba el caso extremo donde se intenta finalizar una reunión que aún no ha sido iniciada.
     * Verifica que el sistema lance correctamente la excepción ReunionEstadoException.
     */
    @Test
    @DisplayName("Caso Extremo: Intentar finalizar una reunión que no ha iniciado")
    void testFinalizarReunionSinIniciarLanzaExcepcion() {
        assertThrows(ReunionEstadoException.class, () -> {
            reunionVirtual.finalizar();
        }, "Debería lanzar ReunionEstadoException al finalizar sin iniciar.");
    }

    /**
     * Prueba la lógica del cálculo matemático del porcentaje de asistencia.
     * Simula un escenario con dos invitados y un solo asistente para verificar que el resultado sea el 50%.
     */
    @Test
    @DisplayName("Lógica: Calcular porcentaje de asistencia correctamente")
    void testPorcentajeAsistencia() {
        Empleado invitado2 = new Empleado("002", "Norambuena Meza", "María José", "marianoram414@gmail.com", depto);
        
        reunionVirtual.getInvitaciones().add(new Invitacion(Instant.now(), organizador));
        reunionVirtual.getInvitaciones().add(new Invitacion(Instant.now(), invitado2));
        
        reunionVirtual.getAsistencias().add(new Asistencia(organizador));

        float porcentaje = reunionVirtual.obtenerPorcentajeAsistencia();

        assertEquals(50.0f, porcentaje, 0.01f, "El porcentaje de asistencia debería ser exactamente 50.0%");
    }
}