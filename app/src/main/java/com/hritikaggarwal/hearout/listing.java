package com.hritikaggarwal.hearout;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.speech.RecognizerIntent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class listing extends AppCompatActivity {

    private ListAdapter adapter;
    private ArrayList<String> listItems;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);

        listItems = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, listItems);
        listView = (ListView) findViewById(R.id.listview);

        // TEXT TO SPEECH AFTER LISTENING STARTS *************
        Intent intent2 = new Intent(android.speech.RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent2.putExtra(android.speech.RecognizerIntent.EXTRA_LANGUAGE_MODEL, android.speech.RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent2.putExtra(android.speech.RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent2.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent2, 10);
        } else {
            Toast.makeText(listing.this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }

        // TEXT TO SPEECH AFTER LISTENING ENDS *************


        Button btn = (Button) findViewById(R.id.hear_again);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TEXT TO SPEECH AFTER LISTENING STARTS *************
                Intent intent = new Intent(android.speech.RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(android.speech.RecognizerIntent.EXTRA_LANGUAGE_MODEL, android.speech.RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(android.speech.RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, 10);
                } else {
                    Toast.makeText(listing.this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
                }

                // TEXT TO SPEECH AFTER LISTENING ENDS *************
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        TextView textView = findViewById(R.id.NameDisplay);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    listItems.add(result.get(0));
                    listView.setAdapter(adapter);

                    // TEXT TO SPEECH AFTER LISTENING STARTS *************
                    Intent intent2 = new Intent(android.speech.RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent2.putExtra(android.speech.RecognizerIntent.EXTRA_LANGUAGE_MODEL, android.speech.RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent2.putExtra(android.speech.RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

                    if (intent2.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent2, 10);
                    } else {
                        Toast.makeText(listing.this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
                    }

                    // TEXT TO SPEECH AFTER LISTENING ENDS *************

                }

                break;
        }
    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(listing.this, ListeningPage.class);
        startActivity(intent);

        super.onBackPressed();
    }
}
