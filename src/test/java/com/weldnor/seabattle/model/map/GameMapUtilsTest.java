package com.weldnor.seabattle.model.map;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class GameMapUtilsTest {

    private GameMap map;

    @Before
    public void setUp() throws Exception {
        map = new GameMap();

        //первый корабль
        for (int i = 0; i < 3; i++)
            map.getCell(0, i).setType(CellType.Ship);

        map.getCell(0, 2).setDestroyed(true);
        map.getCell(0, 2).setDestroyed(true);

        //второй корабль
        for (int i = 0; i < 4; i++)
            map.getCell(i, 7).setType(CellType.Ship);

        //третий корабль
        for (int i = 2; i < 5; i++) {
            Cell cell = map.getCell(8, i);
            cell.setType(CellType.Ship);
            if (i != 4) cell.setDestroyed(true);
        }

        //мины
        map.getCell(6, 8).setType(CellType.Mine);
        map.getCell(6, 8).setDestroyed(true);

        map.getCell(4, 5).setType(CellType.Mine);

        //минный тральщик
        map.getCell(3, 3).setType(CellType.Minesweeper);

    }

    @Test
    public void Reverse_NotDestroyedShipCell_IsHiddenCell() {
        GameMap reverseMap = GameMapUtils.reverse(map);
        Cell cell = reverseMap.getCell(0, 0);
        assertThat(cell.getType()).isEqualTo(CellType.Hidden);
    }

    @Test
    public void Reverse_DestroyedMineCell_IsSameCell() {
        GameMap reverseMap = GameMapUtils.reverse(map);
        Cell cell = map.getCell(6, 8);
        assertThat(cell.getType()).isEqualTo(CellType.Mine);
    }

    @Test
    public void IsValid_CorrectMap_ReturnsTrue() {
        GameMap map = GameMapUtils.loadMapFromFile("src/test/resources/is_valid_maps/correct.txt");
        assertThat(GameMapUtils.isValid(map)).isTrue();
    }

    @Test
    public void IsValid_WithoutShips_ReturnsFalse() {
        GameMap map = GameMapUtils.loadMapFromFile("src/test/resources/is_valid_maps/without_ships.txt");
        assertThat(GameMapUtils.isValid(map)).isFalse();
    }

    @Test
    public void IsValid_IncorrectPlacementOfShips_ReturnsFalse() {
        GameMap map = GameMapUtils.loadMapFromFile("src/test/resources/is_valid_maps/incorrect_placement_of_ships.txt");
        assertThat(GameMapUtils.isValid(map)).isFalse();
    }

    @Test
    public void IsValid_IncorrectPlacementOfMine_ReturnsFalse() {
        GameMap map = GameMapUtils.loadMapFromFile("src/test/resources/is_valid_maps/incorrect_placement_of_mine.txt");
        assertThat(GameMapUtils.isValid(map)).isFalse();
    }

    @Test
    public void IsValid_IncorrectPlacementOfMinesweeper_ReturnsFalse() {
        GameMap map = GameMapUtils.loadMapFromFile("src/test/resources/is_valid_maps/incorrect_placement_of_minesweeper.txt");
        assertThat(GameMapUtils.isValid(map)).isFalse();
    }

    @Test
    public void IsValid_ReducedNumberOfShips_ReturnsFalse() {
        GameMap map = GameMapUtils.loadMapFromFile("src/test/resources/is_valid_maps/reduced_number_of_ships.txt");
        assertThat(GameMapUtils.isValid(map)).isFalse();
    }

    @Test
    public void IsValid_IncreasedNumberOfShips_ReturnsFalse() {
        GameMap map = GameMapUtils.loadMapFromFile("src/test/resources/is_valid_maps/increased_number_of_ships.txt");
        assertThat(GameMapUtils.isValid(map)).isFalse();
    }

    @Test
    public void IsValid_WithDestroyedCells_ReturnsFalse() {
        GameMap map = GameMapUtils.loadMapFromFile("src/test/resources/is_valid_maps/with_destroyed_cells.txt");
        assertThat(GameMapUtils.isValid(map)).isFalse();
    }

    @Test
    public void IsValid_IncorrectSizeOfShips_ReturnsFalse() {
        GameMap map = GameMapUtils.loadMapFromFile("src/test/resources/is_valid_maps/incorrect_size_of_ships.txt");
        assertThat(GameMapUtils.isValid(map)).isFalse();
    }

    @Test
    public void IsEnd_Begin_ReturnsFalse() {
        GameMap map = GameMapUtils.loadMapFromFile("src/test/resources/is_end_maps/begin.txt");
        assertThat(GameMapUtils.isEnd(map)).isFalse();
    }

    @Test
    public void IsEnd_SameShipsDestroyed_ReturnsFalse() {
        GameMap map = GameMapUtils.loadMapFromFile("src/test/resources/is_end_maps/same_ships_destroyed.txt");
        assertThat(GameMapUtils.isEnd(map)).isFalse();
    }

    @Test
    public void IsEnd_End_ReturnsTrue() {
        GameMap map = GameMapUtils.loadMapFromFile("src/test/resources/is_end_maps/end.txt");
        assertThat(GameMapUtils.isEnd(map)).isTrue();
    }

    @Test
    public void FireInCell_Mine() {
        Cell mine = map.getCell(4, 5);
        GameMapUtils.FireInCell(map, mine.getPoint());

        assertThat(mine.isDestroyed()).isTrue();
        assertThat(map.getNeighboringCells(mine))
                .filteredOn(Cell::isDestroyed)
                .hasSize(8);
    }

    @Test
    public void FireInCell_Minesweeper() {
        Cell minesweeper = map.getCell(3, 3);
        GameMapUtils.FireInCell(map, minesweeper.getPoint());

        assertThat(minesweeper.isDestroyed()).isTrue();
        assertThat(map.getNeighboringCells(minesweeper))
                .filteredOn(Cell::isDestroyed)
                .hasSize(8);
    }

    @Test
    public void FireInCell_NotDestroyedShip_ShouldDestroyOneCell() {
        Cell shipCell = map.getCell(0, 7);
        GameMapUtils.FireInCell(map, shipCell.getPoint());

        assertThat(map.findShip(shipCell))
                .filteredOn(Cell::isDestroyed)
                .hasSize(1);
    }

    @Test
    public void FireInCell_AlmostDestroyedShip_ShouldDestroyAllShip() {
        Cell target = map.getCell(8, 4);
        List<Cell> ship = map.findShip(target);

        Set<Cell> waterCells = new HashSet<>();

        for (Cell shipCell : ship) {
            List<Cell> neighboringCells = map.getNeighboringCells(shipCell);
            for (Cell neighboringCell : neighboringCells)
                if (neighboringCell.getType() == CellType.Water)
                    waterCells.add(neighboringCell);
        }

        GameMapUtils.FireInCell(map, target.getPoint());

        assertThat(ship)
                .filteredOn(Cell::isDestroyed)
                .hasSize(3);

        assertThat(waterCells)
                .filteredOn(Cell::isDestroyed)
                .hasSize(12);
    }
}