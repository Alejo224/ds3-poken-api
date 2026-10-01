import org.json.JSONObject;

import javax.swing.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PokeAPi {

    public void consultarPokemon() {
        try {

            // Solicitar el nombre del pokemon
            String nombrePokemon = JOptionPane.showInputDialog("Ingrese el nombre del pokemon:");

            HttpClient client = HttpClient.newHttpClient();

            // Crear peticion al servidor de pokeapi
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://pokeapi.co/api/v2/pokemon/"+nombrePokemon))
                    .build();

            // Ejecutar solicitud
            HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JSONObject json = new JSONObject(response.body());

                System.out.println("ID Pokemon: " + json.getInt("id"));
                System.out.println("Nombre del pokemon:" + json.getString("name"));



                json.getJSONArray("abilities").forEach(habilidad -> {
                    // Acceder a un objecto sin nombre
                    JSONObject abilitiJson = (JSONObject) habilidad;

                    JSONObject abilityName = abilitiJson.getJSONObject("ability");

                    System.out.println("Nombre de habilidad:" + abilityName.getString("name"));
                });

                // ingresar stats
                json.getJSONArray("stats").forEach( estadisticas -> {
                    JSONObject statJson = (JSONObject)  estadisticas;

                    JSONObject staNAme = statJson.getJSONObject("stat");

                    System.out.println("Stas:" + staNAme.getString("name"));
                });

            } else {
                JOptionPane.showMessageDialog(null, "El pokemon no existe");
            }
        } catch (IOException | InterruptedException e) {
           e.printStackTrace();
        }
    }

    //  psvm
    public static void main(String[] args) {
        PokeAPi pokeAPi = new PokeAPi();
        pokeAPi.consultarPokemon();
    }


}
