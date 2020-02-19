package com.davorpa.labs.games.piedrapapeltijera;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

/**
 * Defines common utilities for working with animations.
 *
 */
public class AppAnimationUtils {

    /**
     * Performs an {@link Animation} object that swap the resource
     * of a provided {@link ImageView} to other drawable resource.
     *
     * @param context Application context used to access resources
     * @param view The {@link ImageView} to be animated
     * @param resId the resource identifier of the next drawable
     */
    public static void animateImageViewChange(
            Context context,
            final ImageView view, final @DrawableRes int resId,
            @Nullable final InOutAnimationListener listener)
    {
        final Animation anim_out = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
        final Animation anim_in  = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        anim_out.setAnimationListener(new Animation.AnimationListener()
        {
            @Override public void onAnimationStart(Animation animation) {
                if (listener != null) listener.onAnimationStart(animation);
            }
            @Override public void onAnimationRepeat(Animation animation) {}
            @Override public void onAnimationEnd(Animation animation)
            {
                view.setImageResource(resId);
                anim_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override public void onAnimationStart(Animation animation) {}
                    @Override public void onAnimationRepeat(Animation animation) {}
                    @Override public void onAnimationEnd(Animation animation) {
                        if (listener != null) listener.onAnimationEnd(animation);
                    }
                });
                view.startAnimation(anim_in);
            }
        });
        view.startAnimation(anim_out);
    }

    /**
     * <p>An animation listener receives notifications from an animation.
     * Notifications indicate animation related events, such as the start
     * or the end or the animation.</p>
     */
    public interface InOutAnimationListener {
        /**
         * <p>Notifies the start of the animation.</p>
         *
         * @param animation The started animation.
         */
        void onAnimationStart(Animation animation);

        /**
         * <p>Notifies the end of the animation.
         *
         * @param animation The animation which reached its end.
         */
        void onAnimationEnd(Animation animation);
    }
}
