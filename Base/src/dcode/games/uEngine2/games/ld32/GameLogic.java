package dcode.games.uEngine2.games.ld32;

import dcode.games.uEngine2.GFX.ScreenContent;
import dcode.games.uEngine2.LOGIC.ILogicTask;
import dcode.games.uEngine2.StData;

import static dcode.games.uEngine2.StData.LOG;
import static dcode.games.uEngine2.games.ld32.LStData.GL;
import static dcode.games.uEngine2.games.ld32.LStData.currentStatus;


/**
 * Created by dusakus on 10.04.15.
 */
public class GameLogic implements ILogicTask {

    private ScreenContent inGameSC;

    @Override
    public boolean isReady() {
        return LStData.currentMode == LStData.MODE_GAME_PLAY;
    }

    @Override
    public void perform() {
        switch (currentStatus) {


            case 101:
                //TODO: game tick
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
                //TODO: Add standard layers here
                currentStatus++;
                break;
            case 13:
                LOG.println("[GL] requesting player textures", "D");
                //TODO: Create player here
                currentStatus++;
                break;
            case 14:
                LOG.println("[GL] initialization complete");
                currentStatus = 2;
                break;
            case 109:
                //TODO: enter game
                StData.currentGC.currentSC = inGameSC;
                currentStatus = 101;
                break;
        }
    }

    @Override
    public boolean doRepeat() {
        return true;
    }
}
