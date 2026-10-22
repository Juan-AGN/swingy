package swingy.gameplay;

import swingy.gameplay.mapgenerator.MapGenerator;
import swingy.utils.Sleep;

public class Gameplay {
    GameData gameState;

    public void setup(Unit player) {
        MapGenerator generator = new MapGenerator();
        this.gameState = new GameData(generator.generateMap((int)player.unitrole.level), player);
    }

    public void loop() {
        while (gameState.isInside(gameState.player.where))
        {
            Sleep.wait(33);
        }
    } 
}