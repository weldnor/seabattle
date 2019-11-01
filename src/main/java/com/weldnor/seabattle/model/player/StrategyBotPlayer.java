package com.weldnor.seabattle.model.player;

import com.weldnor.seabattle.model.MoveType;
import com.weldnor.seabattle.model.map.GameMap;
import com.weldnor.seabattle.model.map.Point;
import com.weldnor.seabattle.model.player.mapstrategy.MapStrategy;
import com.weldnor.seabattle.model.player.movestrategy.MoveStrategy;

public class StrategyBotPlayer implements BotPlayer {

    private MapStrategy mapStrategy;
    private MoveStrategy moveStrategy;

    public StrategyBotPlayer(MapStrategy mapStrategy, MoveStrategy moveStrategy) {
        this.mapStrategy = mapStrategy;
        this.moveStrategy = moveStrategy;
    }

    @Override
    public Point makeMove(GameMap ownMap, GameMap enemyMap, MoveType moveType) {
        return moveStrategy.makeMove(ownMap, enemyMap, moveType);
    }

    @Override
    public GameMap makeMap() {
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
