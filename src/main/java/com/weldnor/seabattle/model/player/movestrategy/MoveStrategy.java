package com.weldnor.seabattle.model.player.movestrategy;

import com.weldnor.seabattle.model.MoveType;
import com.weldnor.seabattle.model.map.GameMap;
import com.weldnor.seabattle.model.map.Point;

public interface MoveStrategy {
    Point makeMove(GameMap ownMap, GameMap enemyMap, MoveType moveType);
}
