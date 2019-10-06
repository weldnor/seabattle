package com.weldnor.seabattle.model;

import com.weldnor.seabattle.model.map.*;
import com.weldnor.seabattle.model.player.BotPlayer;
import com.weldnor.seabattle.model.player.Player;

import java.util.List;

public class Game {
    private MoveType currentMoveType;
    private Player firstPlayer;
    private Player secondPlayer;
    private Player currentPlayer;
    private Map firstPlayerMap;
    private Map secondPlayerMap;


    public Game(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.currentPlayer = firstPlayer;
        this.currentMoveType = MoveType.Normal;
    }

    private void swapPlayer() {
        if (currentPlayer == firstPlayer) {
            currentPlayer = secondPlayer;
        } else {
            currentPlayer = firstPlayer;
        }
    }

    private void makeMove(Point point) {
        Map ownMap = getCurrentPlayerMap();
        Map enemyMap = getCurrentEnemyMap();

        int x = point.getX();
        int y = point.getY();

        if (currentMoveType == MoveType.Normal) {
            Cell cell = enemyMap.getCell(x, y);

            if (cell.isDestroyed()) {
                swapPlayer();
            }

            switch (cell.getType()) {
                case Water:
                    MapUtils.FireInCell(enemyMap, point);
                    currentMoveType = MoveType.Normal;
                    swapPlayer();
                    break;


                case Mine:
                    MapUtils.FireInCell(enemyMap, point);
                    currentMoveType = MoveType.SelectShip;
                    break;


                case Minesweeper:
                    MapUtils.FireInCell(enemyMap, point);
                    //ищем не уничтоженные мины
                    List<Cell> mines = ownMap.findCells(CellType.Mine);
                    for (Cell mine : mines) {
                        if (!mine.isDestroyed()) {
                            currentMoveType = MoveType.SelectMine;
                            return;
                        }
                    }
                    //если все мины уничтоженны
                    currentMoveType = MoveType.Normal;
                    swapPlayer();
                    break;


                case Ship:
                    MapUtils.FireInCell(enemyMap, point);
                    break;
            }
        } else if (currentMoveType == MoveType.SelectMine) {
            Cell cell = ownMap.getCell(x, y);
            assert cell.getType() == CellType.Mine && !cell.isDestroyed();

            MapUtils.FireInCell(ownMap, point);
            currentMoveType = MoveType.Normal;
            swapPlayer();
        } else if (currentMoveType == MoveType.SelectShip) {
            Cell cell = ownMap.getCell(x, y);
            assert cell.getType() == CellType.Ship && !cell.isDestroyed();

            MapUtils.FireInCell(ownMap, point);
            currentMoveType = MoveType.Normal;
            swapPlayer();
        }
    }

    public void makeMoveByBot() {
        Map ownMap = getCurrentPlayerMap();
        Map enemyMap = getCurrentEnemyMap();

        BotPlayer player = (BotPlayer) currentPlayer;
        Point move = player.makeMove(ownMap, enemyMap, currentMoveType);
        makeMove(move);
    }

    public void makeMoveByHuman(Point point) {
        makeMove(point);
    }

    public boolean isEnd() {
        return MapUtils.isEnd(firstPlayerMap) || MapUtils.isEnd(secondPlayerMap);
    }

    public Map getCurrentPlayerMap() {
        return currentPlayer == firstPlayer ? firstPlayerMap : secondPlayerMap;
    }

    public Map getCurrentEnemyMap() {
        return currentPlayer == firstPlayer ? secondPlayerMap : firstPlayerMap;
    }

    public MoveType getCurrentMoveType() {
        return currentMoveType;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Map getFirstPlayerMap() {
        return firstPlayerMap;
    }

    public void setFirstPlayerMap(Map firstPlayerMap) {
        this.firstPlayerMap = firstPlayerMap;
    }

    public Map getSecondPlayerMap() {
        return secondPlayerMap;
    }

    public void setSecondPlayerMap(Map secondPlayerMap) {
        this.secondPlayerMap = secondPlayerMap;
    }
}
