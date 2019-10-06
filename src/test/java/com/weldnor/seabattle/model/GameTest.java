package com.weldnor.seabattle.model;

import com.weldnor.seabattle.model.map.Map;
import com.weldnor.seabattle.model.map.MapUtils;
import com.weldnor.seabattle.model.map.Point;
import com.weldnor.seabattle.model.player.BotPlayer;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class GameTest {

    private BotPlayer firstPlayer;
    private BotPlayer secondPlayer;
    private Map firstPlayerMap;
    private Map secondPlayerMap;
    private Game game;

    @Before
    public void setUp() throws Exception {
        firstPlayer = mock(BotPlayer.class);
        firstPlayerMap = MapUtils.loadMapFromResource("/valid_maps/1.txt");

        secondPlayer = mock(BotPlayer.class);
        secondPlayerMap = MapUtils.loadMapFromResource("/valid_maps/2.txt");

        game = new Game(firstPlayer, secondPlayer);
        game.setFirstPlayerMap(firstPlayerMap);
        game.setSecondPlayerMap(secondPlayerMap);
    }

    @Test
    public void MakeMove_NormalAndFireToWater_Normal() {
        when(firstPlayer.makeMove(any(Map.class), any(Map.class), eq(MoveType.Normal)))
                .thenReturn(new Point(0, 0));

        game.makeMoveByBot();

        assertThat(game.getCurrentPlayer()).isEqualTo(secondPlayer);
        assertThat(game.getCurrentMoveType()).isEqualTo(MoveType.Normal);
    }

    @Test
    public void MakeMove_NormalAndFireToMine_SelectShip() {
        when(firstPlayer.makeMove(any(Map.class), any(Map.class), eq(MoveType.Normal)))
                .thenReturn(new Point(2, 6));

        game.makeMoveByBot();

        assertThat(game.getCurrentPlayer()).isEqualTo(firstPlayer);
        assertThat(game.getCurrentMoveType()).isEqualTo(MoveType.SelectShip);
    }

    @Test
    public void MakeMove_NormalAndFireToMinesweeper_SelectMine() {
        when(firstPlayer.makeMove(any(Map.class), any(Map.class), eq(MoveType.Normal)))
                .thenReturn(new Point(0, 9));

        game.makeMoveByBot();
        assertThat(game.getCurrentPlayer()).isEqualTo(firstPlayer);
        assertThat(game.getCurrentMoveType()).isEqualTo(MoveType.SelectMine);
    }

    @Test
    public void MakeMove_NormalAndFireToShip_Normal() {
        when(firstPlayer.makeMove(any(Map.class), any(Map.class), eq(MoveType.Normal)))
                .thenReturn(new Point(4, 0));

        game.makeMoveByBot();
        assertThat(game.getCurrentPlayer()).isEqualTo(firstPlayer);
        assertThat(game.getCurrentMoveType()).isEqualTo(MoveType.Normal);
    }

    @Test
    public void MakeMove_SelectMine_Normal() {
        when(firstPlayer.makeMove(any(Map.class), any(Map.class), eq(MoveType.Normal)))
                .thenReturn(new Point(0, 9));

        when(firstPlayer.makeMove(any(Map.class), any(Map.class), eq(MoveType.SelectMine)))
                .thenReturn(new Point(5, 3));

        game.makeMoveByBot();
        game.makeMoveByBot();

        assertThat(game.getCurrentPlayer()).isEqualTo(secondPlayer);
        assertThat(game.getCurrentMoveType()).isEqualTo(MoveType.Normal);
    }

    @Test
    public void MakeMove_SelectShip_Normal() {
        when(firstPlayer.makeMove(any(Map.class), any(Map.class), eq(MoveType.Normal)))
                .thenReturn(new Point(2, 6));

        when(firstPlayer.makeMove(any(Map.class), any(Map.class), eq(MoveType.SelectShip)))
                .thenReturn(new Point(0, 0));

        game.makeMoveByBot();
        game.makeMoveByBot();

        assertThat(game.getCurrentPlayer()).isEqualTo(secondPlayer);
        assertThat(game.getCurrentMoveType()).isEqualTo(MoveType.Normal);
    }
}