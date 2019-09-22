package com.weldnor.seabattle.model.player;

import com.weldnor.seabattle.model.MoveType;
import com.weldnor.seabattle.model.map.Map;
import com.weldnor.seabattle.model.map.Point;

public interface Player {
    //TODO: name?
    //TODO: определиться с реализациями
    PlayerType getPlayerType();

    Map makeMap();

    Point makeMove(Map playerMap, Map enemyMap, MoveType moveType);
}
