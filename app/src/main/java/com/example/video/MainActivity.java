package com.example.video;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOption;

import com.google.firebase.auth.FirebaseAuth;


import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    Button demoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        try {

            MediaStore.Video options;
            options = new MediaStore.Video().wait()
                    .setServerURL(new URL(""))
                    .setWelcomePageEnabled(false)
                    .setFeatureFlag("invite.enabled", false)
                    .setFeatureFlag("help.enabled", false)
                    .build();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onButtonClick(View v) {
        EditText editText = findViewById(R.id.codeBox);
        String text = editText.getText().toString();

        startActivity(new Intent(MainActivity.this, LoginActivity.class));

        if (text.length() > 0) {
            MediaStore.Video options
                    = new MediaStore.Video().Builder()
///            JitsiMeetConferenceOptions op=new JistiMeetConferenceOptions.Builder()
                    .setRoom(text)
                    .setFeatureFlag("invite.enabled", false)
                    .setFeatureFlag("help.enabled", false)
                    .build();
//            JitsiMeetActivity.launch(this,options);
        }


        demoBtn = (Button) findViewById(R.id.demoBtn);
        demoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }

}