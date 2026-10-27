package swingy.view;

import java.awt.image.BufferedImage;

public final class Sprites {

    public static final String []imgRoutes = {
        "imgs/playertank.png",
        "imgs/grass.png",
        "imgs/tree.png",
        "imgs/background.png"
    };

    private static final Sprites INSTANCE = new Sprites();

    Sprite[] allSprites;

    public static final byte PLAYER_TANK = 0;
    public static final byte GRASS = 1;
    public static final byte TREE = 2;
    public static final byte BACKGROUND = 3;

    Sprites() {
        allSprites = new Sprite[imgRoutes.length];

        for (int i = 0; i < imgRoutes.length; i++)
            allSprites[i] = parseImage(imgRoutes[i]);
    }

    public static Sprite parseImage(String toParse) {
        BufferedImage image = ImageHandler.getImage(toParse);
        Sprite toRet = new Sprite();
        if (image == null)
            toRet.createErrorSprite();
        else
        {
            for (int y = 0; y < 16; y ++)
                for (int x = 0; x < 16; x ++)
                    toRet.pixelmap[y][x] = new Pixel(image.getRGB(x, y));
        }
        return (toRet);
    } 

    public static Sprites getInstance() {
        return INSTANCE;
    }

    public static void main(String[] args) {
        Sprites sprites = Sprites.getInstance();
        Sprite test = sprites.allSprites[GRASS];
        test.fuseSprite(sprites.allSprites[PLAYER_TANK]);
        for (int z = 0; z < sprites.allSprites.length; z ++)
        {
            for (int y = 0; y < 16; y++)
            {
                for (int x = 0; x < 16; x++)
                {
                    Pixel pixel = sprites.allSprites[z].pixelmap[y][x];

                    if (pixel.transparency)
                    {
                        System.out.print("  ");
                    }
                    else
                        {
                        System.out.print(pixel.tpixel);
                    }
                }
                System.out.println();
            }
        }
        for (int y = 0; y < 16; y++)
        {
            for (int x = 0; x < 16; x++)
            {
                Pixel pixel = test.pixelmap[y][x];

                if (pixel.transparency)
                {
                    System.out.print("  ");
                }
                else
                    {
                    System.out.print(pixel.tpixel);
                }
            }
            System.out.println();
        }
    }
}
