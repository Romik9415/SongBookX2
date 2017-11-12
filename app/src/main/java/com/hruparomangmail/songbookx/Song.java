package com.hruparomangmail.songbookx;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Song {
    private String title;
    private String lyrics;
    private String author;
    private int vladas;

    public Song(String title, String lyrics, String author, int vladas) {
        this.title = title;
        this.lyrics = lyrics;
        this.author = author;
        this.vladas = vladas;
    }

    public Song(){}

//    @Exclude
//    public Map<String, Object> toMap() {
//        HashMap<String, Object> result = new HashMap<>();
//        result.put("title", title);
//        result.put("author", author);
//        result.put("lyrics", lyrics);
//        result.put("vladas",vladas);
//
//        return result;
//    }

    public String getTitle() {
        return title;
    }

    public String getLyrics() {
        return lyrics;
    }

    public String getAuthor() {
        return author;
    }

    public int getVladas() {
        return vladas;
    }
}
