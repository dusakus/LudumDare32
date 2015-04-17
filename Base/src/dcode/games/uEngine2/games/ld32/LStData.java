package dcode.games.uEngine2.games.ld32;

/**
 * Created by dusakus on 09.02.15.
 */
public class LStData {

    public static final int MODE_SHUTDOWN = -10;
    public static final int MODE_INIT = 0;
    public static final int MODE_MENU_MAIN = 10;
    public static final int MODE_GAME_PLAY = 100;

    public static int currentMode = 0;

    public static int currentStatus = 0;

    public static GameLogic GL;
}

