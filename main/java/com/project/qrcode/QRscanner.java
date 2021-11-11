package com.project.qrcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class QRscanner extends AppCompatActivity {
    private static final String FILE_NAME = "SavedCode.txt";
    public String[] codes;
    CodeScanner codeScanner;
    CodeScannerView scannview;
    TextView resultData;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscanner);
        scannview = findViewById(R.id.scannerView);
        codeScanner = new CodeScanner(this, scannview);
        resultData = findViewById(R.id.textView);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        CheckData(result.getText());
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }

    public void GOTOMENU(View view){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }


    public void CheckData(String result){
        switch (result) {
            case "6942": // a
                Checked(load(), "A");
                break;
            case "1234": // b
                Checked(load(), "B");
                break;
            case "2345": //c
                Checked(load(), "C");
                break;

            default:
                break;
            }
    }
    public void save(String result){
        sp = getSharedPreferences("ScannedCodes", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString("codes", result);
        editor.commit();

    }

    public String load() {
        SharedPreferences sp = getApplicationContext().getSharedPreferences("ScannedCodes", Context.MODE_PRIVATE);
        return sp.getString("codes", "");
    }

    public void Checked(String result, String Check){
        char[] charResult = result.toCharArray();
        boolean Testpos = false;
        for(int i = 0; i < charResult.length; i++){
            if(charResult[i] == Check.toCharArray()[0]){
                Testpos = true;
            }
        }
        if(Testpos == false){
            save(Check + result );
        }
    }
}