package com.weldnor.seabattle.model.map;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MapTest {

    private Map map;

    @Before
    public void setUp() throws Exception {
        map = new Map();

        //первый корабль
        for (int i = 0; i < 3; i++)
            map.getCell(0, i).setType(CellType.Ship);

        map.getCell(0, 2).setDestroyed(true);
        map.getCell(0, 2).setDestroyed(true);

        //второй корабль
        for (int i = 0; i < 4; i++)
            map.getCell(i, 7).setType(CellType.Ship);

        //мины
        map.getCell(6, 8).setType(CellType.Mine);
        map.getCell(4, 5).setType(CellType.Mine);

        //минный тральщик
        map.getCell(3, 3).setType(CellType.Minesweeper);
    }

    @Test
    public void FindCells_MapWithMines_ReturnsMines() {
        List<Cell> mines = map.findCells(CellType.Mine);
        assertThat(mines).containsOnly(
                map.getCell(6, 8),
                map.getCell(4, 5)
        );
    }

    @Test
    public void FindCells_MapWithMinesweeper_ReturnsMinesweeper() {
        List<Cell> minesweeper = map.findCells(CellType.Minesweeper);
        assertThat(minesweeper).containsOnly(
                map.getCell(3, 3)
        );
    }

    @Test
    public void FindAllShips_MapWithShips_ReturnsShips() {
        List<List<Cell>> ships = map.findAllShips();
        assertThat(ships).hasSize(2);
    }

    @Test
    public void GetNeighboringCells_InCorner() {
        List<Cell> cells = map.getNeighboringCells(map.getCell(0, 0));
        assertThat(cells).containsOnly(
                map.getCell(0, 1),
                map.getCell(1, 0),
                map.getCell(1, 1)
        );
    }

    @Test
    public void GetNeighboringCells_InCenter() {
        List<Cell> cells = map.getNeighboringCells(map.getCell(3, 6));
        assertThat(cells).containsOnly(
                map.getCell(2, 5), map.getCell(2, 6),
                map.getCell(2, 7), map.getCell(3, 5),
                map.getCell(3, 7), map.getCell(4, 5),
                map.getCell(4, 6), map.getCell(4, 7)
        );
    }
}