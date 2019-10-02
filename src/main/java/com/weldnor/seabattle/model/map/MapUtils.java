package com.weldnor.seabattle.model.map;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MapUtils {

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

    public static boolean isValid(Map map) {
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

                //проверяем, что клетка не уничтоженна
                if (cell.isDestroyed())
                    return false;
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

        //проверяем корабли на корректный размер
        int l1 = 0, l2 = 0, l3 = 0, l4 = 0;
        List<List<Cell>> ships = map.findAllShips();

        //проверяем число кораблей
        if (ships.size() != 10)
            return false;

        for (List<Cell> ship : ships) {
            switch (ship.size()) {
                case 1:
                    l1++;
                    break;
                case 2:
                    l2++;
                    break;
                case 3:
                    l3++;
                    break;
                case 4:
                    l4++;
                    break;
                default:
                    return false;
            }
        }

        return l1 == 4 && l2 == 3 && (l3 == 2 || l4 == 1);
    }

    public static boolean isEnd(Map map) {
        List<List<Cell>> ships = map.findAllShips();

        for (List<Cell> ship : ships)
            for (Cell cell : ship)
                if (!cell.isDestroyed())
                    return false;

        return true;
    }

    public static void FireInCell(Map map, Point point) {
        int x = point.getX();
        int y = point.getY();

        assert x >= 0 && x < 10;
        assert y >= 0 && y < 10;

        Cell cell = map.getCell(x, y);

        assert cell.getType() != CellType.Hidden;

        switch (cell.getType()) {
            case Water: {
                cell.setDestroyed(true);
                break;
            }
            case Mine:
            case Minesweeper: {
                cell.setDestroyed(true);

                List<Cell> neighboringCells = map.getNeighboringCells(cell);

                for (Cell neighboringCell : neighboringCells)
                    neighboringCell.setDestroyed(true);

                break;
            }
            case Ship: {
                cell.setDestroyed(true);

                List<Cell> ship = map.findShip(cell);
                List<Cell> waterCells = new ArrayList<>();

                //проверяем, что корабль полностью уничтожен
                for (Cell shipCell : ship) {
                    if (!shipCell.equals(cell) && !shipCell.isDestroyed())
                        break;
                }

                for (Cell shipCell : ship) {
                    List<Cell> neighboringCells = map.getNeighboringCells(shipCell);
                    for (Cell neighboringCell : neighboringCells) {
                        if (neighboringCell.getType() == CellType.Water && !waterCells.contains(neighboringCell)) {
                            waterCells.add(neighboringCell);
                        }
                    }
                }


                for (Cell waterCell : waterCells)
                    waterCell.setDestroyed(true);

                break;
            }


        }
    }

    public static Map loadMapFromString(String string) {
        Scanner scanner = new Scanner(string);

        Map map = new Map();

        for (int i = 0; i < map.size(); i++) {
            String line = scanner.nextLine();

            for (int j = 0; j < map.size(); j++) {
                Cell cell = map.getCell(i, j);
                char c = line.charAt(j);

                if (Character.isLowerCase(c))
                    cell.setDestroyed(true);

                c = Character.toLowerCase(c);

                switch (c) {
                    case 'w':
                        cell.setType(CellType.Water);
                        break;
                    case 'm':
                        cell.setType(CellType.Mine);
                        break;
                    case 's':
                        cell.setType(CellType.Ship);
                        break;
                    case 't':
                        cell.setType(CellType.Minesweeper);
                        break;
                    case 'h':
                        cell.setType(CellType.Hidden);
                        break;
                }
            }
        }
        return map;
    }

    public static Map loadMapFromFile(String path) {
        File file = new File(path).getAbsoluteFile();
        StringBuilder builder = new StringBuilder();

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                builder.append(scanner.nextLine());
                builder.append('\n');
            }
            return loadMapFromString(builder.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
