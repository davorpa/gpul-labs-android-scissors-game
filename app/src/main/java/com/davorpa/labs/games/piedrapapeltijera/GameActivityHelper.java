package com.davorpa.labs.games.piedrapapeltijera;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

class GameActivityHelper
{
    final GameActivity activity;


    GameActivityHelper(
            final @NonNull GameActivity activity)
    {
        this.activity = activity;
    }


    void setPlayingImage(
            final @NonNull ImageView image,
            final @DrawableRes int resId)
    {
        setPlayingImage(image, resId, null);
    }


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
                activity, android.R.anim.fade_out);
        final Animation anim_in = AnimationUtils.loadAnimation(
                activity, android.R.anim.fade_in);
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


    @NonNull
    @DrawableRes
    int resolveDrawableFromPlayRequest(
            final @NonNull Game.PlayRequest request)
    {
        switch (request) {
            case STONE:
                return R.drawable.icon_stone;
            case PAPER:
                return R.drawable.icon_papper;
            case SCISSORS:
                return R.drawable.icon_scissors;
        }
        // Never should happen
        throw new UnsupportedOperationException(
                "Unreachable action for play request " + request);
    }


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


    @NonNull
    String getDescriptionForResourcePlayIconId(
            final @DrawableRes int resId)
    {
        switch (resId) {
            case R.drawable.icon_quest:
                return activity.getString(R.string.lbl_thinking);
            case R.drawable.icon_stone:
                return activity.getString(R.string.lbl_stone);
            case R.drawable.icon_papper:
                return activity.getString(R.string.lbl_paper);
            case R.drawable.icon_scissors:
                return activity.getString(R.string.lbl_scissors);
        }
        // Never should happen
        throw new UnsupportedOperationException(
                "Unsupported playing image resource: " + resId);
    }
}
