package com.davorpa.labs.games.piedrapapeltijera;

import java.util.Random;

public class Game {

    public enum PlayResult {
        PLAYER,
        DRAW,
        OPPONENT
    }

    public enum PlayRequest {
        STONE,
        PAPER,
        SCISSORS
    }

    private Random random = new Random();

    public PlayRequest generateRandomPlayRequest() {
        PlayRequest[] values = PlayRequest.values();
        int gen_idx = random.nextInt(values.length);
        return values[gen_idx];
    }

    public PlayResult computePlayResult(PlayRequest player, PlayRequest opponent) {
        if (player == opponent) {
            return PlayResult.DRAW;
        }
        else if (
            (PlayRequest.STONE.equals(player) && PlayRequest.SCISSORS.equals(opponent)) ||
            (PlayRequest.PAPER.equals(player) && PlayRequest.STONE.equals(opponent)) ||
            (PlayRequest.SCISSORS.equals(player) && PlayRequest.PAPER.equals(opponent))
        ) {
            return PlayResult.PLAYER;
        }
        return PlayResult.OPPONENT;
    }
}
