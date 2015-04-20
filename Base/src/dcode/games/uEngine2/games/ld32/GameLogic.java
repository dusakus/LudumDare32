package dcode.games.uEngine2.games.ld32;

import dcode.games.uEngine2.GFX.ScreenContent;
import dcode.games.uEngine2.GFX.layers.ClearColorLayer;
import dcode.games.uEngine2.GFX.postproc.PP_scaleblur;
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

	private ScreenContent inGameSC;
	private GameWorld currentGameWorld;
	private WorldPlayer player;

	private int currentLevel = 1;

	@Override
	public boolean isReady() {
		return LStData.currentMode == LStData.MODE_GAME_PLAY;
	}

	@Override
	public void perform() {
		switch (currentStatus) {


			case 201:
				updateWorldOffset();
				currentGameWorld.tick();
				inputTick();
				if (invincTicks > 0) invincTicks--;
				break;
			case 1:
				LOG.println("[GL] Entering game_play environment");
				GL = this;
				currentStatus++;
				break;
			case 2:
				if (inGameSC != null) currentStatus = 109;
				else currentStatus = 11;
				break;
			case 11:
				LOG.println("[GL] inGameSC is null, initializing...", "D");
				inGameSC = new ScreenContent();
				inGameSC.postProcessors[0] = new PP_scaleblur(1.0085f);
				currentStatus++;
				break;
			case 12:
				inGameSC.layers_Background.add(new ClearColorLayer(new Color(24, 40, 84)));
				inGameSC.layers_Background.add(new ParalaxBackground());
				inGameSC.layers_Center.add(new Layer_WORLDDraw());
				inGameSC.layers_Overlay.add(new Layer_StatusOverlay());
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
		if (InHandler.instance.isKeyPressed_SHIFT()) {
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
}
