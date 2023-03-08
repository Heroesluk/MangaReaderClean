package org.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class MangaDexParser {

    public static ArrayList<MangaObject> ParseSearchResults(JsonObject data) {
        ArrayList<MangaObject> temp = new ArrayList<MangaObject>();

        JsonArray collection = data.get("data").getAsJsonArray();
        if (collection.size() == 0) {
            System.out.println("No manga found");
            return new ArrayList<MangaObject>();

        }

        for (int i = 0; i < collection.size(); i++) {
            try {
                temp.add(JsonToManga(collection.get(i).getAsJsonObject()));

            } catch (Exception e) {
                System.out.println("Huj");

                System.out.println(collection.get(i));
            }

        }

        return temp;
    }


    public static ArrayList<MangaChapterObject> GetMangaChapters(JsonObject data) {
        ArrayList<MangaChapterObject> chapters = new ArrayList<MangaChapterObject>();

        JsonArray collection = data.get("data").getAsJsonArray();
        if (collection.size() == 0) {
            System.out.println("No chapters found");
            return new ArrayList<MangaChapterObject>();
        }

        for (int i = 0; i < collection.size(); i++) {
            chapters.add(JsonToChapter(collection.get(i).getAsJsonObject()));
        }
        return chapters;
    }


    private static MangaObject JsonToManga(JsonObject manga_data) {
        JsonObject attributes = manga_data.get("attributes").getAsJsonObject();
        JsonArray relationships = manga_data.get("relationships").getAsJsonArray();

        String id = manga_data.get("id").getAsString();

        String cover_file_name = "";

        for (JsonElement relation : relationships) {
            if (relation.getAsJsonObject().get("type").getAsString().equals("cover_art")) {
                cover_file_name =
                        relation.getAsJsonObject().get("attributes").getAsJsonObject()
                                .get("fileName").getAsString();

            }
        }


        String title = "";
        JsonObject title_obj = attributes.get("title").getAsJsonObject();
        if (title_obj.has("en")) {
            title = title_obj.get("en").getAsString();
        } else if (title_obj.has("jp-ro")) {
            title = title_obj.get("ja-ro").getAsString();
        } else if (title_obj.has("ja")) {
            title = title_obj.get("ja").getAsString();
        }

        if (cover_file_name.length() == 0) {
            throw new IllegalArgumentException("Cover file not found");
        }


        return new MangaObject(id, title, cover_file_name);


    }

    private static MangaChapterObject JsonToChapter(JsonObject chapter) {
        JsonObject attributes = chapter.get("attributes").getAsJsonObject();

        String id = chapter.get("id").getAsString();
        float chapter_number = attributes.get("chapter").getAsFloat();
        String translatedLanguage = attributes.get("translatedLanguage").getAsString();
        int pages = attributes.get("pages").getAsInt();


        return new MangaChapterObject(id, chapter_number, translatedLanguage, pages);
    }


    public static void PrintSearchResults(ArrayList<MangaObject> results) {
        for (MangaObject res : results) {
            System.out.println(res.toString());
        }

    }

}


class MangaObject {
    final String id;
    final String title;
    final String cover_file_name;

    MangaObject(String id, String title, String cover_file_name) {
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

class MangaChapterObject {
    final String id;
    final float chapter_number;
    final String translatedLanguage;
    final int pages;

    MangaChapterObject(String id, float chapter_number, String translatedLanguage, int pages) {
        this.id = id;
        this.chapter_number = chapter_number;
        this.pages = pages;
        this.translatedLanguage = translatedLanguage;

    }

}
