package swingy.gameplay;

import java.io.IOException;
import java.util.Vector;

import swingy.gameplay.mapgenerator.MapGenerator;
import swingy.gameplay.roles.*;
import swingy.gameplay.behaviors.*;
import swingy.utils.NameGenerator;
import swingy.utils.Sleep;
import swingy.utils.TakeInput;
import swingy.view.TermPrinter;

public class Gameplay {
    GameData gameState;

    public void setup(Unit player) {
        MapGenerator generator = new MapGenerator();
        this.gameState = new GameData(generator.generateMap((int)player.unitrole.level), player);
        this.gameState.player.where = new Coordinates(gameState.map.getCenter().getx() - 1, gameState.map.getCenter().gety());
    }

    public void loop() {
        int setenemycooldown = 1500;
        int enemycooldown = 0;
        int basems = 100;
        int ms = 100;
        long startTime = System.currentTimeMillis();
        long endTime = System.currentTimeMillis();
        int elapsed = 0;
        TermPrinter printer = new TermPrinter(gameState);

        printer.termShowState(gameState);
        while (gameState.isInside(gameState.player.where))
        {
            ms = basems - elapsed;
            if (ms > 0)
                Sleep.wait(ms);
            else
                ms = 1;
            startTime = System.currentTimeMillis();

            switch (gameState.player.activity(basems, gameState, null)) {
                case ' ':
                    printer.miniMapSize();
                    break;
                case 'n':
                    return;
                default:
                    break;
            }
            gameState.enemies.frame(basems, gameState);
            printer.termShowState(gameState);
            endTime = System.currentTimeMillis();
            elapsed = (int)(endTime - startTime);
            System.out.println(elapsed);
        }
    } 

    public static void main(String[] args) {
        Unit player = new Unit((byte)0, NameGenerator.generateName(), new Tank(1), new Player());
        Gameplay game = new Gameplay();
        try {
            TakeInput.initTerminal();
        } catch (InterruptedException error) {
            System.out.println(error);
        } catch (IOException error) {
            System.out.println(error);
        }

        while (player.getLevel() < 101) {
            game.setup(player);
            game.loop();
            player.levelUp();
        }
    }
}