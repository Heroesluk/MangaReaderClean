package org.example;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

//418791c0-35cf-4f87-936b-acd9cddf0989 kaoru hana

//todo:
//More tests, negative tests
//Download chapter, think about how path to the chapter should be defined
//Basic application:
    //Search: display results -> click result= Move scene to manga showcase -> click chapter = download chapter and open reader

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {


        UserLogic usrLogic = new UserLogic();
        usrLogic.searchForManga("Kaoru Hana wa");
        usrLogic.fetch_chapters("418791c0-35cf-4f87-936b-acd9cddf0989");
        usrLogic.print_out("M");



        MangaChapterObject chapter  = usrLogic.get_chapter(5);

        chapter.AddChapterData(usrLogic.fetch_chapter_data(chapter.id));
        Handler_HTTP.Download_Chapter(chapter);



        System.out.println(chapter.toString());

        Handler_HTTP.Download_image("https://uploads.mangadex.org/data/e0cad38feab0cbb167a3ac1f19dbf3a7/1-500a9595a31a6d1fa73e727d823e80d87cc7057f18bee2ebaec305bca3529279.png","Kaoru_hana1.png");




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
            if(response.statusCode()!=200){
                return null;
            };

            System.out.println("httpGetRequest: " + request_link);
            System.out.println("httpGetRequest: " + responseBody);


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
        return new ArrayList<MangaObject>();

    }

    public static ArrayList<MangaChapterObject> RetrieveMangaChapters(String manga_id, String target_language) {
        JsonObject response = Handler_HTTP.httpGetRequest("https://api.mangadex.org/manga/" + manga_id + "/feed" + "?translatedLanguage[]=" + target_language);

        if (response != null) {
            return MangaDexParser.GetMangaChapters(response);

        }
        return new ArrayList<MangaChapterObject>();

    }

    public static ChapterData GetChapterData(String chapter_id){
        JsonObject response = Handler_HTTP.httpGetRequest("https://api.mangadex.org/at-home/server/" + chapter_id);
        if (response != null) {
            return MangaDexParser.JsonToChapterData(response);

        }
        System.out.println("https://api.mangadex.org/at-home/server/" + chapter_id);
        return null;


    }


    //4-e88037c3d89cb2f3f9fcc9967a7a59516bfc01f5854d5e68a7ac68cde390b245.jpg

    //cec05b3eb895b9f5e467bb31abffbfb7
    //https://uploads.mangadex.org

    String linkk  = "https://uploads.mangadex.org/data/e0cad38feab0cbb167a3ac1f19dbf3a7/1-500a9595a31a6d1fa73e727d823e80d87cc7057f18bee2ebaec305bca3529279.png";

    public static void Download_image(String link, String file_name) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .uri(URI.create(link))
                .headers("Accept-Enconding", "gzip, deflate")
                .build();
        HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

        InputStream is = response.body();
        FileOutputStream fos = new FileOutputStream(new File(file_name));
        int inByte;
        while((inByte = is.read()) != -1)
            fos.write(inByte);
        is.close();
        fos.close();
    }

    public static void Download_Chapter(MangaChapterObject chapter) throws IOException, InterruptedException {
        String link_base = chapter.data.baseUrl + "/data/" + chapter.data.hash + "/";


        for(String link: chapter.data.img_links){
            Download_image(link_base +link,link);
        }

    }

}




