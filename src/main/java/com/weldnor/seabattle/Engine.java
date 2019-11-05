package com.weldnor.seabattle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.tomaslanger.chalk.Chalk;
import com.weldnor.seabattle.model.Game;
import com.weldnor.seabattle.model.GameState;
import com.weldnor.seabattle.model.map.*;
import com.weldnor.seabattle.model.player.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Engine {
    private final String savePath = "/save.json";

    private Scanner scanner = new Scanner(System.in);
    private Game game = new Game();

    public void start() {
        File saveFile = new File(this.getClass()
                .getResource(savePath)
                .getPath());

        if (saveFile.exists() && saveFile.length() > 0) {
            System.out.println("load game? y/n");
            if (scanner.nextLine().equals("y")) {
                loadGame();
            } else {
                createNewGame();
            }
        } else {
            createNewGame();
        }

        while (!game.isEnd()) {
            tick();
        }
        System.out.println("END.");
    }

    private void tick() {
        while (!game.isEnd()) {

            if (game.getCurrentPlayer() instanceof BotPlayer) {
                game.makeMoveByBot();

            } else {
                System.out.println("own map:");
                printMap(game.getCurrentPlayerMap());

                System.out.println("enemy map:");
                printMap(GameMapUtils.reverse(game.getCurrentEnemyMap()));


                Point point = null;

                switch (game.getCurrentMoveType()) {
                    case Normal:
                        System.out.println("select cell:");
                        point = selectCell();
                        break;
                    case SelectShip:
                        System.out.println("select ship cell:");
                        point = selectShipCell();
                        break;
                    case SelectMine:
                        System.out.println("select mine cell:");
                        point = selectMineCell();
                        break;

                }
                game.makeMoveByHuman(point);
            }
            saveGame();
        }
    }

    private void printMap(GameMap map) {
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.size(); j++) {
                Cell cell = map.getCell(i, j);

                //water
                if (cell.getType() == CellType.Water && !cell.isDestroyed())
                    System.out.print(Chalk.on(".").blue());
                if (cell.getType() == CellType.Water && cell.isDestroyed())
                    System.out.print(Chalk.on(".").red());

                //ship
                if (cell.getType() == CellType.Ship && !cell.isDestroyed())
                    System.out.print(Chalk.on("#").yellow());
                if (cell.getType() == CellType.Ship && cell.isDestroyed())
                    System.out.print(Chalk.on("#").red());

                //mine
                if (cell.getType() == CellType.Mine && !cell.isDestroyed())
                    System.out.print(Chalk.on("*").green());
                if (cell.getType() == CellType.Mine && cell.isDestroyed())
                    System.out.print(Chalk.on("*").red());

                //minesweeper
                if (cell.getType() == CellType.Minesweeper && !cell.isDestroyed())
                    System.out.print(Chalk.on("%").white());
                if (cell.getType() == CellType.Minesweeper && cell.isDestroyed())
                    System.out.print(Chalk.on("%").red());

                //hidden
                if (cell.getType() == CellType.Hidden)
                    System.out.print(Chalk.on(".").gray());
            }
            System.out.println();
        }
    }

    private Point selectCell() {
        GameMap map = game.getCurrentPlayerMap();

        int x = -1;
        int y = -1;

        while (0 > x || x >= map.size() || 0 > y || y >= map.size()) {
            System.out.println("enter x:");
            x = scanner.nextInt();
            System.out.println("enter y:");
            y = scanner.nextInt();
        }

        return new Point(x, y);
    }

    private Point selectShipCell() {
        GameMap map = game.getCurrentPlayerMap();

        while (true) {
            Point p = selectCell();
            Cell c = map.getCell(p);
            if (c.getType() == CellType.Ship && !c.isDestroyed())
                return p;
        }
    }

    private Point selectMineCell() {
        GameMap map = game.getCurrentPlayerMap();

        while (true) {
            Point p = selectCell();
            Cell c = map.getCell(p);
            if (c.getType() == CellType.Mine && !c.isDestroyed())
                return p;
        }
    }

    private void createNewGame() {
        game.setFirstPlayer(new HumanPlayer());
        game.setSecondPlayer(new SimpleBotPlayer());

        game.setFirstPlayerMap(GameMapUtils.loadMapFromConsole());
        game.setSecondPlayerMap(((BotPlayer) game.getSecondPlayer()).makeMap());
    }

    public void saveGame() {
        ObjectMapper objectMapper = new ObjectMapper();

        SimpleModule module = new SimpleModule("my_module");
        module.addSerializer(Player.class, new PlayerSerializer(Player.class));
        objectMapper.registerModule(module);

        File saveFile = new File(this.getClass().getResource(savePath).getPath());

        try {
            objectMapper.writeValue(
                    saveFile,
                    game.getState()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGame() {
        Scanner scanner = new Scanner(Engine.class.getResourceAsStream(savePath));
        StringBuilder builder = new StringBuilder();

        while (scanner.hasNextLine()) {
            builder.append(scanner.nextLine()).append("\n");
        }
        String json = builder.toString();

        System.out.println(json);

        ObjectMapper objectMapper = new ObjectMapper();

        SimpleModule module = new SimpleModule("my_module");
        module.addDeserializer(Player.class, new PlayerDeserializer(Player.class));
        objectMapper.registerModule(module);

        try {
            GameState state = objectMapper.readValue(json, GameState.class);
            System.out.println(state);
            game.setState(state);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
