package dcode.games.uEngine2.games.ld32.logicTasks.enemy.ai.walking;

import dcode.games.uEngine2.LOGIC.ILogicTask;
import dcode.games.uEngine2.games.ld32.world.WorldEntity;

/**
 * Created by dusakus on 18.04.15.
 */
public class GoTo implements ILogicTask {

    private WorldEntity we;

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void perform() {

    }

    @Override
    public boolean doRepeat() {
        return false;
    }
}
