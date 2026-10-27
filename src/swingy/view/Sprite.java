package swingy.view;

public class Sprite {
    public Pixel[][] pixelmap = new Pixel[16][16];

    Sprite() {
        
    }

    Sprite(Sprite toCopy) {
        for (int y = 0; y < 16; y++)
            for (int x = 0; x < 16; x++)
                pixelmap[y][x] = new Pixel(toCopy.pixelmap[y][x]);
    }

    public void fuseSprite(Sprite other) {
        for (int y = 0; y < 16; y++)
            for (int x = 0; x < 16; x++)
                if (!other.pixelmap[y][x].transparency)
                    this.pixelmap[y][x] = new Pixel(other.pixelmap[y][x]);
    }

    public void createErrorSprite() {
        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                pixelmap[y][x] = new Pixel(0, 0, 0);
                pixelmap[y][x].setrgb(0, 0, 0);
            }
        }

        int[][][] smallLetters = {
            {{1,1},{1,0},{1,1},{1,0},{1,1}},
            {{1,1},{1,0},{1,1},{1,1},{1,0}},
            {{1,1},{1,0},{1,1},{1,0},{1,1}},
            {{1,1},{1,0},{1,0},{1,0},{1,1}},
            {{1,1},{1,0},{1,1},{1,1},{1,0}}
        };

        int xOffset = 3;
        int yOffset = 5;

        for (int i = 0; i < smallLetters.length; i++) {
            for (int y = 0; y < 5; y++) {
                for (int x = 0; x < 2; x++) {
                    if (smallLetters[i][y][x] == 1) {
                        pixelmap[y + yOffset][x + xOffset + i * 2]
                            .setrgb(255, 0, 0);
                    }
                }
            }
        }
    }
}
