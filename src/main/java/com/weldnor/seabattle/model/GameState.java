package com.weldnor.seabattle.model;

import com.weldnor.seabattle.model.map.GameMap;
import com.weldnor.seabattle.model.player.Player;

public class GameState {
    private MoveType currentMoveType;
    private Player firstPlayer;
    private Player secondPlayer;
    private int currentPlayerIndex = 1;
    private GameMap firstPlayerMap;
    private GameMap secondPlayerMap;

    public MoveType getCurrentMoveType() {
        return currentMoveType;
    }

    public void setCurrentMoveType(MoveType currentMoveType) {
        this.currentMoveType = currentMoveType;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(Player secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int currentPlayer) {
        this.currentPlayerIndex = currentPlayer;
    }

    public Player getCurrentPlayer() {
        if (currentPlayerIndex == 1)
            return firstPlayer;
        return secondPlayer;
    }

    public void setCurrentPlayer(Player player) {
        if (player == firstPlayer) {
            currentPlayerIndex = 1;
        } else {
            currentPlayerIndex = 2;
        }
    }

    public GameMap getFirstPlayerMap() {
        return firstPlayerMap;
    }

    public void setFirstPlayerMap(GameMap firstPlayerMap) {
        this.firstPlayerMap = firstPlayerMap;
    }

    public GameMap getSecondPlayerMap() {
        return secondPlayerMap;
    }

    public void setSecondPlayerMap(GameMap secondPlayerMap) {
        this.secondPlayerMap = secondPlayerMap;
    }

    public GameMap getCurrentPlayerMap() {
        if (getCurrentPlayer() == firstPlayer)
            return firstPlayerMap;
        //else
        return secondPlayerMap;
    }

    public GameMap getCurrentEnemyMap() {
        if (getCurrentPlayer() == firstPlayer)
            return secondPlayerMap;
        //else
        return firstPlayerMap;
    }
}