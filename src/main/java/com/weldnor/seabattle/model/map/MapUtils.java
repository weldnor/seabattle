package com.weldnor.seabattle.model.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MapUtils {

    public static List<Cell> findShip(Map map, Cell cell) {
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

            if (cx + 1 < map.size() && map.getCell(cx + 1, cy).getType() == CellType.Ship)
                stack.push(map.getCell(cx + 1, cy));

            if (cx - 1 >= 0 && map.getCell(cx - 1, cy).getType() == CellType.Ship)
                stack.push(map.getCell(cx - 1, cy));

            if (cy + 1 < map.size() && map.getCell(cx, cy + 1).getType() == CellType.Ship)
                stack.push(map.getCell(cx, cy + 1));

            if (cy - 1 >= 0 && map.getCell(cx, cy - 1).getType() == CellType.Ship)
                stack.push(map.getCell(cx, cy - 1));
        }
        return result;
    }


    public static List<List<Cell>> findAllShips(Map map) {
        List<List<Cell>> ships = new ArrayList<>();

        List<Cell> shipCells = map.findCells(CellType.Ship);

        while (!shipCells.isEmpty()) {
            List<Cell> ship = findShip(map, shipCells.get(0));
            for (Cell cell : ship)
                shipCells.remove(cell);

            ships.add(ship);
        }
        return ships;
    }

    public static Map reverse(Map map) {
        Map result = (Map) map.clone();
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.size(); j++) {
                //смотрим клетки, которые не уничтоженны, и меняем их тип на hidden;
                if (!result.getCell(i, j).isDestroyed()) {
                    result.getCell(i, j).setType(CellType.Hidden);
                }
            }
        }
        return result;
    }

    public static boolean isValide(Map map) {
        //проверяем размер карты
        if (map.size() != 10)
            return false;

        int shipCellsNumber = 0;
        int mineCellsNumber = 0;
        int minesweeperCellsNumber = 0;

        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.size(); j++) {
                Cell cell = map.getCell(i, j);

                switch (cell.getType()) {
                    case Ship:
                        shipCellsNumber++;
                        break;

                    case Minesweeper:
                        minesweeperCellsNumber++;
                        break;

                    case Mine:
                        mineCellsNumber++;
                        break;

                    //проверяем, что на карте нет скрытых клеток
                    case Hidden:
                        return false;
                }
            }
        }

        //проверяем количество клеток
        if (shipCellsNumber != 4 + 6 + 6 + 4)
            return false;

        if (mineCellsNumber != 1)
            return false;

        if (minesweeperCellsNumber != 1)
            return false;

        //проверяем корабли на валидность
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.size(); j++) {
                if (map.getCell(i, j).getType() == CellType.Ship) {
                    //проверяем отсутствие клеток на диагоналях
                    if (i + 1 < map.size() && j + 1 < map.size()
                            && map.getCell(i + 1, j + 1).getType() == CellType.Ship)
                        return false;

                    if (i + 1 < map.size() && j - 1 >= 0
                            && map.getCell(i + 1, j - 1).getType() == CellType.Ship)
                        return false;

                    if (i - 1 >= 0 && j + 1 < map.size()
                            && map.getCell(i - 1, j + 1).getType() == CellType.Ship)
                        return false;

                    if (i - 1 >= 0 && j - 1 >= 0
                            && map.getCell(i - 1, j - 1).getType() == CellType.Ship)
                        return false;
                }

                //проверяем. что у клеток мин/минных тральщиков нет соседей
                if (map.getCell(i, j).getType() == CellType.Mine
                        || map.getCell(i, j).getType() == CellType.Minesweeper) {
                    //диагональные клетки
                    if (i + 1 < map.size() && j + 1 < map.size()
                            && map.getCell(i + 1, j + 1).getType() == CellType.Ship)
                        return false;

                    if (i + 1 < map.size() && j - 1 >= 0
                            && map.getCell(i + 1, j - 1).getType() == CellType.Ship)
                        return false;

                    if (i - 1 >= 0 && j + 1 < map.size()
                            && map.getCell(i - 1, j + 1).getType() == CellType.Ship)
                        return false;

                    if (i - 1 >= 0 && j - 1 >= 0
                            && map.getCell(i - 1, j - 1).getType() == CellType.Ship)
                        return false;

                    //вертикальные и горизонталиные клетки
                    if (i + 1 < map.size()
                            && map.getCell(i + 1, j).getType() != CellType.Water)
                        return false;

                    if (i - 1 >= 0
                            && map.getCell(i - 1, j).getType() != CellType.Water)
                        return false;

                    if (j + 1 < map.size()
                            && map.getCell(i, j + 1).getType() != CellType.Water)
                        return false;

                    if (j - 1 >= 0
                            && map.getCell(i, j - 1).getType() != CellType.Water)
                        return false;
                }
            }
        }

        //TODO
        //проверить размеры кораблей

        return true;
    }
}
