package com.yato.projet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yato.projet.Bird;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //-----------------------Constantes-------------------------//
    private final static int ACTIVITY0  = 0;
    private final static int ACTIVITY1 = 1;
    private final static int ACTIVITY2  = 2;
    private final static int ACTIVITY3 = 3;
    //-----------------------Constantes-------------------------//


    //-----------------------Variables--------------------------//
    private Bird ocean = new Bird("Chuck",3, "chuck", "Carte : God Valley", 0);
    private Bird ville = new Bird("Will",3, "will", "Carte : Little Garden", 0);
    private Bird montagne = new Bird("Nuck",3, "nuck", "Carte : Skypedia", 0);
    private Bird hiver = new Bird("Fox",3, "fox", "Carte : Laugh Tale", 0);
    //-----------------------Variables--------------------------//


    //-----------------------Layout--------------------------//
    private ListView listView;
    private List<Bird> birdList = new ArrayList<>();
    //-----------------------Layout--------------------------//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //-----------------------Bird List--------------------------//
        birdList.add(ocean);
        birdList.add(ville);
        birdList.add(montagne);
        birdList.add(hiver);
        //-----------------------Bird List--------------------------//

        //-----------------------Layout--------------------------//
        listView = findViewById(R.id.list);
        listView.setAdapter(new birdAdapter(this, birdList));
        birdAdapter adapter = new birdAdapter(this,birdList);
        //-----------------------Layout--------------------------//


        //-----------------------ITEMLISTENER-----------------------//
        //----------------------------------------------------------//
        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (position == 1) {
                Intent activity0 = new Intent(MainActivity.this, Activity0.class);
                activity0.putExtra("meilleurescore",ocean.getMeilleurescore());
                startActivityForResult(activity0, ACTIVITY0);

            }
            if (position == 3) {
                Intent activity1 = new Intent(MainActivity.this, Activity1.class);
                activity1.putExtra("meilleurescore",ville.getMeilleurescore());
                startActivityForResult(activity1, ACTIVITY1);
            }
            if (position == 2) {
                Intent activity2 = new Intent(MainActivity.this, Game.class);
                activity2.putExtra("meilleurescore",montagne.getMeilleurescore());
                startActivity(activity2);
            }
            if (position == 0) {
                Intent activity3 = new Intent(MainActivity.this, Activity3.class);
                activity3.putExtra("meilleurescore",hiver.getMeilleurescore());
                startActivityForResult(activity3, ACTIVITY3);
            }
        });
        //----------------------------------------------------------//
        //-----------------------ITEMLISTENER-----------------------//
    }

    //----------------------RESPONSELISTENER--------------------//
    //----------------------------------------------------------//
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int scoreIn = data.getExtras().getInt("score");
        if (requestCode == ACTIVITY3 && ocean.getMeilleurescore() <= scoreIn) {
            ocean.setMeilleurescore(data.getExtras().getInt("score")); //On récupére le score
        }
        if (requestCode == ACTIVITY0) {
            ville.setMeilleurescore(data.getExtras().getInt("score")); //On récupére le score
        }
        if (requestCode == ACTIVITY2) {
            montagne.setMeilleurescore(data.getExtras().getInt("score")); //On récupére le score
        }
        if (requestCode == ACTIVITY1) {
            hiver.setMeilleurescore(data.getExtras().getInt("score")); //On récupére le score
        }
        listView.setAdapter(new birdAdapter(this, birdList)); //Update
    }
    //----------------------------------------------------------//
    //----------------------RESPONSELISTENER--------------------//


    //----------------------------BACKUP------------------------//
    //----------------------------------------------------------//
    //On vien sauvegarder nos valeur pour les reset "switch .."
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("KEYSCORE1", ocean.getMeilleurescore());
        outState.putInt("KEYSCORE2", ville.getMeilleurescore());
        outState.putInt("KEYSCORE3", montagne.getMeilleurescore());
        outState.putInt("KEYSCORE4", hiver.getMeilleurescore());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        ocean.setMeilleurescore(savedInstanceState.getInt("KEYSCORE1"));
        ville.setMeilleurescore(savedInstanceState.getInt("KEYSCORE2"));
        montagne.setMeilleurescore(savedInstanceState.getInt("KEYSCORE3"));
        hiver.setMeilleurescore(savedInstanceState.getInt("KEYSCORE4"));
    }
    //----------------------------------------------------------//
    //----------------------------BACKUP------------------------//
}