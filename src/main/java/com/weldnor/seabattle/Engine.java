package com.weldnor.seabattle;

import com.weldnor.seabattle.model.Game;
import com.weldnor.seabattle.model.map.GameMapUtils;
import com.weldnor.seabattle.model.map.Point;
import com.weldnor.seabattle.model.player.BotPlayer;
import com.weldnor.seabattle.model.player.HumanPlayer;
import com.weldnor.seabattle.model.player.Player;
import com.weldnor.seabattle.model.player.SimpleBotPlayer;

import java.util.Scanner;

public class Engine {
    private Scanner scanner = new Scanner(System.in);
    private Game game;
    private Player firstPlayer;
    private Player secondPlayer;

    private int mode;

    public void init() {
        System.out.println("hi!\n" +
                "[1] player vs player\n" +
                "[2] player vs bot\n"
        );

        int mode = scanner.nextInt();
        this.mode = mode;

        switch (mode) {
            case 1:
                firstPlayer = new HumanPlayer();
                secondPlayer = new HumanPlayer();
                break;
            case 2:
                firstPlayer = new HumanPlayer();
                secondPlayer = new SimpleBotPlayer();
                break;
        }

        game = new Game(firstPlayer, secondPlayer);

        switch (mode) {
            case 1:
                game.setFirstPlayerMap(GameMapUtils.loadMapFromConsole());
                game.setSecondPlayerMap(GameMapUtils.loadMapFromConsole());
                break;
            case 2:
                game.setFirstPlayerMap(GameMapUtils.loadMapFromConsole());
                game.setSecondPlayerMap(((BotPlayer) secondPlayer).makeMap());
        }
    }

    public void start() {
        int tick = 0;
        while (!game.isEnd()) {


            System.out.println(tick);
            tick++;

            if (game.getCurrentPlayer() instanceof BotPlayer) {
                game.makeMoveByBot();

            } else {
                switch (game.getCurrentMoveType()) {
                    case Normal:
                        System.out.println("Normal");
                        break;
                    case SelectShip:
                        System.out.println("Select Ship");
                        break;
                    case SelectMine:
                        System.out.println("Select Mine");
                        break;
                }

                String[] ownMapLines = game.getCurrentPlayerMap()
                        .toString()
                        .split("\n");

                String[] enemyMapLines = GameMapUtils.reverse(game.getCurrentEnemyMap())
                        .toString()
                        .split("\n");

                for (int i = 0; i < 10; i++) {
                    System.out.print(ownMapLines[i]);
                    System.out.print("   ");
                    System.out.println(enemyMapLines[i]);
                }

                System.out.println("enter x, y:");

                int x = scanner.nextInt();
                int y = scanner.nextInt();

                game.makeMoveByHuman(new Point(x, y));
            }
        }
    }

}
