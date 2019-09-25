package com.weldnor.seabattle.model.player;

import com.weldnor.seabattle.model.MoveType;
import com.weldnor.seabattle.model.map.Map;
import com.weldnor.seabattle.model.map.Point;
import com.weldnor.seabattle.model.player.mapstrategy.MapStrategy;
import com.weldnor.seabattle.model.player.movestrategy.MoveStrategy;

public class SimpleBotPlayer implements BotPlayer {

    private MapStrategy mapStrategy;
    private MoveStrategy moveStrategy;

    public SimpleBotPlayer(MapStrategy mapStrategy, MoveStrategy moveStrategy) {
        this.mapStrategy = mapStrategy;
        this.moveStrategy = moveStrategy;
    }

    @Override
    public Point makeMove(Map ownMap, Map enemyMap, MoveType moveType) {
        return moveStrategy.makeMove(ownMap, enemyMap, moveType);
    }

    @Override
    public Map makeMap() {
        return mapStrategy.makeMap();
    }

    public MapStrategy getMapStrategy() {
        return mapStrategy;
    }

    public void setMapStrategy(MapStrategy mapStrategy) {
        this.mapStrategy = mapStrategy;
    }

    public MoveStrategy getMoveStrategy() {
        return moveStrategy;
    }

    public void setMoveStrategy(MoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }
}
