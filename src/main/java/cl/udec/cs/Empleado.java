package cl.udec.cs;

public class Empleado implements Invitable {
    private String id;
    private String apellidos;
    private String nombre;
    private String correo;
    private Departamento departamento;

    public Empleado(String id, String apellidos, String nombre, String correo, Departamento departamento) {
        this.id = id;
        this.apellidos = apellidos;
        this.nombre = nombre;
        this.correo = correo;
        this.departamento = departamento;
    }

    @Override
    public void invitar() {
        // TODO: Implementar lógica
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public Departamento getDepartamento() { return departamento; }
    public void setDepartamento(Departamento departamento) { this.departamento = departamento; }

    @Override
    public String toString() {
        return "Empleado{" +
                "id='" + id + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", departamento=" + departamento.getNombre() +
                '}';
    }
}
