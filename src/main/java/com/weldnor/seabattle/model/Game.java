package com.weldnor.seabattle.model;

import com.weldnor.seabattle.model.map.Map;
import com.weldnor.seabattle.model.player.BotPlayer;
import com.weldnor.seabattle.model.player.Player;

public class Game {
    private Player firstPlayer;
    private Player secondPlayer;

    private Map firstPlayerMap;
    private Map secondPlayerMap;

    public Game(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    public void start() {
        if (firstPlayer instanceof BotPlayer) {
            firstPlayerMap = ((BotPlayer) firstPlayer).makeMap();
        }
        //TODO: проверка корректности карт
        //TODO: обработка HumanPlayer

        if (secondPlayer instanceof BotPlayer) {
            secondPlayerMap = ((BotPlayer) secondPlayer).makeMap();
        }
    }

    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public void setSecondPlayer(Player secondPlayer) {
        this.secondPlayer = secondPlayer;
    }
}
