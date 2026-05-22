package cl.udec.cs;

import java.time.Instant;

public class Invitacion {
    private Instant hora;
    private Invitable invitado;

    public Invitacion(Instant hora, Invitable invitado) {
        this.hora = hora;
        this.invitado = invitado;
    }

    public Instant getHora() { return hora; }
    public void setHora(Instant hora) { this.hora = hora; }

    public Invitable getInvitado() { return invitado; }
    public void setInvitado(Invitable invitado) { this.invitado = invitado; }

    @Override
    public String toString() {
        return "Invitacion{" +
                "hora=" + hora +
                ", invitado=" + invitado +
                '}';
    }
}