package com.hruparomangmail.songbookx;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 *
 * Created by RomanKhrupa on 12.03.2016.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private List<Card> cardList;
    private Context mainContext;

    public CardAdapter(List<Card> cardList,Context context){
        this.cardList = cardList;
        this.mainContext = context;
    }

    @Override
    public int getItemCount(){
        return cardList.size();
    }

    @Override
    public void onBindViewHolder(CardViewHolder cardViewHolder,int i){
        final Card card = cardList.get(i);
        cardViewHolder.title.setText(card.getTitle());
        cardViewHolder.lyrics.setText(card.getLyrics());
        cardViewHolder.author.setText(card.getAuthor());
        cardViewHolder.category.setText(card.getNameOfCategory());
        cardViewHolder.id.setText(String.valueOf(card.getId()));

/*        cardViewHolder.card.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){

                Intent intent = new Intent(mainContext,CardDetailActivity.class);
                intent.putExtra(ExpenseDetailActivity.EXTRA_ID, expense.getId());
                mainContext.startActivity(intent);

            }
        });*/

    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup viewGroup,int i)
    {
        View item_view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view,viewGroup,false);
        return new CardViewHolder(item_view);
    }




    public static class CardViewHolder extends RecyclerView.ViewHolder{
        protected TextView id;
        protected TextView title;
        protected TextView lyrics;
        protected TextView author;
        protected TextView category;
        protected CardView card;

        public CardViewHolder(View v){
            super(v);
            id = (TextView)v.findViewById(R.id.card_id);
            title = (TextView)v.findViewById(R.id.card_title);
            lyrics = (TextView) v.findViewById(R.id.card_lyrics);
            author = (TextView) v.findViewById(R.id.card_author);
            card = (CardView) v.findViewById(R.id.card_view);

        }

    }
}