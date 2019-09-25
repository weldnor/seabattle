package com.weldnor.seabattle.model.player;

import com.weldnor.seabattle.model.MoveType;
import com.weldnor.seabattle.model.map.Map;
import com.weldnor.seabattle.model.map.Point;

public interface BotPlayer extends Player {
    Point makeMove(Map ownMap, Map enemyMap, MoveType moveType);

    Map makeMap();
}
