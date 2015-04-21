package dcode.games.uEngine2.games.ld32.worlds;

import dcode.games.uEngine2.StData;

/**
 * Created by dusakus on 18.04.15.
 */
public class WorldList {
    public static PWorld[] worldlist = new PWorld[11];

    public static void fillList() {
        StData.LOG.println("Filling World list");
        worldlist[0] = new TestWorld();
        worldlist[1] = new World1();
        worldlist[2] = new World2();
        worldlist[3] = new World3();
        worldlist[4] = new World4();
        worldlist[5] = new World5();
        worldlist[6] = new World11();
        worldlist[7] = new World12();
        worldlist[8] = new World13();
        worldlist[9] = new World14();
        worldlist[10] = new World15();
    }

}
