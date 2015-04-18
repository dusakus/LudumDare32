package dcode.games.uEngine2.games.ld32.entity;

import dcode.games.uEngine2.games.ld32.world.WorldEntity;

/**
 * Created by dusakus on 18.04.15.
 */
public interface IEntityLogic {
	boolean shouldCheck();
	void update(WorldEntity we);
}
