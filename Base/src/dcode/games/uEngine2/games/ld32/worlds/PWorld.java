package dcode.games.uEngine2.games.ld32.worlds;

import dcode.games.uEngine2.games.ld32.world.GameWorld;

import java.awt.*;

/**
 * Created by dusakus on 18.04.15.
 */
public abstract class PWorld {

    public abstract String getTexID();

    public abstract void loadYerself(GameWorld target); //Load the world into world container

    public abstract boolean checkUnlocked(int RColor);

    public abstract void triggerEvent(Color data);
}
