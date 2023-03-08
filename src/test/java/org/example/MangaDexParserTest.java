package org.example;

import com.google.gson.JsonObject;
import junit.framework.TestCase;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class MangaDexParserTest extends TestCase {
    JsonObject response = Handler_HTTP.httpGetRequest("https://api.mangadex.org/manga?includes[]=cover_art&title=Kaoru+hana+wa");
    ArrayList<MangaObject> results = MangaDexParser.ParseSearchResults(response);
    MangaObject kaoru_hana = results.get(0);



    public MangaDexParserTest() throws URISyntaxException, IOException, InterruptedException {
    }


    public void testParseSearchResults() {
        assertEquals(results.size(),2);
    }

    public void testMangaObjectAssign(){

        assertEquals(kaoru_hana.id, "418791c0-35cf-4f87-936b-acd9cddf0989");
        assertEquals(kaoru_hana.title, "The Fragrant Flower Blooms With Dignity");
        assertEquals(kaoru_hana.cover_file_name,"48683660-9ed2-4a9f-b2f4-fccb77455c90.jpg");


    }

    public void testSubsequentSearch() throws URISyntaxException, IOException, InterruptedException {
        JsonObject response = Handler_HTTP.httpGetRequest("https://api.mangadex.org/manga?includes[]=cover_art&title=Jojo");
        ArrayList<MangaObject> results = MangaDexParser.ParseSearchResults(response);
        assertEquals(results.size(),10);

    }

    public  void testFetchingChapters(){
        JsonObject response = Handler_HTTP.httpGetRequest("https://api.mangadex.org/manga/f98660a1-d2e2-461c-960d-7bd13df8b76d/feed");
        ArrayList<MangaChapterObject> chapters = MangaDexParser.GetMangaChapters(response);
        assertEquals(chapters.size(),20);
    }

}