package com.hritikaggarwal.hearout;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;

public class ListeningPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listening_page);

        Context context = this;
        File directory = context.getFilesDir();

        //File file = new File(directory,"myfile");
        String temp = "No text here";
        try {
            FileInputStream fin = context.openFileInput("myfile.dat");
            int c;
            while( (c = fin.read()) != -1){
                temp = temp + Character.toString((char) c);
            }
            fin.close();
        } catch (Exception e) {
            temp = temp + "hello";
            e.printStackTrace();
        }


        TextView textView = findViewById(R.id.NameDisplay);
        textView.setText(temp);
        //nLog.d(LOG_TAG, "Button clicked!");
    }
}
