package CalculadoraVoltaje;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

/**
 * Calculadora de voltaje — Ley de Ohm: V = I * R
 * POO II — Semana 2: manejo de excepciones en Java
 *
 * Estructura de manejo: try / catch específicos / catch (Throwable) / finally
 */
public class CalculadoraVoltaje extends JFrame {

    // JFrame es Serializable: se declara el UID para evitar el warning [serial]
    private static final long serialVersionUID = 1L;

    // ---------- Componentes de la interfaz ----------
    private final JTextField campoAmperios     = new JTextField(12);
    private final JTextField campoOhmios       = new JTextField(12);
    private final JButton    botonCalcular     = new JButton("Calcular voltaje");
    private final JLabel     etiquetaResultado = new JLabel("Ingrese amperios y ohmios");

    // ---------- Constructor ----------
    // this-escape: JFrame invoca métodos sobrescribibles (setContentPane, etc.)
    // desde el constructor. Es inevitable al extender JFrame y la clase no se
    // pensó para heredarse, por eso se silencia explícitamente el aviso del JDK 21.
    @SuppressWarnings("this-escape")
    public CalculadoraVoltaje() {
        super("Cálculo de voltaje - Universidad de Islandia");
        construirInterfaz();

        // Manejador de eventos del botón
        botonCalcular.addActionListener(evento -> calcular());
    }

    // ---------- Construcción de la interfaz gráfica ----------
    private void construirInterfaz() {

        // Fila 1: amperios | Fila 2: ohmios | Fila 3: botón
        JPanel formulario = new JPanel(new GridLayout(3, 2, 10, 10));
        formulario.add(new JLabel("Amperios (A):"));
        formulario.add(campoAmperios);
        formulario.add(new JLabel("Ohmios (Ω):"));
        formulario.add(campoOhmios);
        formulario.add(new JLabel(""));            // celda vacía de alineación
        formulario.add(botonCalcular);

        // Etiqueta de resultado / mensajes de error
        JPanel panelResultado = new JPanel(new BorderLayout(6, 0));
        panelResultado.add(new JLabel("Resultado:"), BorderLayout.WEST);
        panelResultado.add(etiquetaResultado, BorderLayout.CENTER);

        JPanel contenido = new JPanel(new BorderLayout(0, 14));
        contenido.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        contenido.add(formulario, BorderLayout.CENTER);
        contenido.add(panelResultado, BorderLayout.SOUTH);

        setContentPane(contenido);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(460, 210);
        setMinimumSize(new Dimension(460, 210));
        setResizable(false);
        setLocationRelativeTo(null);
    }

    // ---------- Lógica del botón: manejo, captura y omisión evitada ----------
    private void calcular() {
        try {
            // 1) Conversión de texto a número.
            //    Puede lanzar NumberFormatException (dato no numérico o vacío).
            double amperios = Double.parseDouble(campoAmperios.getText().trim());
            double ohmios   = Double.parseDouble(campoOhmios.getText().trim());

            // 2) Cálculo con validación de regla de negocio.
            //    Puede lanzar ArithmeticException (throw declarado en el método).
            double voltaje = calcularVoltaje(amperios, ohmios);

            // 3) Salida exitosa en la etiqueta
            etiquetaResultado.setForeground(new Color(0, 110, 0));
            etiquetaResultado.setText(
                    String.format(Locale.US, "Voltaje = %.2f V", voltaje));

        } catch (NumberFormatException ex) {
            // Captura específica 1: el dato ingresado no es un número
            mostrarError("Error de formato: " + ex.getMessage());

        } catch (ArithmeticException ex) {
            // Captura específica 2: el dato viola la regla (negativo / no finito)
            mostrarError("Valor inválido: " + ex.getMessage());

        } catch (Throwable t) {
            // Red de seguridad final: cualquier error no previsto
            mostrarError("Error inesperado: " + t.getClass().getName());
            t.printStackTrace();   // traza para diagnóstico

        } finally {
            // Cierre garantizado: se ejecuta haya o no excepción
            campoAmperios.requestFocus();
            campoAmperios.selectAll();
        }
    }

    /**
     * Cálculo del voltaje con devolución de excepciones.
     * El "throws" de la firma es el contrato del método; el "throw" del cuerpo
     * es la acción que señala la violación de la regla.
     */
    public double calcularVoltaje(double amperios, double ohmios)
            throws ArithmeticException {

        // parseDouble acepta "NaN" e "Infinity": se rechazan explícitamente
        if (Double.isNaN(amperios) || Double.isNaN(ohmios)
                || Double.isInfinite(amperios) || Double.isInfinite(ohmios)) {
            throw new ArithmeticException("Los valores deben ser finitos");
        }

        // Regla de negocio: ni amperios ni ohmios pueden ser negativos
        if (amperios < 0 || ohmios < 0) {
            throw new ArithmeticException("No se admiten valores negativos");
        }

        double voltaje = amperios * ohmios;

        // Desbordamiento del producto a infinito
        if (Double.isInfinite(voltaje)) {
            throw new ArithmeticException("El resultado excede el rango");
        }
        return voltaje;
    }

    /** Escribe el mensaje de error en la etiqueta, en rojo. */
    private void mostrarError(String mensaje) {
        etiquetaResultado.setForeground(Color.RED);
        etiquetaResultado.setText(mensaje);
    }

    // ---------- Punto de entrada ----------
    public static void main(String[] args) {
        // Ejecuta la tarea en el hilo de despacho de eventos de Swing (EDT)
        SwingUtilities.invokeLater(() -> new CalculadoraVoltaje().setVisible(true));
    }
}