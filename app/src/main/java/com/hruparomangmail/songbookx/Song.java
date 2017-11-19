package com.hruparomangmail.songbookx;

public class Song {
    private String id;
    private String title;
    private String lyrics;
    private String author;
    private int vladas;

    public Song(String id,String title, String lyrics, String author, int vladas) {
        this.title = title;
        this.lyrics = lyrics;
        this.author = author;
        this.vladas = vladas;
    }

    public Song(){}

    public String getTitle() {
        return title;
    }

    public String getLyrics() {
        return lyrics;
    }

    public String getId() {return id;}

    public String getAuthor() {
        return author;
    }

    public int getVladas() {
        return vladas;
    }
}
