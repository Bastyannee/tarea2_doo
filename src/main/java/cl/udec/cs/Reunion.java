package cl.udec.cs;

import java.util.Date;
import java.time.Instant;
import java.time.Duration;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;
import cl.udec.cs.ReunionEstadoException;

public abstract class Reunion {
    private Date fecha;
    private Instant horaPrevista;
    private Duration duracionPrevista;
    private Instant horaInicio;
    private Instant horaFin;
    private boolean iniciada = false;
    private boolean finalizada = false;

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

    /**
     * Retorna una copia defensiva de todas las asistencias registradas.
     */
    public List<Asistencia> obtenerAsistencias() {
        return new ArrayList<>(this.asistencias);
    }

    /**
     * Filtra la lista de asistencias para retornar únicamente aquellas
     * que sean instancias de la clase Retraso.
     */
    public List<Retraso> obtenerRetrasos() {
        List<Retraso> listaRetrasos = new ArrayList<>();
        for (Asistencia a : this.asistencias) {
            if (a instanceof Retraso) {
                listaRetrasos.add((Retraso) a);
            }
        }
        return listaRetrasos;
    }

    /**
     * Calcula las ausencias realizando una diferencia de conjuntos entre
     * los invitados y los asistentes registrados.
     */
    public List<Empleado> obtenerAusencias() {
        List<Empleado> ausentes = new ArrayList<>();

        // Optimización O(1) para búsquedas usando un Set de IDs
        Set<String> idsAsistentes = new HashSet<>();
        for (Asistencia a : this.asistencias) {
            idsAsistentes.add(a.getEmpleado().getId());
        }

        // Iteramos sobre las invitaciones para ver quién no está en el Set
        for (Invitacion inv : this.invitaciones) {
            // Verificamos si el invitado es un Empleado (útil para el futuro polimorfismo)
            if (inv.getInvitado() instanceof Empleado) {
                Empleado emp = (Empleado) inv.getInvitado();
                if (!idsAsistentes.contains(emp.getId())) {
                    ausentes.add(emp);
                }
            }
        }
        return ausentes;
    }

    /**
     * Retorna la cantidad total de personas que asistieron (incluyendo retrasos).
     */
    public int obtenerTotalAsistencia() {
        return this.asistencias.size();
    }

    /**
     * Calcula el porcentaje de asistencia en base a las invitaciones enviadas.
     */
    public float obtenerPorcentajeAsistencia() {
        if (this.invitaciones.isEmpty()) {
            return 0.0f; // Evitar división por cero
        }
        return ((float) this.asistencias.size() / this.invitaciones.size()) * 100.0f;
    }

    public float calcularTiempoReal() {
        if(this.horaInicio == null || this.horaFin == null){
            return 0.0f;
        }
        Duration duracion = Duration.between(this.horaInicio, this.horaFin);

        return duracion.toMillis() / 60000.0f;
    }

    /**
     * Inicia la reunión marcando el tiempo actual.
     * @throws ReunionEstadoException si la reunión ya fue iniciada o finalizada.
     */
    public void iniciar() throws ReunionEstadoException {
        if (this.iniciada) {
            throw new ReunionEstadoException("Error: La reunión ya ha sido iniciada previamente.");
        }
        if (this.finalizada) {
            throw new ReunionEstadoException("Error: No se puede iniciar una reunión que ya ha finalizado.");
        }

        this.horaInicio = Instant.now();
        this.iniciada = true;
    }

    /**
     * Finaliza la reunión marcando el tiempo de cierre.
     * @throws ReunionEstadoException si se intenta finalizar sin haber iniciado.
     */
    public void finalizar() throws ReunionEstadoException {
        if (!this.iniciada) {
            throw new ReunionEstadoException("Error: No se puede finalizar una reunión que no ha sido iniciada.");
        }
        if (this.finalizada) {
            throw new ReunionEstadoException("Error: La reunión ya se encuentra finalizada.");
        }

        this.horaFin = Instant.now();
        this.finalizada = true;
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
