package dcode.games.uEngine2.games.ld32.worlds;

import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.games.ld32.world.GameWorld;
import dcode.games.uEngine2.games.ld32.world.WorldEntity;

import java.awt.*;

/**
 * Created by dusakus on 18.04.15.
 */
public class TestWorld extends PWorld{


	@Override
	public String getTexID() {
		return "TESTWORLD";
	}

	@Override
	public void loadYerself(GameWorld target) {
		target.entities.add(new TestEntity());
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

	}
}
