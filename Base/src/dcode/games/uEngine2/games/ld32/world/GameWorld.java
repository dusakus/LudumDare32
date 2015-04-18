package dcode.games.uEngine2.games.ld32.world;

import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.games.ld32.LStData;
import dcode.games.uEngine2.games.ld32.parts.WorldTrigger;
import dcode.games.uEngine2.games.ld32.worlds.PWorld;
import dcode.games.uEngine2.games.ld32.worlds.WorldList;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by dusakus on 18.04.15.
 */
public class GameWorld {
    public ArrayList<WorldEntity> entities;
    public WorldPlayer wp;
    public WorldTrigger[] triggers = new WorldTrigger[255];

    public int levelID = -1;

    public GameWorld(int currentLevel) {
        entities = new ArrayList<WorldEntity>();
        levelID = currentLevel;
        PWorld p = WorldList.worldlist[levelID];
        StData.resources.grf.registerTexture(StData.resources.grf.getTexture(p.getTexID()), "WORLD");
        p.loadYerself(this);
    }

    public void tick() {
        for (WorldEntity we : entities) {
            if (we != null) we.tick();
        }
        wp.updatePlayer();
        for (int i = 0; i < 255; i++) {
            if (triggers[i] != null) {
                triggers[i].checkTrigger(getDataAt(wp.inRoomX, wp.inRoomY));
            }
        }
        LStData.roomWidth = StData.resources.grf.getTexture("WORLD").getWidth();
        LStData.roomHeight = StData.resources.grf.getTexture("WORLD").getHeight();

    }

    public void loadIntoWorld() {
        for (WorldEntity we : entities) {
            if (we != null) we.register();
        }
        LStData.GL.getSContent().sprites[2] = wp;
        LStData.GL.getSContent().sprites_middle[0] = 2;
    }

    public void unloadFromWorld() {
        for (WorldEntity we : entities) {
            if (we != null) we.unregister();
        }
    }

    private Color getDataAt(int inRoomX, int inRoomY) {
        return new Color(255, 255, 255);
    }

    public void trigger(Color data) {

    }
}
