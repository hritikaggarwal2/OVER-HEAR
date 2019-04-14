package com.hritikaggarwal.hearout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.File;
import java.io.FileInputStream;

import ai.picovoice.porcupine.Porcupine;
import ai.picovoice.porcupinemanager.KeywordCallback;
import ai.picovoice.porcupinemanager.PorcupineManager;
import ai.picovoice.porcupinemanager.PorcupineManagerException;

public class ListeningPage extends AppCompatActivity {
    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listening_page);

        SharedPreferences prefs = this.getSharedPreferences("com.hritikaggarwal.hearout", Context.MODE_PRIVATE);
        String retrieveName = prefs.getString("name", null);


        TextView textView = findViewById(R.id.NameDisplay);
        textView.setText(retrieveName);

        Boolean nameDetected = false;
        String filename = retrieveName.toLowerCase().replaceAll("\\s+", "_");
        String keywordFilePath = new File(this.getFilesDir(), filename + ".ppn")
                .getAbsolutePath();
        String modelFilePath = new File(this.getFilesDir(), "params.pv").getAbsolutePath();
        float sensitivity = 0.5f;
        ToggleButton recordButton = findViewById(R.id.startListening);
        try {
        PorcupineManager manager = new PorcupineManager(
                modelFilePath,
                keywordFilePath,
                sensitivity,
                new KeywordCallback() {
                    public void run(int keyword_index) {
                        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                        vibrator.vibrate(VibrationEffect.createOneShot(10000, 255));
                    }
                });
            if (recordButton.isChecked()) {
                manager.start();
            }
            else {
                manager.stop();
            }
        } catch (PorcupineManagerException e) {
        }

    }
}
