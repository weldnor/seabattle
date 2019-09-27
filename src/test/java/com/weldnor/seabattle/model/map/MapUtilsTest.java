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

    @Test
    public void IsValid_CorrectMap_ReturnsTrue() {
        Map map = MapUtils.loadMapFromFile("src/test/resources/is_valid_maps/correct.txt");
        assertThat(MapUtils.isValid(map)).isTrue();
    }

    @Test
    public void IsValid_WithoutShips_ReturnsFalse() {
        Map map = MapUtils.loadMapFromFile("src/test/resources/is_valid_maps/without_ships.txt");
        assertThat(MapUtils.isValid(map)).isFalse();
    }

    @Test
    public void IsValid_IncorrectPlacementOfShips_ReturnsFalse() {
        Map map = MapUtils.loadMapFromFile("src/test/resources/is_valid_maps/incorrect_placement_of_ships.txt");
        assertThat(MapUtils.isValid(map)).isFalse();
    }

    @Test
    public void IsValid_IncorrectPlacementOfMine_ReturnsFalse() {
        Map map = MapUtils.loadMapFromFile("src/test/resources/is_valid_maps/incorrect_placement_of_mine.txt");
        assertThat(MapUtils.isValid(map)).isFalse();
    }

    @Test
    public void IsValid_IncorrectPlacementOfMinesweeper_ReturnsFalse() {
        Map map = MapUtils.loadMapFromFile("src/test/resources/is_valid_maps/incorrect_placement_of_minesweeper.txt");
        assertThat(MapUtils.isValid(map)).isFalse();
    }

    @Test
    public void IsValid_ReducedNumberOfShips_ReturnsFalse() {
        Map map = MapUtils.loadMapFromFile("src/test/resources/is_valid_maps/reduced_number_of_ships.txt");
        assertThat(MapUtils.isValid(map)).isFalse();
    }

    @Test
    public void IsValid_IncreasedNumberOfShips_ReturnsFalse() {
        Map map = MapUtils.loadMapFromFile("src/test/resources/is_valid_maps/increased_number_of_ships.txt");
        assertThat(MapUtils.isValid(map)).isFalse();
    }

    @Test
    public void IsValid_WithDestroyedCells_ReturnsFalse() {
        Map map = MapUtils.loadMapFromFile("src/test/resources/is_valid_maps/with_destroyed_cells.txt");
        assertThat(MapUtils.isValid(map)).isFalse();
    }

    @Test
    public void IsValid_IncorrectSizeOfShips_ReturnsFalse() {
        Map map = MapUtils.loadMapFromFile("src/test/resources/is_valid_maps/incorrect_size_of_ships.txt");
        assertThat(MapUtils.isValid(map)).isFalse();
    }
}