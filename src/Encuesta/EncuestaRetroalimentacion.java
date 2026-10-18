package Encuesta;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;

/**
 * Clase principal que representa la ventana (JFrame) de la aplicación de
 * encuesta de retroalimentación para la empresa Servicebottios.
 *
 * @author Desarrollador Servicebottios
 * @version 1.0
 */
public class EncuestaRetroalimentacion extends JFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor de la ventana principal. Configura el título, el layout, el
     * tamaño y agrega el panel de encuesta.
     */
    public EncuestaRetroalimentacion() {
        // Configuración básica de la ventana (JFrame)
        setTitle("Encuesta de Retroalimentación");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(650, 500);
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        setResizable(false);

        // Se agrega el panel intermedio que contiene todos los componentes
        PanelEncuesta panel = new PanelEncuesta();
        add(panel, BorderLayout.CENTER);
    }

    /**
     * Método principal que lanza la aplicación en el hilo de despacho de
     * eventos (EDT).
     *
     * La interfaz gráfica es la entrada de datos; el resultado de cada
     * encuesta se muestra en la consola estándar.
     */
    public static void main(String[] args) {
        // Aviso inicial: toda la salida de la aplicación se imprime en consola.
        System.out.println("Encuesta de Retroalimentación - Servicebottios");
        System.out.println("Complete el formulario y pulse \"Enviar encuesta\".");
        System.out.println("El resumen de cada encuesta se imprimirá en esta consola.");
        System.out.println();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                EncuestaRetroalimentacion ventana = new EncuestaRetroalimentacion();
                ventana.setVisible(true);
            }
        });
    }
}
