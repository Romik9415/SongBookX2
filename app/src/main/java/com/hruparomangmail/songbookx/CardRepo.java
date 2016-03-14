package com.hruparomangmail.songbookx;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 *
 * Created by Roman Khrupa on 12.03.2016.
 */
public class CardRepo {
    private DBHelper dbHelper;

    public CardRepo(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(Card card){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Card.KEY_TITLE,card.getTitle());
        values.put(Card.KEY_LYRICS,card.getLyrics());
        values.put(Card.KEY_AUTHOR,card.getAuthor());
        values.put(Card.KEY_CATEGORY, card.getNameOfCategory());

        long post_id = db.insert(Card.TABLE,null,values);
        db.close();
        return (int) post_id;
    }

    public ArrayList<Card> getCardList(){
        //Get full list of cards
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = " SELECT * FROM " + Card.TABLE;
        ArrayList<Card> cardList = new ArrayList<Card>();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                Card card = new Card(
                        cursor.getString(cursor.getColumnIndex(Card.KEY_TITLE)),
                        cursor.getString(cursor.getColumnIndex(Card.KEY_LYRICS)),
                        cursor.getString(cursor.getColumnIndex(Card.KEY_AUTHOR)),
                        cursor.getString(cursor.getColumnIndex(Card.KEY_CATEGORY))
                );
                card.setId(cursor.getInt(cursor.getColumnIndex(Card.KEY_ID)));
                Log.v("text",Card.KEY_ID);
                cardList.add(card);

            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return cardList;
    }
    public Card getCardById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Card card= new Card("","","",Card.Category.first.name());
        Cursor cursor = db.rawQuery("select * from "+Card.TABLE+" where "+Card.KEY_ID+"='"+String.valueOf(Id)+"'", null);

        if (cursor.moveToFirst()) {
            do {
                card.setId(cursor.getInt(cursor.getColumnIndex(Card.KEY_ID)));
                card.setTitle(cursor.getString(cursor.getColumnIndex(Card.KEY_TITLE)));
                card.setLyrics(cursor.getString(cursor.getColumnIndex(Card.KEY_LYRICS)));
                card.setAuthor(cursor.getString(cursor.getColumnIndex(Card.KEY_AUTHOR)));
                card.setCategory(cursor.getString(cursor.getColumnIndex(Card.KEY_CATEGORY)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return card;
    }


    public void deleteAll(){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.delete(Card.TABLE, null, null);
        db.close();
    }

    public Card getRandomQuote(){
        //Generates random card
        Card card= new Card("","","", Card.Category.first.name());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + Card.TABLE + " ORDER BY RANDOM() LIMIT 1";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            do {
                card.setTitle(cursor.getString(cursor.getColumnIndex(Card.KEY_TITLE)));
                card.setLyrics(cursor.getString(cursor.getColumnIndex(Card.KEY_LYRICS)));
                card.setAuthor(cursor.getString(cursor.getColumnIndex(Card.KEY_AUTHOR)));
                card.setCategory(cursor.getString(cursor.getColumnIndex(Card.KEY_CATEGORY)));
                card.setId(cursor.getInt(cursor.getColumnIndex(Card.KEY_ID)));
            }while (cursor.moveToNext());
        }
                cursor.close();
        db.close();
        return card;
    }

}
