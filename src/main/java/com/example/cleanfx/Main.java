package com.example.cleanfx;


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




