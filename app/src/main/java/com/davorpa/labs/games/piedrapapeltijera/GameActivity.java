package com.davorpa.labs.games.piedrapapeltijera;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        final TextView txt_username = findViewById(R.id.txt_username);
        txt_username.setText(getIntent().getStringExtra(MainActivity.EXTRA_USERNAME));
    }

    /**
     * Método que se ejecuta al pulsar el botón Back
     * @param view
     */
    public void onBackButton(final View view) {
        onBackPressed();
    }

    /**
     * Método que se ejecuta al pulsar el botón Stone
     * @param view
     */
    public void onStoneButton(final View view) {
        Toast.makeText(this, R.string.lbl_todo, Toast.LENGTH_LONG).show();
    }

    /**
     * Método que se ejecuta al pulsar el botón Paper
     * @param view
     */
    public void onPaperButton(final View view) {
        Toast.makeText(this, R.string.lbl_todo, Toast.LENGTH_LONG).show();
    }

    /**
     * Método que se ejecuta al pulsar el botón Scissors
     * @param view
     */
    public void onScissorsButton(final View view) {
        Toast.makeText(this, R.string.lbl_todo, Toast.LENGTH_LONG).show();
    }
}
