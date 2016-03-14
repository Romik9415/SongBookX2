package com.hruparomangmail.songbookx;

/**
 *Created by Roman Khrupa on 12.03.2016.
 * All card info
 */
public class Card {
    private int id;
    private String title;
    private String lyrics;
    private String author;

    public enum Category{
        first,
        second,
    };
    private Category category;

    //VALUES
    public static final String TABLE="Card";
    public static final String KEY_ID="id";
    public static final String KEY_TITLE="title";
    public static final String KEY_LYRICS="lyrics";
    public static final String KEY_AUTHOR="author";
    public static final String KEY_CATEGORY="category";

    //Card info
    public Card(String title,String lyrics,String author,String category){
        this.title=title;
        this.lyrics=lyrics;
        this.author=author;
        this.category=Category.valueOf(category);
    }
    //id
    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    //Title
    public void setTitle(String title){
        this.title=title;
    }
    public String getTitle(){
        return this.title;
    }

    //lyrics
    public void setLyrics(String lyrics){
        this.lyrics=lyrics;
    }
    public String getLyrics(){
        return this.lyrics;
    }
    //author
    public void setAuthor(String  author){
       this.author=author;
    }
    public String getAuthor(){
        return this.author;
    }
    //Category
    public String getNameOfCategory(){
        return this.category.name();
    }
    public void setCategory(String category){
        this.category=Category.valueOf(category);
    }
    public Category getCategory(){
        return this.category;
    }

}


