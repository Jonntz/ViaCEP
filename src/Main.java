import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    public static void main ( String[] args ) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite um CEP para pesquisar: ");
        String cep = scanner.nextLine();

        Gson gson = new GsonBuilder ()
                .setPrettyPrinting()
                .create();

        String baseUrl = "https://viacep.com.br/ws/" + cep + "/json/";

        try{
            HttpClient http = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri (URI.create(baseUrl.replaceAll("-", "").trim()))
                    .build();
            HttpResponse <String> response = http.send(request, HttpResponse.BodyHandlers.ofString());

            Adress adress = gson.fromJson(response.body(), Adress.class);

            FileWriter writer = new FileWriter("endereço.json");
            writer.write(gson.toJson(adress));
            writer.close();

        } catch (IOException | RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        }

        System.out.println("O programa foi finalizado!");
    }
}