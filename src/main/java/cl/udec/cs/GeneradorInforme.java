package cl.udec.cs;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Clase encargada de consolidar los datos de una reunión y plasmarlos ordenadamente en un archivo de texto.
 */
public class GeneradorInforme {

    /**
     * Constructor por defecto.
     */
    public GeneradorInforme() {
    }

    /**
     * Toma una reunión estructurada e imprime el reporte estructurado en un .txt.
     * @param reunion Instancia evaluada.
     * @param rutaArchivo Ruta de destino del archivo de texto.
     */
    public static void Informe(Reunion reunion, String rutaArchivo) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(rutaArchivo));

            writer.println("Informe de la reunión");
            writer.println();

            writer.println("Fecha: " + reunion.getFecha());
            writer.println("Hora prevista: " + reunion.getHoraPrevista());
            writer.println("Hora de inicio: " + reunion.getHoraInicio());
            writer.println("Hora de fin: " + reunion.getHoraFin());
            writer.println("Organizador: " + reunion.getOrganizador().toString());
            writer.println("Tipo de reunion: " + reunion.getTipoReunion());

            if (reunion instanceof ReunionVirtual) {
                ReunionVirtual virtual = (ReunionVirtual) reunion;
                writer.println("Enlace de conexión: " + virtual.getEnlace());
            }
            if (reunion instanceof ReunionPresencial) {
                ReunionPresencial presencial = (ReunionPresencial) reunion;
                writer.println("Sala: " + presencial.getSala());
            }
            writer.println();

            writer.println("Cantidad de invitados: " + reunion.getInvitaciones().size());
            writer.println("Total de asistentes: " + reunion.obtenerTotalAsistencia());
            writer.println("Porcentaje de asistencia: " + reunion.obtenerPorcentajeAsistencia() + "%");
            writer.println("Duración de la reunión: " + reunion.calcularTiempoReal() + " minutos");
            writer.println();

            writer.println("Invitados/Presentes: ");
            for (Asistencia asistente : reunion.obtenerAsistencias()) {
                writer.println("- " + asistente.getEmpleado().toString());
            }
            writer.println();

            writer.println("Retrasos: ");
            for (Retraso retraso : reunion.obtenerRetrasos()) {
                writer.println("- " + retraso.getEmpleado().toString() + " (Hora de llegada: " + retraso.getHora() + ")");
            }
            writer.println();

            writer.println("Ausentes: ");
            for (Empleado ausente : reunion.obtenerAusencias()) {
                writer.println("- " + ausente.toString());
            }
            writer.println();

            writer.println("Notas: ");
            for (Nota nota : reunion.getNotas()) {
                writer.println("- " + nota.toString());
            }

            writer.println();
            writer.close();
            System.out.print("El informe se ha generado correctamente.");

        } catch (IOException e) {
            System.out.print("Hubo un error al generar el informe.");
            e.printStackTrace();
        }
    }
}