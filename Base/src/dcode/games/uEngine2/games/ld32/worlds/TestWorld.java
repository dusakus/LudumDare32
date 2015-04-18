package dcode.games.uEngine2.games.ld32.worlds;

import dcode.games.uEngine2.BGTasks.internalTasks.LoadBasicTexture;
import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.games.ld32.entity.IEntityData;
import dcode.games.uEngine2.games.ld32.entity.IEntityLogic;
import dcode.games.uEngine2.games.ld32.world.GameWorld;
import dcode.games.uEngine2.games.ld32.world.WorldEntity;

import java.awt.*;

/**
 * Created by dusakus on 18.04.15.
 */
public class TestWorld extends PWorld{


	@Override
	public String getTexID() {
		return "WORLD";
	}

	@Override
	public void loadYerself(GameWorld target) {
		target.entities.add(new TestEntity(new TestEntityData(), new TestEntityLogic()));
		StData.generalBGT.LPTasks.add(new LoadBasicTexture("world/TESTWORLD.png", "WORLD"));
	}

	@Override
	public boolean checkUnlocked(int RColor) {
		return true;
	}

	@Override
	public void triggerEvent(Color data) {
		if(data.getBlue() == 240){
			StData.LOG.println("TRIGGERED");
		}
	}

	private class TestEntity extends WorldEntity {

		public TestEntity(IEntityData ed, IEntityLogic el) {
			super(ed, el);
		}
	}

	private class TestEntityData implements IEntityData {

		@Override
		public int getType() {
			return 10;
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
	}

	private class TestEntityLogic implements IEntityLogic {
		@Override
		public boolean shouldCheck() {
			return false;
		}

		@Override
		public void update(WorldEntity we) {

		}
	}
}
