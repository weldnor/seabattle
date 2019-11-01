package com.weldnor.seabattle.model.player;

import com.weldnor.seabattle.model.MoveType;
import com.weldnor.seabattle.model.map.GameMap;
import com.weldnor.seabattle.model.map.Point;

public interface BotPlayer extends Player {
    Point makeMove(GameMap ownMap, GameMap enemyMap, MoveType moveType);

    GameMap makeMap();
}
