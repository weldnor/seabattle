package com.weldnor.seabattle.model.player;

import com.weldnor.seabattle.model.MoveType;
import com.weldnor.seabattle.model.map.Map;
import com.weldnor.seabattle.model.map.Point;

public class BotPlayer implements Player {
    @Override
    public PlayerType getPlayerType() {
        return null;
    }

    @Override
    public Map makeMap() {
        return null;
    }

    @Override
    public Point makeMove(Map playerMap, Map enemyMap, MoveType moveType) {
        return null;
    }
}
