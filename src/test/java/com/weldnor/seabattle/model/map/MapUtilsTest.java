package com.weldnor.seabattle.model.map;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MapUtilsTest {

    private Map map;

    @Before
    public void setUp() throws Exception {
        map = new Map();
        map.getCell(0, 0).setType(CellType.Ship);
        map.getCell(0, 1).setType(CellType.Ship);
        map.getCell(0, 2).setType(CellType.Ship);

        map.getCell(0, 0).setDestroyed(true);

        map.getCell(5, 5).setType(CellType.Mine);

    }

    @Test
    public void testReverse() {
        Map reverseMap = MapUtils.reverse(map);
        assertThat(reverseMap.getCell(0, 0).getType()).isEqualTo(CellType.Ship);
        assertThat(reverseMap.getCell(0, 0).isDestroyed()).isTrue();
        assertThat(reverseMap.getCell(0, 1).getType()).isEqualTo(CellType.Hidden);
        assertThat(reverseMap.getCell(0, 1).isDestroyed()).isFalse();
        assertThat(reverseMap.getCell(5, 5).getType()).isEqualTo(CellType.Hidden);
    }

    @Test
    public void testFindShip() {
        List<Cell> ship = MapUtils.findShip(map, map.getCell(0, 0));
        assertThat(ship).containsOnly(
                map.getCell(0, 0),
                map.getCell(0, 1),
                map.getCell(0, 2));
    }
}