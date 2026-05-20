package cl.udec.cs;

import java.util.Date;
import java.time.Instant;
import java.time.Duration;

public class ReunionVirtual extends Reunion {
    private String enlace;

    public ReunionVirtual(Date fecha, Instant horaPrevista, Duration duracionPrevista, TipoReunion tipoReunion, Empleado organizador, String enlace) {
        super(fecha, horaPrevista, duracionPrevista, tipoReunion, organizador);
        this.enlace = enlace;
    }

    public String getEnlace() {
        return enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

    @Override
    public String toString() {
        return "ReunionVirtual{" +
                "enlace='" + enlace + '\'' +
                ", " + super.toString() +
                '}';
    }
}
