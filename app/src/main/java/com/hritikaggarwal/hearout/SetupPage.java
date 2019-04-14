package com.hritikaggarwal.hearout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
//                // writing into the internal storage of the file
//                String filename = "myfile";
//                String fileContents = editText.getText().toString();
//                FileOutputStream outputStream;
//                if (FileHelper.saveToFile(fileContents)) {
//                    Toast.makeText(SetupPage.this, "Saved to file", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(SetupPage.this, "Error save file!!!", Toast.LENGTH_SHORT).show();
//                }
                startActivity(intent);
            }
        });
    }
}
