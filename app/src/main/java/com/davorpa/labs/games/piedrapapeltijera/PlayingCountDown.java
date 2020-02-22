package com.davorpa.labs.games.piedrapapeltijera;

import android.os.CountDownTimer;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class PlayingCountDown {

    private final int fromValue;
    private final CountDownTimer delegate;
    private AtomicInteger current;

    public PlayingCountDown(int fromValue) {
        this.fromValue = fromValue;
        this.current = new AtomicInteger(fromValue);
        this.delegate = new CountDownTimer(fromValue * 1000 + 200, 1000) {
            @Override public void onTick(long millisUntilFinished) {
                int value = current.getAndDecrement();
                PlayingCountDown.this.onTick(value);
            }

            @Override public void onFinish() {
                int value = current.getAndDecrement();
                PlayingCountDown.this.onFinish(value);
            }
        };
    }

    /**
     * Cancel the countdown.
     */
    public synchronized final PlayingCountDown cancel() {
        delegate.cancel();
        current = new AtomicInteger(this.fromValue);
        return this;
    }

    /**
     * Start the countdown.
     */
    public synchronized final PlayingCountDown start() {
        current = new AtomicInteger(this.fromValue);
        onStart(current.get());
        delegate.start();
        return this;
    }

    /**
     * Callback fired on start.
     * @param value the current value for the count down.
     */
    public void onStart(int value) {
    };

    /**
     * Callback fired on regular interval.
     * @param value the current value for the count down .
     */
    public abstract void onTick(int value);

    /**
     * Callback fired when the time is up.
     * @param value the current value for the count down .
     */
    public abstract void onFinish(int value);
}
