package cl.udec.cs;

/**
 * Excepción lanzada cuando se intenta realizar una operación inválida 
 * para el estado actual de la reunión (ej. finalizar sin iniciar).
 */
public class ReunionEstadoException extends Exception {
    public ReunionEstadoException(String mensaje) {
        super(mensaje);
    }
}
