package dcode.games.uEngine2.games.ld32.world;

import dcode.games.uEngine2.BGTasks.internalTasks.LoadBasicTexture;
import dcode.games.uEngine2.GFX.sprites.Sprite;
import dcode.games.uEngine2.LOGIC.ILogicTask;
import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.games.ld32.LStData;

import java.awt.*;

/**
 * Created by dusakus on 18.04.15.
 */
public class WorldPlayer extends Sprite {

	public int inRoomX = 100;
	public int inRoomY = 100;
	public int commandGoLeft = 0;
	public int commandGoRight = 0;
	public int commandGoJump = 0;
	public boolean hazPhysics = false;
	private String itemMod = "";
	private int animId = 1;             // 1-4 facing left, 11-14 facing right
	private int animFrame = 1;
	private int lastInRoomX = 100;
	private int lastInRoomY = 100;
	private int animDelay = 3;

	public WorldPlayer(int x, int y) {
		inRoomX = x;
		inRoomY = y;

		lastInRoomX = x;
		lastInRoomY = y;
	}

	public static void loadTextures(PlayerItem item) {
		String s = "";
		if (item != null && item.isMainItem) s = item.playerModifier;
		for (int i = 1; i < 5; i++) {
			StData.generalBGT.LPTasks.add(new LoadBasicTexture("sprite/player/PLA" + s + "FRAME" + i + ".png", "PLA" + s + i));             //Load textures for both facings
			StData.generalBGT.LPTasks.add(new LoadBasicTexture("sprite/player/PLA" + s + "FRAME" + (i + 10) + ".png", "PLA" + s + (i + 10)));
		}
	}

	@Override
	public int getX() {
		return super.getX() - 15;
	}

	@Override
	public int getY() {
		return super.getY() - 31;
	}

	@Override
	public Image getCustomTexture() {
		return StData.resources.grf.getTexture("PLA" + itemMod + animId);
	}

	@Override
	public boolean doCustomRender() {
		return false;
	}

	@Override
	public void customRender(Graphics2D G) {

	}

	public void goLeft() {
		if (commandGoLeft > 5) return;
		StData.currentGC.currentLT.registerBasic(new playerMotionPerformer(this, -1));
		commandGoLeft++;
	}

	public void goRight() {
		if (commandGoRight > 5) return;
		StData.currentGC.currentLT.registerBasic(new playerMotionPerformer(this, 1));
		commandGoRight++;

	}

	public void goJump() {
		if (commandGoJump > 5) return;
		StData.currentGC.currentLT.registerBasic(new playerJumpLogic(this, -1, 1));
		commandGoJump++;

	}

	public void updateCoords(int x, int y) {
		//TODO: proper movement, with collisions

		inRoomX += x;
		inRoomY += y;
	}

	public void updatePlayer() {
		if (animDelay == 0) {
			if (lastInRoomX != inRoomX || lastInRoomY != inRoomY) {
				animFrame++;
				if (animFrame > 4) animFrame = 1;
				if (lastInRoomX < inRoomX) animId = animFrame + 10;
				if (lastInRoomX > inRoomX) animId = animFrame;
				lastInRoomX = inRoomX;
				lastInRoomY = inRoomY;
				animDelay = 3;
			}
		} else {
			animDelay--;
		}

		if (!hazPhysics) {
			StData.currentGC.currentLT.registerBasic(new playerPhysicsLogic(this, 1, 2));
			hazPhysics = true;
		}

		this.x = inRoomX - LStData.renderOffsetX;
		this.y = inRoomY - LStData.renderOffsetY;
	}

	private boolean checkCollisionAt(int x, int y) {
		return LStData.GL.getColisionAt(x, y);
	}

	private void tryMoveX(int dir) {
		//TODO: Check collision first
		if (!checkCollisionAt(inRoomX + dir, inRoomY))
			this.inRoomX += dir;

	}

	private void tryMoveY(int dir) {
		//TODO: Check collision first
		if (!checkCollisionAt(inRoomX, inRoomY + dir))
			this.inRoomY += dir;

	}

	private class playerMotionPerformer implements ILogicTask {
		private int tickCount = 3;
		private WorldPlayer wp;
		private int dir;

		public playerMotionPerformer(WorldPlayer wp, int dir) {
			this.wp = wp;
			this.dir = dir;
		}

		@Override
		public boolean isReady() {
			return true;
		}

		@Override
		public void perform() {
			if (tickCount > 1) {
				wp.tryMoveX(dir);
				if (tickCount == 2) wp.tryMoveX(dir);
				tickCount--;
			} else {
				if (dir > 0) wp.commandGoRight--;
				else wp.commandGoLeft--;
			}
		}

		@Override
		public boolean doRepeat() {
			return tickCount > 0;
		}
	}

	private class playerPhysicsLogic implements ILogicTask {

		private WorldPlayer wp;
		private int dir;
		private int steps;

		public playerPhysicsLogic(WorldPlayer wp, int dir, int steps) {
			this.wp = wp;
			this.dir = dir;
			this.steps = steps;
		}

		@Override
		public boolean isReady() {
			return true;
		}

		@Override
		public void perform() {
			for (int i = 0; i < steps; i++) {
				wp.tryMoveY(dir);
			}
		}

		@Override
		public boolean doRepeat() {
			return wp.hazPhysics;
		}
	}

	private class playerJumpLogic implements ILogicTask {

		private int tickCount = 5;
		private WorldPlayer wp;
		private int dir;
		private int steps;

		public playerJumpLogic(WorldPlayer wp, int dir, int steps) {
			this.wp = wp;
			this.dir = dir;
			this.steps = steps;
		}

		@Override
		public boolean isReady() {
			return true;
		}

		@Override
		public void perform() {
			for (int i = 0; i < steps; i++) {
				wp.tryMoveY(dir);
			}
			tickCount--;
			if (tickCount == 0) {
				wp.commandGoJump--;
			}
		}

		@Override
		public boolean doRepeat() {
			return tickCount >= 0;
		}
	}

}
