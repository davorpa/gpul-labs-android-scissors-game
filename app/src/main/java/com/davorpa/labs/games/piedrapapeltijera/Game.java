package com.davorpa.labs.games.piedrapapeltijera;

import androidx.annotation.NonNull;

import java.util.Objects;
import java.util.Random;

/**
 * Game logic.
 */
public class Game {

    /**
     * Enumeration that holds the play status.
     */
    public enum PlayResult {
        PLAYER,
        DRAW,
        OPPONENT
    }

    /**
     * Enumeration that holds the play options.
     */
    public enum PlayRequest {
        STONE,
        PAPER,
        SCISSORS
    }


    /**
     * Value object to encapsulate a play result with who wins and the reason.
     */
    public static final class ComputedPlayResult
    {
        public final PlayResult who;
        public final PlayRequest reason;

        /**
         * Constructor for a ComputedPlayResult.
         *
         * @param who the result of playing
         * @param reason the reason why wins
         */
        ComputedPlayResult(
                final PlayResult who,
                final PlayRequest reason)
        {
            this.who = who;
            this.reason = reason;
        }

        /**
         * Checks the two objects for equality by delegating to their respective
         * {@link Object#equals(Object)} methods.
         *
         * @param o the {@link ComputedPlayResult} to which this one is to be checked for equality
         * @return true if the underlying objects of the Pair are both considered
         *         equal
         */
        @Override
        public boolean equals(Object o)
        {
            if (!(o instanceof ComputedPlayResult)) {
                return false;
            }
            ComputedPlayResult p = (ComputedPlayResult) o;
            return Objects.equals(p.who, who) && Objects.equals(p.reason, reason);
        }

        /**
         * Compute a hash code using the hash codes of the underlying objects
         *
         * @return a hashcode of the Pair
         */
        @Override
        public int hashCode()
        {
            return (who == null ? 0 : who.hashCode()) ^ (reason == null ? 0 : reason.hashCode());
        }

        @Override
        public String toString()
        {
            return "ComputedPlayResult{" + who + " " + reason + "}";
        }
    }




    private Random random = new Random();

    /**
     * Generate a random play option to use for example as a machine play option.
     * @return a random game play option.
     */
    public PlayRequest generateRandomPlayRequest()
    {
        PlayRequest[] values = PlayRequest.values();
        int gen_idx = random.nextInt(values.length);
        return values[gen_idx];
    }


    /**
     * Compute game play result.
     * @param player player play option.
     * @param opponent opponent play option.
     * @return the game play result.
     */
    public @NonNull ComputedPlayResult computePlayResult(
            final @NonNull PlayRequest player,
            final @NonNull PlayRequest opponent)
    {
        // nobody wins if same request value
        if (player == opponent) {
            return new ComputedPlayResult(PlayResult.DRAW, null);
        }

        // compute the winner
        PlayResult winner = (
                (PlayRequest.STONE.equals(player) && PlayRequest.SCISSORS.equals(opponent)) ||
                (PlayRequest.PAPER.equals(player) && PlayRequest.STONE.equals(opponent)) ||
                (PlayRequest.SCISSORS.equals(player) && PlayRequest.PAPER.equals(opponent))
            )
            ? PlayResult.PLAYER
            : PlayResult.OPPONENT;

        // compute the reason why winner wins
        PlayRequest reason = null;
        if (
                (PlayRequest.STONE.equals(player) && PlayRequest.SCISSORS.equals(opponent)) ||
                (PlayRequest.STONE.equals(opponent) && PlayRequest.SCISSORS.equals(player))
        ) {
            reason = PlayRequest.STONE;
        } else if (
                (PlayRequest.PAPER.equals(player) && PlayRequest.STONE.equals(opponent)) ||
                (PlayRequest.PAPER.equals(opponent) && PlayRequest.STONE.equals(player))
        ) {
            reason = PlayRequest.PAPER;
        } else if (
                (PlayRequest.SCISSORS.equals(player) && PlayRequest.PAPER.equals(opponent)) ||
                (PlayRequest.SCISSORS.equals(opponent) && PlayRequest.PAPER.equals(player))
        ) {
            reason = PlayRequest.SCISSORS;
        }

        return new ComputedPlayResult(winner, reason);
    }
}
