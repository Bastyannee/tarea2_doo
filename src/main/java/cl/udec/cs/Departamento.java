package cl.udec.cs;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Departamento {
    private String nombre;
    private List<Empleado> empleados;

    public Departamento(String nombre) {
        this.nombre = nombre;
        this.empleados = new ArrayList<>();
    }

    public void agregarEmpleado(Empleado emp) {
        if (!this.empleados.contains(emp)) {
            this.empleados.add(emp);
            emp.setDepartamento(this);
        }
    }

    public List<Empleado> getEmpleados() {
        return this.empleados;
    }

    public int obtenerCantidadEmpleados() {
        return this.empleados.size();
    }

    /**
     * Invita a todos los empleados del departamento a una reunión.
     */
    public void invitar(Reunion reunion) {
        for (Empleado emp : this.empleados) {
            reunion.getInvitaciones().add(new Invitacion(Instant.now(), emp));
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Retorna una representación estructurada compatible con el generador de informes.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(nombre).append("\n\nEmpleados:\n\n");
        for (Empleado emp : empleados) {
            sb.append(emp.getNombre()).append(" ").append(emp.getApellidos()).append(" \n");
            sb.append("ID: ").append(emp.getId()).append("\n");
            sb.append("Correo: ").append(emp.getCorreo()).append("\n\n");
        }
        return sb.toString();
    }
}