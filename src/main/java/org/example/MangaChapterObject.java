package org.example;

public class MangaChapterObject {
    final String id;
    final float chapter_number;
    final String translatedLanguage;
    final int pages;

    ChapterData data = null;



    MangaChapterObject(String id, float chapter_number, String translatedLanguage, int pages) {
        this.id = id;
        this.chapter_number = chapter_number;
        this.pages = pages;
        this.translatedLanguage = translatedLanguage;

    }

    void AddChapterData(ChapterData data){
        this.data = data;
    }

    @Override
    public String toString() {
        String str2;
        String str = "MangaChapterObject{" +
                "id='" + id + '\'' +
                ", chapter_number=" + chapter_number +
                ", translatedLanguage='" + translatedLanguage + '\'' +
                ", pages=" + pages +
                '}';

        if (data != null) {
            str2 = str + data.toString();
            return str2;

        }
        return str;
    }



}
