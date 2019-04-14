package com.hritikaggarwal.hearout;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private NotificationManagerCompat notificationManager;
    Button startVibration;
    Vibrator vibrator;
    Button endVibration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        notificationManager = NotificationManagerCompat.from(this);

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

        startVibration = findViewById(R.id.startVibrateButton);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        startVibration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrator.vibrate(VibrationEffect.createOneShot(10000, 255));
            }
        });

        endVibration = findViewById(R.id.stopVibrateButton);
        endVibration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrator.cancel();
            }
        });
    }

    public void sendOnChannel1(View v) {
        String title = "Somebody's calling you!";
        String message = "Tap to Hear them out";
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, App.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_hearout)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();


        notificationManager.notify(1, notification);
    }


}
