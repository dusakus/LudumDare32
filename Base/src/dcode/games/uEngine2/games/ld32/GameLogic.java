package dcode.games.uEngine2.games.ld32;

import dcode.games.uEngine2.GFX.ScreenContent;
import dcode.games.uEngine2.GFX.layers.ClearColorLayer;
import dcode.games.uEngine2.LOGIC.ILogicTask;
import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.games.ld32.render.Layer_WORLDDraw;
import dcode.games.uEngine2.games.ld32.render.ParalaxBackground;
import dcode.games.uEngine2.games.ld32.world.GameWorld;
import dcode.games.uEngine2.games.ld32.world.WorldPlayer;
import dcode.games.uEngine2.games.ld32.worlds.WorldList;
import dcode.games.uEngine2.tools.numbarTools;

import java.awt.*;
import java.awt.event.KeyEvent;

import static dcode.games.uEngine2.StData.LOG;
import static dcode.games.uEngine2.games.ld32.LStData.GL;
import static dcode.games.uEngine2.games.ld32.LStData.currentStatus;


/**
 * Created by dusakus on 10.04.15.
 */
public class GameLogic implements ILogicTask {

    private ScreenContent inGameSC;
    private GameWorld currentGameWorld;
    private WorldPlayer player;

    private int currentLevel = 1;

    @Override
    public boolean isReady() {
        return LStData.currentMode == LStData.MODE_GAME_PLAY;
    }

    @Override
    public void perform() {
        switch (currentStatus) {


            case 201:
                updateWorldOffset();
                currentGameWorld.tick();
                inputTick();
                break;
            case 1:
                LOG.println("[GL] Entering game_play environment");
                GL = this;
                currentStatus++;
                break;
            case 2:
                if (inGameSC != null) currentStatus = 109;
                else currentStatus = 11;
                break;
            case 11:
                LOG.println("[GL] inGameSC is null, initializing...", "D");
                inGameSC = new ScreenContent();
                currentStatus++;
                break;
            case 12:
                inGameSC.layers_Background.add(new ClearColorLayer(Color.cyan));
                inGameSC.layers_Background.add(new ParalaxBackground());
                inGameSC.layers_Center.add(new Layer_WORLDDraw());
                currentStatus++;
                break;
            case 13:
                LOG.println("[GL] requesting player textures", "D");
                player = new WorldPlayer(100, 100);


                currentStatus++;
                break;
            case 14:
                LOG.println("[GL] initialization complete");
                currentStatus = 2;
                break;
            case 109:
                if (currentGameWorld == null || currentGameWorld.levelID != currentLevel) {
                    StData.LOG.println("[GL] Loading level " + currentLevel);
                    currentStatus = 100;
                } else {
                    StData.currentGC.currentSC = inGameSC;
                    currentStatus = 201;
                }
                break;

            case 100:
                if (WorldList.worldlist[currentLevel] == null) {
                    LOG.println("[GL] taget world not aviable, entering missingno");
                    currentLevel = 0;
                } else {
                    currentGameWorld = new GameWorld(currentLevel);
                    currentGameWorld.wp = player;
                }
                currentStatus++;
                break;
            case 101:
                currentGameWorld.loadIntoWorld();
                currentStatus = 109;
                break;
        }
    }

    private void inputTick() {
        if (InHandler.instance.isKeyPressed(KeyEvent.VK_LEFT)) player.goLeft();
        if (InHandler.instance.isKeyPressed(KeyEvent.VK_RIGHT)) player.goRight();
        if (InHandler.instance.isKeyPressed(KeyEvent.VK_UP)) player.goJump();
    }

    private void updateWorldOffset() {
        LStData.renderOffsetX = numbarTools.clamp(((WorldPlayer) StData.currentGC.currentSC.sprites[2]).inRoomX - 200, 0, LStData.roomWidth - 400);
        LStData.renderOffsetY = numbarTools.clamp(((WorldPlayer) StData.currentGC.currentSC.sprites[2]).inRoomY - 150, 0, LStData.roomHeight - 300);
    }

    @Override
    public boolean doRepeat() {
        return true;
    }

    public ScreenContent getSContent() {
        return inGameSC;
    }

    public boolean getColisionAt(int x, int y) {
        return currentGameWorld.collide(x, y);
    }
}
