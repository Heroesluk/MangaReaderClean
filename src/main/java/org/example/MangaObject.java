package org.example;

public class MangaObject {
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
