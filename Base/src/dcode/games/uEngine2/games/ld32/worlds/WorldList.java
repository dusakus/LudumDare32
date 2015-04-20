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
        worldlist[1] = new World1();
        worldlist[2] = new World2();
        worldlist[3] = new World3();
        worldlist[4] = new World4();
    }

}
