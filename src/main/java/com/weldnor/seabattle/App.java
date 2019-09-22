package com.weldnor.seabattle;

import com.weldnor.seabattle.model.map.Cell;
import com.weldnor.seabattle.model.map.CellType;
import com.weldnor.seabattle.model.map.Map;
import com.weldnor.seabattle.model.map.MapUtils;

import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        Map map = new Map();
        map.getCell(0, 0).setType(CellType.Ship);
        map.getCell(0, 1).setType(CellType.Ship);
        map.getCell(0, 2).setType(CellType.Ship);

        map.getCell(3, 0).setType(CellType.Ship);
        map.getCell(4, 0).setType(CellType.Ship);

        map.getCell(7, 0).setType(CellType.Ship);
        map.getCell(8, 0).setType(CellType.Ship);

        List<List<Cell>> ships = MapUtils.findAllShips(map);

        System.out.println(ships.size());
    }
}
