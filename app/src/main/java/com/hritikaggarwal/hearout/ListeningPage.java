package com.hritikaggarwal.hearout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
    private PorcupineManager porcupineManager = null;
    private MediaPlayer notificationPlayer;
    private RelativeLayout layout;
    private ToggleButton recordButton;
    String retrieveName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listening_page);
        Utils.configurePorcupine(this);

        SharedPreferences prefs = this.getSharedPreferences("com.hritikaggarwal.hearout", Context.MODE_PRIVATE);
        retrieveName = prefs.getString("name", null);


        TextView textView = findViewById(R.id.NameDisplay);
        textView.setText(retrieveName);

        notificationPlayer = MediaPlayer.create(this, R.raw.notification);
        layout = findViewById(R.id.layout);
        recordButton = findViewById(R.id.startListening);

        Boolean nameDetected = false;
        String filename = retrieveName.toLowerCase().replaceAll("\\s+", "_");
        String keywordFilePath = new File(this.getFilesDir(), filename + ".ppn")
                .getAbsolutePath();
        String modelFilePath = new File(this.getFilesDir(), "params.pv").getAbsolutePath();
        float sensitivity = 0.5f;

        // vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        // vibrator.vibrate(VibrationEffect.createOneShot(10000, 255));

    }

    /**
     * Handler for the record button. Processes the audio and uses Porcupine library to detect the
     * keyword. It increments a counter to indicate the occurrence of a keyword.
     * @param view ToggleButton used for recording audio.
     */
    public void process(View view) {
        try {
            if (recordButton.isChecked()) {
                // check if record permission was given.
                if (Utils.hasRecordPermission(this)) {
                    porcupineManager = initPorcupine();
                    porcupineManager.start();

                } else {
                    Utils.showRecordPermission(this);
                }
            } else {
                porcupineManager.stop();
            }
        } catch (PorcupineManagerException e) {
            Utils.showErrorToast(this);
        }
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
                        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                        vibrator.vibrate(VibrationEffect.createOneShot(10000, 255));
                }
                });
            }
        });
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
