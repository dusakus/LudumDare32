package dcode.games.uEngine2.games.ld32.world;

import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.games.ld32.LStData;
import dcode.games.uEngine2.games.ld32.parts.WorldTrigger;
import dcode.games.uEngine2.games.ld32.worlds.PWorld;
import dcode.games.uEngine2.games.ld32.worlds.WorldList;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by dusakus on 18.04.15.
 */
public class GameWorld {
    public ArrayList<WorldEntity> entities;
    public WorldPlayer wp;
    public WorldTrigger[] triggers = new WorldTrigger[255];
    public int levelID = -1;
    BufferedImage dataTexture;
    PWorld worldProc;

    public GameWorld(int currentLevel) {
        wp = LStData.GL.getPlayer();
        entities = new ArrayList<WorldEntity>();
        levelID = currentLevel;
        worldProc = WorldList.worldlist[levelID];
        StData.resources.grf.registerTexture(StData.resources.grf.getTexture(worldProc.getTexID()), "WORLD");
        worldProc.loadYerself(this);
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

        dataTexture = StData.resources.grf.getTexture("WORLDD");
        worldProc.triggerEvent(new Color(dataTexture.getRGB(wp.inRoomX, wp.inRoomY)));

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

    public Color getDataAt(int inRoomX, int inRoomY) {
        return new Color(dataTexture.getRGB(wp.inRoomX, wp.inRoomY));
    }

    public void trigger(Color data) {

    }

    public boolean collide(int x, int y, boolean justFalling) {
        Color c = new Color(dataTexture.getRGB(x, y));
        if (!justFalling && c.getGreen() == 255) return false;
        if (c.getRed() == 255) return true;
        else if (c.getRed() == 0) return false;
        else return worldProc.checkUnlocked(c.getRed());
    }

    public void killEntity(WorldEntity we) {
        we.unregister();
        entities.remove(we);
    }
}
