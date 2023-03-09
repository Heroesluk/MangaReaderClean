package org.example;

import java.util.ArrayList;

public class UserLogic {
    static ArrayList<MangaObject> mangaSearchResults = new ArrayList<>();
    static ArrayList<MangaChapterObject> chapterList = new ArrayList<>();

    void searchForManga(String manga_name) {
        mangaSearchResults = Handler_HTTP.SearchForManga(manga_name);
    }

    void fetch_chapters(String manga_id) {
        chapterList = Handler_HTTP.RetrieveMangaChapters(manga_id, "en");
    }

    ChapterData fetch_chapter_data(String chapter_id) {
        return Handler_HTTP.GetChapterData(chapter_id);
    }


    void print_out(String type) {
        if(type.equals("C")){
            PrintGeneric(chapterList);
        } else if (type.equals("M")) {
            PrintGeneric(mangaSearchResults);
        }
    }

    private static <t> void PrintGeneric(ArrayList<t> results) {
        for (t res : results) {
            System.out.println(res.toString());
        }
    }



}
