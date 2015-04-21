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
public class World13 extends PWorld {

	@Override
	public String getTexID() {
		return "WORLD";
	}

	@Override
	public void loadYerself(GameWorld target) {
		StData.generalBGT.LPTasks.add(new LoadBasicTexture("world/level13.png", "WORLD"));
		StData.generalBGT.LPTasks.add(new LoadBasicTexture("world/lvl-013_DATA.png", "WORLDD"));

		target.entities.add(new WorldEntity(new PotatoDATA(140, 97), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(173, 129), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(233, 161), new Pizza(), 15, 34));
		target.entities.add(new WorldEntity(new PotatoDATA(375, 161), new Grapes(), 15, 26));
		target.entities.add(new WorldEntity(new PotatoDATA(455, 97), new Pizza(), 15, 34));
		target.entities.add(new WorldEntity(new PotatoDATA(550, 161), new Pizza(), 15, 34));
		target.entities.add(new WorldEntity(new PotatoDATA(496, 97), new Grapes(), 15, 26));
		target.entities.add(new WorldEntity(new PotatoDATA(650, 161), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(690, 161), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(800, 161), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(895, 161), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new OnionDATA(1000, 100), new Onion(), 15, 30));
		target.entities.add(new WorldEntity(new OnionDATA(752, 193), new Onion(), 15, 30));
		target.entities.add(new WorldEntity(new OnionDATA(848, 193), new Onion(), 15, 30));
		target.entities.add(new WorldEntity(new OnionDATA(946, 193), new Onion(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(352, 385), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(450, 385), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(527, 352), new Pizza(), 15, 34));
		target.entities.add(new WorldEntity(new PotatoDATA(591, 352), new Pizza(), 15, 34));
		target.entities.add(new WorldEntity(new PotatoDATA(687, 352), new Pizza(), 15, 34));
		target.entities.add(new WorldEntity(new PotatoDATA(750, 352), new Pizza(), 15, 34));
		target.entities.add(new WorldEntity(new PotatoDATA(848, 352), new Pizza(), 15, 34));
		target.entities.add(new WorldEntity(new PotatoDATA(910, 352), new Pizza(), 15, 34));

		target.wp.inRoomY = 60;
		target.wp.inRoomX = 11;

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

