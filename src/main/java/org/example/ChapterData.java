package org.example;

import java.util.ArrayList;

public class ChapterData {
    final String baseUrl;
    final String hash;
    final ArrayList<String> img_links;


    public ChapterData(String baseUrl, String hash, ArrayList<String> img_links) {
        this.baseUrl = baseUrl;
        this.hash = hash;
        this.img_links = img_links;
    }

    @Override
    public String toString() {
        return "ChapterData{" +
                "baseUrl='" + baseUrl + '\'' +
                ", hash='" + hash + '\'' +
                ", img_links=" + img_links +
                '}';
    }
}
