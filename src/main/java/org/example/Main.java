package org.example;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        System.out.println("Hello world!");

//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create("https://postman-echo.com/get"))
//                .GET()
//                .build();

        JsonObject response = Handler_HTTP.httpGetRequest("https://api.mangadex.org/manga?title=Kaoru+hana");

        MangaDexParser parser = new MangaDexParser();
        parser.ParseSearchResults(response);


    }

};

class Handler_HTTP{
    public static JsonObject httpGetRequest(String request_link) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .uri(URI.create(request_link))
                .headers("Accept-Enconding", "gzip, deflate")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String responseBody = response.body();
        int responseStatusCode = response.statusCode();

        System.out.println("httpGetRequest: " + request_link);
        System.out.println("httpGetRequest: " + responseBody);
        System.out.println("httpGetRequest status code: " + responseStatusCode);


        return JsonParser.parseString(responseBody).getAsJsonObject();
    }

}




