package org.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class MangaDexParser {

    public static ArrayList<MangaObject> ParseSearchResults(JsonObject data) {
        ArrayList<MangaObject> temp = new ArrayList<MangaObject>();

        JsonArray collection = data.get("data").getAsJsonArray();
        if(collection.size()==0){
            System.out.println("No manga found");
            return new ArrayList<MangaObject>();

        }

        for (int i = 0; i < collection.size(); i++) {
            temp.add(JsonToManga(collection.get(i).getAsJsonObject()));

        }

        return temp;
    }

    private static MangaObject JsonToManga(JsonObject manga_data) {
        JsonObject attributes = manga_data.get("attributes").getAsJsonObject();
        JsonArray relationships = manga_data.get("relationships").getAsJsonArray();

        String cover_file_name = relationships.get(2).getAsJsonObject().get("attributes").getAsJsonObject().get("fileName").getAsString();
        String id = manga_data.get("id").getAsString();
        String title = "";

        JsonObject title_obj = attributes.get("title").getAsJsonObject();
        if (title_obj.has("en")) {
            title = title_obj.get("en").getAsString();
        } else if (title_obj.has("jp-ro")) {
            title = title_obj.get("ja-ro").getAsString();
        } else if (title_obj.has("ja")) {
            title = title_obj.get("ja").getAsString();
        }

        if(cover_file_name.length()==0){
            throw new IllegalArgumentException("Cover file not found");
        }


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
