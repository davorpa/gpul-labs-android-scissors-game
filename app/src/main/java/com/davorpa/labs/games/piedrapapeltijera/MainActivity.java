package com.davorpa.labs.games.piedrapapeltijera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static final String EXTRA_USERNAME =  BuildConfig.APPLICATION_ID + ".USERNAME";


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Capture the layout's components
        final EditText txt_username = findViewById(R.id.txt_username);

        //Init view with values provided by parent intent
        final String username = getIntent().getStringExtra(MainActivity.EXTRA_USERNAME);
        if (username != null) {
            txt_username.setText(username);
            txt_username.selectAll();
            txt_username.requestFocus();
        }
    }


    /**
     * Método que se ejecuta al pulsar el botón Play
     * @param view
     */
    public void onPlayClick(final View view) {
        // Capture the layout's components
        final EditText txt_username = findViewById(R.id.txt_username);

        // TODO: Find user in last players to set current user in game
        // validate username
        final String username = txt_username.getText().toString();
        if (username == null || username.trim().isEmpty()) {
            txt_username.setError(getString(R.string.err_required));
            txt_username.requestFocus();
            //Open device keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(txt_username, InputMethodManager.SHOW_IMPLICIT);
            return;
        }

        // Navigate to other activity
        final Intent intent = new Intent(MainActivity.this, GameActivity.class);
        intent.putExtra(MainActivity.EXTRA_USERNAME, username);
        startActivity(intent);
    }
}
