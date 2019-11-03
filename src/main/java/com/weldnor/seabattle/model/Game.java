package com.weldnor.seabattle.model;

import com.weldnor.seabattle.model.map.*;
import com.weldnor.seabattle.model.player.BotPlayer;
import com.weldnor.seabattle.model.player.Player;

import java.util.List;

public class Game {

    private GameState state = new GameState();

    public Game(Player firstPlayer, Player secondPlayer) {
        state.setFirstPlayer(firstPlayer);
        state.setSecondPlayer(secondPlayer);
        state.setCurrentPlayer(firstPlayer);
        state.setCurrentMoveType(MoveType.Normal);
    }

    private void swapPlayer() {
        if (state.getCurrentPlayer() == state.getFirstPlayer()) {
            state.setCurrentPlayer(state.getSecondPlayer());
        } else {
            state.setCurrentPlayer(state.getFirstPlayer());
        }
    }

    private void makeMove(Point point) {
        GameMap ownMap = getCurrentPlayerMap();
        GameMap enemyMap = getCurrentEnemyMap();

        int x = point.getX();
        int y = point.getY();

        if (state.getCurrentMoveType() == MoveType.Normal) {
            Cell cell = enemyMap.getCell(x, y);

            if (cell.isDestroyed()) {
                swapPlayer();
            }

            switch (cell.getType()) {
                case Water:
                    GameMapUtils.FireInCell(enemyMap, point);
                    state.setCurrentMoveType(MoveType.Normal);
                    swapPlayer();
                    break;


                case Mine:
                    GameMapUtils.FireInCell(enemyMap, point);
                    state.setCurrentMoveType(MoveType.SelectShip);
                    break;


                case Minesweeper:
                    GameMapUtils.FireInCell(enemyMap, point);
                    //ищем не уничтоженные мины
                    List<Cell> mines = ownMap.findCells(CellType.Mine);
                    for (Cell mine : mines) {
                        if (!mine.isDestroyed()) {
                            state.setCurrentMoveType(MoveType.SelectMine);
                            return;
                        }
                    }
                    //если все мины уничтоженны
                    state.setCurrentMoveType(MoveType.Normal);
                    swapPlayer();
                    break;


                case Ship:
                    GameMapUtils.FireInCell(enemyMap, point);
                    break;
            }
        } else if (state.getCurrentMoveType() == MoveType.SelectMine) {
            Cell cell = ownMap.getCell(x, y);
            assert cell.getType() == CellType.Mine && !cell.isDestroyed();

            GameMapUtils.FireInCell(ownMap, point);
            state.setCurrentMoveType(MoveType.Normal);
            swapPlayer();
        } else if (state.getCurrentMoveType() == MoveType.SelectShip) {
            Cell cell = ownMap.getCell(x, y);
            assert cell.getType() == CellType.Ship && !cell.isDestroyed();

            GameMapUtils.FireInCell(ownMap, point);
            state.setCurrentMoveType(MoveType.Normal);
            swapPlayer();
        }
    }

    public void makeMoveByBot() {
        GameMap ownMap = getCurrentPlayerMap();
        GameMap enemyMap = getCurrentEnemyMap();

        BotPlayer player = (BotPlayer) state.getCurrentPlayer();
        Point move = player.makeMove(ownMap, enemyMap, state.getCurrentMoveType());
        makeMove(move);
    }

    public void makeMoveByHuman(Point point) {
        makeMove(point);
    }

    public boolean isEnd() {
        return GameMapUtils.isEnd(state.getFirstPlayerMap()) || GameMapUtils.isEnd(state.getSecondPlayerMap());
    }

    public GameMap getCurrentPlayerMap() {
        return state.getCurrentPlayerMap();
    }

    public GameMap getCurrentEnemyMap() {
        return state.getCurrentEnemyMap();
    }

    public MoveType getCurrentMoveType() {
        return state.getCurrentMoveType();
    }

    public Player getFirstPlayer() {
        return state.getFirstPlayer();
    }

    public Player getSecondPlayer() {
        return state.getSecondPlayer();
    }

    public Player getCurrentPlayer() {
        return state.getCurrentPlayer();
    }

    public GameMap getFirstPlayerMap() {
        return state.getFirstPlayerMap();
    }

    public void setFirstPlayerMap(GameMap firstPlayerMap) {
        state.setFirstPlayerMap(firstPlayerMap);
    }

    public GameMap getSecondPlayerMap() {
        return state.getSecondPlayerMap();
    }

    public void setSecondPlayerMap(GameMap secondPlayerMap) {
        state.setSecondPlayerMap(secondPlayerMap);
    }
}
