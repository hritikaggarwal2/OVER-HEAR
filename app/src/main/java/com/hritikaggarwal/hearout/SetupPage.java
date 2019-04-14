package com.hritikaggarwal.hearout;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileOutputStream;

public class SetupPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_page);

        Button btn = (Button) findViewById(R.id.button2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start your second activity
                Intent intent = new Intent(SetupPage.this, ListeningPage.class);
                // Taking the name to the next page
                EditText editText = (EditText) findViewById(R.id.editText);
                String name = editText.getText().toString();
                SharedPreferences prefs = getSharedPreferences("com.hritikaggarwal.hearout", Context.MODE_PRIVATE);
                prefs.edit().putString("name", name).apply();
                startActivity(intent);
                finishAffinity();
            }
        });

    }
}
