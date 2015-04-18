package dcode.games.uEngine2.games.ld32.world;

import dcode.games.uEngine2.GFX.sprites.Sprite;
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

	protected int textureVariation = 0;
	public String texKey = "MISS";

	IEntityData entitydata;
	IEntityLogic entityLogic;
	private int SpriteId = -1;


	public WorldEntity(IEntityData ed, IEntityLogic el) {
		entitydata = ed;
		entityLogic = el;
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
				break;
			}
		}
		if(SpriteId > 10){
			LStData.GL.getSContent().sprites_middle[SpriteId] = SpriteId;
		} else {
			StData.LOG.println("NO FREE SPRITE SOCKETS!!!", "E3");
		}
	}

	public void unregister() {
		if(SpriteId < 10) return;
		LStData.GL.getSContent().sprites[SpriteId] = null;
	}

	public void tick() {
		if (entityLogic.shouldCheck()) entityLogic.update(this);
	}

	@Override
	public int getX() {
		return x - LStData.renderOffsetX;
	}

	@Override
	public int getY() {
		return y - LStData.renderOffsetY;
	}

	@Override
	public Image getCustomTexture() {
		return StData.resources.grf.getTexture(texKey + textureVariation);
	}

	@Override
	public boolean doCustomRender() {
		return false;
	}

	@Override
	public void customRender(Graphics2D G) {

	}

	private class DummyELogic implements IEntityLogic {
		@Override
		public boolean shouldCheck() {
			return false;
		}

		@Override
		public void update(WorldEntity we) {

		}
	}
}
