package org.koffa.javafxgui.helpers;

import com.google.gson.Gson;
import org.koffa.javafxgui.dto.Recipe;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RecipeApiFacade implements RecipeSender {

    private final String url;

    /**
     * Creates a new RecipeSender
     * @throws IOException if the connection fails
     */
    public RecipeApiFacade(String url) {
        // Open the connection
        this.url = url;
    }
    public boolean test() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url + "/test").openConnection();
        connection.setRequestMethod("GET");
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            getResponse(br, response);
            return response.toString().equals("Test successful");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            connection.disconnect();
        }
    }

    private static void getResponse(BufferedReader br, StringBuilder response) throws IOException {
        String responseLine;
        while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
        }
    }

    /**
     * Sends a recipe to the database
     * @param recipe the recipe to send
     * @return the response from the database
     * @throws ProtocolException if the protocol is not supported
     */
    @Override
    public String send(Recipe recipe) throws IOException {
        // Open the connection
        HttpURLConnection connection = (HttpURLConnection) new URL(url + "/publish").openConnection();
        // Set the request method and headers
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);
        // Convert the recipe to a json string
        Gson gson = new Gson();
        String jsonString = gson.toJson(recipe);
        // Send the request
        try(OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Get the response
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            getResponse(br, response);
            connection.disconnect();
            return response.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            connection.disconnect();
        }
    }
}
