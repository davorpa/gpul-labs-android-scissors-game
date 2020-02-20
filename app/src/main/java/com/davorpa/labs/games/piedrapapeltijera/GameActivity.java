package com.davorpa.labs.games.piedrapapeltijera;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    ImageView img_player;
    ImageView img_machine;
    TextView lbl_match_status;

    Game game = new Game();

    volatile CountDownTimer countdown;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Capture the layout's components
        final TextView txt_username = findViewById(R.id.txt_username);
        img_player = findViewById(R.id.img_player);
        img_machine = findViewById(R.id.img_machine);
        lbl_match_status = findViewById(R.id.lbl_match_status);

        // init this components
        txt_username.setText(getIntent().getStringExtra(MainActivity.EXTRA_USERNAME));
        setMatchStatusMessage("");
        setPlayingImage(img_player, R.drawable.icon_quest);
        setPlayingImage(img_machine, R.drawable.icon_quest);
    }

    @Override
    protected void onDestroy() {
        if (countdown != null) {
            countdown.cancel();
        }
        super.onDestroy();
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
        // Set player option
        final Game.PlayRequest playerRequest = resolvePlayerRequest(view.getId());
        updateViewForPlayerRequest(playerRequest);

        synchronized (this) {
            // Init opponent option
            setPlayingImage(img_machine, R.drawable.icon_quest);

            // cancel last countdown before recreate it (use double race with volatile+synchronized)
            if (countdown != null) {
                countdown.cancel();
            }
            countdown = new CountDownTimer(3500, 1000) {
                int number = 3;

                @Override public void onTick(long millisUntilFinished) {
                    setMatchStatusMessage(String.valueOf(number--));
                }

                @Override public void onFinish() {
                    // Sync machine intent image
                    final Game.PlayRequest machineRequest =
                            game.generateRandomPlayRequest();
                    updateViewForOpponentRequest(machineRequest);

                    // Generate match result
                    final Game.PlayResult playResult =
                            game.computePlayResult(playerRequest, machineRequest);
                    updateViewForPlayResult(playResult);
                }
            };
            countdown.start();
        }
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
        final int imgResId;
        switch (request) {
            case STONE:
                imgResId = R.drawable.icon_stone;
                break;
            case PAPER:
                imgResId = R.drawable.icon_papper;
                break;
            case SCISSORS:
                imgResId = R.drawable.icon_scissors;
                break;
            default:
                // Never should happen
                throw new UnsupportedOperationException(
                        "Unreachable action for player request " + request);
        }
        setPlayingImage(img_player, imgResId);
    }


    private void updateViewForOpponentRequest(Game.PlayRequest request) {
        final int imgResId;
        switch (request) {
            case STONE:
                imgResId = R.drawable.icon_stone;
                break;
            case PAPER:
                imgResId = R.drawable.icon_papper;
                break;
            case SCISSORS:
                imgResId = R.drawable.icon_scissors;
                break;
            default:
                // Never should happen
                throw new UnsupportedOperationException(
                        "Unreachable action for opponent request " + request);
        }
        setPlayingImage(img_machine, imgResId);
    }


    private void setPlayingImage(
            final ImageView image, final @DrawableRes int resId)
    {
        final String desc;
        switch (resId) {
            case R.drawable.icon_quest:
                desc = getString(R.string.lbl_thinking);
                break;
            case R.drawable.icon_stone:
                desc = getString(R.string.lbl_stone);
                break;
            case R.drawable.icon_papper:
                desc = getString(R.string.lbl_paper);
                break;
            case R.drawable.icon_scissors:
                desc = getString(R.string.lbl_scissors);
                break;
            default:
                // Never should happen
                throw new UnsupportedOperationException(
                        "Unsupported playing image resource: " + resId);
        }

        image.setImageResource(resId);
        image.setContentDescription(desc);
    }


    private void updateViewForPlayResult(Game.PlayResult playResult) {
        switch (playResult) {
            case PLAYER:
                setMatchStatusMessage(getString(R.string.lbl_win));
                break;
            case OPPONENT:
                setMatchStatusMessage(getString(R.string.lbl_loose));
                break;
            case DRAW:
                setMatchStatusMessage(getString(R.string.lbl_draw));
                break;
        }
    }


    private void setMatchStatusMessage(final CharSequence text) {
        lbl_match_status.setText(text);
    }

}
