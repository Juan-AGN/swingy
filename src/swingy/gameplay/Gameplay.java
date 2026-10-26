package swingy.gameplay;

import java.io.IOException;
import java.util.Vector;

import swingy.gameplay.mapgenerator.MapGenerator;
import swingy.gameplay.roles.Assasin;
import swingy.gameplay.roles.Tank;
import swingy.utils.NameGenerator;
import swingy.utils.Sleep;
import swingy.utils.TakeInput;

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

    public void tempShowState() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        int fovx = 9;
        int fovy = 9;
        int spritesize = 16;
        String print[] = new String[(fovy * spritesize) + 2];
        for (int i = 0; i < print.length; i ++)
            print[i] = "";

        int basey = (int)gameState.player.where.y - (fovy / 2);
        int y = 0;
        int basex = (int)gameState.player.where.x - (fovx / 2);
        print[0] += "┌";
        for (int x = 0; x < (fovx * spritesize) * 2; x++) { 
            print[0] += "─";
        }
        print[0] += "┐";
        for (; y < fovy; y++) {
            for (int countery = 0; countery < spritesize; countery ++)
                print[(y * spritesize) + countery + 1] +=  "│";

            for (int x = 0; x < fovx; x++) {
                if (!gameState.player.where.compare(new Coordinates(x + basex, y + basey)))
                {
                    for (int countery = 0; countery < spritesize; countery ++)
                    {
                        for (int counterx = 0; counterx < spritesize; counterx ++)
                        {
                            if (gameState.map.isInside(new Coordinates(x + basex, y + basey)))
                                print[(y * spritesize) + 1 + countery] += (gameState.map.hasWall(new Coordinates(x + basex, y + basey)) ? "██" : "░░");
                            else
                                print[(y * spritesize) + 1 + countery] += "  ";
                        }
                    }
                }
                else
                {
                    for (int countery = 0; countery < spritesize; countery ++)
                    {
                        for (int counterx = 0; counterx < spritesize; counterx ++)
                        {
                            if (counterx == 1 && countery == 0)
                                print[(y * spritesize) + 1 + countery] += "  ";
                            else if (counterx == 0 && countery == 1)
                                print[(y * spritesize) + 1 + countery] += "  ";
                            else if (counterx == 1 && countery == 1)
                                print[(y * spritesize) + 1 + countery] += "  ";
                            else if (counterx == 2 && countery == 1)
                                print[(y * spritesize) + 1 + countery] += "  ";
                            else if (counterx == 1 && countery == 2)
                                print[(y * spritesize) + 1 + countery] += "  ";
                            else
                                print[(y * spritesize) + 1 + countery] += "  ";
                        }
                    }
                }
            }
            for (int countery = 0; countery < spritesize; countery ++)
                print[(y * spritesize) + countery + 1] +=  "│";
        }
        print[(y * spritesize) + 1] += "└";
        for (int x = 0; x < (fovx * spritesize) * 2; x++) { 
            print[(y * spritesize) + 1] += "─";
        }
        print[(y * spritesize) + 1] += "┘";

        for (int i = 0; i < print.length; i ++)
            System.out.println(print[i]);
        System.out.println("-The grand adventure of <<" + gameState.player.getName() + ">>, may he live forever and get to the end of the adventure by defeating his nemesis, <<" + NameGenerator.generateName() + ">>-");
    }

    public void setup(Unit player) {
        MapGenerator generator = new MapGenerator();
        this.gameState = new GameData(generator.generateMap((int)player.unitrole.level), player);
        player.where = new Coordinates(gameState.map.getCenter().getx() - 1, gameState.map.getCenter().gety());
    }

    public void loop() {
        int key = 0;
        int setcooldown = 50;
        int cooldown = 0;
        int ms = 33;

        tempShowState();
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
            tempShowState();
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