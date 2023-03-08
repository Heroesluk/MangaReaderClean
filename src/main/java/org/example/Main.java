package org.example;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.*;
import java.net.URI;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        System.out.println("Hello world!");

//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create("https://postman-echo.com/get"))
//                .GET()
//                .build();

        Handler_HTTP.httpGetRequest();

    }


    //HttpResponse<String> response =



};

class Handler_HTTP{
    public static void httpGetRequest() throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .uri(URI.create("http://jsonplaceholder.typicode.com/posts/1"))
                .headers("Accept-Enconding", "gzip, deflate")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String responseBody = response.body();
        int responseStatusCode = response.statusCode();

        System.out.println("httpGetRequest: " + responseBody);
        System.out.println("httpGetRequest status code: " + responseStatusCode);
    }
}


