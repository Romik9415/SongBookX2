package com.hruparomangmail.songbookx;

import android.animation.AnimatorInflater;
import android.animation.StateListAnimator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    public void onBindViewHolder(final CardViewHolder cardViewHolder,int i){
        final Card card = cardList.get(i);
        cardViewHolder.title.setText(card.getTitle());
        cardViewHolder.lyrics.setText(card.getLyrics());
        cardViewHolder.author.setText(card.getAuthor());
        cardViewHolder.id.setText(String.valueOf(card.getId()));
        cardViewHolder.card.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){

                Intent intent = new Intent(mainContext,Activity_detail_full.class);
                intent.putExtra(Activity_detail_full.EXTRA_ID, card.getId());
                View sharedView = cardViewHolder.title;
                //Pair<View, String> p2 = Pair.create(sharedView, "lyrics");
                Pair<View, String> p3 = Pair.create(sharedView, "block");
                //Pair<View, String> p1 = Pair.create(sharedView, "title");
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    //Shadow
                    StateListAnimator stateListAnimator = AnimatorInflater
                            .loadStateListAnimator(mainContext, R.anim.lift_on_touch);
                    v.setStateListAnimator(stateListAnimator);
                    //
                ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity)mainContext,p3);
                    mainContext.startActivity(intent,transitionActivityOptions.toBundle());}
                else{mainContext.startActivity(intent);}

            }
        });

    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup viewGroup,int i)
            //card info
    {
        View item_view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view,viewGroup,false);
        return new CardViewHolder(item_view);
    }




    public static class CardViewHolder extends RecyclerView.ViewHolder{
        protected TextView id;
        protected TextView title;
        protected TextView lyrics;
        protected TextView author;
        //protected TextView category;
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