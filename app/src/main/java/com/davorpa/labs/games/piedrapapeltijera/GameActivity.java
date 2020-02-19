package com.davorpa.labs.games.piedrapapeltijera;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        final TextView txt_username = findViewById(R.id.txt_username);
        txt_username.setText(getIntent().getStringExtra(MainActivity.EXTRA_USERNAME));

        ImageView img_player = findViewById(R.id.img_player);
        img_player.setImageResource(R.drawable.icon_quest);
        img_player.setContentDescription(getString(R.string.lbl_thinking));

        ImageView img_machine = findViewById(R.id.img_machine);
        img_machine.setImageResource(R.drawable.icon_quest);
        img_machine.setContentDescription(getString(R.string.lbl_thinking));
    }

    /**
     * Método que se ejecuta al pulsar el botón Back
     * @param view the button instance that fires the event
     */
    public void onBackButton(final View view) {
        onBackPressed();
    }

    /**
     * Método que se ejecuta al pulsar el botón Stone
     * @param view the button instance that fires the event
     */
    public void onStoneButton(final View view) {
        Toast.makeText(this, R.string.lbl_todo, Toast.LENGTH_LONG).show();
    }

    /**
     * Método que se ejecuta al pulsar el botón Paper
     * @param view the button instance that fires the event
     */
    public void onPaperButton(final View view) {
        Toast.makeText(this, R.string.lbl_todo, Toast.LENGTH_LONG).show();
    }

    /**
     * Método que se ejecuta al pulsar el botón Scissors
     * @param view the button instance that fires the event
     */
    public void onScissorsButton(final View view) {
        Toast.makeText(this, R.string.lbl_todo, Toast.LENGTH_LONG).show();
    }

}
