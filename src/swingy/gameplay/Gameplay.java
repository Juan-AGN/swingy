package swingy.gameplay;

import java.io.IOException;
import java.util.Vector;

import swingy.gameplay.mapgenerator.MapGenerator;
import swingy.gameplay.roles.Assasin;
import swingy.gameplay.roles.Tank;
import swingy.utils.NameGenerator;
import swingy.utils.Sleep;
import swingy.utils.TakeInput;
import swingy.view.TermPrinter;

public class Gameplay {
    GameData gameState;

    private void moveup(Unit tomove) {
        if (!gameState.hasWall(new Coordinates(tomove.where.x, tomove.where.y - 1)))
            tomove.where = new Coordinates(tomove.where.x, tomove.where.y - 1);
    }

    private void movedown(Unit tomove) {
        if (!gameState.hasWall(new Coordinates(tomove.where.x, tomove.where.y + 1)))
            tomove.where = new Coordinates(tomove.where.x, tomove.where.y + 1);    
    }

    private void moveleft(Unit tomove) {
        if (!gameState.hasWall(new Coordinates(tomove.where.x - 1, tomove.where.y)))
            tomove.where = new Coordinates(tomove.where.x - 1, tomove.where.y);
    }

    private void moveright(Unit tomove) {
        if (!gameState.hasWall(new Coordinates(tomove.where.x + 1, tomove.where.y)))
            tomove.where = new Coordinates(tomove.where.x + 1, tomove.where.y);
    }



    public void setup(Unit player) {
        MapGenerator generator = new MapGenerator();
        this.gameState = new GameData(generator.generateMap((int)player.unitrole.level), player);
        this.gameState.player.where = new Coordinates(gameState.map.getCenter().getx() - 1, gameState.map.getCenter().gety());
    }

    public void loop() {
        int key = 0;
        int setcooldown = 50;
        int cooldown = 0;
        int setenemycooldown = 1500;
        int enemycooldown = 0;
        int ms = 122;
        TermPrinter printer = new TermPrinter();

        printer.termShowState(gameState);
        while (gameState.isInside(gameState.player.where))
        {
            Sleep.wait(ms);
            if (cooldown <= 0)
            {
                key = TakeInput.getInput();
                cooldown = setcooldown;
            }
            else
                cooldown -= ms;

            switch (key) {
                case 'w':
                    moveup(gameState.player);
                    break;
                case 'a':
                    moveleft(gameState.player);
                    break;
                case 's':
                    movedown(gameState.player);
                    break;
                case 'd':
                    moveright(gameState.player);
                    break;        
                default:
                    break;
            }
            key = 0;
            printer.termShowState(gameState);
        }
    } 

    public static void main(String[] args) {
        Unit player = new Unit(0, NameGenerator.generateName(), new Tank(1));
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