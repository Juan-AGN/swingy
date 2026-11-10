package swingy.gameplay.behaviors;

import swingy.gameplay.Behavior;
import swingy.gameplay.Coordinates;
import swingy.gameplay.GameData;
import swingy.utils.TakeInput;

public class Player extends Behavior {
    public Player() {
        this.id = 0;
        this.cd = 100;
    }

    @Override 
    public byte getDirection(GameData info, boolean [][] map, Coordinates where) {
        int key;

        key = TakeInput.getInput();
            switch (key) {
                case 'w':
                    return (1);
                case 'a':
                    return (2);
                case 's':
                    return (3);
                case 'd':
                    return (4);
                case ' ':
                    return (5);
                default:
                    return (-1);
            }
    }
    
}
