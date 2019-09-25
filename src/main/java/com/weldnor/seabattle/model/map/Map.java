package com.weldnor.seabattle.model.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Map implements Cloneable {

    private final int SIZE = 10;

    private Cell[][] cells;

    public Map() {
        cells = new Cell[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                cells[i][j] = new Cell(false, CellType.Water, new Point(i, j));
    }

    private Map(Cell[][] cells) {
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
    private List<Cell> findShip(Cell cell) {
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

        Map map = (Map) o;

        if (SIZE != map.SIZE) return false;
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
        Cell[][] newCells = cells.clone();
        return new Map(newCells);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Cell curr = getCell(i, j);
                switch (curr.getType()) {
                    case Water:
                        builder.append('w');
                        break;
                    case Mine:
                        builder.append('m');
                        break;
                    case Ship:
                        builder.append('s');
                        break;
                    case Minesweeper:
                        builder.append('M');
                        break;
                    case Hidden:
                        builder.append('h');
                        break;
                }
            }
            builder.append("\n");
        }

        return builder.toString();
    }
}
