package com.hruparomangmail.songbookx.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hruparomangmail.songbookx.BuildConfig;
import com.hruparomangmail.songbookx.Card;
import com.hruparomangmail.songbookx.CardAdapter;
import com.hruparomangmail.songbookx.CardRepo;
import com.hruparomangmail.songbookx.QRScaner;
import com.hruparomangmail.songbookx.R;
import com.hruparomangmail.songbookx.Song;
import com.hruparomangmail.songbookx.SongsBeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    CardRepo cardRepo= new CardRepo(this);
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    Context context=this;
    int c=0 ;
    int status=0;
    public Integer current_card_id;
    SwipeRefreshLayout swipeRefreshLayout;
    FirebaseDatabase database;
    //ToDO add songs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imageBG();
        status=0;
       database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("groups/"+"1"+"/currentSong");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(status!=0&& current_card_id!=dataSnapshot.getValue(Integer.class)) {
                    current_card_id = dataSnapshot.getValue(Integer.class);
                    Intent intent = new Intent(context, Activity_detail_full.class);
                    intent.putExtra(Activity_detail_full.EXTRA_ID, current_card_id);
                    startActivity(intent);
                }
                status=1;

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read current_card_id
                Log.w("firebase_error", "Failed to read current_card_id.", error.toException());
            }
        });

        final DatabaseReference apkVersion = database.getReference("latest_apk");
        final String versionName = BuildConfig.VERSION_NAME;
        apkVersion.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String verNum = String.valueOf(dataSnapshot.child("version").getValue(Double.class));
                String varDescription = String.valueOf(dataSnapshot.child("description").getValue(String.class));
                final String verURl = String.valueOf(dataSnapshot.child("link").getValue(String.class));
                if(!Objects.equals(versionName,verNum)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage(varDescription).setTitle(R.string.new_version_available)
                            .setPositiveButton(R.string.dowload, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(verURl));
                                    startActivity(browserIntent);
                                }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                    builder.create().show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        RecyclerView recyclerView = findViewById(R.id.itemL);
        ImageView img = (ImageView) findViewById(R.id.BG);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c=c+1;
            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(c>3) {
//                    TextView text = (TextView) findViewById(R.id.empty_text);
//                    text.setText("" + (10 - c));
//                }
//                if(c>9){Snackbar.make(view, "You go yo heaven without queue ^-^", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();c=0;}
                if(cardRepo.getCardList().size()>0){
                Intent intent = new Intent(context, Activity_detail_full.class);
                current_card_id = cardRepo.getRandomQuote().getId();
                intent.putExtra(Activity_detail_full.EXTRA_ID, current_card_id);
                            startActivity(intent);}
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateRecycler();
                if(swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

        });
        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.darkPrimary)
        );
        updateRecycler();
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if(id == R.id.action_about){
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_qr_code_scaner) {
            if (!(ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_DENIED)){
                startActivity(new Intent(MainActivity.this, QRScaner.class));
            }
            else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[] {android.Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
            }
            return true;
        }




        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.download_action) {
           //TODo add selector of group
            //TODO check internet connection
            database.getReference("songs").child("-KyBmTVa-CVSlwwHTeSl").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.e("Count " ,""+dataSnapshot.getChildrenCount());
                    collectSongs((Map<String,Object>) dataSnapshot.getValue());
                    updateRecycler();
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            updateRecycler();
        }
        if (id == R.id.addLyrics) {
            SongsBeta.inSong(cardRepo);
            updateRecycler();
        }
        if (id == R.id.delete_all) {
            cardRepo.deleteAll();
            updateRecycler();
        }
        if (id == R.id.soon){
           // View addSongViewDialog = findViewById(R.id.)
            View view = View.inflate(this, R.layout.add_song_dialog, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(view)
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    }).setPositiveButton(R.string.upload_song, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference newSongsRef = database.getReference("songs");//TODO create groups selector
                    String groupId = "-KyBmTVa-CVSlwwHTeSl";
                    String id = newSongsRef.child(groupId).push().getKey();
                    Song song = new Song("q","q","s",0);
                    newSongsRef.child(groupId).child(id).setValue(song);
                    Toast.makeText(context, "Uploaded", Toast.LENGTH_SHORT).show();
                }
            });
            builder.create().show();
        }




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void collectSongs(Map<String, Object> value) {
        //List<Song> phoneNumbers = new ArrayList<>();
        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : value.entrySet()){
            //Get user map
            Map songs = (Map) entry.getValue();
            //Get phone field and append to list
            String title= songs.get("title").toString();
            String lyrics = songs.get("lyrics").toString();
            String author = songs.get("author").toString();
//            int vladas = Integer.getInteger(songs.get("vladas").toString()); //TODO add vladas
            cardRepo.insert(new Card(title,lyrics,author,Card.Category.first.name()));

        }

       // System.out.println(phoneNumbers.toString());
    }

    public void updateRecycler(){
        //Updater function
        List<Card> cards = cardRepo.getCardList();
        CardAdapter cardAdapter = new CardAdapter(cards,this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.itemL);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(cardAdapter);
        if(swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
        imageBG();
    }

    public void imageBG(){
        if(cardRepo.getCardList().size()>0){
            findViewById(R.id.BG).setVisibility(View.INVISIBLE);
        findViewById(R.id.empty_text).setVisibility(View.INVISIBLE);
            findViewById(R.id.main_bg).setVisibility(View.VISIBLE);}
        else{findViewById(R.id.BG).setVisibility(View.VISIBLE);
            findViewById(R.id.empty_text).setVisibility(View.VISIBLE);
            findViewById(R.id.main_bg).setVisibility(View.INVISIBLE);}
    }


}
