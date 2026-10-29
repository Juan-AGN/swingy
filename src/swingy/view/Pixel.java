package swingy.view;

public class Pixel {
    String tpixel = "██";
    boolean transparency;

    Pixel() {
        transparency = false;
    }

    Pixel(int r, int g, int b) {
        transparency = true;
        tpixel = rgb(r, g, b);
    }

    Pixel(Pixel toCopy) {
        transparency = toCopy.transparency;
        tpixel = toCopy.tpixel;
    }

    Pixel(int color) {
        int alpha = (color >>> 24) & 0xff;
        int red   = (color >> 16) & 0xFF;
        int green = (color >> 8)  & 0xFF;
        int blue  = color & 0xFF;

        setrgb(red, green, blue);

        if (alpha < 125)
            transparency = true;
    }

    public void setrgb(int r, int g, int b) {
        transparency = false;
        tpixel = "\u001B[38;2;" + r + ";" + g + ";" + b + "m██\u001B[0m";
    }

    public void setAlpha(int rgba) {
        int alpha = (rgba >>> 24) & 0xff; 

        if (alpha < 122)
            transparency = true;
    }

    public static String rgb(int r, int g, int b) {
        return ("\u001B[38;2;" + r + ";" + g + ";" + b + "m██\u001B[0m");
    }

    public static int getAlpha(int rgba) {
        int alpha = (rgba >>> 24) & 0xff; 

        return (alpha);
    }
}
