package dcode.games.uEngine2.games.ld32;

import dcode.games.uEngine2.BGTasks.PBGTask;
import dcode.games.uEngine2.BGTasks.internalTasks.LoadBasicTexture;
import dcode.games.uEngine2.BGTasks.internalTasks.LoadBitmapFont;
import dcode.games.uEngine2.LOGIC.ILogicTask;
import dcode.games.uEngine2.SFX.tslib.TinySound;
import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.games.ld32.items.ItemList;
import dcode.games.uEngine2.games.ld32.world.WorldPlayer;
import dcode.games.uEngine2.games.ld32.worlds.WorldList;
import dcode.games.uEngine2.tools.ext.j2s.gifReader;
import dcode.games.uEngine2.tools.ext.j2s.mirrorImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import static dcode.games.uEngine2.StData.LOG;
import static dcode.games.uEngine2.StData.currentGC;
import static dcode.games.uEngine2.games.ld32.LStData.*;

/**
 * Created by dusakus on 10.04.15.
 */
public class MainLogic implements ILogicTask {
	@Override
	public boolean isReady() {
		return true;
	}

	@Override
	public void perform() {
		switch (currentMode) {
			case MODE_GAME_PLAY:

				break;
			case MODE_MENU_MAIN:

				break;
			case MODE_INIT:
				switch (currentStatus) {
					case 0:
						LOG.println("[INIT] Logic Entrypoint reached");
						LOG.println("[INIT] Quickstarting loading screen", "D");
						currentGC.currentSC.layers_Overlay.add(new LAYER_Loading(9999));
						currentStatus++;
						break;
					case 1:
						LOG.println("[INIT] Creating Logic objects", "D");
						StData.logicTasks.registerBasic(new MenuLogic());
						StData.logicTasks.registerBasic(new GameLogic());
						currentStatus += 8;
						break;
					case 9:
						LOG.println("[INIT] Initializing registries", "D");
						ItemList.fillList();
						WorldList.fillList();
						bgm = TinySound.loadMusic(getClass().getResource("/dcode/games/uEngine2/games/ld32/res/msx/Poof.wav"));
						boss = TinySound.loadMusic(getClass().getResource("/dcode/games/uEngine2/games/ld32/res/msx/boss.wav"));
						menu = TinySound.loadMusic(getClass().getResource("/dcode/games/uEngine2/games/ld32/res/msx/Song.wav"));
						LStData.SND_laser = TinySound.loadSound(getClass().getResource("/dcode/games/uEngine2/games/ld32/res/sfx/laser.wav"));
						LStData.SND_rlaser = TinySound.loadSound(getClass().getResource("/dcode/games/uEngine2/games/ld32/res/sfx/rainbow_laser.wav"));
						LStData.SND_punch = TinySound.loadSound(getClass().getResource("/dcode/games/uEngine2/games/ld32/res/sfx/punch.wav"));
						LStData.SND_shot = TinySound.loadSound(getClass().getResource("/dcode/games/uEngine2/games/ld32/res/sfx/shot.wav"));
						LStData.SND_item = TinySound.loadSound(getClass().getResource("/dcode/games/uEngine2/games/ld32/res/sfx/item.wav"));
						LStData.SND_boom = TinySound.loadSound(getClass().getResource("/dcode/games/uEngine2/games/ld32/res/sfx/boom.wav"));
						currentStatus++;
						break;
					case 10:
						LOG.println("[INIT] requesting texture preloading", "D");

						//Load generic fonts
						StData.generalBGT.LPTasks.add(new LoadEnemyTextures());
						StData.generalBGT.LPTasks.add(new LoadPlayerTextures());
						StData.generalBGT.LPTasks.add(new LoadStatusTextures());
						StData.generalBGT.LPTasks.add(new LoadBitmapFont("font/pixel_7_9_BLACK.png", "FNTB", 7, 9, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890!?#*)(][><,.:;-+_=&~^$@\"/\\"));
						StData.generalBGT.LPTasks.add(new LoadBitmapFont("font/pixel_7_9_WHITE.png", "FNTW", 7, 9, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890!?#*)(][><,.:;-+_=&~^$@\"/\\"));

						//Load Menu pointer (TEMP)
						StData.generalBGT.LPTasks.add(new LoadBasicTexture("sprite/menu/arrowRight.png", "MARR"));
						StData.generalBGT.LPTasks.add(new LoadBasicTexture("sprite/menu/arrowLeft.png", "MARL"));
						StData.generalBGT.LPTasks.add(new LoadBasicTexture("sprite/menu/arrowUp.png", "MARU"));
						StData.generalBGT.LPTasks.add(new LoadBasicTexture("sprite/menu/arrowDown.png", "MARD"));

						StData.generalBGT.LPTasks.add(new LoadBasicTexture("sprite/enemy/rbwframe.png", "EnONIR"));

						StData.generalBGT.LPTasks.add(new LoadBasicTexture("Frames/credits.png", "CREDITS"));
						StData.generalBGT.LPTasks.add(new LoadBasicTexture("Frames/gemover.png", "DEATH"));
						StData.generalBGT.LPTasks.add(new LoadBasicTexture("Frames/PAUSE.png", "PAUSE"));
						StData.generalBGT.LPTasks.add(new LoadBasicTexture("Frames/help.png", "HELP"));


						LStData.menuBackground = new gifReader();
						LStData.introseq = new gifReader();
						LStData.menuBackground.read(getClass().getResourceAsStream("/dcode/games/uEngine2/games/ld32/res/gfx/Frames/menu.gif"));
						LStData.introseq.read(getClass().getResourceAsStream("/dcode/games/uEngine2/games/ld32/res/gfx/Frames/entry.gif"));

						for (int i = 1; i < 12; i++) {
							StData.resources.grf.registerTexture(LStData.introseq.getFrame(i), "INTRO" + i);
						}
						LStData.introseq.read(getClass().getResourceAsStream("/dcode/games/uEngine2/games/ld32/res/gfx/Frames/outro.gif"));

						for (int i = 1; i < 7; i++) {
							StData.resources.grf.registerTexture(LStData.introseq.getFrame(i), "OUTRO" + i);
						}

						StData.generalBGT.LPTasks.add(new LoadBasicTexture("world/TESTWORLD.png", "WORLD"));

						//Load player textures
						WorldPlayer.loadTextures(null);
						for (int i = 0; i < ItemList.getSize(); i++) {
							if (ItemList.itemExists(i)) WorldPlayer.loadTextures(ItemList.getItem(i));
						}

						//TODO: load preloadeable textures here
						//Add more states if needed

						//Load MenuBackground  (TEMP)
						StData.generalBGT.LPTasks.add(new LoadBasicTexture("Frames/menuBG.png", "MeBG1"));
						currentStatus = 31;
						break;
					case 31:
						currentStatus = 48;
						break;
					case 48:
						LOG.println("Will now wait for textures...", "D");
						currentStatus++;
						break;
					case 49:
						if (StData.resources.grf.isTextureAviable("MeBG1")) { //TODO: replace id with last requested texture
							currentStatus++;
							LOG.println("Textures loaded", "D");
						}
						break;
					case 50:
						LOG.println("[INIT] done, entering menu...");
						currentMode = MODE_MENU_MAIN;
						currentStatus = 1;
						break;
					default:
						if (currentStatus < 0 || currentStatus > 50) {
							LOG.println("[INIT] critical exception occured in state engine", "E5");
						}
						currentStatus++;
				}
				break;
			case MODE_SHUTDOWN:
				StData.gameIsRunning = false;
				break;
			default:
				LOG.println("[MGL] critical exception occured in state engine", "E5");
				currentMode = MODE_INIT;
				currentStatus = 0;
		}
	}

	@Override
	public boolean doRepeat() {
		return true;
	}


	private class LoadEnemyTextures extends PBGTask {
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> keys = new ArrayList<String>();

		@Override
		public boolean isReady() {
			return true;
		}

		@Override
		public void perform() {
			names.add("ziemniak.gif");
			keys.add("EnPOTS");
			names.add("ziemniak_atak.gif");
			keys.add("EnPOTA");
			names.add("ziemniak_ruch.gif");
			keys.add("EnPOTR");
			names.add("grapes_explosion_ready.gif");
			keys.add("EnGRAE");
			names.add("grapes_explosion.gif");
			keys.add("EnGRAB");
			names.add("grapes_ruch.gif");
			keys.add("EnGRAR");
			names.add("pizza_float.gif");
			keys.add("EnPIZS");
			names.add("pizza_atak.gif");
			keys.add("EnPIZA");
			names.add("pizza_bomb_extension.gif");
			keys.add("EnPIZB");
			names.add("onion_idle.gif");
			keys.add("EnONIS");
			names.add("onion_rainbow_assault.gif");
			keys.add("EnONIA");
			names.add("hamburger_jump.gif");
			keys.add("EnHAMS");
			names.add("hamburger_laser.gif");
			keys.add("EnHAMA");
			names.add("fsm_float.gif");
			keys.add("EnFSMS");
			names.add("fsm_meat_armaggedon.gif");
			keys.add("EnFSMA");
			names.add("fsm_pasta_strike.gif");
			keys.add("EnFSMa");

			for (String s : names) {
				StData.LOG.println("Loading enemy sprite " + s);
				gifReader gif = new gifReader();
				gif.read(getClass().getResourceAsStream("/dcode/games/uEngine2/games/ld32/res/gfx/sprite/enemy/" + s));
				for (int i = 0; i < gif.getFrameCount(); i++) {
					StData.LOG.println("Frame " + i + " as " + keys.get(names.indexOf(s)) + (i + 1));
					StData.resources.grf.registerTexture(gif.getFrame(i), keys.get(names.indexOf(s)) + (i + 1));
					StData.LOG.println("Mirrored Frame " + i + " as " + keys.get(names.indexOf(s)) + (i + 11));
					StData.resources.grf.registerTexture(mirrorImage.mirror(gif.getFrame(i)), keys.get(names.indexOf(s)) + (i + 11));
				}
				StData.LOG.println("done");
			}

		}
	}

	private class LoadPlayerTextures extends PBGTask {
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> keys = new ArrayList<String>();

		@Override
		public boolean isReady() {
			return true;
		}

		@Override
		public void perform() {
			names.add("hero_still.gif");
			keys.add("NS");
			names.add("hero_ruch.gif");
			keys.add("NM");
			names.add("hero_jump.gif");
			keys.add("NJ");
			names.add("hero_still_meat.gif");
			keys.add("MS");
			names.add("hero_ruch_meat.gif");
			keys.add("MM");
			names.add("hero_jump_meat.gif");
			keys.add("MJ");
			names.add("hero_still_carrot.gif");
			keys.add("CS");
			names.add("hero_ruch_carrot.gif");
			keys.add("CM");
			names.add("hero_jump_carrot.gif");
			keys.add("CJ");
			names.add("hero_carrot_attack.gif");
			keys.add("CA");
			names.add("hero_meat_strike.gif");
			keys.add("MA");

			for (String s : names) {
				StData.LOG.println("Loading player sprite " + s);
				gifReader gif = new gifReader();
				gif.read(getClass().getResourceAsStream("/dcode/games/uEngine2/games/ld32/res/gfx/sprite/player/" + s));
				for (int i = 0; i < gif.getFrameCount(); i++) {
					StData.LOG.println("Frame " + i + " as Pla" + keys.get(names.indexOf(s)) + (i + 1));
					StData.resources.grf.registerTexture(gif.getFrame(i), "Pla" + keys.get(names.indexOf(s)) + (i + 1));
					StData.LOG.println("Mirrored Frame " + i + " as Pla" + keys.get(names.indexOf(s)) + (i + 11));
					if (i < 10)
						StData.resources.grf.registerTexture(mirrorImage.mirror(gif.getFrame(i)), "Pla" + keys.get(names.indexOf(s)) + (i + 11));
				}
				StData.LOG.println("done");
			}

			try {
				BufferedImage bf = ImageIO.read(getClass().getResourceAsStream("/dcode/games/uEngine2/games/ld32/res/gfx/sprite/player/carletleft.png"));
				StData.resources.grf.registerTexture(bf, "CBULETL");
				StData.resources.grf.registerTexture(mirrorImage.mirror(bf), "CBULETR");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	private class LoadStatusTextures extends PBGTask {

		@Override
		public boolean isReady() {
			return true;
		}

		@Override
		public void perform() {
			gifReader gif = new gifReader();
			gif.read(getClass().getResourceAsStream("/dcode/games/uEngine2/games/ld32/res/gfx/hp_stat.gif"));
			gifReader gif2 = new gifReader();
			gif2.read(getClass().getResourceAsStream("/dcode/games/uEngine2/games/ld32/res/gfx/am_stat.gif"));
			for (int i = 0; i < gif.getFrameCount(); i++) {
				StData.LOG.println("Frame " + i + " as HBAR" + (i + 1));
				StData.resources.grf.registerTexture(gif.getFrame(i), "HBAR" + (i));
			}
			for (int i = 0; i < gif2.getFrameCount(); i++) {
				StData.LOG.println("Mirrored Frame " + i + " as ABAR" + (i + 11));
				StData.resources.grf.registerTexture(gif2.getFrame(i), "ABAR" + (i));
			}
			StData.LOG.println("done");
		}

	}
}

