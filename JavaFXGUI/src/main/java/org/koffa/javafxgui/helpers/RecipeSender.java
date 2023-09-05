package org.koffa.javafxgui.helpers;

import com.google.gson.Gson;
import org.koffa.javafxgui.dto.Recipe;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RecipeSender {
    private final HttpURLConnection connection;

    /**
     * Creates a new RecipeSender
     * @throws IOException if the connection fails
     */
    public RecipeSender() throws IOException {
        String urlString = "http://localhost:8080/api/v1/recipe/publish";
        URL url = new URL(urlString);
        this.connection = (HttpURLConnection) url.openConnection();
    }

    /**
     * Sends a recipe to the database
     * @param recipe the recipe to send
     * @return the response from the database
     * @throws ProtocolException if the protocol is not supported
     */
    public String sendRecipe(Recipe recipe) throws ProtocolException {
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);
        Gson gson = new Gson();
        String jsonString = gson.toJson(recipe);
        try(OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return response.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
