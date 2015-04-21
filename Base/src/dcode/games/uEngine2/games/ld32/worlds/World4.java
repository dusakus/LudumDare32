package dcode.games.uEngine2.games.ld32.worlds;

import dcode.games.uEngine2.BGTasks.internalTasks.LoadBasicTexture;
import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.games.ld32.LStData;
import dcode.games.uEngine2.games.ld32.entity.IEntityData;
import dcode.games.uEngine2.games.ld32.entity.enemy.Grapes;
import dcode.games.uEngine2.games.ld32.entity.enemy.Onion;
import dcode.games.uEngine2.games.ld32.entity.enemy.Pizza;
import dcode.games.uEngine2.games.ld32.entity.enemy.Potato;
import dcode.games.uEngine2.games.ld32.world.GameWorld;
import dcode.games.uEngine2.games.ld32.world.WorldEntity;

import java.awt.*;

/**
 * Created by dusakus on 20.04.15.
 */
public class World4 extends PWorld {
	@Override
	public String getTexID() {
		return "WORLD";
	}

	@Override
	public void loadYerself(GameWorld target) {
		StData.generalBGT.LPTasks.add(new LoadBasicTexture("world/lvl-004.png", "WORLD"));
		StData.generalBGT.LPTasks.add(new LoadBasicTexture("world/lvl-004_DATA.png", "WORLDD"));

		target.entities.add(new WorldEntity(new PotatoDATA(243, 267), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(283, 267), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(323, 267), new Grapes(), 15, 26));
		target.entities.add(new WorldEntity(new PotatoDATA(363, 267), new Grapes(), 15, 26));
		target.entities.add(new WorldEntity(new PotatoDATA(403, 267), new Potato(), 15, 30));

		target.entities.add(new WorldEntity(new PotatoDATA(483, 267), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(523, 267), new Pizza(), 15, 34));
		target.entities.add(new WorldEntity(new PotatoDATA(563, 267), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(603, 267), new Grapes(), 15, 26));

		target.entities.add(new WorldEntity(new OnionDATA(920, 277), new Onion(), 15, 30));
		target.entities.add(new WorldEntity(new OnionDATA(1120, 240), new Onion(), 15, 30));
		target.entities.add(new WorldEntity(new OnionDATA(1320, 244), new Onion(), 15, 30));

		target.entities.add(new WorldEntity(new PotatoDATA(1000, 254), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(1056, 242), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(1070, 222), new Grapes(), 15, 26));

		target.entities.add(new WorldEntity(new PotatoDATA(1600, 277), new Grapes(), 15, 26));
		target.entities.add(new WorldEntity(new PotatoDATA(1640, 277), new Pizza(), 15, 34));
		target.entities.add(new WorldEntity(new PotatoDATA(1680, 277), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(1720, 277), new Grapes(), 15, 26));
		target.entities.add(new WorldEntity(new PotatoDATA(1760, 277), new Pizza(), 15, 34));
		target.entities.add(new WorldEntity(new PotatoDATA(1800, 277), new Potato(), 15, 30));

		target.entities.add(new WorldEntity(new PotatoDATA(2176, 268), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(2236, 260), new Potato(), 15, 30));

		target.wp.inRoomY = 275;
		target.wp.inRoomX = 30;

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
		}

	}


	private class PotatoDATA implements IEntityData {

		Point p;

		public PotatoDATA(int x, int y) {
			p = new Point(x, y);
		}

		@Override
		public int getType() {
			return 0;
		}

		@Override
		public Point getInitialLocation() {
			return p;
		}

		@Override
		public String getTextureId() {
			return "EnPOT";
		}

		@Override
		public int getDepth() {
			return 0;
		}

		@Override
		public int getHealth() {
			return 200;
		}
	}

	private class OnionDATA implements IEntityData {
		Point p;

		public OnionDATA(int i, int i1) {
			p = new Point(i, i1);
		}

		@Override
		public int getType() {
			return 0;
		}

		@Override
		public Point getInitialLocation() {
			return p;
		}

		@Override
		public String getTextureId() {
			return "EnONI";
		}

		@Override
		public int getDepth() {
			return 0;
		}

		@Override
		public int getHealth() {
			return 500;
		}
	}

}
