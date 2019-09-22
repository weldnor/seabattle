package com.weldnor.seabattle.model.player.movestrategy;

import com.weldnor.seabattle.model.map.Map;
import com.weldnor.seabattle.model.map.Point;

public interface MoveStrategy {
    Point makeMove(Map playerMap, Map enemyMap);
}
