package com.davorpa.labs.games.piedrapapeltijera;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    ImageView img_player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        final TextView txt_username = findViewById(R.id.txt_username);
        txt_username.setText(getIntent().getStringExtra(MainActivity.EXTRA_USERNAME));

        img_player = findViewById(R.id.img_player);
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
     * Método que se ejecuta al pulsar el botón Stone|Paper|Scissors
     * @param view the button instance that fires the event
     */
    public void onPlayButton(final View view) {
        // Sync player intent image
        final int imgResId;
        final String imgDesc;
        switch (view.getId()) {
            case R.id.btn_stone:
                imgResId = R.drawable.icon_stone;
                imgDesc = getString(R.string.lbl_stone);
                break;
            case R.id.btn_paper:
                imgResId = R.drawable.icon_papper;
                imgDesc = getString(R.string.lbl_paper);
                break;
            case R.id.btn_scissors:
                imgResId = R.drawable.icon_scissors;
                imgDesc = getString(R.string.lbl_scissors);
                break;
            default:
                // Never should happen
                throw new UnsupportedOperationException(
                        "Unreachable action for button " + view.getId());
        }
        AppAnimationUtils.animateImageViewChange(this, img_player, imgResId,
                new AppAnimationUtils.InOutAnimationListener(){
                    @Override public void onAnimationStart(Animation animation) { }
                    @Override public void onAnimationEnd(Animation animation) {
                        img_player.setContentDescription(imgDesc);
                    }
                }
            );

        //TODO Fire game logic
    }

}
