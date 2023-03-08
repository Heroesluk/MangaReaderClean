package org.example;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args)  {


        UserLogic usrLogic = new UserLogic();
        usrLogic.searchForManga("Kaoru hana wa rin to");
        usrLogic.fetch_chapters("418791c0-35cf-4f87-936b-acd9cddf0989");
        usrLogic.print_out("M");
        usrLogic.print_out("C");




    }

};

class Handler_HTTP{
    final static String base_url = "https://api.mangadex.org/";
    final static String base_data_url = "https://uploads.mangadex.org/";


    public static JsonObject httpGetRequest(String request_link) {
        try {
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

        } catch (Exception e) {
            System.out.println("Error occured during the request");
            return null;
        }

    }


    public static ArrayList<MangaObject> SearchForManga(String manga_name) {
        String manga_name_f = manga_name.replaceAll(" ", "+");
        JsonObject response = Handler_HTTP.httpGetRequest("https://api.mangadex.org/manga?includes[]=cover_art&title=" + manga_name_f);

        if (response != null) {
            return MangaDexParser.ParseSearchResults(response);
        }
        return null;

    }

    public static ArrayList<MangaChapterObject> RetrieveMangaChapters(String manga_id, String target_language) {
        JsonObject response = Handler_HTTP.httpGetRequest("https://api.mangadex.org/manga/" + manga_id + "/feed" + "?translatedLanguage[]=" + target_language);
        if (response != null) {
            return MangaDexParser.GetMangaChapters(response);


        }
        return null;


    }


}




