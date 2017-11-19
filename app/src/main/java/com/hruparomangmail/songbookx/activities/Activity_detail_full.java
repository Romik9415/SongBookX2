package com.hruparomangmail.songbookx.activities;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.WriterException;
import com.hruparomangmail.songbookx.Card;
import com.hruparomangmail.songbookx.CardRepo;
import com.hruparomangmail.songbookx.R;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;


public class Activity_detail_full extends AppCompatActivity {
//Full card info.
    public  final static String EXTRA_ID="ID";
    private Card card;
    private String card_id = "";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String groupName = "1";
    DatabaseReference groupRef = database.getReference("groups/"+groupName+"/currentSong");

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

        card_id = getIntent().getStringExtra(EXTRA_ID);
        card = cardRepo.getCardById(card_id);
        title.setText(card.getTitle());
        lyrics.setText(card.getLyrics());
        author.setText(card.getAuthor());
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_qr_code_scaner) {
            View view = View.inflate(this, R.layout.qr_generator_view, null);
            TextView t = view.findViewById(R.id.text);
            ImageView qr_code_image_view = view.findViewById(R.id.qr_code_image_view);

            //----------QR code generator--------//
            QRGEncoder qrgEncoder = new QRGEncoder(
                    "song/"+card_id, null,
                    QRGContents.Type.TEXT,
                    2000);
            try {
                Bitmap bitmap = qrgEncoder.encodeAsBitmap();
                qr_code_image_view.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(view)
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            builder.create().show();
            return true;
        }


        if (id == R.id.action_share) {
            groupRef.setValue(card_id);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
