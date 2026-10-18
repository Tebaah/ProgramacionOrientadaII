package Encuesta;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Gestor que controla los eventos generados en la encuesta de
 * retroalimentación de Servicebottios.
 *
 * La entrada de datos se realiza en la interfaz gráfica
 * ({@link PanelEncuesta}), pero toda la salida de la aplicación se escribe
 * en la consola estándar mediante {@code System.out}.
 *
 * @author Desarrollador Servicebottios
 * @version 1.1
 */
public class GestorEventos implements ActionListener {

    /** Comando asociado al botón "Enviar encuesta". */
    public static final String ACCION_ENVIAR = "enviar";

    /** Comando asociado al botón "Limpiar". */
    public static final String ACCION_LIMPIAR = "limpiar";

    /** Separador que delimita cada encuesta impresa en la consola. */
    private static final String SEPARADOR =
            "------------------------------------------------------------";

    /** Panel de la encuesta sobre el que se actúa. */
    private final PanelEncuesta panel;

    /**
     * Constructor del gestor de eventos.
     *
     * @param panel panel de la encuesta que será controlado
     */
    public GestorEventos(PanelEncuesta panel) {
        this.panel = panel;
    }

    /**
     * Registra este gestor como listener de los botones del panel.
     */
    public void registrarEventos() {
        panel.getBtnEnviar().addActionListener(this);
        panel.getBtnLimpiar().addActionListener(this);
    }

    /**
     * Atiende los eventos de los botones de la encuesta.
     *
     * @param evento evento generado por el botón presionado
     */
    @Override
    public void actionPerformed(ActionEvent evento) {
        String comando = evento.getActionCommand();
        if (ACCION_ENVIAR.equals(comando)) {
            enviarEncuesta();
        } else if (ACCION_LIMPIAR.equals(comando)) {
            limpiarEncuesta();
        }
    }

    /**
     * Valida los datos del formulario y escribe el resumen de la encuesta en
     * la consola estándar.
     */
    private void enviarEncuesta() {
        String nombre = panel.getNombre();
        if (nombre.isEmpty()) {
            aviso("Por favor ingrese su nombre.");
            panel.enfocarNombre();
            return;
        }

        int calificacion = panel.getCalificacion();
        if (calificacion == 0) {
            aviso("Por favor seleccione una calificación de 1 a 5.");
            return;
        }

        String servicio = panel.getServicio();
        String comentarios = panel.getComentarios();

        // Salida de la encuesta por la consola
        System.out.println(SEPARADOR);
        System.out.println("ENCUESTA DE RETROALIMENTACIÓN - SERVICEBOTTIOS");
        System.out.println(SEPARADOR);
        System.out.println("Nombre      : " + nombre);
        System.out.println("Servicio    : " + servicio);
        System.out.println("Calificación: " + calificacion + " / 5");
        System.out.println("Comentarios : "
                + (comentarios.isEmpty() ? "(sin comentarios)" : comentarios));
        System.out.println(SEPARADOR);
        System.out.println("Gracias por su retroalimentación, " + nombre + ".");
        System.out.println();

        panel.setEstado("Encuesta enviada correctamente. Revise la consola.",
                new Color(0, 128, 0));
        panel.limpiarFormulario();
    }

    /**
     * Limpia el formulario para iniciar una nueva encuesta.
     */
    private void limpiarEncuesta() {
        panel.limpiarFormulario();
        System.out.println(
                "[INFO] Formulario limpiado. Puede iniciar una nueva encuesta.");
        System.out.println();
        panel.setEstado("Formulario limpiado. Puede iniciar una nueva encuesta.",
                Color.GRAY);
    }

    /**
     * Informa de un dato faltante: escribe el aviso en la consola y lo
     * refleja en la etiqueta de estado del panel.
     *
     * @param mensaje descripción del dato que falta
     */
    private void aviso(String mensaje) {
        System.out.println("[AVISO] " + mensaje);
        System.out.println();
        panel.setEstado(mensaje, new Color(192, 0, 0));
    }
}

