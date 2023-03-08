package org.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class MangaDexParser {



    public ArrayList<MangaObject> ParseSearchResults(JsonObject data) {
        ArrayList<MangaObject> temp = new ArrayList<MangaObject>();

        JsonArray collection = data.get("data").getAsJsonArray();

        for (int i = 0; i < collection.size(); i++) {
            temp.add(JsonToManga(collection.get(i).getAsJsonObject()));

        }

        return temp;
    }


    private MangaObject JsonToManga(JsonObject manga_data) {
        JsonObject attributes = manga_data.get("attributes").getAsJsonObject();
        JsonArray relationships = manga_data.get("relationships").getAsJsonArray();

        String cover_file_name = relationships.get(2).getAsJsonObject().get("attributes").getAsJsonObject().get("fileName").toString();
        String id = manga_data.get("id").toString();
        String title = attributes.get("title").toString();


        return new MangaObject(id,title,cover_file_name);

    }

    public static void PrintSearchResults(ArrayList<MangaObject> results){
        for(MangaObject res: results){
            System.out.println(res.toString());
        }

    }

}


class MangaObject {
    final String id;
    final String title;
    final String cover_file_name;

    MangaObject(String id, String title, String cover_file_name){
        this.id = id;
        this.title = title;
        this.cover_file_name = cover_file_name;

    }

    @Override
    public String toString() {
        return "MangaObject{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", cover_file_name='" + cover_file_name + '\'' +
                '}';
    }
}
