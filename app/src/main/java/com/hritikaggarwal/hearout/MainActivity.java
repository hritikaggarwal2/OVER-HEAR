package com.hritikaggarwal.hearout;

import android.app.Activity;
import android.content.Intent;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button button;
    Vibrator vibrator;
    Button button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

        Button btn = (Button) findViewById(R.id.button);


        //Assign a listener to your button
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start your second activity
                Intent intent = new Intent(MainActivity.this, SetupPage.class);
                startActivity(intent);
            }
        });

        button = findViewById(R.id.startVibrateButton);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrator.vibrate(VibrationEffect.createOneShot(10000, 255));
            }
        });

        button2 = findViewById(R.id.stopVibrateButton);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrator.cancel();
            }
        });
    }


}
