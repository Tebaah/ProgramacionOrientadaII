package KasaPackage;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class IPC extends JFrame {

    private static final String API_URL = "http://localhost:3000";
    private static final int TIMEOUT_MS = 5000;

    private final JTextArea resultTextArea;
    private final JButton fetchDataButton;

    public IPC() {
        super("Web Service App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        resultTextArea = new JTextArea();
        resultTextArea.setEditable(false);
        resultTextArea.setLineWrap(true);
        resultTextArea.setWrapStyleWord(true);

        fetchDataButton = new JButton("Obtener datos");
        fetchDataButton.addActionListener(e -> fetchData());

        setLayout(new BorderLayout());
        add(new JScrollPane(resultTextArea), BorderLayout.CENTER);
        add(fetchDataButton, BorderLayout.SOUTH);
    }

    private void fetchData() {
        resultTextArea.setText("Consultando " + API_URL + " ...");
        fetchDataButton.setEnabled(false);

        // La petición HTTP se hace fuera del EDT para que la ventana no se congele.
        new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() throws Exception {
                return fetchJson();
            }

            @Override
            protected void done() {
                fetchDataButton.setEnabled(true);
                try {
                    parseAndDisplayData(get());
                } catch (Exception ex) {
                    Throwable cause = ex.getCause() != null ? ex.getCause() : ex;
                    resultTextArea.setText("Error: " + cause.getMessage() + "\n\n"
                            + "Verifica que la API esté corriendo en " + API_URL);
                }
            }
        }.execute();
    }

    private String fetchJson() throws IOException {
        URL url = URI.create(API_URL).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setConnectTimeout(TIMEOUT_MS);
            connection.setReadTimeout(TIMEOUT_MS);

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("El servidor respondió con el código " + responseCode);
            }

            StringBuilder response = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }
            return response.toString();
        } finally {
            connection.disconnect();
        }
    }

    private void parseAndDisplayData(String jsonData) throws Exception {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(jsonData);

        // La API responde {"IPC":[...]}; se aceptan también otras formas de la clave.
        JSONArray registrosArray = (JSONArray) jsonObject.get("IPC");
        if (registrosArray == null) {
            registrosArray = (JSONArray) jsonObject.get("ipc");
        }
        if (registrosArray == null) {
            for (Object value : jsonObject.values()) {
                if (value instanceof JSONArray) {
                    registrosArray = (JSONArray) value;
                    break;
                }
            }
        }
        if (registrosArray == null) {
            throw new IllegalArgumentException(
                    "La respuesta JSON no contiene una lista de registros");
        }

        StringBuilder text = new StringBuilder();
        text.append("IPC - Índice de Precios al Consumidor\n\n");
        for (Object registroObj : registrosArray) {
            JSONObject registro = (JSONObject) registroObj;
            Object periodo = registro.get("periodo");
            if (periodo == null) {
                periodo = registro.get("mes");
            }
            text.append("Periodo: ").append(periodo).append('\n');
            text.append("Valor: ").append(registro.get("valor")).append("\n\n");
        }

        resultTextArea.setText(text.toString());
        resultTextArea.setCaretPosition(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new IPC().setVisible(true));
    }
}
