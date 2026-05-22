package cl.udec.cs;

/**
 * Excepción lanzada cuando faltan datos críticos para ejecutar 
 * una operación en la reunión o generar un reporte.
 */
public class DatosIncompletosException extends RuntimeException {
    public DatosIncompletosException(String mensaje) {
        super(mensaje);
    }
}
