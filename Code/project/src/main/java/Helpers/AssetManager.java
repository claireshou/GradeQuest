package Helpers;

import Display.Game;
import Gamestates.Playing;
import StaticEntity.Door;

public class AssetManager {

    Playing playing;

    public AssetManager(Playing playing) {
        this.playing = playing;
    }

    public void setObjects() {
        playing.staticEntities[0] = new Door(new Position(23 * Game.tileSize, 23 * Game.tileSize));
    }
}
