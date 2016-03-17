package com.hruparomangmail.songbookx;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Activity_detail_full extends AppCompatActivity {
/*
* Full card info.
*/
    public  final static String EXTRA_ID="ID";
    private Card card;
    private int card_id = 0;

    TextView title;
    TextView lyrics;
    TextView author;

    CardRepo cardRepo = new CardRepo(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_full);

        title = (TextView) findViewById(R.id.title);
        lyrics = (TextView) findViewById(R.id.lyrics);
        author = (TextView) findViewById(R.id.author);

        card_id= getIntent().getIntExtra(EXTRA_ID,0);
        card = cardRepo.getCardById(card_id);
        title.setText(card.getTitle());
        lyrics.setText(card.getLyrics());
        author.setText(card.getAuthor());


    }
}
