package dcode.games.uEngine2.games.ld32;

import dcode.games.uEngine2.BGTasks.PBGTask;
import dcode.games.uEngine2.GFX.ScreenContent;
import dcode.games.uEngine2.GFX.layers.ClearColorLayer;
import dcode.games.uEngine2.GFX.layers.FillTextureLayer;
import dcode.games.uEngine2.LOGIC.ILogicTask;
import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.games.ld32.render.Layer_StatusOverlay;
import dcode.games.uEngine2.games.ld32.render.Layer_WORLDDraw;
import dcode.games.uEngine2.games.ld32.render.ParalaxBackground;
import dcode.games.uEngine2.games.ld32.world.GameWorld;
import dcode.games.uEngine2.games.ld32.world.WorldEntity;
import dcode.games.uEngine2.games.ld32.world.WorldPlayer;
import dcode.games.uEngine2.games.ld32.worlds.WorldList;
import dcode.games.uEngine2.tools.numbarTools;

import java.awt.*;
import java.awt.event.KeyEvent;

import static dcode.games.uEngine2.StData.LOG;
import static dcode.games.uEngine2.games.ld32.LStData.*;


/**
 * Created by dusakus on 10.04.15.
 */
public class GameLogic implements ILogicTask {

	public int currentLevel = 1;
	private ScreenContent inGameSC;
	private GameWorld currentGameWorld;
	private WorldPlayer player;
	private int gearDelay = 10;
	private int pdelay = 100;

	@Override
	public boolean isReady() {
		return LStData.currentMode == LStData.MODE_GAME_PLAY;
	}

	@Override
	public void perform() {
		switch (currentStatus) {


			case 201:
				if (StData.currentGC.currentSC.layers_Overlay.size() > 0) {
					StData.gameFreeze = true;
				}
				updateWorldOffset();
				currentGameWorld.tick();
				inputTick();
				if (invincTicks > 0) invincTicks--;
				if (healthStat <= 0) {
					currentStatus = 1201;
					currentMode = MODE_MENU_MAIN;
					bgm.stop();
				}
				break;
			case 202:
				StData.currentGC.currentSC.layers_Overlay.clear();
				currentStatus = 201;
				pdelay = 100;
				break;
			case 1:
				LOG.println("[GL] Entering game_play environment");
				GL = this;
				bgm.play(true);
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
				inGameSC.layers_Background.add(new ClearColorLayer(new Color(24, 40, 84)));
				inGameSC.layers_Background.add(new ParalaxBackground());
				inGameSC.layers_Center.add(new Layer_WORLDDraw());
				inGameSC.layers_Center.add(new Layer_StatusOverlay());
				currentStatus++;
				break;
			case 13:
				LOG.println("[GL] requesting player textures", "D");
				player = new WorldPlayer(100, 100);


				currentStatus++;
				break;
			case 14:
				LOG.println("[GL] initialization complete");
				currentStatus = 2;
				break;
			case 109:
				if (currentGameWorld == null || currentGameWorld.levelID != currentLevel) {
					StData.LOG.println("[GL] Loading level " + currentLevel);
					currentStatus = 100;
				} else {
					StData.currentGC.currentSC = inGameSC;
					currentStatus = 201;
				}
				break;

			case 100:
				if (WorldList.worldlist[currentLevel] == null) {
					LOG.println("[GL] taget world not aviable, entering missingno");
					currentLevel = 0;
				} else {
					currentGameWorld = new GameWorld(currentLevel);
					currentGameWorld.wp = player;
				}
				currentStatus++;
				break;
			case 101:
				currentGameWorld.loadIntoWorld();
				currentStatus = 109;
				break;
			case 209:
				currentGameWorld.unloadFromWorld();
				currentLevel++;
				currentStatus = 109;
				break;
		}
	}

	private void inputTick() {
		if (InHandler.instance.isKeyPressed(KeyEvent.VK_LEFT)) player.goLeft();
		if (InHandler.instance.isKeyPressed(KeyEvent.VK_RIGHT)) player.goRight();
		if (InHandler.instance.isKeyPressed(KeyEvent.VK_UP)) player.goJump();
		if (InHandler.instance.isKeyPressed(KeyEvent.VK_Z)) player.tryAttacking();
		if (InHandler.instance.isKeyPressed(KeyEvent.VK_ENTER)) {
			LStData.currentStatus = 1202;
			LStData.currentMode = MODE_MENU_MAIN;
		}
		if (InHandler.instance.isKeyPressed(KeyEvent.VK_P) && pdelay == 0) {
			StData.currentGC.currentSC.layers_Overlay.add(new FillTextureLayer("PAUSE"));
			StData.generalBGT.WaitingTasks.add(new continueWatcher());
		}

		if (InHandler.instance.isKeyPressed(KeyEvent.VK_X) && gearDelay == 0) {
			if (player.getItemModifier() == "C") player.setItemModifier("M");
			else player.setItemModifier("C");
			gearDelay = 15;
		}
		if (InHandler.instance.isKeyPressed_SHIFT() && InHandler.instance.isKeyPressed_CONTROLL() && InHandler.instance.isKeyPressed_ALT()) {
			//Debug tools
			if (InHandler.instance.isKeyPressed(KeyEvent.VK_F5)) {
				StData.LOG.println("[DebugTools] Reloading worldEntities, some entities might be missing");
				currentGameWorld.unloadFromWorld();
				currentGameWorld.loadIntoWorld();
			}
			if (InHandler.instance.isKeyPressed(KeyEvent.VK_F7)) player.hazPhysics = false;
			if (InHandler.instance.isKeyPressed(KeyEvent.VK_F8)) player.hazPhysics = true;
			if (InHandler.instance.isKeyPressed(KeyEvent.VK_F1)) player.setItemModifier("N");
			if (InHandler.instance.isKeyPressed(KeyEvent.VK_F2)) player.setItemModifier("C");
			if (InHandler.instance.isKeyPressed(KeyEvent.VK_F3)) player.setItemModifier("M");
			if (InHandler.instance.isKeyPressed(KeyEvent.VK_F9)) ammoStat = maxAmmo;
			if (InHandler.instance.isKeyPressed(KeyEvent.VK_F10)) healthStat = maxHealth;
			if (InHandler.instance.isKeyPressed(KeyEvent.VK_F11) && currentStatus == 201) {
				currentLevel--;
				if (currentLevel < 0) currentLevel = 0;
				currentStatus = 109;
			}
			if (InHandler.instance.isKeyPressed(KeyEvent.VK_F12) && currentStatus == 201) {
				currentLevel++;
				currentStatus = 109;
			}
		}
		if (gearDelay > 0) {
			gearDelay--;
		}
		if (pdelay > 0) {
			pdelay--;
		}

	}

	public void updateWorldOffset() {
		LStData.renderOffsetX = numbarTools.clamp(((WorldPlayer) StData.currentGC.currentSC.sprites[2]).inRoomX - 100, 0, LStData.roomWidth - 200);
		LStData.renderOffsetY = numbarTools.clamp(((WorldPlayer) StData.currentGC.currentSC.sprites[2]).inRoomY - 75, 0, LStData.roomHeight - 150);
	}

	@Override
	public boolean doRepeat() {
		return true;
	}

	public ScreenContent getSContent() {
		return inGameSC;
	}

	public boolean getColisionAt(int x, int y, boolean juf) {
		return currentGameWorld.collide(x, y, juf);
	}

	public WorldPlayer getPlayer() {
		return player;
	}

	public void updateTriggers(int inRoomX, int i) {
		currentGameWorld.trigger(currentGameWorld.getDataAt(inRoomX, i));
	}

	public void damageArea(Rectangle area, int damage) {
		for (WorldEntity we : currentGameWorld.entities) {
			if (we != null) we.damageArea(area, damage);
		}
	}

	public void killEntity(WorldEntity we) {
		currentGameWorld.killEntity(we);
	}

	public void reset() {

		player = null;
		currentGameWorld = null;
		inGameSC = null;
		StData.currentGC.currentLT.clear();

		LStData.healthStat = LStData.maxHealth;
		LStData.ammoStat = LStData.maxAmmo;
	}

	private class continueWatcher extends PBGTask {

		private int pdelay = 100;

		public continueWatcher() {
			this.TaskPriority = PRIORITY_HIGH;

		}

		@Override
		public boolean isReady() {
			return pdelay > -1;
		}

		@Override
		public void perform() {

			if (pdelay == 0) {
				if (InHandler.instance.isKeyPressed(KeyEvent.VK_P)) {
					StData.gameFreeze = false;
					LStData.currentStatus = 202;
					pdelay = -2;
				}
			} else {
				pdelay--;
			}
		}
	}
}
