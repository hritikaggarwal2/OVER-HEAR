package com.hritikaggarwal.hearout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Locale;

public class ListeningPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listening_page);

        //Context context = this;
        //File directory = context.getFilesDir();
        SharedPreferences prefs = this.getSharedPreferences("com.hritikaggarwal.hearout", Context.MODE_PRIVATE);
        String retrieveName = prefs.getString("name", null);
        //File file = new File(directory,"myfile");
//        String temp = "No text here";
//        try {
//            FileInputStream fin = context.openFileInput("myfile.dat");
//            int c;
//            while( (c = fin.read()) != -1){
//                temp = temp + Character.toString((char) c);
//            }
//            fin.close();
//        } catch (Exception e) {
//            temp = temp + "hello";
//            e.printStackTrace();
//        }


        TextView textView = findViewById(R.id.NameDisplay);
        textView.setText(retrieveName);
        //nLog.d(LOG_TAG, "Button clicked!");
    }

    public void getSpeechInput(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        TextView textView = findViewById(R.id.NameDisplay);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    textView.setText(result.get(0));
                }
                break;
        }
    }
}
