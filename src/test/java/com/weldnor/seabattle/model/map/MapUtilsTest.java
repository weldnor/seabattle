package com.weldnor.seabattle.model.map;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MapUtilsTest {

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
        map.getCell(6, 8).setDestroyed(true);

        map.getCell(4, 5).setType(CellType.Mine);

        //минный тральщик
        map.getCell(3, 3).setType(CellType.Minesweeper);

    }

    @Test
    public void Reverse_NotDestroyedShipCell_IsHiddenCell() {
        Map reverseMap = MapUtils.reverse(map);
        Cell cell = map.getCell(0, 0);
        assertThat(cell.getType()).isEqualTo(CellType.Hidden);
    }

    @Test
    public void Reverse_DestroyedMineCell_IsSameCell() {
        Map reverseMap = MapUtils.reverse(map);
        Cell cell = map.getCell(6, 8);
        assertThat(cell.getType()).isEqualTo(CellType.Mine);
    }
}