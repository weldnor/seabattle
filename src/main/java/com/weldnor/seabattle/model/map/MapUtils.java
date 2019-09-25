package com.weldnor.seabattle.model.map;

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
