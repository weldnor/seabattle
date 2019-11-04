package com.weldnor.seabattle.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.weldnor.seabattle.model.map.GameMap;
import com.weldnor.seabattle.model.player.Player;

public class GameState {
    private MoveType currentMoveType;
    private Player firstPlayer;
    private Player secondPlayer;
    private GameMap firstPlayerMap;
    private GameMap secondPlayerMap;
    private int currentPlayerIndex = 1;

    @JsonGetter
    public MoveType getCurrentMoveType() {
        return currentMoveType;
    }

    @JsonSetter
    public void setCurrentMoveType(MoveType currentMoveType) {
        this.currentMoveType = currentMoveType;
    }

    @JsonGetter
    public Player getFirstPlayer() {
        return firstPlayer;
    }

    @JsonSetter
    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    @JsonGetter
    public Player getSecondPlayer() {
        return secondPlayer;
    }

    @JsonSetter
    public void setSecondPlayer(Player secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    @JsonGetter
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    @JsonSetter
    public void setCurrentPlayerIndex(int currentPlayer) {
        this.currentPlayerIndex = currentPlayer;
    }

    @JsonGetter
    public GameMap getFirstPlayerMap() {
        return firstPlayerMap;
    }

    @JsonSetter
    public void setFirstPlayerMap(GameMap firstPlayerMap) {
        this.firstPlayerMap = firstPlayerMap;
    }

    @JsonGetter
    public GameMap getSecondPlayerMap() {
        return secondPlayerMap;
    }

    @JsonSetter
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
}