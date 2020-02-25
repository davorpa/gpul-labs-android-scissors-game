package com.davorpa.labs.games.piedrapapeltijera;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Helper class to bridge/translate game UI with their respective game logic.
 */
class GameActivityHelper
{
    final GameActivity activity;


    GameActivityHelper(
            final @NonNull GameActivity activity)
    {
        this.activity = activity;
    }


    /**
     * Change the image source to a target image.
     * @param image image view to manage.
     * @param resId the target drawable resource identifier to set.
     */
    void setPlayingImage(
            final @NonNull ImageView image,
            final @DrawableRes int resId)
    {
        setPlayingImage(image, resId, null);
    }

    /**
     * Change the image source doing a fade animation between current and target image.
     * @param image image view to manage.
     * @param resId the target drawable resource identifier to set.
     * @param onAnimationEnd optional callback to execute when animation ends,
     *                       if <tt>null</tt> no animation is done.
     */
    void setPlayingImage(
            final @NonNull ImageView image,
            final @DrawableRes int resId,
            final @Nullable Runnable onAnimationEnd)
    {
        final String desc = getDescriptionForResourcePlayIconId(resId);

        if (onAnimationEnd == null) { // no animation needed
            image.setImageResource(resId);
            image.setContentDescription(desc);
            return;
        }

        final Animation anim_out = AnimationUtils.loadAnimation(
                activity, R.anim.fade_out);
        final Animation anim_in = AnimationUtils.loadAnimation(
                activity, R.anim.fade_in);
        anim_out.setAnimationListener(new Animation.AnimationListener()
        {
            @Override public void onAnimationStart(Animation animation) {}
            @Override public void onAnimationRepeat(Animation animation) {}
            @Override public void onAnimationEnd(Animation animation)
            {
                image.setImageResource(resId);
                image.setContentDescription(desc);
                anim_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override public void onAnimationStart(Animation animation) {}
                    @Override public void onAnimationRepeat(Animation animation) {}
                    @Override public void onAnimationEnd(Animation animation) {
                        onAnimationEnd.run();
                    }
                });
                image.startAnimation(anim_in);
            }
        });
        image.startAnimation(anim_out);
    }


    /**
     * Resolve/translate the {@link com.davorpa.labs.games.piedrapapeltijera.Game.PlayRequest}
     * game object from their UI id.
     * @param resId the view identifier.
     * @return
     */
    @NonNull
    Game.PlayRequest resolvePlayRequestFromResourceId(
            final @IdRes int resId)
    {
        switch (resId) {
            case R.id.btn_stone:
                return Game.PlayRequest.STONE;
            case R.id.btn_paper:
                return Game.PlayRequest.PAPER;
            case R.id.btn_scissors:
                return Game.PlayRequest.SCISSORS;
        }
        // Never should happen
        throw new UnsupportedOperationException(
                "Unreachable action for play option " + resId);
    }


    /**
     * Resolve/translate the drawable id
     * from their {@link com.davorpa.labs.games.piedrapapeltijera.Game.PlayRequest}
     * game object.
     * @param request the game object to inspect.
     * @return the drawable identifier.
     */
    @NonNull
    @DrawableRes
    int resolveDrawableFromPlayRequest(
            final @NonNull Game.PlayRequest request)
    {
        switch (request) {
            case STONE:
                return R.drawable.icon_stone;
            case PAPER:
                return R.drawable.icon_paper;
            case SCISSORS:
                return R.drawable.icon_scissors;
        }
        // Never should happen
        throw new UnsupportedOperationException(
                "Unreachable action for play request " + request);
    }


    /**
     * Gets the UI message to text the game status reason
     * that match with the given
     * {@link com.davorpa.labs.games.piedrapapeltijera.Game.PlayRequest} game object.
     * @param reason the game object to inspect.
     * @return the string value message.
     */
    @Nullable
    String resolvePlayResultReason(
            final @Nullable Game.PlayRequest reason)
    {
        if (reason != null) {
            switch (reason) {
                case STONE:
                    return activity.getString(R.string.stone_brokes_scissors);
                case PAPER:
                    return activity.getString(R.string.paper_wraps_stones);
                case SCISSORS:
                    return activity.getString(R.string.scissor_cuts_papers);
            }
        }
        return null;
    }


    /**
     * Gets the UI message to text the game status
     * that match with the given
     * {@link com.davorpa.labs.games.piedrapapeltijera.Game.PlayResult} game object.
     * @param who the game object to inspect.
     * @return the string value message.
     */
    @Nullable
    String resolvePlayResultSubject(
            final @Nullable Game.PlayResult who)
    {
        if (who != null) {
            switch (who) {
                case PLAYER:
                    return activity.getString(R.string.lbl_win);
                case OPPONENT:
                    return activity.getString(R.string.lbl_loose);
                case DRAW:
                    return activity.getString(R.string.lbl_draw);
            }
        }
        return null;
    }


    /**
     * Gets the UI description to text the game status
     * that hint the given image resource identifier.
     * @param resId the drawable identifier.
     * @return the string value hint.
     */
    @NonNull
    String getDescriptionForResourcePlayIconId(
            final @DrawableRes int resId)
    {
        switch (resId) {
            case R.drawable.icon_quest:
                return activity.getString(R.string.lbl_thinking);
            case R.drawable.icon_stone:
                return activity.getString(R.string.lbl_stone);
            case R.drawable.icon_paper:
                return activity.getString(R.string.lbl_paper);
            case R.drawable.icon_scissors:
                return activity.getString(R.string.lbl_scissors);
        }
        // Never should happen
        throw new UnsupportedOperationException(
                "Unsupported playing image resource: " + resId);
    }
}
