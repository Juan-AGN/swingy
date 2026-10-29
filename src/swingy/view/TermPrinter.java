package swingy.view;

import java.util.Map;

import swingy.gameplay.Coordinates;
import swingy.gameplay.GameData;
import swingy.gameplay.Unit;
import swingy.gameplay.mapgenerator.GMap;
import swingy.utils.NameGenerator;

public class TermPrinter {
    public void setup() {};
    private Sprite [][]mapTab = new Sprite[9][9];
    private final int fovx = 9;
    private final int fovy = 9;
    private final int spritesize = 16;
    private Pixel [][]minimap = new Pixel[34][144];
    private GMap map;
    private boolean[][] vision;
    private byte amplify = 0;

    public TermPrinter(GameData gameState) {
        int length = gameState.getMap().getMap().length;
        map = gameState.getMap();
        vision = new boolean[length][length];
    }

    public void miniMapSize() {
        switch (amplify) {
            case 0:
                amplify = 1;
                break ;
            case 1:
                amplify = 2;
                break ;
            default:
                amplify = 0;
                break ;
        }
    }

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
                if (map.isInside(new Coordinates(x + basex, y + basey)))
                {
                    if (map.hasWall(new Coordinates(x + basex, y + basey)))
                        this.mapTab[y][x] = wall;
                    if (y > 2 - 1 && y < fovy - 2 && x < fovx - 2 && x > 2 - 1)
                    vision[y + basey][x + basex] = true;
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

    private void setupMinimap(GameData gameState) {
        Unit player = gameState.getPlayer();
        int amplified = 1 + amplify;
        int fovy = 34;
        int fovx = 144;
        int restx = ((minimap[0].length - (fovx / amplified) * amplified) / 2) - amplify;
        int resty = ((minimap.length - (fovy / amplified) * amplified) / 2);
        int basey = (int) player.getY() - (fovy / amplified) / 2;
        int basex = (int) player.getX() - (fovx / amplified) / 2;

        for (int y = 0; y < (fovy / amplified); y++) {
            for (int x = 0; x < (fovx / amplified); x++)
            {
                for (int countx = 0; countx < amplified; countx++)
                {
                    for (int county = 0; county < amplified; county++)
                    {
                        int ny = (y * amplified) + resty;
                        int nx = (x * amplified) + restx;

                        if (ny + county < 0 || nx + countx < 0 || nx + countx >= minimap[0].length || ny + county >= minimap.length)
                            continue ;
                        if (player.getY() == basey + y && player.getX() == basex + x)
                            this.minimap[ny + county][nx + countx] = new Pixel(102, 102, 255);
                        else if (!map.isInside(new Coordinates(x + basex, y + basey)) || !vision[y + basey][x + basex])
                            this.minimap[ny + county][nx + countx] = new Pixel(255, 255, 224);
                        else if (map.hasWall(new Coordinates(x + basex, y + basey)))
                            this.minimap[ny + county][nx + countx] = new Pixel(160, 160, 160);
                        else 
                            this.minimap[ny + county][nx + countx] = new Pixel(224, 224, 224);
                    }
                }
            }
        }
    }

    private void printTabs() {
        String print[] = new String[(fovy * spritesize) + 3 + minimap.length];
        StringBuilder sb = new StringBuilder();
        int y = 0;

        for (int i = 0; i < print.length; i ++)
            print[i] = "";
        print[0] += new Pixel().tpixel;
        print[print.length - 1] += new Pixel().tpixel;
        print[(fovx * spritesize) + 1] += new Pixel().tpixel;
        for (int counterx = 0; counterx < (fovx * spritesize); counterx++)
            print[0] += new Pixel().tpixel;
        for (int counterx = 0; counterx < minimap[0].length ; counterx++)
            print[print.length - 1] += new Pixel().tpixel;
        for (int counterx = 0; counterx < (fovx * spritesize); counterx++)
            print[(fovx * spritesize) + 1] += new Pixel().tpixel;
        print[0] += new Pixel().tpixel;
         print[(fovx * spritesize) + 1] += new Pixel().tpixel;
        print[print.length - 1] += new Pixel().tpixel;

        for (; y < mapTab.length; y ++)
        {
            for (int countery = 0; countery < spritesize; countery ++)
            {
                print[(y * spritesize) + countery + 1] +=  new Pixel().tpixel;
                for (int x = 0; x < mapTab.length; x ++)
                    for (int counterx = 0; counterx < spritesize; counterx ++)
                        print[(y * spritesize) + countery + 1] += mapTab[y][x].pixelmap[countery][counterx].tpixel;
                print[(y * spritesize) + countery + 1] +=  new Pixel().tpixel;
            }
        }
    
        for (int miniy = 0; miniy < minimap.length; miniy ++)
        {
            print[(y * spritesize) + 2 + miniy] +=  new Pixel().tpixel;
            for (int minix = 0; minix < minimap[0].length; minix ++)
                print[(y * spritesize) + 2 + miniy] += minimap[miniy][minix].tpixel;
            print[(y * spritesize) + 2 + miniy] +=  new Pixel().tpixel;
        }

        sb.append("\033[H");
        for (int i = 0; i < print.length; i ++)
            sb.append(print[i]).append("\n");
        System.out.println(sb.toString());
        System.out.flush();
    }

    public void termShowState(GameData gameState) {
        setGround();
        checkWalls(gameState);
        putPlayer(gameState);
        setupMinimap(gameState);
        printTabs();
    }
}

/*
        System.out.print("\033[H\033[2J");
        System.out.flush();
            for (int countery = 0; countery < spritesize; countery ++)
                print[(y * spritesize) + countery + 1] +=  new Pixel().tpixel;
            for (int i = 0; i < print.length; i ++)
                System.out.println(print[i]);    }
            print[(y * spritesize) + 1] += "└";
            for (int x = 0; x < (fovx * spritesize) * 2; x++) { 
                print[(y * spritesize) + 1] += new Pixel().tpixel;
            }
            print[(y * spritesize) + 1] += new Pixel().tpixel;
        print[0] += "┌";
        for (int x = 0; x < (fovx * spritesize) * 2; x++) { 
            print[0] += new Pixel().tpixel;
        }
        print[0] += new Pixel().tpixel;
*/
