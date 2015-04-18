package dcode.games.uEngine2.games.ld32.entity;

import dcode.games.uEngine2.games.ld32.world.WorldEntity;

import java.awt.*;

/**
 * Created by dusakus on 18.04.15.
 */
public class FolowerEntity implements IEntityData, IEntityLogic {
	@Override
	public int getType() {
		return WorldEntity.TYPE_FOLLOWER;
	}

	@Override
	public Point getInitialLocation() {
		return new Point(150, 150);
	}

	@Override
	public String getTextureId() {
		return "TESTENTITY";
	}

	@Override
	public int getDepth() {
		return 10;
	}

	@Override
	public boolean shouldCheck() {
		return true;
	}

	@Override
	public void update(WorldEntity we) {
		//TODO: follower test
	}
}
