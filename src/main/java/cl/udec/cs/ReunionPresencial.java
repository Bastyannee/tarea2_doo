package cl.udec.cs;

import java.util.Date;
import java.time.Instant;
import java.time.Duration;

public class ReunionPresencial extends Reunion {
    private String sala;

    public ReunionPresencial(Date fecha, Instant horaPrevista, Duration duracionPrevista, TipoReunion tipoReunion, Empleado organizador, String sala) {
        super(fecha, horaPrevista, duracionPrevista, tipoReunion, organizador);
        this.sala = sala;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    @Override
    public String toString() {
        return "ReunionPresencial{" +
                "sala='" + sala + '\'' +
                ", " + super.toString() +
                '}';
    }
}

