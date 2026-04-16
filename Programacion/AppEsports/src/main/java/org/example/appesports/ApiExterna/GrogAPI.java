package org.example.appesports.ApiExterna;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GrogAPI {
    private static final String API_KEY = "API_KEY_AQUI";

    private static final String API_URL = "https://api.groq.com/openai/v1/chat/completions";

    private static final String MODELO = "llama-3.3-70b-versatile";

    public static String preguntarALaIA(String mensaje) throws Exception {
        try {
            String mensajeEscapado = escaparParaJson(mensaje);

            String cuerpoJson = "{"
                    + "\"model\": \"" + MODELO + "\","
                    + "\"messages\": ["
                    + "  { \"role\": \"system\", \"content\": \"Eres un asistente directo. Responde siempre en español de forma clara y concisa.\" },"
                    + "  { \"role\": \"user\",   \"content\": \""  +"Dime la prioridad de esta tarea del 1 al 5 (1 siendo la mayor prioridad) y porque brevemente, " + mensajeEscapado + "\" }"
                    + "]"
                    + "}";

            HttpClient cliente = HttpClient.newHttpClient();

            HttpRequest peticion = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + API_KEY)  // <-- Autenticación con Bearer token
                    .POST(HttpRequest.BodyPublishers.ofString(cuerpoJson))
                    .build();

            HttpResponse<String> respuesta = cliente.send(
                    peticion,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (respuesta.statusCode() == 429) {
                throw new Exception("Límite de peticiones alcanzado. Espera unos segundos e inténtalo de nuevo.");
            }
            if (respuesta.statusCode() != 200) {
                return "Error HTTP " + respuesta.statusCode() + ". Comprueba tu API Key.";
            }

            return extraerContenidoDeRespuesta(respuesta.body());

        } catch (Exception e) {
            return "Error de conexión: " + e.getMessage();
        }
    }

    private static String extraerContenidoDeRespuesta(String json) {

        String clave = "\"content\":\"";

        int pos = json.indexOf(clave);
        if (pos != -1) {
            pos = json.indexOf(clave, pos + 1);
        }

        if (pos == -1) {
            pos = json.indexOf(clave);
        }

        if (pos == -1) {
            return "No se pudo interpretar la respuesta de la API.";
        }

        pos += clave.length();

        StringBuilder contenido = new StringBuilder();
        for (int i = pos; i < json.length(); i++) {
            char c = json.charAt(i);

            if (c == '\\' && i + 1 < json.length()) {
                char siguiente = json.charAt(i + 1);
                switch (siguiente) {
                    case 'n'  -> { contenido.append('\n'); i++; }
                    case 't'  -> { contenido.append('\t'); i++; }
                    case '"'  -> { contenido.append('"');  i++; }
                    case '\\' -> { contenido.append('\\'); i++; }
                    default   -> { contenido.append(c); }
                }
            } else if (c != '"') {
                contenido.append(c);
            }
        }
        return contenido.toString().trim();
    }

    private static String escaparParaJson(String texto) {
        return texto
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
