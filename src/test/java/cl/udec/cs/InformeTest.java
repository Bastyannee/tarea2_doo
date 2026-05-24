package cl.udec.cs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.time.Instant;
import java.time.Duration;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;

/**
 * Clase de pruebas unitarias para la clase Informe.
 */
class InformeTest {
    /**
     * Ruta del archivo de texto que se genera durante la prueba.
     */
    private final String RUTA_DOCUMENTO = "reporte_salida_test.txt";
    private Empleado organizador;
    private ReunionVirtual sesionOnline;
    private GeneradorInforme creadorReportes;
    private Departamento depto;

    /**
     * Inicializa el entorno de prueba antes de cada método.
     */
    @BeforeEach
    void iniciarComponentes() {
        creadorReportes = new GeneradorInforme();
        organizador = new Empleado("002", "Norambuena Meza", "María José", "marianoram414@gmail.com", depto);
        sesionOnline = new ReunionVirtual(new Date(), Instant.now(), Duration.ofMinutes(45), TipoReunion.TECNICA, organizador, "https://meet.google.com/abc-defg-hij");
    }

    /**
     * Elimina el archivo de salida generado durante las pruebas, solo si existe.
     */
    @AfterEach
    void limpiarArchivos() {
        File fichero = new File(RUTA_DOCUMENTO);
        if (fichero.exists()) {
            fichero.delete();
        }
    }

    /**
     * Verifica el flujo de generación de informe bajo condiciones normales.
     */
    @Test
    void testCrearInformeFlujoNormal() throws ReunionEstadoException {
        sesionOnline.getNotas().add(new Nota("Primer apunte: Configuración de entorno lista."));
        sesionOnline.getNotas().add(new Nota("Segundo apunte: Revisión de arquitectura base de datos."));
        sesionOnline.getAsistencias().add(new Asistencia(organizador));
        sesionOnline.iniciar();
        sesionOnline.finalizar();
        GeneradorInforme.Informe(sesionOnline, RUTA_DOCUMENTO);
        File documentoFinal = new File(RUTA_DOCUMENTO);

        assertTrue(documentoFinal.exists(), "Error: El archivo de salida .txt no fue localizado en el disco.");
        assertTrue(documentoFinal.length() > 0, "Error: El archivo fue creado pero se encuentra vacío.");
    }

    /**
     * Evalúa el comportamiento del sistema ante un caso extremo o erróneo.
     */
    @Test
    void testCrearInformeConDatosNulos() {
        assertThrows(Exception.class, () -> {
            GeneradorInforme.Informe(null, RUTA_DOCUMENTO);
        }, "Se esperaba una excepción al intentar procesar un objeto de reunión nulo.");
    }
}