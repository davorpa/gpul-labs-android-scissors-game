package com.davorpa.labs.games.piedrapapeltijera;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    ImageView img_player;
    ImageView img_machine;
    TextView lbl_match_status;

    Game game = new Game();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        final TextView txt_username = findViewById(R.id.txt_username);
        txt_username.setText(getIntent().getStringExtra(MainActivity.EXTRA_USERNAME));

        img_player = findViewById(R.id.img_player);
        img_player.setImageResource(R.drawable.icon_quest);
        img_player.setContentDescription(getString(R.string.lbl_thinking));

        img_machine = findViewById(R.id.img_machine);
        img_machine.setImageResource(R.drawable.icon_quest);
        img_machine.setContentDescription(getString(R.string.lbl_thinking));

        lbl_match_status = findViewById(R.id.lbl_match_status);
        lbl_match_status.setText("");
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
        final Game.PlayRequest playerRequest = resolvePlayerRequest(view.getId());
        updateViewForPlayerRequest(playerRequest);

        // Sync machine intent image
        final Game.PlayRequest machineRequest = game.generateRandomPlayRequest();
        updateViewForOpponentRequest(machineRequest);

        // Generate match result
        Game.PlayResult playResult = game.computePlayResult(playerRequest, machineRequest);
        updateViewForPlayResult(playResult);
    }

    private Game.PlayRequest resolvePlayerRequest(@IdRes int resId) {
        final Game.PlayRequest playerRequest;
        switch (resId) {
            case R.id.btn_stone:
                playerRequest = Game.PlayRequest.STONE;
                break;
            case R.id.btn_paper:
                playerRequest = Game.PlayRequest.PAPER;
                break;
            case R.id.btn_scissors:
                playerRequest = Game.PlayRequest.SCISSORS;
                break;
            default:
                // Never should happen
                throw new UnsupportedOperationException(
                        "Unreachable action for player request " + resId);
        }
        return playerRequest;
    }

    private void updateViewForPlayerRequest(Game.PlayRequest request) {
        int imgResId;
        String imgDesc;
        switch (request) {
            case STONE:
                imgResId = R.drawable.icon_stone;
                imgDesc = getString(R.string.lbl_stone);
                break;
            case PAPER:
                imgResId = R.drawable.icon_papper;
                imgDesc = getString(R.string.lbl_paper);
                break;
            case SCISSORS:
                imgResId = R.drawable.icon_scissors;
                imgDesc = getString(R.string.lbl_scissors);
                break;
            default:
                // Never should happen
                throw new UnsupportedOperationException(
                        "Unreachable action for player request " + request);
        }
        img_player.setImageResource(imgResId);
        img_player.setContentDescription(imgDesc);
    }

    private void updateViewForOpponentRequest(Game.PlayRequest request) {
        int imgResId;
        String imgDesc;
        switch (request) {
            case STONE:
                imgResId = R.drawable.icon_stone;
                imgDesc = getString(R.string.lbl_stone);
                break;
            case PAPER:
                imgResId = R.drawable.icon_papper;
                imgDesc = getString(R.string.lbl_paper);
                break;
            case SCISSORS:
                imgResId = R.drawable.icon_scissors;
                imgDesc = getString(R.string.lbl_scissors);
                break;
            default:
                // Never should happen
                throw new UnsupportedOperationException(
                        "Unreachable action for opponent request " + request);
        }
        img_machine.setImageResource(imgResId);
        img_machine.setContentDescription(imgDesc);
    }

    private void updateViewForPlayResult(Game.PlayResult playResult) {
        switch (playResult) {
            case PLAYER:
                lbl_match_status.setText(R.string.lbl_win);
                break;
            case OPPONENT:
                lbl_match_status.setText(R.string.lbl_loose);
                break;
            case DRAW:
                lbl_match_status.setText(R.string.lbl_draw);
                break;
        }
    }

}
