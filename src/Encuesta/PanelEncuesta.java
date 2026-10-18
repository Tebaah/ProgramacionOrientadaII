package Encuesta;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Panel que contiene todos los componentes visuales de la encuesta de
 * retroalimentación para la empresa Servicebottios.
 *
 * El manejo de los eventos de los botones se delega a la clase
 * {@link GestorEventos}.
 *
 * @author Desarrollador Servicebottios
 * @version 1.0
 */
public class PanelEncuesta extends JPanel {

    private static final long serialVersionUID = 1L;

    /** Opciones de servicio que se pueden evaluar en la encuesta. */
    private static final String[] SERVICIOS = {
            "Atención al cliente", "Soporte técnico", "Ventas", "Postventa" };

    private JTextField txtNombre;
    private JComboBox<String> cmbServicio;
    private JRadioButton rbPuntaje1;
    private JRadioButton rbPuntaje2;
    private JRadioButton rbPuntaje3;
    private JRadioButton rbPuntaje4;
    private JRadioButton rbPuntaje5;
    private ButtonGroup grupoPuntaje;
    private JTextArea txtComentarios;
    private JButton btnEnviar;
    private JButton btnLimpiar;
    private JLabel lblEstado;

    /**
     * Constructor del panel. Crea los componentes, los distribuye en la
     * ventana y registra los eventos de los botones.
     */
    public PanelEncuesta() {
        setLayout(new BorderLayout(0, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        add(crearEncabezado(), BorderLayout.NORTH);
        add(crearFormulario(), BorderLayout.CENTER);
        add(crearPie(), BorderLayout.SOUTH);

        // Se crea el gestor de eventos y se registran los listeners de los botones
        GestorEventos gestor = new GestorEventos(this);
        gestor.registrarEventos();
    }

    /**
     * Crea el encabezado del panel con el título y el subtítulo de la encuesta.
     *
     * @return panel con el encabezado
     */
    private JPanel crearEncabezado() {
        JPanel encabezado = new JPanel(new BorderLayout(0, 5));
        JLabel titulo = new JLabel("Encuesta de Retroalimentación");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        JLabel subtitulo = new JLabel(
                "Su opinión nos ayuda a mejorar el servicio de Servicebottios.");
        subtitulo.setForeground(Color.GRAY);
        encabezado.add(titulo, BorderLayout.NORTH);
        encabezado.add(subtitulo, BorderLayout.SOUTH);
        return encabezado;
    }

    /**
     * Crea el formulario con los datos de la encuesta (nombre, servicio,
     * calificación y comentarios).
     *
     * @return panel con el formulario
     */
    private JPanel crearFormulario() {
        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBorder(BorderFactory.createTitledBorder("Datos de la encuesta"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fila 0: nombre del cliente
        formulario.add(new JLabel("Nombre:"), configurar(gbc, 0, 0, 1, 0));

        txtNombre = new JTextField(25);
        formulario.add(txtNombre, configurar(gbc, 1, 0, 2, 1));

        // Fila 1: servicio evaluado
        formulario.add(new JLabel("Servicio:"), configurar(gbc, 0, 1, 1, 0));

        cmbServicio = new JComboBox<String>(SERVICIOS);
        formulario.add(cmbServicio, configurar(gbc, 1, 1, 2, 1));

        // Fila 2: calificación de 1 a 5
        formulario.add(new JLabel("Calificación:"), configurar(gbc, 0, 2, 1, 0));

        formulario.add(crearOpcionesPuntaje(), configurar(gbc, 1, 2, 2, 1));

        // Fila 3: comentarios
        gbc.anchor = GridBagConstraints.NORTHWEST;
        formulario.add(new JLabel("Comentarios:"), configurar(gbc, 0, 3, 1, 0));

        txtComentarios = new JTextArea(6, 25);
        txtComentarios.setLineWrap(true);
        txtComentarios.setWrapStyleWord(true);
        gbc.fill = GridBagConstraints.BOTH;
        formulario.add(new JScrollPane(txtComentarios), configurar(gbc, 1, 3, 2, 1));

        return formulario;
    }

    /**
     * Configura las restricciones del GridBagLayout y las retorna para
     * colocar un componente en la celda indicada.
     *
     * @param gbc   restricciones a configurar
     * @param x     columna donde se ubica el componente
     * @param y     fila donde se ubica el componente
     * @param ancho número de columnas que ocupa el componente
     * @param pesoX peso horizontal del componente
     * @return las mismas restricciones ya configuradas
     */
    private GridBagConstraints configurar(GridBagConstraints gbc, int x, int y,
            int ancho, double pesoX) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = ancho;
        gbc.weightx = pesoX;
        return gbc;
    }

    /**
     * Crea el grupo de botones de opción para calificar el servicio de 1 a 5.
     *
     * @return panel con las opciones de calificación
     */
    private JPanel crearOpcionesPuntaje() {
        JPanel panelPuntaje = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        grupoPuntaje = new ButtonGroup();
        rbPuntaje1 = crearOpcionPuntaje("1");
        rbPuntaje2 = crearOpcionPuntaje("2");
        rbPuntaje3 = crearOpcionPuntaje("3");
        rbPuntaje4 = crearOpcionPuntaje("4");
        rbPuntaje5 = crearOpcionPuntaje("5");
        panelPuntaje.add(rbPuntaje1);
        panelPuntaje.add(rbPuntaje2);
        panelPuntaje.add(rbPuntaje3);
        panelPuntaje.add(rbPuntaje4);
        panelPuntaje.add(rbPuntaje5);
        JLabel ayuda = new JLabel("(1 = Muy malo, 5 = Excelente)");
        ayuda.setForeground(Color.GRAY);
        panelPuntaje.add(ayuda);
        return panelPuntaje;
    }

    /**
     * Crea un botón de opción de puntaje y lo agrega al grupo de botones.
     *
     * @param etiqueta valor que se muestra en el botón de opción
     * @return el botón de opción creado
     */
    private JRadioButton crearOpcionPuntaje(String etiqueta) {
        JRadioButton opcion = new JRadioButton(etiqueta);
        grupoPuntaje.add(opcion);
        return opcion;
    }

    /**
     * Crea el pie del panel con la etiqueta de estado y los botones de acción.
     *
     * @return panel con el pie de la encuesta
     */
    private JPanel crearPie() {
        JPanel pie = new JPanel(new BorderLayout(0, 5));

        lblEstado = new JLabel(" ");
        lblEstado.setForeground(Color.GRAY);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setActionCommand(GestorEventos.ACCION_LIMPIAR);

        btnEnviar = new JButton("Enviar encuesta");
        btnEnviar.setActionCommand(GestorEventos.ACCION_ENVIAR);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnEnviar);

        pie.add(lblEstado, BorderLayout.WEST);
        pie.add(panelBotones, BorderLayout.EAST);
        return pie;
    }

    /**
     * @return el botón para enviar la encuesta
     */
    public JButton getBtnEnviar() {
        return btnEnviar;
    }

    /**
     * @return el botón para limpiar el formulario
     */
    public JButton getBtnLimpiar() {
        return btnLimpiar;
    }

    /**
     * @return el nombre ingresado por el usuario, sin espacios sobrantes
     */
    public String getNombre() {
        return txtNombre.getText().trim();
    }

    /**
     * @return el servicio seleccionado por el usuario
     */
    public String getServicio() {
        return (String) cmbServicio.getSelectedItem();
    }

    /**
     * @return la calificación seleccionada (1 a 5) o 0 si no se seleccionó ninguna
     */
    public int getCalificacion() {
        if (rbPuntaje1.isSelected()) {
            return 1;
        }
        if (rbPuntaje2.isSelected()) {
            return 2;
        }
        if (rbPuntaje3.isSelected()) {
            return 3;
        }
        if (rbPuntaje4.isSelected()) {
            return 4;
        }
        if (rbPuntaje5.isSelected()) {
            return 5;
        }
        return 0;
    }

    /**
     * @return los comentarios ingresados por el usuario, sin espacios sobrantes
     */
    public String getComentarios() {
        return txtComentarios.getText().trim();
    }

    /**
     * Muestra un mensaje de estado en el pie del panel.
     *
     * @param mensaje texto que se desea mostrar
     * @param color   color con el que se muestra el mensaje
     */
    public void setEstado(String mensaje, Color color) {
        lblEstado.setForeground(color);
        lblEstado.setText(mensaje);
    }

    /**
     * Restablece todos los campos del formulario a su estado inicial.
     */
    public void limpiarFormulario() {
        txtNombre.setText("");
        cmbServicio.setSelectedIndex(0);
        grupoPuntaje.clearSelection();
        txtComentarios.setText("");
        enfocarNombre();
    }

    /**
     * Coloca el cursor en el campo del nombre.
     */
    public void enfocarNombre() {
        txtNombre.requestFocusInWindow();
    }
}

