package swingy.view;

import swingy.gameplay.Coordinates;
import swingy.gameplay.GameData;
import swingy.gameplay.Unit;
import swingy.utils.NameGenerator;

public class TermPrinter {
    public void setup() {};
    private Sprite [][]mapTab = new Sprite[9][9];
    private final int fovx = 9;
    private final int fovy = 9;
    private final int spritesize = 16;

    private void setGround() {
        for (int y = 0; y < 9; y ++)
            for (int x = 0; x < 9; x ++)
                mapTab[y][x] = Sprites.getInstance().allSprites[Sprites.GRASS];
    }

    private void checkWalls(GameData gameState) {
        Unit player = gameState.getPlayer();
        Sprite wall = new Sprite(Sprites.getInstance().allSprites[Sprites.GRASS]);
        Sprite empty = new Sprite(Sprites.getInstance().allSprites[Sprites.BACKGROUND]);
        wall.fuseSprite(Sprites.getInstance().allSprites[Sprites.TREE]);
        int basey = (int)player.getY() - (fovy / 2);
        int y = 0;
        int basex = (int)player.getX() - (fovx / 2);
        for (; y < fovy; y++) {
            for (int x = 0; x < fovx; x++) {
                if (gameState.getMap().isInside(new Coordinates(x + basex, y + basey)))
                {
                    if (gameState.getMap().hasWall(new Coordinates(x + basex, y + basey)))
                        this.mapTab[y][x] = wall;
                }
                else
                    this.mapTab[y][x] = empty;
            }
        }
    }

    private void putPlayer(GameData gameState) {
        Sprite player = new Sprite(Sprites.getInstance().allSprites[Sprites.GRASS]);
        player.fuseSprite(Sprites.getInstance().allSprites[Sprites.PLAYER_TANK]); 
        this.mapTab[(fovy / 2)][(fovx / 2)] = player;
    }

    private void printTabs() {
        String print[] = new String[(fovy * spritesize) + 2];
        for (int i = 0; i < print.length; i ++)
            print[i] = "";
        print[0] += "┌";
        print[print.length - 1] += "└";
        for (int counterx = 0; counterx < (fovx * spritesize * 2); counterx++)
            print[0] += "─";
        for (int counterx = 0; counterx < (fovx * spritesize * 2); counterx++)
            print[print.length - 1] += "─";
        print[0] += "┐";
        print[print.length - 1] += "┘";

        for (int y = 0; y < mapTab.length; y ++)
        {
            for (int countery = 0; countery < spritesize; countery ++)
            {
                print[(y * spritesize) + countery + 1] +=  "│";
                for (int x = 0; x < mapTab.length; x ++)
                    for (int counterx = 0; counterx < spritesize; counterx ++)
                        print[(y * spritesize) + countery + 1] += mapTab[y][x].pixelmap[countery][counterx].tpixel;
                print[(y * spritesize) + countery + 1] +=  "│";
            }
        }

        System.out.print("\033[H\033[2J");
        System.out.flush();
        for (int i = 0; i < print.length; i ++)
            System.out.println(print[i]);
    }

    public void termShowState(GameData gameState) {
        setGround();
        checkWalls(gameState);
        putPlayer(gameState);
        printTabs();
    }
}

/*
        System.out.print("\033[H\033[2J");
        System.out.flush();
            for (int countery = 0; countery < spritesize; countery ++)
                print[(y * spritesize) + countery + 1] +=  "│";
            for (int i = 0; i < print.length; i ++)
                System.out.println(print[i]);    }
            print[(y * spritesize) + 1] += "└";
            for (int x = 0; x < (fovx * spritesize) * 2; x++) { 
                print[(y * spritesize) + 1] += "─";
            }
            print[(y * spritesize) + 1] += "┘";
        print[0] += "┌";
        for (int x = 0; x < (fovx * spritesize) * 2; x++) { 
            print[0] += "─";
        }
        print[0] += "┐";
*/
