package dcode.games.uEngine2.games.ld32;

import dcode.games.uEngine2.*;

/**
 * Created by dusakus on 10.04.15.
 */
public class LD32_INIT {
    public static void main(String[] args) {
        uGameSetup gs = new uGameSetup();
        gs.FPS = 60;
        gs.TPS_logic = 60;
        gs.TPS_MSX = 0;
        gs.TPS_BG = 60;

        gs.debug = false;
        gs.width = 200;
        gs.height = 150;
        gs.scale = 4;

        gs.spriteTableSize = 121;
        gs.enableSpriteWrappers = false;
        gs.postProcCount = 1;
        gs.spriteLayerSize = 121;

        gs.soundEnabled = true;

        gs.safeName = "ld32";
        gs.screenName = "LD32";
        gs.windowTitle = " FOOD FIGHT apocalypse   for Ludum Dare 32 ";

        PuGameBase gb = new PuGameBase();

        gb.setup = gs;
        gb.initialInputHandler = new InHandler();

        gb.contentInitializer = new PuGameLoader() {
            @Override
            public void loadInitialGameContent() {
                StData.logicTasks.registerBasic(new MainLogic());
            }

            @Override
            public void engineStopped() {
                //TODO: add save to file feature if needed
                System.out.println("Exitting...");
                System.exit(0);
            }
        };

        Startup.StartGame(gb);
    }

}