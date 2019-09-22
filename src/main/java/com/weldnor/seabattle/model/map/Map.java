package com.weldnor.seabattle.model.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public void setCell(int x, int y, Cell cell) {
        cells[x][y] = cell;
    }

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

    public int size() {
        return SIZE;
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
        //TODO write tests
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
