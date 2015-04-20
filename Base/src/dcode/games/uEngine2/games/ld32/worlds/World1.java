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
 * Created by dusakus on 18.04.15.
 */
public class World1 extends PWorld {

	private WorldEntity wall;

	@Override
	public String getTexID() {
		return "WORLD";
	}

	@Override
	public void loadYerself(GameWorld target) {
		StData.generalBGT.LPTasks.add(new LoadBasicTexture("world/lvl-001.png", "WORLD"));
		StData.generalBGT.LPTasks.add(new LoadBasicTexture("world/lvl-001_DATA.png", "WORLDD"));
		StData.generalBGT.LPTasks.add(new LoadBasicTexture("world/lvl-001_wall.png", "WALL"));

		wall = new WorldEntity(new wallDATA(), null, 0, 0);
		wall.texKey = "WALL";


		target.entities.add(new WorldEntity(new PotatoDATA(200, 100), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(250, 100), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(300, 100), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(350, 100), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(400, 100), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(450, 100), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(500, 100), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(550, 100), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(600, 100), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(650, 100), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(700, 100), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(750, 100), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(800, 100), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(850, 100), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(900, 100), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(950, 100), new Potato(), 15, 30));
		target.entities.add(new WorldEntity(new OnionDATA(1000, 100), new Onion(), 15, 30));
		target.entities.add(new WorldEntity(new PotatoDATA(1050, 100), new Grapes(), 15, 26));
		target.entities.add(new WorldEntity(new PotatoDATA(1050, 100), new Pizza(), 15, 34));
		target.entities.add(wall);

		target.wp.inRoomY = 250;
		target.wp.inRoomX = 100;

	}

	@Override
	public boolean checkUnlocked(int RColor) {
		return false;
	}

	@Override
	public void triggerEvent(Color data) {
		switch (data.getBlue() - 100) {
			case 1:
				StData.LOG.println("World1: building enterance");
				break;
			case 2:
				StData.LOG.println("World1: car doors");
				break;
			case 3:
				StData.LOG.println("World1: ending low");
				break;
			case 4:
				StData.LOG.println("World1: ending high");
				LStData.currentStatus = 209;
				break;
			case 5:
				StData.LOG.println("World1: beginning");
				break;
			case 11:
				wall.setY(400);
				break;
			case 12:
				wall.setY(112);
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

	private class wallDATA implements IEntityData {

		@Override
		public int getType() {
			return WorldEntity.TYPE_STATIC;
		}

		@Override
		public Point getInitialLocation() {
			return new Point(721, 112);
		}

		@Override
		public String getTextureId() {
			return "WALL";
		}

		@Override
		public int getDepth() {
			return 0;
		}

		@Override
		public int getHealth() {
			return Integer.MAX_VALUE;
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

