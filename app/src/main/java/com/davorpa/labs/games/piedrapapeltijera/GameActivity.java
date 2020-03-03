package com.davorpa.labs.games.piedrapapeltijera;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class GameActivity extends AppCompatActivity {

    private static final String TAG  = "GameActivity";


    @BindView(R.id.txt_username) TextView txt_username;
    @BindView(R.id.img_player) ImageView img_player;
    @BindView(R.id.img_opponent) ImageView img_opponent;
    @BindView(R.id.lbl_match_status) TextView lbl_match_status;
    @BindView(R.id.lbl_match_status_desc) TextView lbl_match_status_desc;
    @BindView(R.id.grp_play_buttons) Group grp_play_buttons;
    Unbinder unbinder;

    GameActivityHelper helper = new GameActivityHelper(this);
    Game game = new Game();


    @Override
    protected void onCreate(
            final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //
        // Capture the layout's components handled by ButterKnife annotations
        //
        unbinder = ButterKnife.bind(this);

        //
        // Init this components
        //

        // set playerName by text provided from the entrant intent
        txt_username.setText(getIntent().getStringExtra(MainActivity.EXTRA_USERNAME));
        // clear play result
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
        unbinder.unbind();
    }


    /**
     * Event callback executed when Back button is clicked
     * @param view the button instance that fires the event
     */
    @OnClick({R.id.btn_back})
    public void onBackButton(
            final View view)
    {
        // Do the same android back button does
        onBackPressed();
    }


    PlayingCountDown playingCountdown;
    volatile boolean playingBlocked;
    Game.PlayRequest playerRequest;
    Game.PlayRequest opponentRequest;

    /**
     * Event callback executed when Stone|Paper|Scissors button is clicked
     * @param btn the button instance that fires the event
     */
    @OnClick({R.id.btn_stone, R.id.btn_paper, R.id.btn_scissors})
    public void onPlayButton(
            final Button btn)
    {
        // Set player current option
        playerRequest = helper.resolvePlayRequestFromResourceId(btn.getId());
        updateViewForPlayerRequest(playerRequest, () -> {});

        synchronized (this) {
            // Allow change option while play buttons are available (synchronized + volatile)
            if (playingBlocked) {
                return;
            }
            playingBlocked = true;

            // Set opponent current option
            opponentRequest = game.generateRandomPlayRequest();

            // Countdown animation
            playingCountdown = new PlayingCountDown(3) {
                @Override public void onStart(final int value) {
                    // Clear last play result
                    updateViewForPlayResult(null);
                    // Init opponent option
                    helper.setPlayingImage(img_opponent, R.drawable.icon_quest);
                }

                @Override public void onTick(final int value) {
                    if (value == 2) {
                        // Disable play buttons
                        enablePlayButtons(false);
                    }
                    // Set countdown number on UI
                    setMatchStatusMessage(value < 1
                            ? getString(R.string.lbl_go)
                            : String.valueOf(value));
                }

                @Override public void onFinish(final int value) {
                    setMatchStatusMessage(getString(R.string.lbl_go));

                    final Game.ComputedPlayResult playResult =
                            game.computePlayResult(playerRequest, opponentRequest);

                    // Set opponent option on UI
                    updateViewForOpponentRequest(
                            opponentRequest,
                            () -> {
                                // Set play result on UI
                                updateViewForPlayResult(playResult);
                                // Enable play buttons
                                enablePlayButtons(true);
                                playingBlocked = false;
                            }
                    );
                }
            }.start();
        }
    }


    /**
     * Enable or disable player play option UI buttons.
     * @param enabled <tt>true</tt> for enable buttons
     */
    private void enablePlayButtons(
            final boolean enabled)
    {
        grp_play_buttons.setVisibility(enabled ? View.VISIBLE : View.INVISIBLE);
        grp_play_buttons.setEnabled(enabled);
    }


    /**
     * Performs the UI update with the player play option.
     * @param request the player play option.
     * @param onAnimationEnd optional callback to execute when change animation ends.
     */
    private void updateViewForPlayerRequest(
            final @NonNull Game.PlayRequest request,
            final @Nullable Runnable onAnimationEnd)
    {
        final int imgResId = helper.resolveDrawableFromPlayRequest(request);
        helper.setPlayingImage(img_player, imgResId, onAnimationEnd);
    }


    /**
     * Performs the UI update with the opponent play option.
     * @param request the opponent play option.
     * @param onAnimationEnd optional callback to execute when change animation ends.
     */
    private void updateViewForOpponentRequest(
            final @NonNull Game.PlayRequest request,
            final @Nullable Runnable onAnimationEnd)
    {
        final int imgResId = helper.resolveDrawableFromPlayRequest(request);
        helper.setPlayingImage(img_opponent, imgResId, onAnimationEnd);
    }


    /**
     * Performs the UI update with the final play result.
     * @param result the play result.
     */
    private void updateViewForPlayResult(
            final @NonNull Game.ComputedPlayResult result)
    {
        if (result == null) {
            setMatchStatusMessage(null);
            setMatchStatusDescription(null);
            return;
        }

        setMatchStatusMessage(
                helper.resolvePlayResultSubject(result.who)
            );
        setMatchStatusDescription(
                helper.resolvePlayResultReason(result.reason)
            );
    }


    /**
     * Set text into the UI control that manage play status.
     * @param text the text to set.
     */
    private void setMatchStatusMessage(
            final @Nullable CharSequence text)
    {
        lbl_match_status.setText(text);
    }


    /**
     * Set text into the UI control that manage play status explanation.
     * @param text the text to set, <tt>null</tt> to hide it.
     */
    private void setMatchStatusDescription(
            final @Nullable CharSequence text)
    {
        lbl_match_status_desc.setVisibility(text == null || text.length() == 0
                ? View.GONE
                : View.VISIBLE);
        lbl_match_status_desc.setText(text);
    }

}
