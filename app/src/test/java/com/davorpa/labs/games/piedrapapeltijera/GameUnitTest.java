package com.davorpa.labs.games.piedrapapeltijera;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Local unit test for {@link Game game class},
 * which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class GameUnitTest {

    Game game;



    @Before
    public void setUp() {
        game = new Game();
    }



    @Test
    public void computePlayRequest_draw_STONE() {
        final Game.PlayRequest player = Game.PlayRequest.STONE;
        final Game.PlayRequest opponent = Game.PlayRequest.STONE;
        assertPlayRequest_draw(player, opponent);
    }

    @Test
    public void computePlayRequest_draw_PAPER() {
        final Game.PlayRequest player = Game.PlayRequest.PAPER;
        final Game.PlayRequest opponent = Game.PlayRequest.PAPER;
        assertPlayRequest_draw(player, opponent);
    }

    @Test
    public void computePlayRequest_draw_SCISSORS() {
        final Game.PlayRequest player = Game.PlayRequest.SCISSORS;
        final Game.PlayRequest opponent = Game.PlayRequest.SCISSORS;
        assertPlayRequest_draw(player, opponent);
    }

    void assertPlayRequest_draw(
            final Game.PlayRequest player,
            final Game.PlayRequest opponent)
    {
        final Game.ComputedPlayResult result = game.computePlayResult(player, opponent);
        assertThat(result, is(notNullValue()));
        assertThat(result.who, is(equalTo(Game.PlayResult.DRAW)));
        assertThat(result.reason, is(nullValue()));
    }



    @Test
    public void computePlayRequest_player_win_STONE_vs_SCCISORS() {
        final Game.PlayRequest player = Game.PlayRequest.STONE;
        final Game.PlayRequest opponent = Game.PlayRequest.SCISSORS;
        assertPlayRequest_player_win(player, opponent);
    }

    @Test
    public void computePlayRequest_player_win_PAPER_vs_STONE() {
        final Game.PlayRequest player = Game.PlayRequest.PAPER;
        final Game.PlayRequest opponent = Game.PlayRequest.STONE;
        assertPlayRequest_player_win(player, opponent);
    }

    @Test
    public void computePlayRequest_player_win_SCCISORS_vs_PAPER() {
        final Game.PlayRequest player = Game.PlayRequest.SCISSORS;
        final Game.PlayRequest opponent = Game.PlayRequest.PAPER;
        assertPlayRequest_player_win(player, opponent);
    }

    void assertPlayRequest_player_win(
            final Game.PlayRequest player,
            final Game.PlayRequest opponent)
    {
        final Game.ComputedPlayResult result = game.computePlayResult(player, opponent);
        assertThat(result, is(notNullValue()));
        assertThat(result.who, is(equalTo(Game.PlayResult.PLAYER)));
        assertThat(result.reason, is(equalTo(player)));
    }



    @Test
    public void computePlayRequest_player_loose_STONE_vs_PAPER() {
        final Game.PlayRequest player = Game.PlayRequest.STONE;
        final Game.PlayRequest opponent = Game.PlayRequest.PAPER;
        assertPlayRequest_player_loose(player, opponent);
    }

    @Test
    public void computePlayRequest_player_loose_PAPER_vs_SCCISORS() {
        final Game.PlayRequest player = Game.PlayRequest.PAPER;
        final Game.PlayRequest opponent = Game.PlayRequest.SCISSORS;
        assertPlayRequest_player_loose(player, opponent);
    }

    @Test
    public void computePlayRequest_player_loose_SCCISORS_vs_STONE() {
        final Game.PlayRequest player = Game.PlayRequest.SCISSORS;
        final Game.PlayRequest opponent = Game.PlayRequest.STONE;
        assertPlayRequest_player_loose(player, opponent);
    }

    void assertPlayRequest_player_loose(
            final Game.PlayRequest player,
            final Game.PlayRequest opponent)
    {
        final Game.ComputedPlayResult result = game.computePlayResult(player, opponent);
        assertThat(result, is(notNullValue()));
        assertThat(result.who, is(equalTo(Game.PlayResult.OPPONENT)));
        assertThat(result.reason, is(equalTo(opponent)));
    }
}
