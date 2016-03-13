package com.hruparomangmail.songbookx;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(Card.KEY_TITLE,card.getTitle());
        values.put(Card.KEY_LYRICS,card.getLyrics());
        values.put(Card.KEY_AUTHOR,card.getAuthor());
        values.put(Card.KEY_CATEGORY,card.getNameOfCategory());

        long post_id = db.insert(Card.TABLE,null,values);
        db.close();
        return (int) post_id;
    }

    public ArrayList<Card> getCardList(){
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
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return cardList;
    }

}
