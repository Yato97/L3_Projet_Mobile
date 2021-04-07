package com.yato.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity2 extends AppCompatActivity {
    //-----------------------Layout--------------------------//
    private TextView score;
    private ImageView perso;
    private ImageView button;
    //-----------------------Layout--------------------------//



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        //-----------------------Layout--------------------------//
        button = findViewById(R.id.button);
        score = findViewById(R.id.score);
        perso = findViewById(R.id.perso);
        //-----------------------Layout--------------------------//

        //-----------------------HOMELISTENER-----------------------//
        //----------------------------------------------------------//
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("score", 1040);
                setResult(2, intent);
                finish();
            }
        });
        //----------------------------------------------------------//
        // -----------------------HOMELISTENER-----------------------//
    }
}