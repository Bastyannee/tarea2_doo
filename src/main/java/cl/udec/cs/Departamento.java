package cl.udec.cs;

public class Departamento {
    private String nombre;

    public Departamento(String nombre) {
        this.nombre = nombre;
    }

    public int obtenerCantidadEmpleados() {
        return 0; // TODO: Implementar lógica
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Departamento{" +
                "nombre='" + nombre + '\'' +
                '}';
    }
}
