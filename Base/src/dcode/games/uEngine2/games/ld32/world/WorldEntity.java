package dcode.games.uEngine2.games.ld32.world;

import dcode.games.uEngine2.GFX.sprites.Sprite;
import dcode.games.uEngine2.LOGIC.ILogicTask;
import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.games.ld32.LStData;
import dcode.games.uEngine2.games.ld32.entity.IEntityData;
import dcode.games.uEngine2.games.ld32.entity.IEntityLogic;

import java.awt.*;

/**
 * Created by dusakus on 18.04.15.
 */
public class WorldEntity extends Sprite {

	public static final int TYPE_INVENTORY = 10;
	public static final int TYPE_DEVICE = 11;
	public static final int TYPE_STATIC = 12;
	public static final int TYPE_FOLLOWER = 13;

	public static final int TYPE_ENEMY_WALKING = 20;
	public String texKey = "MISS";
	public int textureVariation = 0;
	public boolean canJumpNow = true;
	IEntityData entitydata;
	IEntityLogic entityLogic;
	private int SpriteId = -1;
	private boolean active = true;
	private int spriteOffsetY;
	private int spriteOffsetX;
	private int errorCount = 0;
	private int health = 100;


	public WorldEntity(IEntityData ed, IEntityLogic el, int spriteOffsetX, int spriteOffsetY) {
		entitydata = ed;
		entityLogic = el;
		health = ed.getHealth();
		this.spriteOffsetY = spriteOffsetY;
		this.spriteOffsetX = spriteOffsetX;
		if (entityLogic == null) entityLogic = new DummyELogic();
		texKey = ed.getTextureId();
		x = ed.getInitialLocation().x;
		y = ed.getInitialLocation().y;
		z = ed.getDepth();
	}

	public void register() {
		for (int i = 10; i < 100; i++) {
			if (LStData.GL.getSContent().sprites[i] == null) {
				LStData.GL.getSContent().sprites[i] = this;
				SpriteId = i;
				entityLogic.initializeLogic();
				StData.LOG.println("[WE] registering sprite at " + i);  //It's registered, why no render?
				if (entitydata.getType() != TYPE_STATIC)
					StData.currentGC.currentLT.registerBasic(new entityPhysicsLogic(this, 1, 2));
				break;
			}
		}
		if (SpriteId >= 10) { //That was stupid...
			LStData.GL.getSContent().sprites_middle[SpriteId] = SpriteId;
		} else {
			StData.LOG.println("NO FREE SPRITE SOCKETS!!!", "E3");
		}
	}

	public void unregister() {
		if (SpriteId < 10) return;
		this.entityLogic.unload();
		LStData.GL.getSContent().sprites[SpriteId] = null;
		active = false;
	}

	public void tick() {
		if (entityLogic.shouldCheck()) entityLogic.update(this);
	}

	@Override
	public int getX() {
		return x - LStData.renderOffsetX - spriteOffsetX;
	}

	@Override
	public int getY() {
		return y - LStData.renderOffsetY - spriteOffsetY;
	}

	@Override
	public Image getCustomTexture() {
		if (textureVariation > 0)
			return StData.resources.grf.getTexture(texKey + textureVariation);
		else return StData.resources.grf.getTexture(texKey);
	}

	@Override
	public boolean doCustomRender() {
		return false;
	}

	@Override
	public void customRender(Graphics2D G) {

	}

	public void updateCoords(int x, int y) {
		//TODO: proper movement, with collisions

		this.x += x;
		this.y += y;
	}

	public void tryMoving(int direction, int steps, int duration) {
		StData.currentGC.currentLT.registerBasic(new entityMotionPerformer(this, direction, steps, duration));
	}

	public void jump(int i, int i1, int i2) {
		if (canJumpNow)
			StData.currentGC.currentLT.registerBasic(new entityJumpLogic(this, i, i1, i2));
	}

	private boolean checkCollisionAt(int x, int y) {
		try {
			boolean c = LStData.GL.getColisionAt(x, y, !canJumpNow);
			errorCount = 0;
			return c;
		} catch (Exception e) {
			errorCount++;
			if (errorCount > 60) unregister();
		}
		return false;
	}

	private void tryMoveX(int dir) {
		if (!checkCollisionAt(x + dir, y))
			this.x += dir;
	}

	private void tryMoveY(int dir) {
		if (!checkCollisionAt(x, y + dir)) {
			canJumpNow = false;
			this.y += dir;
		} else canJumpNow = true;
	}

	public void damageArea(Rectangle area, int damage) {
		if (this.entitydata.getType() != TYPE_STATIC && area.contains(this.x, this.y)) {
			health -= damage;
		}
	}

	public int getIWX() {
		return x;
	}

	public int getIWY() {
		return y;
	}

	private class DummyELogic implements IEntityLogic {
		@Override
		public void initializeLogic() {

		}

		@Override
		public boolean shouldCheck() {
			return false;
		}

		@Override
		public void update(WorldEntity we) {

		}

		@Override
		public void unload() {

		}
	}

	private class entityMotionPerformer implements ILogicTask {
		private int steps;
		private int tickCount = 3;
		private WorldEntity wp;
		private int dir;

		public entityMotionPerformer(WorldEntity wp, int dir, int steps, int ticks) {
			this.wp = wp;
			this.dir = dir;
			this.steps = steps;
			tickCount = ticks;
		}

		@Override
		public boolean isReady() {
			return true;
		}

		@Override
		public void perform() {
			if (tickCount > 0) {
				for (int i = 0; i < steps; i++) {
					wp.tryMoveX(dir);
				}
				tickCount--;
			}
		}

		@Override
		public boolean doRepeat() {
			return tickCount > 0;
		}
	}

	private class entityPhysicsLogic implements ILogicTask {

		private WorldEntity wp;
		private int dir;
		private int steps;

		public entityPhysicsLogic(WorldEntity wp, int dir, int steps) {
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
			return wp.active;
		}
	}

	private class entityJumpLogic implements ILogicTask {

		private int tickCount = 5;
		private WorldEntity wp;
		private int dir;
		private int steps;

		public entityJumpLogic(WorldEntity wp, int dir, int steps, int ticks) {
			this.wp = wp;
			this.dir = dir;
			this.steps = steps;
			tickCount = ticks;
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
			}
		}

		@Override
		public boolean doRepeat() {
			return tickCount >= 0;
		}
	}

}
