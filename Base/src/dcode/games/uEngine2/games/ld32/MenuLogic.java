package dcode.games.uEngine2.games.ld32;

import dcode.games.uEngine2.LOGIC.ILogicTask;

import static dcode.games.uEngine2.games.ld32.LStData.*;

/**
 * Created by dusakus on 10.04.15.
 */
public class MenuLogic implements ILogicTask {
    @Override
    public boolean isReady() {
        return LStData.currentMode == LStData.MODE_MENU_MAIN;
    }

    @Override
    public void perform() {
        switch (currentStatus) {
            case 1:
                //TODO: leave the loop

        }
    }

    @Override
    public boolean doRepeat() {
        return true;
    }
}
