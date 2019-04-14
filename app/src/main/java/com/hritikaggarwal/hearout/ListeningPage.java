package com.hritikaggarwal.hearout;

import android.app.Notification;
import android.app.PendingIntent;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Locale;

import ai.picovoice.porcupine.Porcupine;
import ai.picovoice.porcupinemanager.KeywordCallback;
import ai.picovoice.porcupinemanager.PorcupineManager;
import ai.picovoice.porcupinemanager.PorcupineManagerException;

public class ListeningPage extends AppCompatActivity {
    Vibrator vibrator;
    private PorcupineManager porcupineManager = null;
    private MediaPlayer notificationPlayer;
    private RelativeLayout layout;
    private ToggleButton recordButton;
    private Context context;

    String retrieveName = "";
    private NotificationManagerCompat notificationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listening_page);
        Utils.configurePorcupine(this);

        SharedPreferences prefs = this.getSharedPreferences("com.hritikaggarwal.hearout", Context.MODE_PRIVATE);
        retrieveName = prefs.getString("name", null);


        TextView textView = findViewById(R.id.NameDisplay);
        textView.setText(retrieveName);
        context = this;
        notificationPlayer = MediaPlayer.create(this, R.raw.notification);
        layout = findViewById(R.id.layout);
        recordButton = findViewById(R.id.startListening);

        Boolean nameDetected = false;
        String filename = retrieveName.toLowerCase().replaceAll("\\s+", "_");
        String keywordFilePath = new File(this.getFilesDir(), filename + ".ppn")
                .getAbsolutePath();
        String modelFilePath = new File(this.getFilesDir(), "params.pv").getAbsolutePath();
        float sensitivity = 0.5f;



        try {
            // check if record permission was given.
            if (Utils.hasRecordPermission(this)) {
                porcupineManager = initPorcupine();
                porcupineManager.start();
            } else {
                Utils.showRecordPermission(this);
            }
        } catch (PorcupineManagerException e) {
            Utils.showErrorToast(this);
        }

        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }


    /**
     * Initialize the porcupineManager library.
     * @return Porcupine instance.
     */
    private PorcupineManager initPorcupine() throws PorcupineManagerException {
        String filename = retrieveName.toLowerCase().replaceAll("\\s+", "_");
        // get the keyword file and model parameter file from internal storage.
        String keywordFilePath = new File(this.getFilesDir(), filename + ".ppn")
                .getAbsolutePath();
        String modelFilePath = new File(this.getFilesDir(), "params.pv").getAbsolutePath();
        return new PorcupineManager(modelFilePath, keywordFilePath, 0.5f, new KeywordCallback() {
            @Override
            public void run(int keyword_index) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!notificationPlayer.isPlaying()) {
                            notificationPlayer.start();
                        }
//                        new CountDownTimer(1000, 100) {
//
//                        @Override
//                        public void onTick(long millisUntilFinished) {
//                            if (!notificationPlayer.isPlaying()) {
//                                notificationPlayer.start();
//                            }
//                        }
//
//                        @Override
//                        public void onFinish() {
//                            layout.setBackgroundColor(Color.TRANSPARENT);
//                        }
//                    }.start();

                        notificationManager = NotificationManagerCompat.from(context);

                        String title = "Somebody's calling you!";
                        String message = "Tap to see what they are saying";
                        Intent intent = new Intent(context, listing.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                        Notification notification = new NotificationCompat.Builder(context, App.CHANNEL_1_ID)
                                .setSmallIcon(R.drawable.ic_hearout)
                                .setContentTitle(title)
                                .setContentText(message)
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                .setAutoCancel(true)
                                .setContentIntent(pendingIntent)
                                .build();


                        notificationManager.notify(1, notification);

                        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                        vibrator.vibrate(VibrationEffect.createOneShot(2000, 255));

                        try {
                            // check if record permission was given.
                            if (Utils.hasRecordPermission(context)) {
                                porcupineManager.stop();
                            } else {
                                Utils.showRecordPermission(ListeningPage.this);
                            }
                        } catch (PorcupineManagerException e) {
                            Utils.showErrorToast(context);
                        }

                }
                });
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

    /**
     * Check the result of the record permission request.
     * @param requestCode request code of the permission request.
     * @param permissions requested permissions.
     * @param grantResults results of the permission requests.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        // We only ask for record permission.
        if (grantResults.length == 0 || grantResults[0] == PackageManager.PERMISSION_DENIED) {
            ToggleButton tbtn = findViewById(R.id.startListening);
            tbtn.toggle();
        } else {
            try {
                porcupineManager = initPorcupine();
                porcupineManager.start();
            } catch (PorcupineManagerException e) {
                Utils.showErrorToast(this);
            }
        }
    }


}
