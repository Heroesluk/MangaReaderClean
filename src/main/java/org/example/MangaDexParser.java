package org.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class MangaDexParser {

    ArrayList<MangaObject> temp = new ArrayList<MangaObject>();


    public ArrayList<MangaObject> ParseSearchResults(JsonObject data) {
        JsonArray collection = data.get("data").getAsJsonArray();

        for (int i = 0; i < collection.size(); i++) {
            temp.add(JsonToManga(collection.get(i).getAsJsonObject()));

        }


        return temp;
    }


    private MangaObject JsonToManga(JsonObject manga_data) {
        String id = manga_data.get("id").toString();
        JsonObject attributes = manga_data.get("attributes").getAsJsonObject();
        String title = attributes.get("title").toString();




        return new MangaObject();

    }


}


class MangaObject {


}
