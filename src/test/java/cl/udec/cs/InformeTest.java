package cl.udec.cs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.time.Instant;
import java.time.Duration;
import java.time.LocalDate;
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
    private Informe creadorReportes;

    /**
     * Inicializa el entorno de prueba antes de cada método.
     */
    @BeforeEach
    void iniciarComponentes() {
        creadorReportes = new Informe();
        organizador = new Empleado("002", "Norambuena Meza", "María José", "marianoram414@gmail.com");
        sesionOnline = new ReunionVirtual(LocalDate.now(), Instant.now(), Duration.ofMinutes(45), organizador, tipoReunion.TECNICA, "https://meet.google.com/abc-defg-hij");
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
    void testCrearInformeFlujoNormal() {
        sesionOnline.agregarNota(new Nota("Primer apunte: Configuración de entorno lista."));
        sesionOnline.agregarNota(new Nota("Segundo apunte: Revisión de arquitectura base de datos."));
        sesionOnline.agregarAsistencia(organizador);
        sesionOnline.iniciar();
        sesionOnline.finalizar();
        creadorReportes.generarInforme(sesionOnline, RUTA_DOCUMENTO);
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
            creadorReportes.generarInforme(null, RUTA_DOCUMENTO);
        }, "Se esperaba una excepción al intentar procesar un objeto de reunión nulo.");
    }
}