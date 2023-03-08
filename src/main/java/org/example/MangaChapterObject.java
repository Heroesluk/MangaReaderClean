package org.example;

public class MangaChapterObject {
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

    @Override
    public String toString() {
        return "MangaChapterObject{" +
                "id='" + id + '\'' +
                ", chapter_number=" + chapter_number +
                ", translatedLanguage='" + translatedLanguage + '\'' +
                ", pages=" + pages +
                '}';
    }
}
