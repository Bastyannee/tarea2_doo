package cl.udec.cs;

import java.util.Date;
import java.time.Instant;
import java.time.Duration;
import java.util.List;
import java.util.ArrayList;

public abstract class Reunion {
    private Date fecha;
    private Instant horaPrevista;
    private Duration duracionPrevista;
    private Instant horaInicio;
    private Instant horaFin;
    
    private TipoReunion tipoReunion;
    private Empleado organizador;
    private List<Nota> notas;
    private List<Invitacion> invitaciones;
    private List<Asistencia> asistencias;

    public Reunion(Date fecha, Instant horaPrevista, Duration duracionPrevista, TipoReunion tipoReunion, Empleado organizador) {
        this.fecha = fecha;
        this.horaPrevista = horaPrevista;
        this.duracionPrevista = duracionPrevista;
        this.tipoReunion = tipoReunion;
        this.organizador = organizador;
        this.notas = new ArrayList<>();
        this.invitaciones = new ArrayList<>();
        this.asistencias = new ArrayList<>();
    }

    public List<Asistencia> obtenerAsistencias() {
        return new ArrayList<>(); // TODO: Implementar
    }

    public List<Empleado> obtenerAusencias() {
        return new ArrayList<>(); // TODO: Implementar
    }

    public List<Retraso> obtenerRetrasos() {
        return new ArrayList<>(); // TODO: Implementar
    }

    public int obtenerTotalAsistencia() {
        return 0; // TODO: Implementar
    }

    public float obtenerPorcentajeAsistencia() {
        return 0.0f; // TODO: Implementar
    }

    public float calcularTiempoReal() {
        return 0.0f; // TODO: Implementar
    }

    public void iniciar() {
        // TODO: Implementar
    }

    public void finalizar() {
        // TODO: Implementar
    }

    // Getters y Setters
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public Instant getHoraPrevista() { return horaPrevista; }
    public void setHoraPrevista(Instant horaPrevista) { this.horaPrevista = horaPrevista; }

    public Duration getDuracionPrevista() { return duracionPrevista; }
    public void setDuracionPrevista(Duration duracionPrevista) { this.duracionPrevista = duracionPrevista; }

    public Instant getHoraInicio() { return horaInicio; }
    public void setHoraInicio(Instant horaInicio) { this.horaInicio = horaInicio; }

    public Instant getHoraFin() { return horaFin; }
    public void setHoraFin(Instant horaFin) { this.horaFin = horaFin; }

    public TipoReunion getTipoReunion() { return tipoReunion; }
    public void setTipoReunion(TipoReunion tipoReunion) { this.tipoReunion = tipoReunion; }

    public Empleado getOrganizador() { return organizador; }
    public void setOrganizador(Empleado organizador) { this.organizador = organizador; }

    public List<Nota> getNotas() { return notas; }
    public void setNotas(List<Nota> notas) { this.notas = notas; }

    public List<Invitacion> getInvitaciones() { return invitaciones; }
    public void setInvitaciones(List<Invitacion> invitaciones) { this.invitaciones = invitaciones; }

    public List<Asistencia> getAsistencias() { return asistencias; }
    public void setAsistencias(List<Asistencia> asistencias) { this.asistencias = asistencias; }

    @Override
    public String toString() {
        return "Reunion{" +
                "fecha=" + fecha +
                ", tipo=" + tipoReunion +
                ", organizador=" + organizador.getNombre() +
                ", totalNotas=" + notas.size() +
                ", totalInvitaciones=" + invitaciones.size() +
                '}';
    }
}
