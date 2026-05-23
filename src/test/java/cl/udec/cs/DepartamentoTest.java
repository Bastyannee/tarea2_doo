package cl.udec.cs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.time.Duration;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias para la clase Departamento. Verifica el comportamiento correcto de las operaciones principales del departamento: agregar empleados, consultar cantidad, obtener nombre, invitar a reuniones y representación como cadena de texto.
 */

class DepartamentoTest {
    private Departamento depto;
    private Empleado emp1;
    private Empleado emp2;
    private Empleado emp3;
    private Reunion reunion;
    private Duration duration;
    private Instant inst;

    /**
     * Inicializa el entorno de prueba antes de cada método.
     * @throws DatoInvalidoException si alguno de los datos proporcionados al constructor empleado o Departamento no es válido.
     */
    @BeforeEach
    void setUp() throws DatoInvalidoException {
        inst = Instant.now();
        duration = Duration.ofHours(2);
        depto = new Departamento("Departamento de Ingeniería Informática y Ciencias de la Computación");
        emp1 = new Empleado("001", "Garrido Fierro", "Tomás Francisco", "tomasgf13@gmail.com", depto);
        emp2 = new Empleado("002", "Norambuena Meza", "María José", "marianoram414@gmail.com", depto);
        emp3 = new Empleado("003", "Pérez Aguayo", "Bastián Antonio", "baperez2024@udec.cl", depto);
        reunion = new ReunionPresencial(inst, duration, emp1, "412", tipoReunion.TECNICA);
    }

    /**
     * Verifica que al agregar un empleado sin departamento al departamento, el empleado quede correctamente asociado a éste.
     * @throws DatoInvalidoException si los datos del nuevo empleado no son válidos.
     */
    @Test
    void testAgregarEmpleado() throws DatoInvalidoException {
        Empleado nuevo = new Empleado("004", "Reveco", "Cristobal", "cristobal.reveco@gmail.com", null);
        depto.agregarEmpleado(nuevo);
        assertNotNull(nuevo.getDepartamento());
    }

    /**
     * Verifica que la cantidad de empleados del departamento sea la esperada.
     */
    @Test
    void testObtenerCantidadEmpleados() {
        assertEquals(3, depto.obtenerCantidadEmpleados());
    }

    /**
     * verifica que el nombre del departamento se retorne correctamente.
     */
    @Test
    void testGetNombreDepartamento() {
        assertEquals("Departamento de Ingeniería Informática y Ciencias de la Computación", depto.getNombreDepartamento());
    }

    /**
     * Verifica que al invitar al departamento a una reunión, todos los empleados reciban la invitación correspondiente.
     */
    @Test
    void testInvitar() {
        depto.invitar(reunion);
        int cuentaInvitados = 0;

        for (int idx = 0; idx < depto.obtenerCantidadEmpleados(); idx++) {
            Empleado emp = (Empleado) depto.getEmpleados().get(idx);
            if (emp.invitacion.getReunion().equals(reunion)) {
                cuentaInvitados++;
            }
        }
        assertEquals(depto.obtenerCantidadEmpleados(), cuentaInvitados);
    }

    /**
     * Verifica que la representación en cadena del departamento tenga el formato esperado.
     */
    @Test
    void testToString() {
        String esperado = "Departamento de Ingeniería Informática y Ciencias de la Computación\n\nEmpleados:\n\n" +
                "Tomás Francisco Garrido Fierro \nID: 001\nCorreo: tomasgf13@gmail.com\n\n" +
                "María José Norambuena Meza \nID: 002\nCorreo: marianoram414@gmail.com\n\n" +
                "Bastián Antonio Pérez Aguayo \nID: 003\nCorreo: baperez2024@udec.cl\n\n";

        assertEquals(esperado, depto.toString());
    }

    /**
     * Limpieza ejecutada tras cada prueba.
     */
    @AfterEach
    void tearDown(){
    }
}