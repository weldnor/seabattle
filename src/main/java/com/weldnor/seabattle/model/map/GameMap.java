package com.weldnor.seabattle.model.map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class GameMap implements Cloneable {
    private static final int SIZE = 10;

    @JsonSerialize
    @JsonDeserialize
    private Cell[][] cells;

    public GameMap() {
        cells = new Cell[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                cells[i][j] = new Cell(false, CellType.Water, new Point(i, j));
    }

    private GameMap(Cell[][] cells) {
        this.cells = cells;
    }

    //метод возвращает все клетки карты заданного типа
    public List<Cell> findCells(CellType type) {
        List<Cell> result = new ArrayList<>();

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (cells[i][j].getType() == type) {
                    result.add(cells[i][j]);
                }
            }
        }
        return result;
    }

    //метод находит все клетки корабля, в котором есть заданная клетка
    public List<Cell> findShip(Cell cell) {
        List<Cell> result = new ArrayList<>();

        //проверяем, что тип клетки - корабль
        if (cell.getType() != CellType.Ship)
            return result;

        Stack<Cell> stack = new Stack<>();
        stack.push(cell);

        while (!stack.isEmpty()) {
            Cell currect = stack.pop();

            if (result.contains(currect))
                continue;

            result.add(currect);

            int cx = currect.getPoint().getX();
            int cy = currect.getPoint().getY();

            if (cx + 1 < this.size() && this.getCell(cx + 1, cy).getType() == CellType.Ship)
                stack.push(this.getCell(cx + 1, cy));

            if (cx - 1 >= 0 && this.getCell(cx - 1, cy).getType() == CellType.Ship)
                stack.push(this.getCell(cx - 1, cy));

            if (cy + 1 < this.size() && this.getCell(cx, cy + 1).getType() == CellType.Ship)
                stack.push(this.getCell(cx, cy + 1));

            if (cy - 1 >= 0 && this.getCell(cx, cy - 1).getType() == CellType.Ship)
                stack.push(this.getCell(cx, cy - 1));
        }
        return result;
    }

    //метод находит все корабли на карте
    public List<List<Cell>> findAllShips() {
        List<List<Cell>> ships = new ArrayList<>();

        List<Cell> shipCells = this.findCells(CellType.Ship);

        while (!shipCells.isEmpty()) {
            List<Cell> ship = findShip(shipCells.get(0));
            for (Cell cell : ship)
                shipCells.remove(cell);

            ships.add(ship);
        }
        return ships;
    }

    public List<Cell> getNeighboringCells(Cell cell) {
        List<Cell> cells = new ArrayList<>();

        int x = cell.getPoint().getX();
        int y = cell.getPoint().getY();

        if (x + 1 < size()) cells.add(getCell(x + 1, y));
        if (x - 1 >= 0) cells.add(getCell(x - 1, y));
        if (y + 1 < size()) cells.add(getCell(x, y + 1));
        if (y - 1 >= 0) cells.add(getCell(x, y - 1));

        if (x + 1 < size() && y + 1 < size()) cells.add(getCell(x + 1, y + 1));
        if (x - 1 >= 0 && y + 1 < size()) cells.add(getCell(x - 1, y + 1));
        if (x + 1 < size() && y - 1 >= 0) cells.add(getCell(x + 1, y - 1));
        if (x - 1 >= 0 && y - 1 >= 0) cells.add(getCell(x - 1, y - 1));

        return cells;
    }

    public int size() {
        return SIZE;
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public void setCell(int x, int y, Cell cell) {
        cells[x][y] = cell;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameMap map = (GameMap) o;

        if (SIZE != SIZE) return false;
        return Arrays.deepEquals(cells, map.cells);
    }

    @Override
    public int hashCode() {
        int result = SIZE;
        result = 31 * result + Arrays.deepHashCode(cells);
        return result;
    }

    @Override
    protected Object clone() {
        Cell[][] newCells = new Cell[size()][size()];

        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                newCells[i][j] = (Cell) cells[i][j].clone();
            }
        }

        return new GameMap(newCells);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {

                Cell cell = getCell(i, j);
                char correctChar = 0;

                switch (cell.getType()) {
                    case Water:
                        correctChar = 'W';
                        break;
                    case Mine:
                        correctChar = 'M';
                        break;
                    case Ship:
                        correctChar = 'S';
                        break;
                    case Minesweeper:
                        correctChar = 'T';
                        break;
                    case Hidden:
                        correctChar = 'H';
                        break;
                }

                if (cell.isDestroyed())
                    correctChar = Character.toLowerCase(correctChar);

                builder.append(correctChar);
            }
            builder.append("\n");
        }

        return builder.toString();
    }
}
