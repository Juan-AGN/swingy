package swingy.utils;

import java.io.IOException;

public final class TakeInput {
    public static int getInput() {
        int inChar = 0;

        try {
            inChar = System.in.read();
        }
        catch (IOException e) {
            System.out.println("Error reading from user");
        }

        return (inChar);
    }   
}
