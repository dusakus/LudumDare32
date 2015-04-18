package dcode.games.uEngine2.games.ld32.worlds;

import dcode.games.uEngine2.StData;

/**
 * Created by dusakus on 18.04.15.
 */
public class WorldList {
    public static PWorld[] worldlist = new PWorld[10];

    public static void fillList() {
        StData.LOG.println("Filling World list");
        worldlist[0] = new TestWorld();
    }

}
