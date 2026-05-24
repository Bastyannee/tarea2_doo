package cl.udec.cs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

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

        reunionVirtual = new ReunionVirtual(new Date(), inst, duration, TipoReunion.TECNICA, organizador, "https://meet.google.com/abc-defg-hij");
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
    @Test
    @DisplayName("Lógica: Calcular el tiempo real de duración en minutos")
    void testCalcularTiempoRealCorrecto() throws ReunionEstadoException, InterruptedException {
        reunionVirtual.iniciar();
        Thread.sleep(100);
        reunionVirtual.finalizar();

        float tiempoReal = reunionVirtual.calcularTiempoReal();
        assertTrue(tiempoReal > 0, "El tiempo calculado real debe ser mayor a 0 minutos.");
    }

    @Test
    @DisplayName("Caso Extremo: Calcular tiempo real de una reunión que no ha sido ejecutada")
    void testCalcularTiempoRealSinCicloCompleto() {
        float tiempoReal = reunionVirtual.calcularTiempoReal();
        assertEquals(0.0f, tiempoReal, "Si la reunión no ha completado su ciclo, el tiempo real debe ser 0.");
    }

    @Test
    @DisplayName("Polimorfismo: Permitir invitaciones tanto de Empleados como de Invitados Externos")
    void testPolimorfismoInvitaciones() {
        Invitable invitadoExterno = new InvitadoExterno("Tomás Garrido", "tomas@externo.com");

        Invitacion inv1 = new Invitacion(Instant.now(), organizador);
        Invitacion inv2 = new Invitacion(Instant.now(), invitadoExterno);

        reunionVirtual.getInvitaciones().add(inv1);
        reunionVirtual.getInvitaciones().add(inv2);

        assertEquals(2, reunionVirtual.getInvitaciones().size(), "La lista debería contener 2 invitaciones.");
        assertInstanceOf(Empleado.class, reunionVirtual.getInvitaciones().get(0).getInvitado());
        assertInstanceOf(InvitadoExterno.class, reunionVirtual.getInvitaciones().get(1).getInvitado());
    }

    @Test
    @DisplayName("Cálculo Exacto: Validar consistencia matemática del porcentaje con valores extremos")
    void testCalculoExactoPorcentajeAsistenciaCero() {
        reunionVirtual.getInvitaciones().add(new Invitacion(Instant.now(), organizador));
        float porcentaje = reunionVirtual.obtenerPorcentajeAsistencia();
        assertEquals(0.0f, porcentaje, 0.001f, "El porcentaje exacto de asistencia debería ser 0.0%");
    }

    @Test
    @DisplayName("Cálculo Exacto: Simular una reunión de larga duración durante el día")
    void testCalcularTiempoRealDuracionProlongada() throws ReunionEstadoException {
        reunionVirtual.iniciar();
        Instant horaInicioForzada = Instant.now().minus(Duration.ofMinutes(150));
        reunionVirtual.setHoraInicio(horaInicioForzada);
        reunionVirtual.finalizar();

        float tiempoCalculado = reunionVirtual.calcularTiempoReal();
        assertEquals(150.0f, tiempoCalculado, 0.05f, "El motor de tiempo debe calcular exactamente 150.0 minutos.");
    }
}
