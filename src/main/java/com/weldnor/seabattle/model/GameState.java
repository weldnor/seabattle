package com.weldnor.seabattle.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.weldnor.seabattle.model.map.GameMap;
import com.weldnor.seabattle.model.player.Player;

public class GameState {
    private Player firstPlayer;
    private Player secondPlayer;
    private GameMap firstPlayerMap;
    private GameMap secondPlayerMap;
    private int currentPlayerIndex = 1;
    private MoveType currentMoveType = MoveType.Normal;

    public GameState() {
    }

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

    @JsonIgnore
    public GameMap getCurrentPlayerMap() {
        if (getCurrentPlayer() == firstPlayer)
            return firstPlayerMap;
        //else
        return secondPlayerMap;
    }

    @JsonIgnore
    public GameMap getCurrentEnemyMap() {
        if (getCurrentPlayer() == firstPlayer)
            return secondPlayerMap;
        //else
        return firstPlayerMap;
    }

    @JsonIgnore
    public Player getCurrentPlayer() {
        if (currentPlayerIndex == 1)
            return firstPlayer;
        return secondPlayer;
    }

    @JsonIgnore
    public void setCurrentPlayer(Player player) {
        if (player == firstPlayer) {
            currentPlayerIndex = 1;
        } else {
            currentPlayerIndex = 2;
        }
    }

    @Override
    public String toString() {
        return "GameState{" +
                "firstPlayer=" + firstPlayer +
                ", secondPlayer=" + secondPlayer +
                ", firstPlayerMap=" + firstPlayerMap +
                ", secondPlayerMap=" + secondPlayerMap +
                ", currentPlayerIndex=" + currentPlayerIndex +
                ", currentMoveType=" + currentMoveType +
                '}';
    }
}