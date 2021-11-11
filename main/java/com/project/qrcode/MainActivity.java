package com.project.qrcode;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    MediaPlayer player;
    TextView databrug;
    TextView datamusk;
    TextView datamuur;

    boolean brugenabled, muurenabled, muskenebled;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        databrug = findViewById(R.id.brug);
        datamusk = findViewById(R.id.musk);
        datamuur = findViewById(R.id.muur);
        muurenabled = loadCheck("A");
        muskenebled = loadCheck("B");
        brugenabled = loadCheck("C");

        if(!muskenebled )
            datamusk.setText("ga naar de pagina over de 4de musketier");
        if(!muurenabled)
            datamuur.setText("ga naar de pagina over het verstigings werk");
        if(!brugenabled)
            databrug.setText("ga naar de pagina over de sint Servaas brug");


    }
    public void GOTOQR(View view){
        startActivity(new Intent(getApplicationContext(), QRscanner.class));
    }

    public void playMusk(View view){
        if(muskenebled ) {
            if (player == null)
                player = MediaPlayer.create(this, R.raw.musketier);
            player.start();
        }
    }

    public void playmuur(View view){
        if(muurenabled) {
            if (player == null)
                player = MediaPlayer.create(this, R.raw.muur_maas);
            player.start();
        }
    }
    public void playbrug(View view){
        if(brugenabled) {
            if (player == null)
                player = MediaPlayer.create(this, R.raw.sint_servaas_brug);
            player.start();
        }
    }

    public void stop (View view){
        if(player != null) {
            player.release();
            player = null;
        }
    }

    public boolean loadCheck(String Check) {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("ScannedCodes", Context.MODE_PRIVATE);
        char[] codes = sp.getString("codes", "").toCharArray();
        char[] c = Check.toCharArray();
        for(int i = 0; i < codes.length; i++ ){
            if(c[0] == codes[i]) {
                return true;
            }
        }
        return false;
    }


}