package edu.escuelaing.arep.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnection {

    private static final String USER_AGENT = "Mozilla/5.0 Chrome/51.0.2704.103 Safari/537.36";
    private static final String GET_URL = "https://www.omdbapi.com/?apikey=4314dbae&t=";

    /**
     * Realiza una solicitud una API gratuita para obtener los datos de cualquier pelicula.
     *
     * @param movie nombre de la pelicula.
     * @return Json con la informacion de la pelicula.
     * @throws IOException Si ocurre un error en la comunicación.
     */
    public String getMovieData(String movie) throws IOException {
        URL obj = new URL(GET_URL + movie);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { 
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response.toString());
            return response.toString();
        } else {
            System.out.println("GET request not worked");
        }
        System.out.println("GET DONE");
        return "{}";
    }
}
