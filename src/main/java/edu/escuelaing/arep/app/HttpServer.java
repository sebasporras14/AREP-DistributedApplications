package edu.escuelaing.arep.app;

import java.net.*;
import java.io.*;
import java.util.HashMap;
import org.json.JSONObject;

public class HttpServer {

    private static HttpConnection httpConnection = new HttpConnection();
    private static HashMap<String, String> movieCache = new HashMap<String, String>();

    /**
     * Método principaldel servidor http.
     *
     * @throws IOException Si ocurre un error al configurar o aceptar conexiones.
     */
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        Socket clientSocket = null;
        while (!serverSocket.isClosed()) {
            try {
                System.out.println("Operando Buscador de Películas ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String inputLine, outputLine;
            boolean firstLine = true;
            String uriString = "";
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received: " + inputLine);
                if (firstLine) {
                    firstLine = false;
                    uriString = inputLine.split(" ")[1];
                }
                if (!in.ready()) {
                    break;
                }
            }
            System.out.println("URI: " + uriString);
            if (uriString.split("=").length > 1) {
                outputLine = getHello(uriString.split("=")[1]);
            } else if (uriString.startsWith("/movie")) {
                outputLine = "";
            } else {
                outputLine = getIndexResponse();
            }
            System.out.println("OUTPUT= " + outputLine);
            out.println(outputLine);
            out.close();
            in.close();
        }
        clientSocket.close();
        serverSocket.close();
    }

    /**
     * verifica si la pelicula esta en el cache, si esta devuelva la informacion, si no la almacena en el cache y devuelve la informacion
     *
     * @param movie el nombre de la pelicula que se quiere buscar.
     * @return Respuesta con la información de la pelicula.
     * @throws IOException Si ocurre un error al obtener la información.
     */
    private static String getHello(String movie) throws IOException {
        if (movieCache.containsKey(movie)) {
            return "HTTP/1.1 200 OK"
                    + "Content-Type: application/json\r\n"
                    + "\r\n" + movieInfo(movieCache.get(movie));
        } else {
            String movieData = httpConnection.getMovieData(movie);
            movieCache.put(movie, movieData);
            return "HTTP/1.1 200 OK"
                    + "Content-Type: application/json\r\n"
                    + "\r\n" + movieInfo(movieData);
        }
    }

    private static String movieInfo(String data) {
        JSONObject json = new JSONObject(data);
        String title = json.get("Title").toString();
        String runtime = json.get("Runtime").toString();
        String genre = json.get("Genre").toString();
        String actors = json.get("Actors").toString();
        String released = json.get("Released").toString();
        String poster = json.get("Poster").toString();
        return "<h2>Movie information</h2>"
                + "<p><strong>Title:</strong> " + title + "</p>"
                + "<p><strong>Runtime:</strong> " + runtime + "</p>"
                + "<p><strong>Genre:</strong> " + genre + "</p>"
                + "<p><strong>Actors:</strong> " + actors + "</p>"
                + "<p><strong>Released:</strong> " + released + "</p>"
                + "<img class=img src=\"" + poster + "\" alt=\"Póster de la película\">";
    }

    /**
     * retorma la pagina HTML con la informacion de la pelicula
     * @return Respuesta con la pagina.
     */
    public static String getIndexResponse() {
        String response = "HTTP/1.1 200 OK"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>\r\n" + //
                "<html>\r\n" + //
                "\r\n" + //
                "<head>\r\n" + //
                "    <meta charset=\\\"UTF-8\\\">\r\n" + //
                "    <meta name=\\\"viewport\\\" content=\\\"width=device-width, initial-scale=1.0\\\">\r\n" + //
                "    <title>Buscador de peliculas</title>\r\n" + //
                "    <style>\r\n" + //
                "        h1 {\r\n" + //
                "            color: aqua\r\n" + //
                "        }\r\n" + //
                "\r\n" + //
                "        body {\r\n" + //
                "            background-color: rgb(58, 60, 62);\r\n" + //
                "            justify-content: center;\r\n" + //
                "            align-items: center;\r\n" + //
                "            text-align: center;\r\n" + //
                "\r\n" + //
                "        }\r\n" + //
                "\r\n" + //
                "        .main {\r\n" + //
                "            position: absolute;\r\n" + //
                "            top: 25%;\r\n" + //
                "            left: 20%;\r\n" + //
                "            flex-direction: column;\r\n" + //
                "            height: 40%;\r\n" + //
                "            width: 20%;\r\n" + //
                "            border-radius: 5%;\r\n" + //
                "            background-color: rgb(20, 20, 21);\r\n" + //
                "            box-shadow: -2px 0px 22px 7px rgba(36, 133, 224, 1);\r\n" + //
                "            padding: 20px;\r\n" + //
                "            margin: 0px;\r\n" + //
                "        }\r\n" + //
                "        #getrespmsg{\r\n" + //
                "            position: absolute;\r\n" + //
                "            top: 7%;\r\n" + //
                "            left: 50%;\r\n" + //
                "            flex-direction: column;\r\n" + //
                "            height: 80%;\r\n" + //
                "            width: 30%;\r\n" + //
                "            border-radius: 5%;\r\n" + //
                "            background-color: rgb(20, 20, 21);\r\n" + //
                "            box-shadow: -2px 0px 22px 7px rgb(27, 216, 219);\r\n" + //
                "            padding: 20px;\r\n" + //
                "            margin: 0px;\r\n" + //
                "            color: aliceblue;\r\n" + //
                "        }\r\n" + //
                "        .boton{\r\n" + //
                "            background-color: rgb(11, 168, 212);\r\n" + //
                "            height: 40px;\r\n" + //
                "            width: 100px;\r\n" + //
                "            border-radius: 20%;\r\n" + //
                "            font-family: Arial, Helvetica, sans-serif;\r\n" + //
                "        }\r\n" + //
                "        .boton:hover {\r\n" + //
                "            background-color: rgb(9, 91, 113);\r\n" + //
                "        }\r\n" + //
                "        .img {\r\n" + //
                "           height: 200px;\r\n" + //
                "        }\r\n" + //
                "    </style>\r\n" + //
                "</head>\r\n" + //
                "\r\n" + //
                "<body>\r\n" + //
                "    <div class=\"main\">\r\n" + //
                "        <from action=\"/hello\">\r\n" + //
                "            <h1>Buscador de peliculas</h1>\r\n" + //
                "            <br><br><br>\r\n" + //
                "            <input type=\"text\" id=\"pelicula\" value=\"x\">\r\n" + //
                "            <br><br>\r\n" + //
                "            <input class=\"boton\" type=\"button\" value=\"Buscar\" onclick=\"loadGetMsg()\">\r\n" + //
                "        </from>\r\n" + //
                "    </div>\r\n" + //
                "    <div id=\"getrespmsg\">\r\n" + //
                "        holaa\r\n" + //
                "    </div>\r\n" + //
                "    <script>\r\n" + //
                "        function loadGetMsg() {\r\n" + //
                "            let nameVar = document.getElementById(\"pelicula\").value;\r\n" + //
                "            const xhttp = new XMLHttpRequest();\r\n" + //
                "            xhttp.onload = function () {\r\n" + //
                "                document.getElementById(\"getrespmsg\").innerHTML =\r\n" + //
                "                    this.responseText;\r\n" + //
                "            }\r\n" + //
                "            xhttp.open(\"GET\", \"/movie?tittle=\" + nameVar);\r\n" + //
                "            xhttp.send();\r\n" + //
                "        }\r\n" + //
                "    </script>\r\n" + //
                "</body>\r\n" + //
                "</html>";
        return response;
    }
}
