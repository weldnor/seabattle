package com.weldnor.seabattle.model;

import com.weldnor.seabattle.model.map.Map;
import com.weldnor.seabattle.model.player.Player;

public class Game {
    private Player firstPlayer;
    private Player secondPlayer;

    private Map firstPlayerMap;
    private Map secondPlayerMap;

    public Game(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;

        firstPlayerMap = firstPlayer.makeMap();
        secondPlayerMap = secondPlayer.makeMap();
    }

    public void start() {
        firstPlayerMap = firstPlayer.makeMap();
        secondPlayerMap = secondPlayer.makeMap();
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
}
