package org.example.appesports.ApiExterna;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Clase para interactuar con la API de Grog (Groq.com) y obtener respuestas de un modelo de lenguaje.
 * Esta clase envía una solicitud POST a la API con un mensaje del usuario y procesa la respuesta para extraer el contenido relevante.
 */
public class GrogAPI {
    private static final String API_KEY = "API_KEY_AQUI";

    private static final String API_URL = "https://api.groq.com/openai/v1/chat/completions";

    private static final String MODELO = "llama-3.3-70b-versatile";

    /**
     * Envía un mensaje a la API de Grog y devuelve la respuesta procesada.
     * @param mensaje El mensaje que se desea enviar a la API.
     * @return La respuesta procesada de la API.
     * @throws Exception Si ocurre un error durante la solicitud o el procesamiento de la respuesta.
     */
    public static String preguntarALaIA(String mensaje) throws Exception {

            String mensajeEscapado = escaparParaJson(mensaje);

            String cuerpoJson = "{"
                    + "\"model\": \"" + MODELO + "\","
                    + "\"messages\": ["
                    + "  { \"role\": \"system\", \"content\": \"Eres un asistente directo. Responde siempre en español de forma clara y concisa.\" },"
                    + "  { \"role\": \"user\",   \"content\": \""  +"Teniendo en cuenta los puntos que tiene cada equipo ahora mismo en la competición razona cual" +
                    "que es el equipo que podría ganar la competición, la respuesta no puede tener mas de 1000 caracteres y necesito que me des un equipo si o si," +
                    "no uses negrita ni ningún estilo de texto" + mensajeEscapado + "\" }"
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
                throw new Exception("Error HTTP " + respuesta.statusCode() + ". Comprueba tu API Key.");
            }

            return extraerContenidoDeRespuesta(respuesta.body());
    }

    /**
     * Extrae el contenido relevante de la respuesta JSON de la API.
     * @param json La respuesta JSON completa de la API.
     * @return El contenido extraído y procesado de la respuesta.
     */
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



        int e = contenido.toString().trim().indexOf("},logprobs:");

        String respuesta = contenido.toString().trim().substring(0, e);

        return respuesta;
    }
    /**
     * Escapa caracteres especiales en un texto para que puedan ser incluidos correctamente en un JSON.
     * @param texto El texto original que se desea escapar.
     * @return El texto escapado, listo para ser incluido en un JSON.
     */
    private static String escaparParaJson(String texto) {
        return texto
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
