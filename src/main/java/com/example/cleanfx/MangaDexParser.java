package com.example.cleanfx;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class MangaDexParser {

    public static ArrayList<MangaObject> ParseSearchResults(JsonObject data) {
        ArrayList<MangaObject> temp = new ArrayList<MangaObject>();

        JsonArray collection = data.get("data").getAsJsonArray();
        if (collection.size() == 0) {
            return temp;

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
            return chapters;
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

    public static ChapterData JsonToChapterData(JsonObject chapter_data){

        JsonObject chapter = chapter_data.get("chapter").getAsJsonObject();

        String baseUrl = chapter_data.get("baseUrl").getAsString();
        String hash = chapter.get("hash").getAsString();
        JsonArray images = chapter.get("data").getAsJsonArray();
        ArrayList<String> img_links = new ArrayList<>();
        for(JsonElement img_link: images) {
            img_links.add(img_link.getAsString());

        }

        return new ChapterData( baseUrl,  hash,  img_links);



    }




}



