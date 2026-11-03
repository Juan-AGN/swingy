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
        int ms = 100;
        TermPrinter printer = new TermPrinter(gameState);

        printer.termShowState(gameState);
        while (gameState.isInside(gameState.player.where))
        {
            Sleep.wait(ms);

            switch (gameState.player.activity(ms, gameState, null)) {
                case 5:
                    printer.miniMapSize();
                    break;
                default:
                    break;
            }

            printer.termShowState(gameState);
        }
    } 

    public static void main(String[] args) {
        Unit player = new Unit(0, NameGenerator.generateName(), new Tank(1), new Player());
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