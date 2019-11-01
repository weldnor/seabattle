package com.weldnor.seabattle.model.player;

import com.weldnor.seabattle.model.MoveType;
import com.weldnor.seabattle.model.map.*;

import java.util.List;
import java.util.Random;

public class SimpleBotPlayer implements BotPlayer {

    private Random random = new Random();

    @Override
    public Point makeMove(GameMap ownMap, GameMap enemyMap, MoveType moveType) {
        if (moveType == MoveType.Normal) {
            int x = random.nextInt(enemyMap.size());
            int y = random.nextInt(enemyMap.size());
            return new Point(x, y);
        }
        if (moveType == MoveType.SelectMine) {
            List<Cell> mines = ownMap.findCells(CellType.Mine);
            Cell mine = mines.get(random.nextInt(mines.size()));
            return mine.getPoint();
        }
        if (moveType == MoveType.SelectShip) {
            List<Cell> shipCells = ownMap.findCells(CellType.Ship);

            for (Cell shipCell : shipCells)
                if (!shipCell.isDestroyed()) return shipCell.getPoint();
        }
        return null;
    }

    @Override
    public GameMap makeMap() {
        return GameMapUtils.loadMapFromResource("/maps/1.txt");
    }
}
