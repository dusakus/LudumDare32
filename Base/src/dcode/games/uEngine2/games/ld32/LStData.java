package dcode.games.uEngine2.games.ld32;

import dcode.games.uEngine2.SFX.tslib.Music;
import dcode.games.uEngine2.SFX.tslib.Sound;
import dcode.games.uEngine2.tools.ext.j2s.gifReader;

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

    public static Music bgm;
    public static Music boss;
    public static Music menu;

    public static GameLogic GL;
    public static int roomWidth;
    public static int roomHeight;
    public static int renderOffsetX = 0;
    public static int renderOffsetY = 0;
    public static int maxHealth = 400;
    public static int healthStat = 400;
    public static int maxAmmo = 32;
    public static int ammoStat = 32;
    public static int invincTicks = 2;
    public static gifReader menuBackground;
    public static Sound SND_laser;
    public static Sound SND_punch;
    public static Sound SND_shot;
    public static Sound SND_item;
    public static Sound SND_rlaser;
    public static Sound SND_boom;
    public static gifReader introseq;
}

