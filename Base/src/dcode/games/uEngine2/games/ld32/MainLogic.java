package dcode.games.uEngine2.games.ld32;

import dcode.games.uEngine2.BGTasks.PBGTask;
import dcode.games.uEngine2.BGTasks.internalTasks.LoadBasicTexture;
import dcode.games.uEngine2.BGTasks.internalTasks.LoadBitmapFont;
import dcode.games.uEngine2.LOGIC.ILogicTask;
import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.games.ld32.items.ItemList;
import dcode.games.uEngine2.games.ld32.world.WorldPlayer;
import dcode.games.uEngine2.games.ld32.worlds.WorldList;
import dcode.games.uEngine2.tools.ext.j2s.gifReader;
import dcode.games.uEngine2.tools.ext.j2s.mirrorImage;

import java.util.ArrayList;

import static dcode.games.uEngine2.StData.LOG;
import static dcode.games.uEngine2.StData.currentGC;
import static dcode.games.uEngine2.games.ld32.LStData.*;

/**
 * Created by dusakus on 10.04.15.
 */
public class MainLogic implements ILogicTask {
    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void perform() {
        switch (currentMode) {
            case MODE_GAME_PLAY:

                break;
            case MODE_MENU_MAIN:

                break;
            case MODE_INIT:
                switch (currentStatus) {
                    case 0:
                        LOG.println("[INIT] Logic Entrypoint reached");
                        LOG.println("[INIT] Quickstarting loading screen", "D");
                        currentGC.currentSC.layers_Overlay.add(new LAYER_Loading(9999));
                        currentStatus++;
                        break;
                    case 1:
                        LOG.println("[INIT] Creating Logic objects", "D");
                        currentGC.currentLT.registerBasic(new MenuLogic());
                        currentGC.currentLT.registerBasic(new GameLogic());
                        currentStatus += 8;
                        break;
                    case 9:
                        LOG.println("[INIT] Initializing registries", "D");
                        ItemList.fillList();
                        WorldList.fillList();
                        currentStatus++;
                        break;
                    case 10:
                        LOG.println("[INIT] requesting texture preloading", "D");

                        //Load generic fonts
                        StData.generalBGT.LPTasks.add(new LoadEnemyTextures());
                        StData.generalBGT.LPTasks.add(new LoadBitmapFont("font/pixel_7_9_BLACK.png", "FNTB", 7, 9, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890!?#*)(][><,.:;-+_=&~^$@\"/\\"));
                        StData.generalBGT.LPTasks.add(new LoadBitmapFont("font/pixel_7_9_WHITE.png", "FNTW", 7, 9, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890!?#*)(][><,.:;-+_=&~^$@\"/\\"));

                        //Load Menu pointer (TEMP)
                        StData.generalBGT.LPTasks.add(new LoadBasicTexture("sprite/menu/arrowRight.png", "MARR"));
                        StData.generalBGT.LPTasks.add(new LoadBasicTexture("sprite/menu/arrowLeft.png", "MARL"));
                        StData.generalBGT.LPTasks.add(new LoadBasicTexture("sprite/menu/arrowUp.png", "MARU"));
                        StData.generalBGT.LPTasks.add(new LoadBasicTexture("sprite/menu/arrowDown.png", "MARD"));

                        StData.generalBGT.LPTasks.add(new LoadBasicTexture("world/TESTWORLD.png", "WORLD"));

                        //Load player textures
                        WorldPlayer.loadTextures(null);
                        for (int i = 0; i < ItemList.getSize(); i++) {
                            if (ItemList.itemExists(i)) WorldPlayer.loadTextures(ItemList.getItem(i));
                        }

                        //TODO: load preloadeable textures here
                        //Add more states if needed

                        //Load MenuBackground  (TEMP)
                        StData.generalBGT.LPTasks.add(new LoadBasicTexture("Frames/menuBG.png", "MeBG1"));
                        currentStatus = 31;
                        break;
                    case 31:
                        currentStatus = 48;
                        break;
                    case 48:
                        LOG.println("Will now wait for textures...", "D");
                        currentStatus++;
                        break;
                    case 49:
                        if (StData.resources.grf.isTextureAviable("MeBG1")) { //TODO: replace id with last requested texture
                            currentStatus++;
                            LOG.println("Textures loaded", "D");
                        }
                        break;
                    case 50:
                        LOG.println("[INIT] done, entering menu...");
                        currentMode = MODE_MENU_MAIN;
                        currentStatus = 1;
                        break;
                    default:
                        if (currentStatus < 0 || currentStatus > 50) {
                            LOG.println("[INIT] critical exception occured in state engine", "E5");
                        }
                        currentStatus++;
                }
                break;
            case MODE_SHUTDOWN:
                StData.gameIsRunning = false;
                break;
            default:
                LOG.println("[MGL] critical exception occured in state engine", "E5");
                currentMode = MODE_INIT;
                currentStatus = 0;
        }
    }

    @Override
    public boolean doRepeat() {
        return true;
    }


    private class LoadEnemyTextures extends PBGTask {
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<String> keys = new ArrayList<String>();

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void perform() {
            names.add("ziemniak.gif");
            keys.add("EnPOTS");
            names.add("ziemniak_atak.gif");
            keys.add("EnPOTA");
            names.add("ziemniak_ruch.gif");
            keys.add("EnPOTR");
            names.add("grapes_explosion_ready.gif");
            keys.add("EnGRAE");
            names.add("grapes_ruch.gif");
            keys.add("EnGRAR");
            names.add("pizza_float.gif");
            keys.add("EnPIZS");

            for (String s : names) {
                StData.LOG.println("Loading enemy sprite " + s);
                gifReader gif = new gifReader();
                gif.read(getClass().getResourceAsStream("/dcode/games/uEngine2/games/ld32/res/gfx/sprite/enemy/" + s));
                for (int i = 0; i < gif.getFrameCount(); i++) {
                    StData.LOG.println("Frame " + i + " as " + keys.get(names.indexOf(s)) + (i + 1));
                    StData.resources.grf.registerTexture(gif.getFrame(i), keys.get(names.indexOf(s)) + (i + 1));
                    StData.resources.grf.registerTexture(mirrorImage.mirror(gif.getFrame(i)), keys.get(names.indexOf(s)) + (i + 11));
                }
                StData.LOG.println("done");
            }

        }
    }
}
