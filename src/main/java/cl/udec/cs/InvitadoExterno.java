package cl.udec.cs;

/**
 * Clase que representa a participantes externos a la empresa.
 * Implementa la interfaz Invitable para integrarse al sistema de reuniones mediante polimorfismo.
 */
public class InvitadoExterno implements Invitable {
    private String nombreCompleto;
    private String correo;

    /**
     * Constructor para un invitado externo.
     * @param nombreCompleto Nombre y apellido del invitado.
     * @param correo Dirección de correo electrónico.
     */
    public InvitadoExterno(String nombreCompleto, String correo) {
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
    }

    /**
     * Envía la invitación al participante externo imprimiendo el resultado en la consola.
     */
    @Override
    public void invitar() {
        System.out.println("Enviando invitación por correo externo a: " + this.correo + " (Invitado: " + this.nombreCompleto + ")");
    }

    // Getters y Setters
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    @Override
    public String toString() {
        return "InvitadoExterno{" +
                "nombreCompleto='" + nombreCompleto + '\'' +
                ", correo='" + correo + '\'' +
                '}';
    }
}