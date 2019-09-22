package com.weldnor.seabattle.model.map;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CellTest {

    private Cell cell1;
    private Cell cell2;
    private Cell cell3;

    @Before
    public void setUp() throws Exception {
        cell1 = new Cell(false, CellType.Ship, new Point(3, 2));
        cell2 = new Cell(false, CellType.Ship, new Point(3, 2));
        cell3 = new Cell(true, CellType.Water, new Point(5, 7));
    }

    @Test
    public void testIsDestroyed() {
        assertThat(cell1.isDestroyed()).isFalse();
        assertThat(cell3.isDestroyed()).isTrue();
    }

    @Test
    public void testGetType() {
        assertThat(cell1.getType()).isEqualTo(CellType.Ship);
        assertThat(cell3.getType()).isEqualTo(CellType.Water);
    }

    @Test
    public void getPoint() {
        assertThat(cell1.getPoint()).isEqualTo(new Point(3, 2));
        assertThat(cell3.getPoint()).isNotEqualTo(new Point(3, 2));
    }

    @Test
    public void testEquals() {
        assertThat(cell1).isEqualTo(cell2);
        assertThat(cell1).isNotEqualTo(cell3);
    }

    @Test
    public void testClone() {
        Cell newCell = (Cell) cell1.clone();
        assertThat(cell1).isEqualTo(newCell);
    }
}