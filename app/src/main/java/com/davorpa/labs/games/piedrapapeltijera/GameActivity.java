package com.davorpa.labs.games.piedrapapeltijera;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

public class GameActivity extends AppCompatActivity {

    private static final String TAG  = "GameActivity";

    ImageView img_player;
    ImageView img_opponent;
    TextView lbl_match_status;
    TextView lbl_match_status_desc;
    Group grp_play_buttons;

    GameActivityHelper helper = new GameActivityHelper(this);
    Game game = new Game();


    @Override
    protected void onCreate(
            final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //
        // Capture the layout's components
        //
        final TextView txt_username = findViewById(R.id.txt_username);
        img_player = findViewById(R.id.img_player);
        img_opponent = findViewById(R.id.img_opponent);
        lbl_match_status = findViewById(R.id.lbl_match_status);
        lbl_match_status_desc = findViewById(R.id.lbl_match_status_desc);
        grp_play_buttons = findViewById(R.id.grp_play_buttons);

        //
        // Init this components
        //

        //set playerName by text provided from the entrant intent
        txt_username.setText(getIntent().getStringExtra(MainActivity.EXTRA_USERNAME));
        //clear play result
        updateViewForPlayResult(null);
        // clear game images
        helper.setPlayingImage(img_player, R.drawable.icon_quest);
        helper.setPlayingImage(img_opponent, R.drawable.icon_quest);
    }

    @Override
    protected void onDestroy()
    {
        // dispose resources
        if (playingCountdown != null) {
            playingCountdown.cancel();
        }
        playingBlocked = false;

        super.onDestroy();
    }


    /**
     * Event callback executed when Back button is clicked
     * @param view the button instance that fires the event
     */
    public void onBackButton(
            final View view)
    {
        onBackPressed();
    }


    PlayingCountDown playingCountdown;
    volatile boolean playingBlocked;
    Game.PlayRequest playerRequest;
    Game.PlayRequest opponentRequest;

    /**
     * Event callback executed when Stone|Paper|Scissors button is clicked
     * @param view the button instance that fires the event
     */
    public void onPlayButton(
            final View view)
    {
        // Set player current option
        playerRequest = helper.resolvePlayRequestFromResourceId(view.getId());
        updateViewForPlayerRequest(playerRequest, () -> {});

        synchronized (this) {
            // Allow change option while play buttons are available
            if (playingBlocked) {
                return;
            }
            playingBlocked = true;

            // Set opponent current option
            opponentRequest = game.generateRandomPlayRequest();

            // countdown animation
            playingCountdown = new PlayingCountDown(3) {
                @Override public void onStart(final int value) {
                    // Clear last play result
                    updateViewForPlayResult(null);
                    // Init opponent option
                    helper.setPlayingImage(img_opponent, R.drawable.icon_quest);
                }

                @Override public void onTick(final int value) {
                    if (value == 2) {
                        enablePlayButtons(false);
                    }
                    setMatchStatusMessage(value < 1
                            ? getString(R.string.lbl_go)
                            : String.valueOf(value));
                }

                @Override public void onFinish(final int value) {
                    setMatchStatusMessage(getString(R.string.lbl_go));

                    final Game.ComputedPlayResult playResult =
                            game.computePlayResult(playerRequest, opponentRequest);

                    updateViewForOpponentRequest(
                            opponentRequest,
                            () -> {
                                updateViewForPlayResult(playResult);
                                enablePlayButtons(true);
                                playingBlocked = false;
                            }
                    );
                }
            }.start();
        }
    }


    private void enablePlayButtons(
            final boolean enabled)
    {
        grp_play_buttons.setVisibility(enabled ? View.VISIBLE : View.INVISIBLE);
        grp_play_buttons.setEnabled(enabled);
    }


    private void updateViewForPlayerRequest(
            final Game.PlayRequest request,
            final Runnable onAnimationEnd)
    {
        final int imgResId = helper.resolveDrawableFromPlayRequest(request);
        helper.setPlayingImage(img_player, imgResId, onAnimationEnd);
    }


    private void updateViewForOpponentRequest(
            final Game.PlayRequest request,
            final Runnable onAnimationEnd)
    {
        final int imgResId = helper.resolveDrawableFromPlayRequest(request);
        helper.setPlayingImage(img_opponent, imgResId, onAnimationEnd);
    }


    private void updateViewForPlayResult(
            final Game.ComputedPlayResult playResult)
    {
        if (playResult == null) {
            setMatchStatusMessage(null);
            setMatchStatusDescription(null);
            return;
        }

        setMatchStatusMessage(
                helper.resolvePlayResultSubject(playResult.who)
            );
        setMatchStatusDescription(
                helper.resolvePlayResultReason(playResult.reason)
            );
    }


    private void setMatchStatusMessage(
            final CharSequence text)
    {
        lbl_match_status.setText(text);
    }

    private void setMatchStatusDescription(
            final CharSequence text)
    {
        lbl_match_status_desc.setVisibility(text == null || text.length() == 0
                ? View.GONE
                : View.VISIBLE);
        lbl_match_status_desc.setText(text);
    }

}
