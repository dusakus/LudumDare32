package dcode.games.uEngine2.games.ld32.worlds;

import dcode.games.uEngine2.BGTasks.internalTasks.LoadBasicTexture;
import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.games.ld32.LStData;
import dcode.games.uEngine2.games.ld32.world.GameWorld;

import java.awt.*;

/**
 * Created by dusakus on 20.04.15.
 */
public class World14 extends PWorld {

	@Override
	public String getTexID() {
		return "WORLD";
	}

	@Override
	public void loadYerself(GameWorld target) {
		StData.generalBGT.LPTasks.add(new LoadBasicTexture("world/lvl-014.png", "WORLD"));
		StData.generalBGT.LPTasks.add(new LoadBasicTexture("world/lvl-014_DATA.png", "WORLDD"));


		target.wp.inRoomY = 100;
		target.wp.inRoomX = 100;

	}

	@Override
	public boolean checkUnlocked(int RColor) {
		return false;
	}

	@Override
	public void triggerEvent(Color data) {
		switch (data.getBlue() - 100) {
			case 0:
				LStData.currentStatus = 209;
				break;
			case 1:
				LStData.currentStatus = 209;
				LStData.GL.currentLevel++;
				break;
			case 2:
				LStData.GL.getPlayer().hazPhysics = true;
				break;
			case 10:
				LStData.GL.getPlayer().getDamaged(10);
				break;
			case 8:
				LStData.healthStat = 0;
				LStData.GL.getPlayer().getDamaged(9999999);

		}

	}
}