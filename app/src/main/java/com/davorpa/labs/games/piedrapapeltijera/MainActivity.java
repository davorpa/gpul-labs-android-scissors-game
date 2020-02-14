package com.davorpa.labs.games.piedrapapeltijera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_USERNAME =  BuildConfig.APPLICATION_ID + ".USERNAME";


    private ImageButton btn_play;
    private TextView lbl_play;
    private EditText txt_username;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Capture the layout's components
        btn_play = findViewById(R.id.btn_play);
        lbl_play = findViewById(R.id.lbl_play);
        txt_username = findViewById(R.id.txt_username);

        // Capture contexts
        final Context thisContext = this;

        // Attach events
        final View.OnClickListener playListener = new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // TODO: Find user in last players to set current user in game
                // validate username
                String username = txt_username.getText().toString();
                if (username == null || username.trim().isEmpty()) {
                    txt_username.setError(getString(R.string.err_required));
                    txt_username.requestFocus();
                    return;
                }

                // Navigate to other activity
                // TODO: Navigate to GameActivity.class
                //Intent intent = new Intent(thisContext, GameActivity.class);
                //intent.putExtra(MainActivity.EXTRA_USERNAME, username);
                //startActivity(intent);
            }
        };
        btn_play.setOnClickListener(playListener);
        lbl_play.setOnClickListener(playListener);
    }
}
